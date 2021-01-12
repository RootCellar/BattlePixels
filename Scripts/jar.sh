#!/bin/bash
#puts the .class files into a jar.

#make sure everything is built first
echo BUILDING...
build.sh

echo
echo
echo BUILDING JAR...
echo

cd ..
cd build

echo Collecting class files...
echo

i=$(find -name "*.class")
echo $i
echo

echo Placing into jar...
echo

jar --create -e Game --file BattlePixels.jar $i
