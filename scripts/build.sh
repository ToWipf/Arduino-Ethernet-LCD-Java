#!/bin/bash
cd ../angular
npm run build

cd ..
rm -rf src/main/resources/META-INF/resources/wipf-app
mkdir src/main/resources/META-INF/resources/wipf-app
mv angular/dist/* src/main/resources/META-INF/resources/

mvn package -Dquarkus.package.uber-jar=true


scp target/wipfapp-1.0-SNAPSHOT-runner.jar root@192.168.2.10:/root/wipfapp/wipfapp-1.0-SNAPSHOT-runner.jar

ssh root@192.168.2.10 /root/wipfapp/stop.sh

ssh root@192.168.2.10 /root/wipfapp/start.sh