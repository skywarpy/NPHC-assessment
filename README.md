# NPHC-assessment
REST API developed for NPHC assessment using Spring.

To get started, import into Eclipse and run as Java application. Manual testing was done using Postman to simulate the GET, POST, PATCH, PULL and DELETE requests.

Requires the Lombok plugin for Eclipse.


CSV files that I used for testing are included in this repository.

## Guide for uploading the CSV files to check functionality of POST:

All files were POSTed with Postman to http://localhost:8080/users/upload with the key "file".

### 1. Uploading to an empty database:
POST the file "CSVTest_UploadWithEmptyDatabase.csv". It should return HTTP Status 201 with the response *"message": "Data has been successfully updated using CSVTest_UploadWithEmptyDatabase.csv"*.

### 2. Uploading a CSV file with no changes to the database:
Do step 1 first to populate the database.
POST the file "CSVTest_Identical.csv". It should return HTTP Status 200 with the response *"message": "Success but no data updated"*.

### 3. Uploading a CSV file with comments to the database:
POST the file "CSVTest_commentedline.csv". If you have done step 1, it should return HTTP Status 200 with the response *"message": "Success but no data updated"*. Else, it will return HTTP Status 201 with the response *"message": "Data has been successfully updated using CSVTest_UploadWithEmptyDatabase.csv"*.

### 4. Uploading a CSV file with an invalid salary to the database:
POST the file "CSVTest_NegativeSalary.csv". It should return HTTP Status 400 with the response *"message": "Invalid salary"*.

### 5. Uploading a CSV file with an invalid date format to the database:
POST the file "CSVTest_WrongDateFormat.csv". It should return HTTP Status 400 with the response *"message": "Invalid Date"*.

### 6. Uploading a CSV file that contains duplicate IDs to the database:
POST the file "CSVTest_duplicateIDs.csv". It should return HTTP Status 400 with the response *"message": "Duplicate IDs in CSV"*.


## Design choices

A H2 database was used as an in-memory database, to avoid actually initialising a MYSQL database on the system's hard drive, since this is a test application. A tradeoff of this is that you will have to either POST a CSV file to populate the database, or write a data.sql file to populate the database on start-up. I chose not to write a data.sql file since I wanted to test the functionality of POSTing a CSV when the database was empty.

The salary of employees are saved as BigDecimals, since that data format is more precise than a Double, making it suitable for applications dealing with money. Employees whose salaries are of low scale (e.g. 1.50) are cast as more BigDecimals with a higher scale (e.g. 1.500). This is to accomodate the storage of employees who have higher scaled salaries (e.g. 40.000) since the database has to store BigDecimals with the same level of precision and scale.

I've split the RestControllers into 4 (GET, POST, PATCH/PULL, DELETE) to deal with the various request types, to make it easier to track if the API met the functionalities that were specified by the assessment document.


## Potential changes

It might be better to store the salaries as Strings in the database, then cast them to BigDecimals whenever a comparison is needed. The current implementation requires us to know the most granular degree of scale possible (for the test data, it was a scale of 3), and set it in the database. As such, if there were to be another set of data with a more precise scale (e.g. 4) to be stored into the current database, it would lose one level of precision.

Storing as Strings avoids this problem, and should we need to utilise numerical comparisons, we can use the CAST query in JPQL to cast the Strings as BigDecimals.


