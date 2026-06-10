$OutputFile = "resumen_generado.txt"
$Filtros = $args

git add .

$TodosLosArchivos = git ls-files

$ArchivosAImprimir = @()

if ($Filtros.Count -eq 0) {
    Write-Host "Generando resumen COMPLETO (solo codigo fuente)..." -ForegroundColor Cyan
    
    $ArchivosAImprimir = $TodosLosArchivos | Where-Object { 
        $_ -match '\.(java|properties|html|css|ts)$' -and 
        $_ -notmatch '(/images/|\.mvn|mvnw|resumen.*|HELP\.md|\.gitignore|\.gitattributes|\.vscode|target/|dist/|node_modules/)' 
    }
} else {
    Write-Host "Generando resumen para: $($Filtros -join ', ')" -ForegroundColor Cyan
    
    foreach ($filtro in $Filtros) {
        switch ($filtro.ToLower()) {
            "controller" { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/controller/.*\.java$' } }
            "repo"       { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match 'Repository\.java$' } }
            "domain"     { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/domain/.*\.java$' } }
            "service"    { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/service/.*\.java$' } }
            "web"        { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '^frontend/src/app/.*\.(ts|html|css)$' } }
            default      { Write-Host "Filtro desconocido ignorado: $filtro" -ForegroundColor Yellow }
        }
    }
}

$ArchivosAImprimir = $ArchivosAImprimir | Select-Object -Unique

if ($ArchivosAImprimir.Count -eq 0) {
    Write-Host "No se encontraron archivos para imprimir." -ForegroundColor Red
    exit
}

Write-Host "Escribiendo $($ArchivosAImprimir.Count) archivos en $OutputFile..." -ForegroundColor Green

$ArchivosAImprimir | ForEach-Object {
    Write-Output "========== $_ =========="
    Get-Content $_
    Write-Output "`n"
} | Out-File -FilePath $OutputFile -Encoding UTF8

Write-Host "Codigo empaquetado con exito en $OutputFile." -ForegroundColor Green