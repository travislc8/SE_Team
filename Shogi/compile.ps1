$path = "src/;lib/mysql-connector-java-5.1.40-bin.jar;lib/ocsf.jar"

$outputPath = "./classes/"

$outerFolder = "./src/"

foreach ($folder in Get-ChildItem $outerFolder -Directory) {
    if (Test-Path $folder -PathType Container) {
        foreach ($file in Get-ChildItem $folder -File) {
                if ($file -like "*.java") {
                    $name = $file.Name.Substring(0, $file.Name.IndexOf(".java")) + ".class"
                    $class = Join-Path $folder $name
                    if (Test-Path $class) {
                        $class = Get-Item $class
                    } else {
                        javac -cp "$path" "$file"
                    }
                    if ($args[0] -eq "force") {
                        Write-Host "Compiling: $file"
                        javac -cp "$path" "$file"
                    } elseif ($file.LastWriteTime -gt $class.LastWriteTime) {
                        Write-Host "Compiling: $file"
                        javac -cp "$path" "$file"
                    } else {
                        Write-Host "No changes to $file "
                    }
                }
        }
    }

}

$path = "src/;lib/hamcrest-core-1.3.jar;lib/junit-4.13.2.jar;lib/mysql-connector-java-5.1.40-bin.jar"
$folderPath = "./src/ShogiTest/GameLogic/"
foreach ($file in Get-ChildItem $folderPath -File) {
                if ($file -like "*.java") {
                    $name = $file.Name.Substring(0, $file.Name.IndexOf(".java")) + ".class"
                    $class = Join-Path $folderPath $name
                    if (Test-Path $class) {
                        $class = Get-Item $class
                    } else {
                        javac -cp "$path" "$file"
                    }

                    if ($args[0] -eq "force") {
                        Write-Host "Compiling: $file"
                        javac -cp "$path" "$file"
                    } elseif ($file.LastWriteTime -gt $class.LastWriteTime) {
                        Write-Host "Compiling: $file"
                        javac -cp "$path" "$file"
                    } else {
                        Write-Host "No changes to $file "
                    }
                }
}
