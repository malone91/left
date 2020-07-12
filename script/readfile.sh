#!/bin/sh
cat aaa.txt | while read line
do
	echo "${line}"
	sleep 5
done