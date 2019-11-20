# WorkManagementSystem
Work distribution service that can distribute tasks to agents.

## Prerequisites 
 Apache Maven 3.5.0 or higher
 Java 1.8 or higher

---

## Build the artifact

Use `mvn clean install` command to build project

---

## Database setup

For information on how to setup the postgres database for the application and create the database, see below:

# Configuring Postgres database in local.
 
 Refer : https://www.postgresql.org/docs/9.3/tutorial-install.html
 
Create the database called workmanagementsystem. Create database with following query. 

     create database workmanagementsystem;
     create schema public;
     
Update following properties in applicaiton.properties file 
     
     spring.datasource.url=jdbc:postgresql://localhost:5432/workmanagementsystem
     spring.datasource.username={userName}
     spring.datasource.password={password}
     
     

## Run the applications

Execute following command to run the application by going in the directory.

    mvn package && java -jar target/WorkManagementSystem-1.0-SNAPSHOT.jar

## Services information

	1) Create a new Task
		Endpoint : http://localhost:8080/workmanagement_service/tasks
		Request Method: POST
		Request Headers : Authorization:Basic YWRtaW46YWRtaW4=
						  Content-Type:application/json
		Request Body:
		 {
			"skillNames": ["Java", "UI"],
			"priority" : "low",
		 	"taskStatus":"Not_Started"
		}
		Response: 
		{
		    "taskId": 16,
		    "agentId": 8,
		    "skillIds": [
			1,
			2
		    ],
		    "skillNames": [
			"Java",
			"UI"
		    ],
		    "priority": "low",
		    "taskStatus": "Not_Started"
		}

	2) Update Task status to Completed
		Endpoint : http://localhost:8080/workmanagement_service/tasks/{taskId}/status/Completed
		Request Method: PUT
		Request Headers : Authorization:Basic YWRtaW46YWRtaW4=
						  Content-Type:application/json
		Request Body:
		Response: 200 OK
		
	3) Get all agents with the tasks currently assigned to them if any
			Endpoint : http://localhost:8080/workmanagement_service/agents
			Request Method: GET
			Request Headers : Authorization:Basic YWRtaW46YWRtaW4=
						  Content-Type:application/json
			Request Body:
			Response: 
			[
        {
          "id": 3,
          "name": "Vivek",
          "skillIds": [
            1,
            7
          ],
          "taskids": [
            4
          ]
        },
        {
          "id": 1,
          "name": "Sam",
          "skillIds": [
            3,
            5,
            7
          ],
          "taskids": [
            2
          ]
        },
        {
          "id": 11,
          "name": "Mahesh",
          "skillIds": [
            1
          ],
          "taskids": [
            3
          ]
        },
        {
          "id": 6,
          "name": "Ganesh",
          "skillIds": [
            4,
            5,
            6
          ],
          "taskids": [
            7,
            1
          ]
        },
        {
          "id": 5,
          "name": "Mark",
          "skillIds": [
            2,
            5,
            7
          ],
          "taskids": []
        },
        {
          "id": 2,
          "name": "Bob",
          "skillIds": [
            1,
            2,
            3
          ],
          "taskids": []
        }
      ]




