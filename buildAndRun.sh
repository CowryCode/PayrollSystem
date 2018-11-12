#!/bin/sh
mvn clean package && docker build -t com.cowrycode/MyHelloWorld .
docker rm -f MyHelloWorld || true && docker run -d -p 8080:8080 -p 4848:4848 --name MyHelloWorld com.cowrycode/MyHelloWorld 
