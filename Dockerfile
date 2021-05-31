FROM openjdk:8-jre-alpine
LABEL maintainer="tmwaura@mfs.co.ke"
RUN apk add --no-cache bash
ENV TZ=Africa/Nairobi
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8888
ADD target/*.jar mymobi-users-service.jar
CMD /bin/sh -c 'touch /mymobi-users-service.jar'
ENTRYPOINT ["java","-Xmx256m","-Djava.security.egd=file:/dev/./urandom","-jar","/mymobi-users-service.jar"]