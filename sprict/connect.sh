#/bin/bash

source_base_dir="/opt/springboot/bootDemo"

ssh root@47.101.42.50 << eeooff
 ${source_base_dir}/publish.sh
 sleep 5
 echo "finished!"
 sleep 2
 #执行完毕
 exit
 eeooff