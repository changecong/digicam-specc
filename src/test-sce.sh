#!/bin/sh

# define
N=4

## create test images
echo ""
echo "####### CREATE IMAGES ########"

for i in $(seq 0 $N)
do
  cp ccd.bmp ccd_$i.bmp
done

## execute
echo ""
echo "######## EXECUTATION #########"
./DigicamSpec > log-sce.txt

## show resutl
cat log-sce.txt

## compare output
# echo "\nCOMPARE LOG"
# ./comlog

## test
echo ""
echo "######## COMPARE IMAGES #######"

for img in test_*.jpg 
do
  diff -s golden.jpg $img
done

# ls -l test_*
## remove images
for img in ccd_*.bmp
do 
  rm $img
done

echo ""
echo "###### REMOVE BMP DONE. ######"
