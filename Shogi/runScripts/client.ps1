if ($args[0] -ne "noCompile") {
    ./compile.ps1
}
cd ../
$path = "src/;lib/ocsf.jar"
java -cp $path Client.ClientGUI
cd ./runScripts
