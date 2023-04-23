FROM openjdk:17
VOLUME /tmp
COPY build/libs/newsroom-service-1.0.jar app.jar
EXPOSE 8080

ENV TIMEZONE="UTC"

ENV JAVA_OPTIONS="-Duser.timezone=${TIMEZONE}"


CMD java ${JAVA_OPTIONS} ${OPTIONS} -jar /app.jar
