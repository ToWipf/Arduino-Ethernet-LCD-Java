#!/bin/bash
java -jar /home/wipf/wipfapp/wipf.jar >> /home/wipf/wipfapp/log &
tail -f /home/wipf/wipfapp/log
