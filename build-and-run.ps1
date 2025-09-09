<#
.SYNOPSIS
Script para construir y ejecutar Ciudadanía 360 en Windows PowerShell.
Uso: .\build-and-run.ps1 [-Mode dev|docker|prod|clean|reset|help]
#>

param(
    [Parameter(Position=0)]
    [ValidateSet("dev","docker","prod","clean","reset","help")]
    [string]$Mode = "dev"
)

# ===== Colores =====
$C_BLUE    = "Blue"
$C_GREEN   = "Green"
$C_YELLOW  = "Yellow"
$C_RED     = "Red"
$C_PURPLE  = "Magenta"
$C_CYAN    = "Cyan"

function Write-Info($msg) { Write-Host $msg -ForegroundColor $C_BLUE }
function Write-Ok($msg)   { Write-Host $msg -ForegroundColor $C_GREEN }
function Write-Warn($msg) { Write-Host $msg -ForegroundColor $C_YELLOW }
function Write-Err($msg)  { Write-Host $msg -ForegroundColor $C_RED }
function Write-Title($msg){ Write-Host $msg -ForegroundColor $C_PURPLE }

# ===== Configuración =====
$POSTGRES_CONTAINER = "postgres_ciudadania360"
$DB_USER            = "ciudadania"
$DB_NAME            = "ciudadania360"
$WAIT_TIMEOUT       = 90 # segundos

# ===== Comprobación de dependencias =====
function Test-Dependencies {
    Write-Info "🔍 Verificando dependencias..."
    $missing = $false

    if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
        Write-Err "Docker no está instalado"
        $missing = $true
    }
    if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
        Write-Warn "docker-compose no encontrado, usando 'docker compose' si está disponible"
    }
    if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
        Write-Err "Maven no está instalado"
        $missing = $true
    }

    if ($missing) { exit 1 }
    Write-Ok "Todas las dependencias están instaladas"
}

# ===== Esperar a que la base de datos esté lista =====
function Wait-ForDB {
    Write-Info "⏳ Esperando a que la base de datos esté lista..."
    $startTs = Get-Date
    while ($true) {
        $status = docker inspect --format='{{.State.Health.Status}}' $POSTGRES_CONTAINER 2>$null
        if ($status -eq "healthy") { Write-Ok "Base de datos lista"; break }

        docker exec $POSTGRES_CONTAINER pg_isready -U $DB_USER -d $DB_NAME 2>$null
        if ($LASTEXITCODE -eq 0) { Write-Ok "Base de datos lista (pg_isready)"; break }

        $elapsed = (New-TimeSpan $startTs (Get-Date)).TotalSeconds
        if ($elapsed -gt $WAIT_TIMEOUT) {
            Write-Err "La base de datos no respondió a tiempo"
            docker logs --tail 50 $POSTGRES_CONTAINER
            exit 1
        }
        Start-Sleep -Seconds 2
    }
}

# ===== Construcción del proyecto =====
function Build-Project {
    Write-Info "🔨 Construyendo proyecto con Maven..."
    mvn clean install -DskipTests
    if ($LASTEXITCODE -eq 0) { Write-Ok "Proyecto construido correctamente" }
    else { Write-Err "Error en la construcción del proyecto"; exit 1 }
}

# ===== Modo desarrollo =====
function Run-Dev {
    Write-Info "🚀 Modo desarrollo: solo Postgres en Docker"
    docker-compose -f docker-compose.yml up -d postgres
    Wait-ForDB
    Write-Info "📊 Ahora puedes levantar Ciudadanía 360 localmente:"
    Write-Host "   mvn spring-boot:run -pl ciudadania360-app -Dspring-boot.run.profiles=docker" -ForegroundColor Cyan
}

# ===== Modo Docker =====
function Run-Docker {
    Write-Info "🐳 Construyendo y ejecutando Ciudadanía 360 en Docker..."
    Build-Project
    docker-compose -f docker-compose.yml build
    docker-compose -f docker-compose.yml up -d
    Wait-ForDB
    Write-Ok "Ciudadanía 360 debería estar corriendo."
    docker-compose -f docker-compose.yml ps
    Write-Host "Endpoint principal: http://localhost:8080" -ForegroundColor Yellow
    Write-Host "Swagger UI: http://localhost:8080/swagger-ui.html" -ForegroundColor Yellow
}

# ===== Modo producción =====
function Build-Prod {
    Write-Info "🏭 Construyendo para producción con Maven..."
    mvn clean verify
    if ($LASTEXITCODE -eq 0) { Write-Ok "Construcción de producción completada" }
    else { Write-Err "Error en la construcción de producción"; exit 1 }
}

# ===== Limpiar recursos =====
function Cleanup {
    Write-Info "🧹 Limpiando recursos..."
    docker-compose -f docker-compose.yml down -v
    mvn clean
    Write-Ok "Limpieza completada"
}

# ===== Reset total =====
function Reset-Docker {
    Write-Warn "🔥 VAS A ELIMINAR TODO Docker (contenedores, imágenes, volúmenes, redes)"
    $confirm = Read-Host "Escribe 'yes' para continuar"
    if ($confirm -ne "yes") { Write-Info "Operación cancelada"; return }

    docker ps -aq | ForEach-Object { docker rm -f $_ }
    docker images -q | ForEach-Object { docker rmi -f $_ }
    docker volume ls -q | ForEach-Object { docker volume rm -f $_ }
    docker network ls --format '{{.ID}} {{.Name}}' | ForEach-Object { if ($_ -notmatch "bridge|host|none") { docker network rm ($_ -split ' ')[0] } }
    docker builder prune -af
    docker system prune -af --volumes

    Write-Ok "Docker limpio"
    docker-compose -f docker-compose.yml up -d postgres
    Wait-ForDB
}

# ===== Mostrar ayuda =====
function Show-Help {
    Write-Host @"
Uso: .\build-and-run.ps1 [-Mode dev|docker|prod|clean|reset|help]

Opciones:
  dev     - Ejecutar en modo desarrollo (solo Postgres en Docker)
  docker  - Construir y ejecutar Ciudadanía 360 en Docker
  prod    - Construir para producción
  clean   - Limpiar contenedores/volúmenes y Maven
  reset   - ELIMINA TODO Docker y levanta el stack limpio
  help    - Mostrar esta ayuda
"@
}

# ===== Main =====
Write-Title "🏗️ Ciudadanía 360 - Windows PowerShell"
switch ($Mode) {
    "dev"    { Test-Dependencies; Run-Dev }
    "docker" { Test-Dependencies; Run-Docker }
    "prod"   { Test-Dependencies; Build-Prod }
    "clean"  { Cleanup }
    "reset"  { Test-Dependencies; Reset-Docker }
    "help"   { Show-Help }
    default  { Write-Err "Opción no válida: $Mode"; exit 1 }
}

Write-Ok "🎉 ¡Proceso completado!"
