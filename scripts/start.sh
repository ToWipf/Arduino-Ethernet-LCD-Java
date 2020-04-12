#!/bin/bash
java -Xmx48m -jar /root/wipfapp/wipfapp-1.0-SNAPSHOT-runner.jar >> /root/wipfapp/wipf.log 2>> /root/wipfapp/wipf.log &
#tail -f /root/wipfapp/log -n 0