# Estuate-assignemnt

Appraisal Management System Documentation

Overview

The Appraisal Management System is a Spring Boot application designed to manage employee appraisals and analyze category-wise deviations in ratings. It includes functionalities to fetch actual percentages of categories, identify deviations, and suggest revisions for balanced category distributions.

Project Structure

1. Application Entry Point

AppraisalApplication

Package: com.estuate.test

Description: The main entry point of the application.

Functionality: Bootstraps the Spring Boot application.

2. Controller

AppraisalController

Package: com.estuate.test.controller

Description: Handles HTTP requests for appraisal-related operations.

Endpoints:

GET /api/appraisal/actual-percentages: Fetches actual percentages of categories.

GET /api/appraisal/deviations: Retrieves category-wise deviations.

GET /api/appraisal/revisions: Suggests revisions for balancing ratings.

3. Entities

Category

Package: com.estuate.test.entities

Description: Represents a category with its standard and actual percentages.

Attributes:

categoryId: Character (Primary Key)

standardPercentage: double

actualPercentage: double

Employee

Package: com.estuate.test.entities

Description: Represents an employee with their rating.

Attributes:

employeeId: int (Primary Key)

employeeName: String

rating: Character

4. Repositories

CategoryRepository

Package: com.estuate.test.repo

Description: Provides CRUD operations for Category entities.

EmployeeRepository

Package: com.estuate.test.repo

Description: Provides CRUD operations for Employee entities.

5. Services

AppraisalService

Package: com.estuate.test.services

Description: Implements business logic for appraisals.

Key Methods:

getActualPercentages(): Retrieves actual percentages for all categories.

getDeviations(): Calculates deviations between actual and standard percentages for categories.

suggestRevisions(List<CategoryDeviation>): Suggests employee revisions to balance deviations.

getAdjustedEmployeeRatings(): Adjusts employee ratings to reduce deviations.

CategoryDeviation

Package: com.estuate.test.services

Description: Represents a deviation between the actual and standard percentages of a category.

Attributes:

category: Character

actualPercentage: double

standardPercentage: double

deviation: double

EmployeeRevision

Package: com.estuate.test.services

Description: Represents a suggested revision for an employee.

Attributes:

employee: Employee

action: String

Database Schema

category Table

Column Name

Data Type

Description

category_id

CHAR(1)

Primary key for the category

standard_percentage

DOUBLE

Standard percentage for the category

actual_percentage

DOUBLE

Actual percentage for the category

employee Table

Column Name

Data Type

Description

employee_id

INT

Primary key for employee

employee_name

VARCHAR

Name of the employee

rating

CHAR(1)

Employee's rating category

API Endpoints

1. Get Actual Percentages

Endpoint: GET /api/appraisal/actual-percentages

Response: List of Category objects with actual percentages.

2. Get Deviations

Endpoint: GET /api/appraisal/deviations

Response: List of CategoryDeviation objects with deviation details.

3. Get Revisions

Endpoint: GET /api/appraisal/revisions

Response: List of EmployeeRevision objects suggesting adjustments.

Business Logic

1. Deviation Calculation

Fetches all employees and categories.

Groups employees by their rating and calculates actual percentages.

Compares actual percentages with standard percentages to determine deviations.

2. Revision Suggestion

Identifies surplus and deficit categories based on deviations.

Suggests employee reassignments to balance deviations.

Ensures minimal disruption by prioritizing high deviations.

3. Rating Adjustment

Iteratively adjusts employee ratings to balance deviations.

Ensures all categories are within acceptable deviation thresholds.

Dependencies

Spring Boot: Application framework.

Spring Data JPA: Database interaction.

Jakarta Persistence: Entity management.

How to Run

Clone the repository.

Set up a MySQL database and update application.properties with connection details.

Build the project using Maven:

mvn clean install

Run the application:

mvn spring-boot:run

Access the API endpoints at http://localhost:8080/api/appraisal.

Future Enhancements

Add authentication and authorization for secure access.

Implement a UI for better visualization of deviations and revisions.

Enhance algorithms for more efficient balancing of deviations.

Introduce notifications for employees affected by revisions.

Contributors

Siddartha S J
