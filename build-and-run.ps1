# Script para construir y ejecutar Ciudadanía 360 en Windows
# Uso: .\build-and-run.ps1 [dev|docker|prod]

param(
    [Parameter(Position=0)]
    [ValidateSet("dev", "docker", "prod", "clean", "help")]
    [string]$Mode = "dev"
)

# Función para mostrar ayuda
function Show-Help {
    Write-Host "Uso: .\build-and-run.ps1 [dev|docker|prod]" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Opciones:" -ForegroundColor Yellow
    Write-Host "  dev     - Ejecutar en modo desarrollo (solo BD)" -ForegroundColor White
    Write-Host "  docker  - Construir y ejecutar todos los servicios en Docker" -ForegroundColor White
    Write-Host "  prod    - Construir para producción" -ForegroundColor White
    Write-Host "  clean   - Limpiar recursos" -ForegroundColor White
    Write-Host ""
    Write-Host "Ejemplos:" -ForegroundColor Yellow
    Write-Host "  .\build-and-run.ps1 dev" -ForegroundColor White
    Write-Host "  .\build-and-run.ps1 docker" -ForegroundColor White
    Write-Host "  .\build-and-run.ps1 prod" -ForegroundColor White
}

# Función para verificar dependencias
function Test-Dependencies {
    Write-Host "🔍 Verificando dependencias..." -ForegroundColor Blue
    
    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Docker no está instalado" -ForegroundColor Red
        exit 1
    }
    
    if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Docker Compose no está instalado" -ForegroundColor Red
        exit 1
    }
    
    if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Maven no está instalado" -ForegroundColor Red
        exit 1
    }
    
    Write-Host "✅ Todas las dependencias están instaladas" -ForegroundColor Green
}

# Función para construir el proyecto
function Build-Project {
    Write-Host "🔨 Construyendo proyecto..." -ForegroundColor Blue
    mvn clean install -DskipTests
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Proyecto construido correctamente" -ForegroundColor Green
    } else {
        Write-Host "❌ Error en la construcción del proyecto" -ForegroundColor Red
        exit 1
    }
}

# Función para ejecutar en modo desarrollo
function Start-DevMode {
    Write-Host "🚀 Iniciando modo desarrollo..." -ForegroundColor Blue
    
    # Levantar solo la base de datos
    docker-compose up -d postgres
    
    Write-Host "⏳ Esperando a que la base de datos esté lista..." -ForegroundColor Yellow
    Start-Sleep -Seconds 10
    
    Write-Host "✅ Base de datos iniciada en localhost:5432" -ForegroundColor Green
    Write-Host "📊 Puedes ejecutar los subsistemas individualmente con:" -ForegroundColor Cyan
    Write-Host "   mvn spring-boot:run -pl subsistema-ciudadano" -ForegroundColor White
    Write-Host "   mvn spring-boot:run -pl subsistema-tramitacion" -ForegroundColor White
    Write-Host "   etc..." -ForegroundColor White
}

# Función para ejecutar en Docker
function Start-DockerMode {
    Write-Host "🐳 Construyendo y ejecutando en Docker..." -ForegroundColor Blue
    
    # Construir el proyecto
    Build-Project
    
    # Construir y ejecutar todos los servicios
    docker-compose up --build -d
    
    Write-Host "⏳ Esperando a que todos los servicios estén listos..." -ForegroundColor Yellow
    Start-Sleep -Seconds 30
    
    Write-Host "✅ Todos los servicios están ejecutándose:" -ForegroundColor Green
    Write-Host "   📊 Subsistema Ciudadano: http://localhost:8082" -ForegroundColor Cyan
    Write-Host "   📋 Subsistema Tramitación: http://localhost:8083" -ForegroundColor Cyan
    Write-Host "   📧 Subsistema Comunicaciones: http://localhost:8084" -ForegroundColor Cyan
    Write-Host "   🎥 Subsistema Videoconferencia: http://localhost:8085" -ForegroundColor Cyan
    Write-Host "   📚 Subsistema Información: http://localhost:8088" -ForegroundColor Cyan
    Write-Host "   🔐 Gestión Roles y Permisos: http://localhost:8089" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "📖 Swagger UI disponible en cada puerto + /swagger-ui.html" -ForegroundColor Yellow
}

# Función para construir para producción
function Build-Production {
    Write-Host "🏭 Construyendo para producción..." -ForegroundColor Blue
    
    # Construir con tests
    mvn clean verify
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Construcción para producción completada" -ForegroundColor Green
        Write-Host "📦 Los JARs están en:" -ForegroundColor Cyan
        Write-Host "   subsistema-ciudadano/target/" -ForegroundColor White
        Write-Host "   subsistema-tramitacion/target/" -ForegroundColor White
        Write-Host "   etc..." -ForegroundColor White
    } else {
        Write-Host "❌ Error en la construcción para producción" -ForegroundColor Red
        exit 1
    }
}

# Función para limpiar
function Clear-Resources {
    Write-Host "🧹 Limpiando recursos..." -ForegroundColor Blue
    docker-compose down
    mvn clean
    Write-Host "✅ Limpieza completada" -ForegroundColor Green
}

# Script principal
Write-Host "🏗️  Ciudadanía 360 - Script de construcción y ejecución" -ForegroundColor Magenta
Write-Host "==================================================" -ForegroundColor Magenta

switch ($Mode) {
    "dev" {
        Test-Dependencies
        Start-DevMode
    }
    "docker" {
        Test-Dependencies
        Start-DockerMode
    }
    "prod" {
        Test-Dependencies
        Build-Production
    }
    "clean" {
        Clear-Resources
    }
    "help" {
        Show-Help
    }
}

Write-Host ""
Write-Host "🎉 ¡Proceso completado!" -ForegroundColor Green

