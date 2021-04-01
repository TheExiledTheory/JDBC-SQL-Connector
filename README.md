# MySQL-Java-Connector [JDBC]
# Description: 

This is a simple program that I wrote to demonstrate and practice the working relation of connecting and executing commands on a SQL database using the Java connectivity module (JDBC). As it stands, it supports the primary DB commands such as (select, insert, delete, and update). The only real work left to be done on it is to add support for any other obscure commands like show view, execute, etc. 

## Last successful test: 03/31/2021
    Windows 10 
    10.0.19041 Build 19041 
    MySQL Community 8.0.23 with Connector/J
    Eclipse IDE for Java Developers - 2020-12
    Java SE Development Kit 15.0.2 (64-bit)
	
# Usage: 

 * To run this, you need to add the MySQL connector jar file to the build path of the program.
      (**If you are using MySQL - you will need to obtain the respective connector for your setup**) 
 * On windows, you can find it located within the MySQL directory from the installation of the MySQL. 
      (**C:\ > Program Files (x86) > MySQL > Connector J 8.0**) 
 * The final step is to make sure that it is included in the build path 
      (**Right click the class > build path > Add external archives > select location of SQL connector jar file**) 
 * Adjust for your configuration as mentioned below!!!
      
    ---------------------------------------------------------------------------------------------------------
    
# Configure!
On line 320 I have listed a few JDBC driver's names for different databases, but if the one you need is not listed, you will need to add it. Similarly, the connection URL format for each database type will be slightly different, so you will need to change line 325 based on your own needs. Lines 657-662 will also need to be changed based on your server configuration. Aside from those few things, most everything should be plug and play. 
      
## TO DO: 
	1: Make the program prettier 
	2: Add support for additional commands 



