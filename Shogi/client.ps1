if ($args[0] -ne "noCompile") {
    ./compile.ps1
}
$path = "src/;lib/ocsf.jar"
java -cp $path Client.ClientGUI
pause
