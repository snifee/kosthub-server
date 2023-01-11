# Final Project Synrgy Batch 5

Server kost app

## Registration

**POST /v1/auth/register**
----
  Creates a new User and returns the success message.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    email: string,
    phone: string,
    password : string,
  }
```
* **Success Response:**  
  Code: 200  
  Content:  `User created`  

* **Error Response:**  
  Code: 405  
  Content:  `email is registered` 
  
## Login

**POST /v1/auth/login**
----
  Require email & password then returning jwt token.
* **URL Params**  
  None
* **Headers**  
  Content-Type: application/json  
* **Body**  
```
  {
    email: string,
    password : string,
  }
```
* **Success Response:**  
  Code: 200  
  Access_Token:  `{JWT Token}`  

* **Error Response:**  
  Code: 405  
  Content:  `Login Failed` 