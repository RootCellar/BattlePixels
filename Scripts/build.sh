#!/bin/bash
#Builds the game

echo 
echo BUILDING...

#move to base
cd ..

#move to source
cd src

#build list of files
mkdir ../info
find -name "*.java" > ../info/find.temp

find -name "*.java" -exec javac -d ../build/ {} +

