#!/bin/bash
cd ../angular
npm run build
if [ $? -eq 0 ];
then
  echo 'build app OK'
else 
  exit 0 
fi

cd ..
rm -rf src/main/resources/META-INF/resources/wipf-app
mkdir src/main/resources/META-INF/resources/wipf-app
mv angular/dist/* src/main/resources/META-INF/resources/
if [ $? -eq 0 ];
then
	echo "move OK"
else
	exit 0 
fi

mvn package -Dquarkus.package.uber-jar=true
if [ $? -eq 0 ];
then
	echo "build OK"
else
	exit 0
fi

scp target/wipfapp-1.0-SNAPSHOT-runner.jar root@192.168.2.10:/root/wipfapp/wipfapp-1.0-SNAPSHOT-runner.jar
if [ $? -eq 0 ];
then
	echo "copy OK"
else
	exit 0
fi

ssh root@192.168.2.10 /root/wipfapp/stop.sh
sleep 2
ssh root@192.168.2.10 /root/wipfapp/start.sh