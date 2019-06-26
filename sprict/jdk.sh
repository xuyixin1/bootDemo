#/bin/bash

set -e

#变量两边之间不能有空格
java_dir="/opt/java"
java_tar="jdk-8u131-linux-x64.tar.gz"
java_version="jdk1.8.0_131"
java_home="${java_dir}/${java_version}"

#-e filename 如果 filename存在，则为真
#-d filename 如果 filename为目录，则为真
#-f filename 如果 filename为常规文件，则为真
#-L filename 如果 filename为符号链接，则为真
#-r filename 如果 filename可读，则为真
#-w filename 如果 filename可写，则为真
#-x filename 如果 filename可执行，则为真
#-s filename 如果文件长度不为0，则为真
#-h filename 如果文件是软链接，则为真

echo ">>>判断文件夹是否存在"
if [ ! -d "${java_dir}" ];
    then
        mkdir $java_dir
fi

cd $java_dir

if [ ! -f "${java_tar}" ];
   then
       wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/${java_tar}
fi


if [ ! -d "${java_home}" ];
    then
        tar -xvf ${java_tar}
fi


if ! grep "JAVA_HOME=${java_home}" /etc/profile
    then
        echo "JAVA_HOME=${java_home}" >>/etc/profile
        echo "CLASSPATH=\$JAVA_HOME/lib" >> /etc/profile
        echo "PATH=\$PATH:\$JAVA_HOME/bin" >> /etc/profile
        echo "export PATH JAVA_HOME CLASSPATH" >> /etc/profile
        source /etc/profile
fi


echo ">>>java -version"
java -version