#!/bin/sh
cat count.txt | tr -s ' ' '\n' | sort | uniq -c | sort -r | awk '{print $2,$1}'
sleep 10