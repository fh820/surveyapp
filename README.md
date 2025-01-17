# SurveyApp

SurveyApp is a web-based survey application that allows users to create, manage, and respond to surveys. It provides functionality for both users and administrators, supporting features like user authentication, survey creation, question management, and response tracking. The app is built using Java, Spring Boot, MongoDB, and Spring Security for authentication and authorization.

## Features

- **User Registration & Login**: Users can register, log in, and manage their account.
- **Survey Creation & Management**: Admins can create surveys with multiple-choice questions, update existing surveys, and delete surveys.
- **Question Management**: Admins can add, update, or delete questions within surveys.
- **Response Submission**: Users can submit responses to surveys and view their past responses.
- **Role-Based Access**: Admins have full access to survey and user management, while regular users can only participate in surveys.

## Tech Stack

- **Backend**: Java, Spring Boot, Spring Security
- **Database**: MongoDB
- **Authentication**: JWT (JSON Web Token)
- **Web Framework**: Spring Data MongoDB

## Setup Instructions

### Clone the repository:

To clone the project, use the following command:

```bash
git clone https://github.com/fh820/surveyapp.git
```
# Install Dependencies:
Make sure you have Java and Maven installed on your system. Navigate to the project directory and run:
```bash
mvn clean install
```
MongoDB:
Ensure you have MongoDB running locally or use a cloud service (e.g., MongoDB Atlas).
Update the application.properties file with the appropriate MongoDB URI.


# Run the Application:
You can run the application using Maven:

```bash
mvn spring-boot:run
```

The application will be available at http://localhost:8080.

# API Documentation

## 1. User APIs

### Register a New User
- **Endpoint**: `POST /api/auth/register`
- **Description**: Register a new user by providing a username, password, and role.
- **Request Body**:
    ```json
    {
      "username": "john_doe",
      "password": "password123",
      "role": "USER"
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**: User is successfully created.

---

### Login User
- **Endpoint**: `POST /api/auth/login`
- **Description**: Authenticate a user and generate a JWT token for future requests.
- **Request Body**:
    ```json
    {
      "username": "john_doe",
      "password": "password123"
    }
    ```
- **Response**:
    ```json
    {
      "token": "JWT_TOKEN_HERE"
    }
    ```
    - **Status**: `200 OK`
    - **Token**: A JWT token that can be used in the `Authorization` header for subsequent requests.

---

## 2. Survey APIs

### Create a Survey
- **Endpoint**: `POST /api/surveys`
- **Description**: Create a new survey with a list of questions.
- **Request Body**:
    ```json
    {
      "name": "Customer Satisfaction Survey",
      "questions": [
        {
          "text": "How satisfied are you with our product?",
          "options": ["Very satisfied", "Satisfied", "Neutral", "Dissatisfied", "Very dissatisfied"],
          "multipleChoice": false
        }
      ]
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**: The newly created survey's ID and details.

---

### Get All Surveys
- **Endpoint**: `GET /api/surveys`
- **Description**: Fetch a list of all surveys.
- **Response**:
    ```json
    [
      {
        "id": "surveyId1",
        "name": "Customer Satisfaction Survey",
        "questions": [...]
      },
      ...
    ]
    ```
    - **Status**: `200 OK`
    - **Body**: An array of surveys with details like survey ID, name, and questions.

---

### Get Survey by ID
- **Endpoint**: `GET /api/surveys/{surveyId}`
- **Description**: Fetch a specific survey by its ID.
- **Response**:
    ```json
    {
      "id": "surveyId1",
      "name": "Customer Satisfaction Survey",
      "questions": [...]
    }
    ```
    - **Status**: `200 OK`
    - **Body**: Details of the survey with the given `surveyId`.

---

## 3. Response APIs

### Submit a Response
- **Endpoint**: `POST /api/responses`
- **Description**: Submit responses to a survey with corresponding answers for each question.
- **Request Body**:
    ```json
    {
      "surveyId": "surveyId1",
      "questionResponses": [
        {
          "questionId": "questionId1",
          "response": "Very satisfied"
        }
      ]
    }
    ```
- **Response**:
    - **Status**: `201 Created`
    - **Body**: Confirmation of the submitted response.

---

### Get All Responses for a Survey
- **Endpoint**: `GET /api/responses/survey/{surveyId}`
- **Description**: Fetch all responses submitted for a specific survey.
- **Response**:
    ```json
    [
      {
        "responseId": "responseId1",
        "surveyId": "surveyId1",
        "questionResponses": [
          {
            "questionId": "questionId1",
            "response": "Very satisfied"
          }
        ]
      }
    ]
    ```
    - **Status**: `200 OK`
    - **Body**: A list of all responses for the survey identified by `surveyId`.

---

### Summary of Endpoints

| **Endpoint**                        | **HTTP Method** | **Description**                      |
|-------------------------------------|-----------------|--------------------------------------|
| `/api/auth/register`                | `POST`          | Register a new user                 |
| `/api/auth/login`                   | `POST`          | Login and get a JWT token           |
| `/api/surveys`                      | `POST`          | Create a new survey                 |
| `/api/surveys`                      | `GET`           | Get all surveys                     |
| `/api/surveys/{surveyId}`           | `GET`           | Get survey details by ID            |
| `/api/responses`                    | `POST`          | Submit responses to a survey        |
| `/api/responses/survey/{surveyId}`  | `GET`           | Get all responses for a survey      |



# Future Features

__Frontend Integration__
User Interface (UI): Build a user-friendly frontend for better interaction with the surveys. The frontend could be built using modern JavaScript frameworks like React.js, providing dynamic, interactive, and responsive pages for survey creation and submission.
Survey Dashboard: A dedicated dashboard for both admins and users to view and manage surveys. Admins could access statistics and manage the survey lifecycle, while users can see ongoing and past surveys.
User Features
Resume Survey: Allow users to pause and resume their survey responses. This would let users leave a survey and return later without losing their progress, enabling a more flexible experience for longer surveys.
Survey Progress Indicator: Include a progress bar that shows users how much of the survey they've completed and how many questions remain.

__Backend Features__
Versioning for Surveys: Implement version control for surveys. Admins could update surveys and track different versions of the same survey, allowing users to respond to the most recent version while keeping the history intact for analysis.
Survey Analytics: Add an analytics dashboard for survey results, where admins can view data insights, such as average scores, trends, and distributions of responses.


__Performance & Scalability__
Caching: Implement caching for frequently accessed survey data to improve performance, especially as the number of surveys and responses grows.
Load Balancing: Introduce load balancing techniques to ensure the app can handle large volumes of users and survey submissions.
