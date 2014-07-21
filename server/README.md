# Build
Build the server by using ant: `ant jar`.  
A jar file will be created in the `release` sub directory.

# Execute
Execute the server by the following command

`java -jar SoapService.jar`

The server will listen on `localhost:8370`. To change the hostname of the server to listen on
execute the server by the following command

`java -jar SoapService.jar your_host_name`

You can not modify the listening port via command line arguments.  
If needed modify `src/ss14xmlproject/SoapService.java` at line 28 and rebuild.
