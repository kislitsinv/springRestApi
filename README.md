# Report on what's done
Well, this is a fully-functional REST API service, that's doing all the things described in a task.
Architecture example was taken from one of the production microservices of current employer.

**

We have two controllers to divide requests logic, and in a global way - we have all service divided in a different
pipelines (entity -> repo -> services -> dtos and stuff -> controllers w/ request and response classes). 
One pipeline per one essence - in our case these are User and Loan.

H2 database was connected (via file mode) and start-sqls were written and implemented (for a data being in tables after app is launched).
One moment that I don't like - there are no GenericResponse class (f.e., {"code": 0, "errorMessage": null, "responseBody" : "..."}) 
or HttpEntity in returns, so in a  better way it must be also implemented.

<b>Java 8, Spring Boot 2.*, Maven project (via Spring Initializr)</b>

<b>Project launch:</b>

mvn clean install ("mvnw" w/ wrapper installed)<br />
mvn spring-boot:run (or just start RestapiApplication.java via IDE)<br />
http://localhost:8080 (GET request on */users* could be done via browser)
<br />

- POST requests (/add, /iou) were tested via PostMan.<br />
- Some basic tests on WEB-SIDE were also written (pls see or execute */src/test/java/xyz/vkislitsin/backend/WebTests.java*)

<br />
<i>WBR, Vladimir Kislitsin</i>

****

### Brief

Implement a RESTful API for tracking IOUs.

Four roommates have a habit of borrowing money from each other frequently, and have trouble remembering who owes whom, and how much.

Your task is to implement a simple [RESTful API](https://en.wikipedia.org/wiki/Representational_state_transfer) that receives [IOU](https://en.wikipedia.org/wiki/IOU)s as POST requests, and can deliver specified summary information via GET requests.

### API Specification

#### User object
```json
{
  "name": "Adam",
  "owes": {
    "Bob": 12.0,
    "Chuck": 4.0,
    "Dan": 9.5
  },
  "owed_by": {
    "Bob": 6.5,
    "Dan": 2.75,
  },
  "balance": "<(total owed by other users) - (total owed to other users)>"
}
```

#### Methods

| Description | HTTP Method | URL | Payload Format | Response w/o Payload | Response w/ Payload |
| --- | --- | --- | --- | --- | --- |
| List of user information | GET | /users | `{"users":["Adam","Bob"]}` | `{"users":<List of all User objects>}` | `{"users":<List of User objects for <users> (sorted by name)}` |
| Create user | POST | /add | `{"user":<name of new user (unique)>}` | N/A | `<User object for new user>` |
| Create IOU | POST | /iou | `{"lender":<name of lender>,"borrower":<name of borrower>,"amount":5.25}` | N/A | `{"users":<updated User objects for <lender> and <borrower> (sorted by name)>}` |

### Other Resources:
- https://restfulapi.net/
- Example RESTful APIs
  - [GitHub](https://developer.github.com/v3/)
  - [Reddit](https://www.reddit.com/dev/api/)
## Exception messages

Sometimes it is necessary to raise an exception. When you do this, you should include a meaningful error message to
indicate what the source of the error is. This makes your code more readable and helps significantly with debugging. Not
every exercise will require you to raise an exception, but for those that do, the tests will only pass if you include
a message.

To raise a message with an exception, just write it as an argument to the exception type. For example, instead of
`raise Exception`, you should write:

```python
raise Exception("Meaningful message indicating the source of the error")
```

## Test your app

Implement some test on web and/or business logic.


### Evaluation Criteria

- Show us your work through your commit history
- Completeness: did you complete the features? Are all the tests running?
- Correctness: does the functionality act in sensible, thought-out ways?
- Maintainability: is it written in a clean, maintainable way?


### CodeSubmit

Please organize, design, and document your code as if it were going into production - then push your changes 
to the master branch. After you have pushed your code, you may submit the assignment on the assignment page.

All the best and happy coding,

The CANDR Team
