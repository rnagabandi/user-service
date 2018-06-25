# user-service
CRUD operations on User

Table of content

Installation:
  
    1) Install JAVA , Maven and Tomcat
  
    2) Clone the project
  
    3) run 'mvn clean install' command to generate the WAR file
  
Services Provided:

1) Add User

  End point: http://localhost:8080/user-service/webapi/service/addUser/
  Type: POST
  Headers:
          Accept:application/json
          Content-Type:application/json
  Request:
          
           {
              "firstName":"Raghav",
              "lastName":"Nagabandi",
              "email":"raghav@gmail.com",
              "address": [
                 {
                  "type":"Local", 
                  "city":"Bangalore",
                  "country":"India",
                  "zipcode":"560037"
                 },
                 {
                  "type":"Permanent", 
                  "city":"Bangalore",
                  "country":"India",
                  "zipcode":"560037"
                 }
              ]

            }
  
  2) Update User
  
       End point: http://localhost:8080/user-service/webapi/service/updateUser/
       Type: PUT
       Headers:
          Accept:application/json
          Content-Type:application/json
       Request:
              
              {
                  "firstName":"zc",
                  "lastName":"Kzxczxc",
                  "email":"anwesh@gmail.com",
                  "address": [
                     {
                      "type":"Local", 
                      "city":"Bangalore",
                      "country":"India",
                      "zipcode":"560037"
                     },
                     {
                      "type":"Permanent", 
                      "city":"Bangalore",
                      "country":"India",
                      "zipcode":"560037"
                     }
                  ]

              }
 
 3) GET User
  
       End point: http://localhost:8080/user-service/webapi/service/getUser/raghav@gmail.com
       Type: GET
       Headers:
          Accept:application/json
          Content-Type:application/json
          
                    
 4) Delete User
  
       End point: http://localhost:8080/user-service/webapi/service/deleteUser/anwesh@gmail.com
       Type: DELETE
       Headers:
          Accept:application/json
          Content-Type:application/json
       
