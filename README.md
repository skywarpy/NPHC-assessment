# NPHC-assessment
REST API developed for NPHC assessment using Spring

To get started, import into Eclipse and run as Java application. Manual testing was done using Postman to simulate the GET, POST, PATCH, PULL and DELETE requests.


##Design choices

A H2 database was used as an in-memory database, to avoid actually initialising a MYSQL database on the system's hard drive, since this is a test application.

The salary of employees are saved as BigDecimals, since that data format is more precise than a Double. Employees whose salaries are of low scale (e.g. 1.50) are cast as more BigDecimals with a higher scale (e.g. 1.500). This is to accomodate the storage of employees who have higher scaled salaries (e.g. 40.000) since the database has to store BigDecimals with the same level of precision and scale.

I've split the RestControllers into 4 (GET, POST, PATCH/PULL, DELETE) to deal with the various request types, to make it easier to track if the API met the functionalities that were specified by the assessment document.
