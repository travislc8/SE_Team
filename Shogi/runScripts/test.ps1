./compile.ps1
cd ../
$path = "src/;lib/hamcrest-core-1.3.jar;lib/junit-4.13.2.jar"

# run
java -cp "$path" org.junit.runner.JUnitCore Tests.GameLogic.AllTestsSuite
cd ./runScripts
