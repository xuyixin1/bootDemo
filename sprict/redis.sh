#/bin/bash
set -e
redis_dir="/opt/redis"
redis_tar="redis-5.0.5.tar.gz"
redis_version="redis-5.0.5"

echo "<<<判断文件是否存在"
#在if和[]之间以及[ ]和之中内容之间都要有空格存在
if [ ! -d "${redis_dir}" ]
   then 
       mkdir ${redis_dir}
fi
	      
cd ${redis_dir}

if [ ! -f "${redis_tar}" ]
   then 
       wget "http://download.redis.io/releases/redis-5.0.5.tar.gz"
fi

#解压tar.gz 用z
tar -xzvf ${redis_tar}


#安装gcc
yum install -y gcc-c++

make 

make PREFIX=${redis_dir} install 

cd bin

#备份文件
cp  redis.conf redis.conf_bak


#-i 修改原文件 s替换内容
sed -i "s|daemonize no|daemonize yes| " redis.conf

sed -i "s|bind 127.0.0.1|#bind 127.0.0.1| " redis.conf

echo "<<<根据需求自动修改端口和密码"

#启动
${redis_dir}/bin/redis-server  ${redis_dir}/${redis_version}/redis.conf


