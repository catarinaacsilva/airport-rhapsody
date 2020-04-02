#!/usr/bin/env bash

mvn -q clean compile

for i in $(seq 1 100)
do
  echo -e "\nRun n.? " $i
  mvn -q exec:java
done
