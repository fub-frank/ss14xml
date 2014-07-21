#!/bin/bash

# xmlValidation use XMLValidation.jar to validate xml files to a xsd file
# author Martin Görick

if [ $1 == '-help' ]
then
  echo "Usage"
  echo "---------------------------------------------------"
  echo "./xmlValidation.sh file.xsd file1.xml file2.xml ..."
  echo "first xsd file and then one ore more xml files     "
else
  java -jar XMLValidation.jar $1 ${@:2}
fi
