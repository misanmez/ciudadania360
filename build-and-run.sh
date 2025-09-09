#!/usr/bin/env bash
# Script para construir y ejecutar Ciudadanía 360 (simplificado)
# Uso: ./build-and-run.sh [dev|docker|prod|clean|reset|help]

set -Eeuo pipefail

# ===== Colores =====
PURPLE='\033[35m'
BLUE='\033[34m'
GREEN='\033[32m'
YELLOW='\033[33m'
RED='\033[31m'
NC='\033[0m'

echo -e "${PURPLE}🏗️  Ciudadanía 360 - Script de construcción y ejecución${NC}"
echo -e "${PURPLE}==================================================${NC}"

# ===== Config =====
POSTGRES_CONTAINER="postgres_ciudadania360"
DB_USER="ciudadania"
DB_NAME="ciudadania360"
WAIT_TIMEOUT=90  # segundos

# ===== Utilidades =====
die() { echo -e "${RED}❌ $*${NC}"; exit 1; }
info() { echo -e "${BLUE}$*${NC}"; }
warn() { echo -e "${YELLOW}⚠️  $*${NC}"; }
ok()   { echo -e "${GREEN}✅ $*${NC}"; }

# ===== Docker Compose =====
resolve_compose() {
  if docker compose version >/dev/null 2>&1; then
    COMPOSE=(docker compose)
    ok "Usando Docker Compose v2 (subcomando: 'docker compose')"
  elif command -v docker-compose >/dev/null 2>&1; then
    COMPOSE=(docker-compose)
    warn "Usando Docker Compose v1 (subcomando: 'docker-compose'). 'depends_on: condition: service_healthy' será ignorado."
  else
    die "No se encontró Docker Compose. Instala el plugin v2 o docker-compose v1."
  fi
}

show_help() {
  cat <<EOF
Uso: $0 [dev|docker|prod|clean|reset|help]

Opciones:
  dev     - Ejecutar en modo desarrollo (solo Postgres en Docker; ejecuta Ciudadanía 360 localmente)
  docker  - Construir y ejecutar Ciudadanía 360 en Docker
  prod    - Construir para producción con Maven
  clean   - Parar y eliminar contenedores/volúmenes y limpiar Maven
  reset   - ELIMINA TODO Docker y levanta el stack limpio
  help    - Mostrar esta ayuda
EOF
}

check_docker() {
  info "🔍 Verificando Docker..."
  command -v docker >/dev/null || die "Docker no está instalado o no está en PATH."
  docker ps >/dev/null 2>&1 || die "El demonio de Docker no responde. ¿Está iniciado?"
  ok "Docker OK"
}

check_maven_if_needed() {
  local mode="$1"
  if [[ "$mode" == "dev" || "$mode" == "prod" ]]; then
    info "🔍 Verificando Maven (requerido para modo ${mode})..."
    command -v mvn >/dev/null || die "Maven no está instalado. Instálalo: sudo apt install maven"
    ok "Maven OK"
  fi
}

# ===== Esperar a que la BD esté lista =====
wait_for_db() {
  local container="${1:-$POSTGRES_CONTAINER}"
  local timeout="${2:-$WAIT_TIMEOUT}"
  info "⏳ Esperando a que la base de datos esté lista (timeout: ${timeout}s)..."

  local start_ts now status
  start_ts=$(date +%s)

  while true; do
    if docker ps -a --format '{{.Names}}' | grep -qw "$container"; then
      status=$(docker inspect --format='{{.State.Health.Status}}' "$container" 2>/dev/null || echo "unknown")
      if [[ "$status" == "healthy" ]]; then
        ok "Base de datos lista (health=healthy)"
        return 0
      fi
      if docker exec "$container" pg_isready -U "$DB_USER" -d "$DB_NAME" >/dev/null 2>&1; then
        ok "Base de datos lista (pg_isready OK)"
        return 0
      fi
    fi

    now=$(date +%s)
    if (( now - start_ts > timeout )); then
      docker logs --tail 80 "$container" || true
      die "La base de datos no respondió a tiempo."
    fi
    sleep 2
  done
}

