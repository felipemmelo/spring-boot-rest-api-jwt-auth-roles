# About the project

This is a very simple project demonstrating how to integrate Spring Boot, JWT, MySQL and role-based access. 

A basic understanding of Spring DI, Spring Boot, Spring Security, REST APIs, JPA Repositories, JWT Concepts and MySQL is required.

## Step 1. Restoring the database dump

For this example we will be using MySQL.

In the project root there is a file named **database_schema.sql**. This file contains a very simple schema with two tables, one for users and another for users' roles. 

There are two registered users: User 1 with username **user1** and password **pass1** and User 2 with username **user2** and password **pass2**.

The User 1 has to roles, **ADMIN** and **USER** whereas User 2 only has the role **USER**.

The passwords are encoded using the services provided by `services.password.PasswordService.java` which uses the secret contained in `src.main.resources.application.properties`.


## Step 2. Connecting to the database

Once the database schema is restored, you will need to change its credentials in `application.properties`.

The dependency necessary to connect to the database is:

`
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
`


## Step 3. Configuring the repository

We will be interacting with the database through JPA by using Hibernate as the implementation provider.

The entities representing the database tables * users * and * users_roles * are located in the package `model`.

The persistence context configuration is done by the class `persistence.PersistenceContext.java`.

The relation between `User` `UsersRoles` is mapped in such a way to load the roles a user has automatically when a user is loaded, thus, we are only creating a repository to access `User` instances. The repository can be found at `repository.UserRepository.java`.  
 
The dependency for this feature is:
 
`
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
`


## Step 4. Creating the API

We are going to create two API entries, one to be accessed in an insecure way (* /api/public/ *) and another one in a secure way (* /api/secure/ *). The API will work somewhat like an `echo`,  sending back the received value along with the security level of the request.

The methods implementing this API can be found in the class `controller.MainController.java`. 


## Step 5. Securing the API

We are now adding the JWT features to secure the API. We will be using [JJWT](https://github.com/jwtk/jjwt) for coping with JWTs.

First we create a minimal representation of a logged user. In this example this representation holds the user's username and roles. The code is in `beans.jwt.MinimalJWTUser.java`. 

Next, we create a service to convert a minimal user representation to a token and vice-versa. The code is in `security.jwt.JWTService.java`. 

We then create a bean to hold users credentials when the try to authenticate and a service to perform the authentication by using `UserRepository.java`. The former can be found at `beans.jwt.AuthenticationBean.java` and the later at `services.user.UserService.java`. 

Now, we create an authentication point in the API which will receive users' credentials and return authentication tokens if the credentials are valid. This authentication feature will make use of both, `UserService.java` to validate users' credentials against the database and `JWTService.java` to convert the credentials into JWTs. The code for this feature can be found at `controller.JWTAuthController.java`.

Next, we add a filter to restrict access to the secure API. Its code is defined in `security.JWTFilter.java` and what it does is to check if the incoming request contains an authentication token. In case it does, the user associated to the token is extracted along with her roles. This filter only intercepts URLs matching a pattern defined in its constructor.

Finally, we configure the filter chain and other access rules in `security.web.WebSecurityConfig.java`. First, we tell Spring Security to allow every incoming request to the public API by setting `web.ignoring().antMatchers(this.openApiPattern)` in the overridden method `configure(WebSecurity web)`. The `this.openApiPattern` is defined in `application.properties`. Next, we tell Spring to only allow users with role * ADMIN * to access the secure API by setting `antMatchers(this.secureApiPattern).hasRole("ADMIN")` in the overridden method `configure(HttpSecurity http)`.   

With this configuration in place, users can expect one of four possible outcomes for each request:

1. HTTP 401 (UNAUTHORIZED) in case no token is found in the request header.
2. HTTP 406 (NOT_ACCEPTABLE) in case a token was found but it is invalid.
3. HTTP 403 (FORBIDDEN) in case the token is valid but the user does not have the ADMIN role.
3. Request successfully processed.

## Step 6. Testing

There are three API entries to be tested, the public, the authorization and the secure.

### Step 6.1. Accessing the public API

To test the public API we can do: `curl http://localhost:8080/api/public/request/felipe`

From this request we expect the response: ** 'felipe' came from a UNsecure channel **.

### Step 6.2. Accessing the authorization API

If we change * public * by * secure * in the request above, thus trying to access the secure API without being previously authorized, we will receive an HTTP 401 in response.

To authenticate using the credentials from * User 2 * we can do the following:

`curl -X POST -H "Content-Type: application/json" -d '{"username":"user2","password":"pass2"}' http://localhost:8080/api/public/auth`

This request should return an valid JWT which we will use to issue requests against the secure API.

### Step 6.3. Accessing the secure API

To the previous request a response similar to this is expected: * eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyN2MyZTg2MC0yNjhmLTQ4MWItODY4NS1kNTIzNDkzYTIzMzAiLCJzdWIiOiJ1c2VyMSIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNDk4NDk2MDUzLCJleHAiOjE0OTg1ODI0NTN9.OArYFUGLqc2YMLogFVFUkpvLbpj3dOWq9LeM3sPruT-JybYZcriL8sK_kwrTvKUXjXSvZRT4JRp2cnQCAi3XxQ *.

That is a valid JWT which we can now use to access the secure API by issuing a request like this:

`curl -H "x-auth token:eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyN2MyZTg2MC0yNjhmLTQ4MWItODY4NS1kNTIzNDkzYTIzMzAiLCJzdWIiOiJ1c2VyMSIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWF0IjoxNDk4NDk2MDUzLCJleHAiOjE0OTg1ODI0NTN9.OArYFUGLqc2YMLogFVFUkpvLbpj3dOWq9LeM3sPruT-JybYZcriL8sK_kwrTvKUXjXSvZRT4JRp2cnQCAi3XxQ" http://localhost:8080/api/secure/request/felipe`

However, although the JWT is valid, the authenticated user is not an ADMIN, thus, an HTTP 403 is expected as response. In order to be able to access the secure API, we need to repeat the token generation process and provide the credentials for * User 1 * whom has the ADMIN role.

After repeating the process, since we have sent a valid authentication token from an ADMIN user, we now have access to the secure API, thus, the response to the previous request must be: ** 'felipe' came from a secure channel. **.