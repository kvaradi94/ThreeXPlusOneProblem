#!/bin/bash

#after mvn clean install
mvn exec:java -Dexec.args="$1 $2"