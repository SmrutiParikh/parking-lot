#!/bin/sh
arg1=$1
##directory where jar file is located
dir=resources
##jar file name
jar_name=parkingLot-1.0-SNAPSHOT.jar

if [ -z "$1" ] ; then
        java -jar $dir/$jar_name
        exit 1

else
	java -jar $arg1/$dir/$jar_name
fi