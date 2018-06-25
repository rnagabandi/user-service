# user-service
CRUD operations on User

Table of content

Installation:
  
    1) Install JAVA , Maven and Tomcat
  
    2) Clone the project
  
    3) run 'mvn clean install' command to generate the WAR file
  
Services Provided:

1) Add User

    a) End point: http://localhost:8080/user-service/webapi/service/addUser/
    
    b) Type: POST
    
    c) Headers:
    
          Accept:application/json
          Content-Type:application/json
  
    d) Request:
          
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
  
        a) End point: http://localhost:8080/user-service/webapi/service/updateUser/
        
        b) Type: PUT
        
        c) Headers:
          Accept:application/json
          Content-Type:application/json
       
       d) Request:
              
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
  
        a) End point: http://localhost:8080/user-service/webapi/service/getUser/raghav@gmail.com
        
        b) Type: GET
        
        c) Headers:
          Accept:application/json
          Content-Type:application/json
          
                    
 4) Delete User
  
        a) End point: http://localhost:8080/user-service/webapi/service/deleteUser/anwesh@gmail.com
       
        b) Type: DELETE
        
        c) Headers:
          Accept:application/json
          Content-Type:application/json
 
 POSTMAN collection link:
 
 https://www.getpostman.com/collections/41375a1f9f527a3a7380
 
 
