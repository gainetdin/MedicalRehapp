# Medical Rehapp

Web application that simulates the operation of an information system of a medical rehabilitation institution. The application consists of two parts: the first part is a document management system for the clinic staff, and the second part is an [information board with upcoming events](https://github.com/gainetdin/MedicalRehappBoard). The application has data storage on the server side and graphical interface.

## Application description

Web application has four types of user roles: user, nurse, doctor and admin. User can view pages with general information (used treatments, list of doctors of the clinic). Nurse can view all events list and can edit them (mark as completed or cancel with reason emphasizing). Doctor additionally can add new patients, edit, discharge and take back to treatment old ones; add, edit, cancel prescriptions. Admin can change roles of users; add new treatments and delete them.

Events are creating from prescription automatically by treatment period and time pattern (daily/weekly, treatments per day). All created events cancel if prescription changes/cancels or doctor discharges the patient.

Every time when events change, the main application sends a message with a new list of events to the information board. Information board updates events datatable immediately by consuming this message.

## Used technologies and frameworks

* Java 11
* Maven
* Spring Framework
* Spring Boot
* Tomcat
* PostgreSQL
* Hibernate
* Spring Data
* Flyway
* Spring Security
* JUnit
* Mockito
* Lombok
* Mapstruct
* Thymeleaf
* Bootstrap
* Javascript

## UI

New prescription page (logged in as doctor):
![Picture1](https://user-images.githubusercontent.com/92216592/163869171-2eb6acf8-1fa0-46de-a466-288805bf9672.png)

Events page (logged in as nurse):
![Picture2](https://user-images.githubusercontent.com/92216592/163869483-bc8ed816-db07-4a7b-b958-861b480fa75f.png)


