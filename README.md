# mail-sender
Microservice to send email to website administrator.  

## Description  
Almost all of the portfolio websites on the web have a contact form, enabling the user to connect with the website owners. In most cases, this is the only part of the website that requires a backend. This application removes that dependency to build backend logic for each contact form by creating a SASS Microservice that can handle the requests from multiple websites. It exposes a single REST endpoint which receives the data and sends an email. It uses Firebase and Gmail SDKs for integration with external services to provide the required functionality. Each request coming to the service should have a valid captcha response string which is firstly verified to see if the request is valid. The service, then queries the Firestore to retrieve the template of the email and the address to which the email is to be sent. It will then apply the form data to the email template and will send the mail to the configured address.

## Database
The service uses Firestore provide by Google Firebase for storing the list of websites registered with it.  
`Collection` : `websites`  
Document structure  
`id` : `Website Url`  
Field | Type  | Description | Example
------|-------|-------------|--------
email| String | Email Address of the mail receiver |
mail-template | String | Email body template | Hi. ${name} with ${email} has asked a question regarding ${subject} - ${comments}

## Request structure  
Each request should have the following structure.
Field | Type  | Description |
------|-------|-------------|
site| String | Site url configured in firebase |
captcha | String | Captcha string |
formData | Object | Form data in key/value pairs. The service substitues matching keys in mail template with the values |


### Send email

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
* Google Firebase
* Google ReCaptcha
* Gmail

### License
[MIT](https://github.com/jijojames18/mail-sender/blob/master/LICENSE)

### Reference
* [Google Firebase](https://firebase.google.com/docs/admin/setup#java)
* [Gmail](https://developers.google.com/gmail/api/quickstart/java)
* [Google ReCaptcha](https://developers.google.com/recaptcha/docs/verify)
