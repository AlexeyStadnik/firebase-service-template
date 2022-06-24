FROM openjdk:17
VOLUME /tmp
COPY build/libs/service-template-0.1.jar app.jar
EXPOSE 8080

ENV TIMEZONE="UTC"

ENV JAVA_OPTIONS="-Duser.timezone=${TIMEZONE}"


CMD java ${JAVA_OPTIONS} ${OPTIONS} -jar /app.jar
