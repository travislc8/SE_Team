if ($args[0] -ne "noCompile") {
    ./compile.ps1
}
$path = "src/;lib/mysql-connector-java-5.1.40-bin.jar;lib/ocsf.jar"
java -cp $path Server.ServerTUI

pause
