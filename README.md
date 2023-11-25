# Webby
A basic static web server using Java's build-in HttpServer.

## Purpose 
This project is made for educational purposes in my path of understanding how web servers works on Java.
Take in consideration this project was not designed for production.

## Building
You can build up this project using maven or just using Java.

- Requirements
    - Java 21
    - Maven 3.6.3 (Optional)

#### Java
````shell
javac -sourcepath src/main/java/ src/main/java/io/github/cuisse/webby/Application.java -d target/
jar cfe webby.jar io.github.cuisse.webby.Application -C target/ .
````

#### Maven
````shell
mvn clean package
````

## Running

#### Java
````shell
java -jar webby.jar index.html ./static http://localhost:8080 1
````
#### Maven
````shell
mvn exec:java -D"exec.args"="index.html ./static http://localhost:8080 1" -D"exec.mainClass"="io.github.cuisse.webby.Application"
````
For more information you can use the help command.
````shell
java -jar webby.jar --help
````

#### Parameters

| Index | Description                  | Default Value                |
|---|------------------------------|------------------------------|
| 0 | Index page                   | index.html                   |
| 1 | Resources directory          | ./static (current directory) |
| 2 | Server address               | http://localhost:8080        |
| 3 | Number of threads to be used | 3                            |

## Contribution
Feel free to contribute via pull requests. Some extra knowledge is always appreciated.