set path="src/;lib/mysql-connector-java-5.1.40-bin.jar;lib/ocsf.jar"
echo %path%
javac -cp "%path%" ./src/Client/ClientGUI.java
pause
