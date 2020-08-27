# mail-sender
Microservice to send email to users.  

## Description  
This service exposes a single REST endpoint which receives the data and sends an email.  

### API
#### Send email

Sends email to the configured user

* **URL**

  /

* **Method:**

  `POST`
  
* **Data Params**

  **Required:**
 
   `site=[string]`  
   `formData=[object]`  
   `captcha=[string]`

* **Success Response:**

  * **Code:** 200 <br />
 
* **Error Response:**

  * **Code:** 500 NOT FOUND <br />
    **Content:** `{ error : "User doesn't exist" }`

  OR

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/users/1",
      dataType: "json",
      type : "POST",
      success : function(r) {
        console.log(r);
      }
    });

### Tech stack
* Java
* Spring Boot

### External Integrations
* Google FireBase
* Google ReCaptcha
* Gmail

### License
[MIT](https://github.com/jijojames18/mail-sender/blob/master/LICENSE)

### Reference
* [Google FireBase](https://firebase.google.com/docs/admin/setup#java)
