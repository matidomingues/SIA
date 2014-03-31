#! /bin/bash
cd gps/
mvn clean package
cd target/
gps_bin_path=`pwd`
mvn install:install-file -Dfile=$gps_bin_path/gps-1.0-SNAPSHOT.jar -DgroupId=SIA1 -DartifactId=gps -Dversion=1.0-SNAPSHOT -Dpackaging=jar
cd ../../deeptriptp
mvn clean install
cd ..
cp deeptriptp/target/deeptriptp-0.0.1-SNAPSHOT-jar-with-dependencies.jar deeptriptp.jar
