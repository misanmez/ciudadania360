#!/bin/bash

# Script para construir y ejecutar Ciudadanía 360
# Uso: ./build-and-run.sh [dev|docker|prod]

set -e

echo "🏗️  Ciudadanía 360 - Script de construcción y ejecución"
echo "=================================================="

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [dev|docker|prod]"
    echo ""
    echo "Opciones:"
    echo "  dev     - Ejecutar en modo desarrollo (solo BD)"
    echo "  docker  - Construir y ejecutar todos los servicios en Docker"
    echo "  prod    - Construir para producción"
    echo ""
    echo "Ejemplos:"
    echo "  $0 dev"
    echo "  $0 docker"
    echo "  $0 prod"
}

# Función para verificar dependencias
check_dependencies() {
    echo "🔍 Verificando dependencias..."
    
    if ! command -v docker &> /dev/null; then
        echo "❌ Docker no está instalado"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        echo "❌ Docker Compose no está instalado"
        exit 1
    fi
    
    if ! command -v mvn &> /dev/null; then
        echo "❌ Maven no está instalado"
        exit 1
    fi
    
    echo "✅ Todas las dependencias están instaladas"
}

# Función para construir el proyecto
build_project() {
    echo "🔨 Construyendo proyecto..."
    mvn clean install -DskipTests
    echo "✅ Proyecto construido correctamente"
}

# Función para ejecutar en modo desarrollo
run_dev() {
    echo "🚀 Iniciando modo desarrollo..."
    
    # Levantar solo la base de datos
    docker-compose up -d postgres
    
    echo "⏳ Esperando a que la base de datos esté lista..."
    sleep 10
    
    echo "✅ Base de datos iniciada en localhost:5432"
    echo "📊 Puedes ejecutar los subsistemas individualmente con:"
    echo "   mvn spring-boot:run -pl subsistema-ciudadano"
    echo "   mvn spring-boot:run -pl subsistema-tramitacion"
    echo "   etc..."
}

# Función para ejecutar en Docker
run_docker() {
    echo "🐳 Construyendo y ejecutando en Docker..."
    
    # Construir el proyecto
    build_project
    
    # Construir y ejecutar todos los servicios
    docker-compose up --build -d
    
    echo "⏳ Esperando a que todos los servicios estén listos..."
    sleep 30
    
    echo "✅ Todos los servicios están ejecutándose:"
    echo "   📊 Subsistema Ciudadano: http://localhost:8082"
    echo "   📋 Subsistema Tramitación: http://localhost:8083"
    echo "   📧 Subsistema Comunicaciones: http://localhost:8084"
    echo "   🎥 Subsistema Videoconferencia: http://localhost:8085"
    echo "   📚 Subsistema Información: http://localhost:8088"
    echo "   🔐 Gestión Roles y Permisos: http://localhost:8089"
    echo ""
    echo "📖 Swagger UI disponible en cada puerto + /swagger-ui.html"
}

# Función para construir para producción
build_prod() {
    echo "🏭 Construyendo para producción..."
    
    # Construir con tests
    mvn clean verify
    
    echo "✅ Construcción para producción completada"
    echo "📦 Los JARs están en:"
    echo "   subsistema-ciudadano/target/"
    echo "   subsistema-tramitacion/target/"
    echo "   etc..."
}

# Función para limpiar
cleanup() {
    echo "🧹 Limpiando recursos..."
    docker-compose down
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

