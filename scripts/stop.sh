#!/bin/bash
pkill -f 'java -jar /root/wipfapp/wipfapp-1.0-SNAPSHOT-runner.jar'
echo $?


# Nicht mehr moeglich:
# curl -X DELETE localhost:8080/sysHalt