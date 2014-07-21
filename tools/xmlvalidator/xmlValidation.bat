rem Windows Batch file to Validate an XML file
rem use the XMLValidation.jar
rem compiled with Java 1.7

rem Autor Martin Görick

set xml=test.xml
set xml1=test1.xml
set xml2=test2.xml
set xsd=test.xsd

java -jar XMLValidaton.jar %xsd% %xml% %xml1% %xml2%

pause
