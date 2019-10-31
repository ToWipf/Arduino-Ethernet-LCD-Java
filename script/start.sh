#!/bin/bash
java -jar /root/wipfapp/wipf.jar >> /root/wipfapp/log 2>> /root/wipfapp/log &
tail -f /root/wipfapp/log -n 0
