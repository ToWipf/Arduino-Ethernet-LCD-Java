#!/bin/bash
ps -ef | grep 'wipfapp' | grep -v grep | awk '{print $2}' | xargs -r kill -9
echo $?


# Nicht mehr moeglich:
# curl -X DELETE localhost:8080/sysHalt