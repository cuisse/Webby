# Webby
A very simple static web server using Java build in HttpServer.

## Purpose 
This project is made for educational purposes in my path of understanding how web servers works on Java.
Take in consideration this project was not designed for production.

## Building
You can build up this project using Java compiler.

### Using Java
````shell
javac -sourcepath src/main/java/ src/main/java/com/webby/Application.java -d target/
jar cfe Webby.jar com.webby.Application -C target/ .
````

## Running
### Using Java
````shell
java -jar Webby.jar /index.html ./static http://localhost:8080 1
````

### Parameters

| Index | Description                 | Default Value              | Notes                                    |
|---|-----------------------------|----------------------------|------------------------------------------|
| 0 | Page main index             | /index.html                | Make sure you prefix your file using '/' |
| 1 | Resources directory         | {current directory}/static |                                          |
| 2 | Server address              | http://localhost:8080      |                                          |
| 3 | Amount of threads to be use | 3                          |                                          |

## Contribution
Feel free to contribute via pull requests. Some extra knowledge is always appreciate.