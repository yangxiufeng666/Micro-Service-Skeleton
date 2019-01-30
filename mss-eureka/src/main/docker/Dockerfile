FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD Micro-Service-Skeleton-Register.jar Micro-Service-Skeleton-Register.jar
RUN sh -c 'touch /Micro-Service-Skeleton-Register.jar'
ENTRYPOINT ["sh", "-c" , "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /Micro-Service-Skeleton-Register.jar" ]