FROM tomcat:8.5-jdk8-openjdk
ADD target/release-management-api-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8076
CMD ["catalina.sh", "run"]