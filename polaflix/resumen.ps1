$OutputFile = "resumen_generado.txt"
$Filtros = $args

# 1. Asegurarnos de que Git conoce todos los archivos nuevos
git add .

# 2. Obtener la lista de todos los archivos del proyecto
$TodosLosArchivos = git ls-files

$ArchivosAImprimir = @()

# 3. Lógica de filtrado
if ($Filtros.Count -eq 0) {
    Write-Host "Ningún filtro especificado. Generando resumen COMPLETO..." -ForegroundColor Cyan
    # Lo coge todo excepto imágenes
    $ArchivosAImprimir = $TodosLosArchivos | Where-Object { 
        $_ -match '\.(java|xml|properties|html|css|js|json|txt)$' -and $_ -notmatch '/images/' 
    }
} else {
    Write-Host "Generando resumen para: $($Filtros -join ', ')" -ForegroundColor Cyan
    
    foreach ($filtro in $Filtros) {
        switch ($filtro.ToLower()) {
            "controller" { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/controller/.*\.java$' } }
            "repo"       { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match 'Repository\.java$' } }
            "domain"     { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/domain/.*\.java$' } }
            "service"    { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '/service/.*\.java$' } }
            "web"        { $ArchivosAImprimir += $TodosLosArchivos | Where-Object { $_ -match '^src/main/resources/static/' -and $_ -notmatch '/images/' } }
            default      { Write-Host "⚠️ Filtro desconocido ignorado: $filtro" -ForegroundColor Yellow }
        }
    }
}

# 4. Eliminar duplicados (por si ejecutas 'domain' y 'repo' a la vez)
$ArchivosAImprimir = $ArchivosAImprimir | Select-Object -Unique

if ($ArchivosAImprimir.Count -eq 0) {
    Write-Host "❌ No se encontraron archivos para imprimir." -ForegroundColor Red
    exit
}

# 5. Generar el archivo final
Write-Host "Escribiendo $($ArchivosAImprimir.Count) archivos en $OutputFile..." -ForegroundColor Green

$ArchivosAImprimir | ForEach-Object {
    Write-Output "========== $_ =========="
    Get-Content $_
    Write-Output "`n"
} | Out-File -FilePath $OutputFile -Encoding UTF8

Write-Host "✨ ¡Listo! Código empaquetado con éxito en $OutputFile." -ForegroundColor Green