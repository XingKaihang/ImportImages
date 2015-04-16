# ImportImages
Cloud Project(Face detection & import images part)

* Environment : This project is under Ubuntu 14.04, and needs the support of OpenCV 2.11, OpenBR, hadoop 2.6.0 and HIPI

* Aim : This project will first detect faces in a image, and then import all face images into a given HIB

* Usage : (under the project root directory) ./importImage/import.sh [IMAGE PATH] [HIB PATH]

* How to deploy : put this directory under hipi root directory, and use the command on previous line.

* Attention : This project uses the /data/temp_image folder to store temporary faces, so you should create a /data directory first. Also, this project uses import_temp.hib and import_temp_merge.hib as the temporary HIB to import images, this may cause overwrite this two hib in you hdfs root directory if you already have this two HIB.

Some useful links to deploy environment: </br>
http://www.samontab.com/web/2014/06/installing-opencv-2-4-9-in-ubuntu-14-04-lts/ </br>
http://openbiometrics.org/doxygen/latest/linux_gcc.html </br>
http://hipi.cs.virginia.edu/gettingstarted.html </br>

