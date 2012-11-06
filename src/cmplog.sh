#!/bin/sh

N=0

while read line; 
do
  echo $line > temp_$N.txt;
  let "N = $N+1"
done < log.txt

let "N=$N-1"

for i in $(seq 1 $N)
do
  cmp temp_0.txt temp_$i.txt
done

rm temp_*

