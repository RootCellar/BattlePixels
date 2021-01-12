#!/bin/bash
#Used to find todo statements and such and collect them in a file

echo
echo COLLECTING INFO...

mkdir ../info
git status
git log > ../info/gitlog.temp

#Move to base directory
echo Moving to base directory
cd ..
mkdir info #ensure folder exists

#Things involving the source code start here
cd src
echo Moved to src folder

#find todo statements
echo Finding todo statements...
grep -r -i -n "todo" > ../info/todo.temp

#find single-line comments
echo Finding single-line comments...
grep -r -i -n "//" > ../info/comment.temp

#count source code lines
echo Counting source code lines...
(find -name "*.java" -exec wc -l {} +) | sort -n -r > ../info/lines.temp

cd ..
echo Moved out of src folder
#No longer working in source code folder

#count text lines
echo Counting lines of text...
(find -name "*.md" -exec wc -l {} +) | sort -n -r > info/mdlines.temp

#find file sizes
echo Finding file sizes...
(du -ch) | sort -h -r > info/size.temp

#find .md files
echo Finding .md files...
(find -name "*.md") > info/md.temp

#find files in src/
echo Finding all files in src...
ls -lsthR src > info/src.temp








