# Script para construir y ejecutar Ciudadanía 360 en Windows
# Uso: .\build-and-run.ps1 [dev|docker|prod]

param(
    [Parameter(Position=0)]
    [ValidateSet("dev", "docker", "prod", "clean", "help")]
    [string]$Mode = "dev"
)

function Show-Help {
    Write-Host "Uso: .\build-and-run.ps1 [dev|docker|prod]" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Opciones:" -ForegroundColor Yellow
    Write-Host "  dev     - Ejecutar en modo desarrollo (solo BD)" -ForegroundColor White
    Write-Host "  docker  - Construir y ejecutar todos los servicios en Docker" -ForegroundColor White
    Write-Host "  prod    - Construir para producción" -ForegroundColor White
    Write-Host "  clean   - Limpiar recursos (incluye volúmenes de BD)" -ForegroundColor White
    Write-Host "  help    - Mostrar esta ayuda" -ForegroundColor White
}

function Test-Dependencies {
    Write-Host "🔍 Verificando dependencias..." -ForegroundColor Blue
    $missing = $false

    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Docker no está instalado" -ForegroundColor Red
        $missing = $true
    }
    if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Docker Compose no está instalado" -ForegroundColor Red
        $missing = $true
    }
    if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
        Write-Host "❌ Maven no está instalado" -ForegroundColor Red
        $missing = $true
    }

    if ($missing) { exit 1 }
    Write-Host "✅ Todas las dependencias están instaladas" -ForegroundColor Green
}

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

function Wait-ForDB {
    Write-Host "⏳ Esperando a que la base de datos esté lista..." -ForegroundColor Yellow
    $ready = $false
    for ($i=0; $i -lt 30; $i++) {
        docker exec postgres_ciudadania360 pg_isready -U ciudadania | Out-Null
        if ($LASTEXITCODE -eq 0) { $ready = $true; break }
        Start-Sleep -Seconds 2
    }
    if ($ready) {
        Write-Host "✅ Base de datos lista" -ForegroundColor Green
    } else {
        Write-Host "❌ La base de datos no respondió a tiempo" -ForegroundColor Red
        exit 1
    }
}

function Start-DevMode {
    Write-Host "🚀 Iniciando modo desarrollo..." -ForegroundColor Blue
    docker-compose up -d postgres
    Wait-ForDB
    Write-Host "📊 Puedes ejecutar los subsistemas individualmente con:" -ForegroundColor Cyan
    Write-Host "   mvn spring-boot:run -pl subsistema-ciudadano" -ForegroundColor White
    Write-Host "   mvn spring-boot:run -pl subsistema-tramitacion" -ForegroundColor White
}

function Start-DockerMode {
    Write-Host "🐳 Construyendo y ejecutando en Docker..." -ForegroundColor Blue
    Build-Project
    docker-compose up --build -d
    Wait-ForDB
    Write-Host "✅ Todos los servicios deberían estar corriendo:" -ForegroundColor Green
    Write-Host "   Ciudadano: http://localhost:8082" -ForegroundColor Cyan
    Write-Host "   Tramitación: http://localhost:8083" -ForegroundColor Cyan
    Write-Host "   Comunicaciones: http://localhost:8084" -ForegroundColor Cyan
    Write-Host "   Videoconferencia: http://localhost:8085" -ForegroundColor Cyan
    Write-Host "   Información: http://localhost:8088" -ForegroundColor Cyan
    Write-Host "   Roles y Permisos: http://localhost:8089" -ForegroundColor Cyan
    Write-Host "📖 Swagger UI disponible en cada puerto + /swagger-ui.html" -ForegroundColor Yellow
}

function Build-Production {
    Write-Host "🏭 Construyendo para producción..." -ForegroundColor Blue
    mvn clean verify
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Construcción para producción completada" -ForegroundColor Green
    } else {
        Write-Host "❌ Error en la construcción para producción" -ForegroundColor Red
        exit 1
    }
}

function Clear-Resources {
    Write-Host "🧹 Limpiando recursos..." -ForegroundColor Blue
    docker-compose down -v
    mvn clean
    Write-Host "✅ Limpieza completada" -ForegroundColor Green
}

Write-Host "🏗️  Ciudadanía 360 - Script de construcción y ejecución" -ForegroundColor Magenta
Write-Host "==================================================" -ForegroundColor Magenta

switch ($Mode) {
    "dev" { Test-Dependencies; Start-DevMode }
    "docker" { Test-Dependencies; Start-DockerMode }
    "prod" { Test-Dependencies; Build-Production }
    "clean" { Clear-Resources }
    "help" { Show-Help }
    default { Show-Help }
}

Write-Host "🎉 ¡Proceso completado!" -ForegroundColor Green
