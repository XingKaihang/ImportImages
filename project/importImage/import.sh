#!/bin/bash
#This program is used for import image from disk to HIB
#usage: ./import.sh example.jpg example.hib
#This program uses a temporary directory at /data/temp_image
function  importfile()
{
	mkdir /data/temp_image
	./importFace $1
	hadoop jar ../../project/project.jar /data/temp_image/ $2
	rm -rf /data/temp_image
}



if test -d /data/temp_image
then
	echo 'This program is processing... Please try it later'
	exit -1
else
	echo 'Start processing...'
	importfile $1 $2
	echo 'Finish'
	exit 0
fi