#!/bin/bash

path="src/:lib/mysql-connector-java-5.1.40-bin.jar:lib/ocsf.jar"

extension=".java"
outerFolder=$(realpath "./src/")

echo $outerFolder

for folder in "$outerFolder"/*; do 
    #printf "folder name = $s; \n" "$folder"
    if [ -d "$folder" ]; then
        for file in "$folder"/*; do
            if [[ $file == *$extension ]]; then
                base=$(basename "$file" .java)
                name="$base.class"

                class=$(realpath "$folder/$name")
                if [ -e "$class" ]; then
                    time1=$(stat -c %Y "$file")
                    time2=$(stat -c %Y "$class")

                    if [ $time2 -lt $time1 ]; then
                        echo "Compiling: $file"
                        javac -cp "$path" "$file"
                    else
                        echo "No Changes to: $file"
                    fi
                else
                    echo "Compiling: $file"
                    javac -cp "$path" "$file"
                fi
            fi
        done
    fi
done
#javac -cp $path Server/GameServer.java
#javac -cp $path Server/ServerTUI.java
#javac -cp $path Server/User.java

#java -cp $path Server.ServerTUI
