CSC 460 - Program 4
Team Members:
  - Laura Shoemake
  - Yebin Brandt
  - Chris Quevedo
  - Hanna Veldhuizen

Operation Instructions

This project runs through the port 28080. As in the run instructions, first tunnel out of lectura to local through the command `ssh -L 28080:localhost:28080 netid@lectura.cs.arizona.edu`. To run the application, enter `mvn spring-boot:run` from the src directory.


Adding a Record

To insert a record into one of the tables, click any of the buttons under the "Add a Record" heading. These are organized by database, so to insert a Patient, click "Add Patient". This will take you to a new page where you can fill out all attributes for the new record then click "Submit". The form will notify you if you have left any of the required attributes blank before letting you submit.


Deleting a Record
Select "Delete <table-name>" to delete a record from <table-name>. Then, fill out the DOB, firstName, and lastName for the record you would like to delete then click "Submit". 


Updating a Record

Select "Update <table-name>" to update a record from <table-name>. In the form field labeled "ID", enter the ID of the record you wish to update. Then, enter any new attributes in the fields following. Only the non-NULL fields will be updated to the newly entered value. The fields shown are the only fields available for updating based on the table you selected. 


Query #1

Navigate to the "Query #1" heading, and fill out the fields in that section. These fields are "First Name", "Last Name", and "DOB" for a patient record. After submitting, you will be routed to a page that lists the patient records that match the name and date of birth you entered.


Query #2

Under the "Query #2" heading, enter a department name and a list of Doctors who work in that department will be returned to you.


Query #3
Because Query #3 requires no input from the user, simply click the "List Patients" button to view a list of patients who are currently checked in to the hospital and expected to stay for at least five days with an outstanding fee to be paid.


Query #4

Because Query #4 requires no input from the user, simply click the "List Patients" button under this query to view a list of patients taking medicine, the receptonist who schedules their appointments, and the pharmacist who prescribed their medicine.


Workload

Hanna was responsible for the front-end. This included creating the templates and displaying the query results. 
Laura was in charge of the databases. She created all necessary tables and reduced them to third normal form. 
Yebin designed the controllers and objects for the insert/update/delete operations. 
Chris was in charge of querying and the controllers associated with these queries.
