#!/bin/bash
for ((  i = 2 ;  i <= 18;  i++  ))
do
	cp config1.properties config$i.properties
done 
