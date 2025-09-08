#!/bin/bash

# Script para construir y ejecutar Ciudadanía 360 en Linux/Mac
# Uso: ./build-and-run.sh [dev|docker|prod|clean|help]

set -e

echo "🏗️  Ciudadanía 360 - Script de construcción y ejecución"
echo "=================================================="

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [dev|docker|prod|clean|help]"
    echo ""
    echo "Opciones:"
    echo "  dev     - Ejecutar en modo desarrollo (solo BD)"
    echo "  docker  - Construir y ejecutar todos los servicios en Docker"
    echo "  prod    - Construir para producción"
    echo "  clean   - Limpiar recursos (incluye volúmenes de BD)"
    echo "  help    - Mostrar esta ayuda"
    echo ""
}

# Función para verificar dependencias
check_dependencies() {
    echo "🔍 Verificando dependencias..."

    for cmd in docker docker-compose mvn; do
        if ! command -v $cmd &> /dev/null; then
            echo "❌ $cmd no está instalado"
            exit 1
        fi
    done

    echo "✅ Todas las dependencias están instaladas"
}

# Función para construir el proyecto
build_project() {
    echo "🔨 Construyendo proyecto..."
    mvn clean install -DskipTests
    echo "✅ Proyecto construido correctamente"
}

# Función para esperar a que la BD esté lista
wait_for_db() {
    echo "⏳ Esperando a que la base de datos esté lista..."
    for i in {1..30}; do
        if docker exec postgres_ciudadania360 pg_isready -U ciudadania &>/dev/null; then
            echo "✅ Base de datos lista"
            return 0
        fi
        sleep 2
    done
    echo "❌ La base de datos no respondió a tiempo"
    exit 1
}

# Función para ejecutar en modo desarrollo
run_dev() {
    echo "🚀 Iniciando modo desarrollo..."

    # Levantar solo la base de datos
    docker-compose up -d postgres
    wait_for_db

    echo "📊 Puedes ejecutar los subsistemas individualmente con:"
    echo "   mvn spring-boot:run -pl subsistema-ciudadano"
    echo "   mvn spring-boot:run -pl subsistema-tramitacion"
    echo "   mvn spring-boot:run -pl subsistema-comunicaciones"
    echo "   mvn spring-boot:run -pl subsistema-videoconferencia"
    echo "   mvn spring-boot:run -pl subsistema-informacion"
    echo "   mvn spring-boot:run -pl subsistema-roles"
    echo "   mvn spring-boot:run -pl subsistema-ia"
    echo "   mvn spring-boot:run -pl subsistema-interno"
}

# Función para ejecutar en Docker
run_docker() {
    echo "🐳 Construyendo y ejecutando en Docker..."

    # Construir el proyecto
    build_project

    # Construir y ejecutar todos los servicios
    docker-compose up --build -d
    wait_for_db

    echo "✅ Todos los servicios deberían estar corriendo:"
    echo "   📊 Subsistema Ciudadano:        http://localhost:8082"
    echo "   📋 Subsistema Tramitación:      http://localhost:8083"
    echo "   📧 Subsistema Comunicaciones:   http://localhost:8084"
    echo "   🎥 Subsistema Videoconferencia: http://localhost:8085"
    echo "   📚 Subsistema Información:      http://localhost:8088"
    echo "   🔐 Roles y Permisos:            http://localhost:8089"
    echo "   🤖 IA:                          http://localhost:8090"
    echo "   🛠️  Interno:                    http://localhost:8091"
    echo ""
    echo "📖 Swagger UI disponible en cada puerto + /swagger-ui.html"
}

# Función para construir para producción
build_prod() {
    echo "🏭 Construyendo para producción..."
    mvn clean verify
    echo "✅ Construcción para producción completada"
    echo "📦 Los JARs están en:"
    echo "   subsistema-ciudadano/target/"
    echo "   subsistema-tramitacion/target/"
    echo "   subsistema-comunicaciones/target/"
    echo "   subsistema-videoconferencia/target/"
    echo "   subsistema-informacion/target/"
    echo "   subsistema-roles/target/"
    echo "   subsistema-ia/target/"
    echo "   subsistema-interno/target/"
}

# Función para limpiar
cleanup() {
    echo "🧹 Limpiando recursos..."
    docker-compose down -v
    mvn clean
    echo "✅ Limpieza completada"
}

# Procesar argumentos
case "${1:-dev}" in
    "dev")
        check_dependencies
        run_dev
        ;;
    "docker")
        check_dependencies
        run_docker
        ;;
    "prod")
        check_dependencies
        build_prod
        ;;
    "clean")
        cleanup
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    *)
        echo "❌ Opción no válida: $1"
        show_help
        exit 1
        ;;
esac

echo ""
echo "🎉 ¡Proceso completado!"
