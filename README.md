**This is a Java FX, MySQL, and Scene builder based Inventory Management System Web Application.**

My inventory management system is connected to the MySQL database server.
Authenticatiion, CRUD operations and fetching of the data are being performed on the GUI from the MySQL Server.
I have attached inventory_Management.Sql script under both branches of my project
This project has 2 branch as follows:
        1. main
        2. master
main branch contains every file of the project such as Main.java, Products.java, ProductsController.java and etc.
master branch contains the inventory_managemnt.sql script file and demo.zip file.
        demo.zip file is nothing but a zip folder of all the files which is visible in main branch.

Important points to remember:
        1. First run the sql script before executing the java code.
        2. I have used only Scene Builder in this project to enhance the GUI of the inventory management system. so do not forget to add JavaFX SDK path in the VM options in the project configurations.
        3. If you face initialization boot layer error then add compiler output path in the VM options after JavaFX SDK path. 
        4. Replace the name of the Database user and password in the Database_connector.java file with your database user name and password.
        5. Do not forget to add the .jar files of MySQL Java database connectivity driver in the dependencies of modules of the JavaFX project.
        6. Use JDK version 21 or the version which came after that.
