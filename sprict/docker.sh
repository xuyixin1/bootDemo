#/bin/bash
kenel=`uname -r`
#截取前3位
kenel=`echo ${kenel:0:3}`
#判断是否大于3.10
if [ $(echo "${kenel} >= 3.10" | bc) = 1 ]
    then
        yum install -y yum-utils
        yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
        yum makecache fast
        yum -y install docker-ce
        systemctl start docker
        systemctl enable docker
    else
        echo "内核版本太低，请您升级内核版本"
fi

echo ">>>docker -version"
docker --version