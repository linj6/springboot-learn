#!/bin/bash
LANG="zh_CN.UTF-8"
pid=`ps ax | grep springboot-graceful-shutdown | grep java | head -1 | awk '{print $1}'`
echo $pid
kill $pid
#curl -X POST http://127.0.0.1:8080/shutdown
while [[ $pid != "" ]]; do
    echo '服务停止中...'
    sleep 1
    pid=`ps ax | grep springboot-graceful-shutdown | grep java | head -1 | awk '{print $1}'`
done
echo '服务停止成功,开始重启服务...'
nohup java -jar springboot-graceful-shutdown-0.0.1-SNAPSHOT.jar &
