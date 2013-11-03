#!/bin/bash
for ((  i = 1 ;  i <= 18;  i++  ))
do
	java -Xmx2048m Recommender training_set.csv test-all.csv /home/bolei/Desktop/test/predictions$i.txt /home/bolei/Desktop/test/config$i.properties 2> ~/Desktop/test/error$i.txt
done 



