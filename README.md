# RestProjectLTU
**Administration of Study Results – SOA Prototype**

<br>

**Project Description**

This prototype was developed as part of the Enterprise Architecture & SOA course at Luleå University of Technology (LTU).

The purpose of the prototype is to demonstrate how REST web services can be used to integrate different systems and simplify the administration of study results.

<br>

**Functionality**

- Retrieve course codes and assignments from Canvas.

- Retrieve modules from E-pok and verify them against Ladok.

- Match student identities between Canvas and Ladok using ITSAdmin.

- Retrieve and display existing grades.

- Register grades and examination dates in Ladok.

- Display the registration status.

<br>

**Technologies**

- Java

- JavaFX and Scene Builder

- Javalin (REST web services)

- Jackson (JSON processing)

- Java HTTP Client

- PostgreSQL

- JDBC

- Maven

- IntelliJ IDEA

<br>

**Getting Started**

1. Open the project in IntelliJ IDEA as a Maven project.

2. Configure the PostgreSQL databases.

3. Set the required database connection credentials.

4. Run Launcher.java to start the REST services and the graphical user interface.

<br>

**Project Scope**

The prototype uses local databases and REST web services to simulate Canvas, E-pok, Ladok and ITSAdmin databses.