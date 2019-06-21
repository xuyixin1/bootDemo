#/bin/bash
set -e

maven_dir="/opt/maven"
maven_version="apache-maven-3.6.1"
maven_tar="${maven_version}-bin.tar.gz"
maven_home="${maven_dir}/${maven_version}"

echo ">>>判断文件夹是否存在"
if [ ! -d "${maven_dir}" ]
    then
        mkdir $maven_dir
fi

cd $maven_dir

if [ ! -f "${maven_tar}" ]
   then
       wget http://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.1/binaries/${maven_tar}
fi


if [ ! -d "${maven_home}" ]
    then
        tar -xvf ${maven_tar}
fi



#\转义 \$PATH 是直接输出$PATH
if ! grep "MAVEN_HOME=${maven_home}" /etc/profile
    then
        echo "MAVEN_HOME=${maven_home}" >>/etc/profile
        echo "PATH=\$PATH:\$MAVEN_HOME/bin" >> /etc/profile
        echo "export PATH MAVEN_HOME" >> /etc/profile
        source /etc/profile
fi

echo ">>>mvn -version"
mvn -version