# ===== Construir proyecto =====
build_project() {
  info "🔨 Construyendo proyecto con Maven (host)..."
  mvn clean install -DskipTests
  ok "Proyecto construido correctamente"
}

# ===== Modo desarrollo =====
run_dev() {
  info "🚀 Modo desarrollo: solo Postgres en Docker"
  "${COMPOSE[@]}" up -d postgres
  wait_for_db "$POSTGRES_CONTAINER" "$WAIT_TIMEOUT"
  echo -e "${BLUE}📊 Ahora puedes levantar Ciudadanía 360 localmente:${NC}"
  echo "   mvn spring-boot:run -pl ciudadania360-app -Dspring-boot.run.profiles=docker"
}

# ===== Modo Docker =====
run_docker() {
  info "🐳 Construyendo y ejecutando Ciudadanía 360 en Docker..."
  "${COMPOSE[@]}" up -d --build
  wait_for_db "$POSTGRES_CONTAINER" "$WAIT_TIMEOUT"
  ok "Ciudadanía 360 debería estar corriendo."
  "${COMPOSE[@]}" ps
  echo -e "${YELLOW}Endpoint principal:${NC} http://localhost:8080"
  echo -e "${YELLOW}📖 Swagger UI:${NC} http://localhost:8080/swagger-ui.html"
}

# ===== Modo producción =====
build_prod() {
  info "🏭 Construyendo para producción con Maven (host)..."
  mvn clean verify
  ok "Construcción de producción completada. Revisa los /target de cada módulo."
}

# ===== Limpiar recursos =====
cleanup() {
  info "🧹 Limpiando recursos del proyecto (compose down -v + mvn clean)..."
  resolve_compose || true
  if [[ "${COMPOSE[*]-}" != "" ]]; then
    "${COMPOSE[@]}" down -v || true
  fi
  mvn -q clean || true
  ok "Limpieza del proyecto completada."
}

# ===== Reset total Docker =====
reset_docker() {
  warn "🔥 VAS A ELIMINAR TODO DOCKER de esta máquina (contenedores, imágenes, volúmenes, redes y caché)."
  read -r -p "Escribe 'yes' para continuar: " confirm
  [[ "${confirm}" != "yes" ]] && { info "Operación cancelada."; return 0; }

  info "🧹 Limpiando Docker..."
  docker ps -q | xargs -r docker stop
  docker ps -aq | xargs -r docker rm -f
  docker images -q | xargs -r docker rmi -f
  docker volume ls -q | xargs -r docker volume rm -f
  docker network ls --format '{{.ID}} {{.Name}}' | awk '$2!="bridge" && $2!="host" && $2!="none"{print $1}' | xargs -r docker network rm
  docker builder prune -af || true
  docker system prune -af --volumes || true
  ok "Docker quedó limpio."

  info "🐳 Levantando solo Postgres para desarrollo..."
  "${COMPOSE[@]}" up -d postgres
  wait_for_db "$POSTGRES_CONTAINER" "$WAIT_TIMEOUT"

  if [[ -f "ciudadania360-app/Dockerfile" ]]; then
    info "📦 Dockerfile encontrado, construyendo ciudadania360-app..."
    "${COMPOSE[@]}" up -d --build ciudadania360-app
    ok "ciudadania360-app levantado en Docker."
  else
    warn "⚠️ No se encontró Dockerfile en ciudadania360-app. Levanta la app con Maven localmente:"
    echo "   mvn spring-boot:run -pl ciudadania360-app -Dspring-boot.run.profiles=docker"
  fi
}

# ===== Main =====
MODE="${1:-dev}"

check_docker
resolve_compose
case "$MODE" in
  help|-h|--help) show_help ;;
  dev)            check_maven_if_needed "dev"; run_dev ;;
  docker)         run_docker ;;
  prod)           check_maven_if_needed "prod"; build_prod ;;
  clean)          cleanup ;;
  reset)          reset_docker ;;
  *)              die "Opción no válida: $MODE. Usa: dev | docker | prod | clean | reset | help" ;;
esac

echo -e "\n${GREEN}🎉 ¡Proceso completado!${NC}"
