# Build
mvn clean package && docker build -t com.cowrycode/PayrollSystem .

# RUN

docker rm -f MyHelloWorld || true && docker run -d -p 8080:8080 -p 4848:4848 --name PayRollSystem com.cowrycode/PayrollSystem
