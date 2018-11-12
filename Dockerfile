FROM airhacks/glassfish
COPY ./target/MyHelloWorld.war ${DEPLOYMENT_DIR}
