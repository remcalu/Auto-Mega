# Install container with openjdk
FROM ubuntu:20.04
WORKDIR /project

# Copy the project into the docker container
COPY . .

# Update the list of commands that can be downloaded, and upgrade the current commands as well
RUN apt-get -y update
RUN apt-get -y upgrade

# Install java and mysql
RUN apt-get install -y openjdk-17-jdk
RUN apt-get install -y mysql-server

# Initialize MySQL user and database
RUN service mysql start && echo 'CREATE USER "admin"@"localhost" IDENTIFIED BY "admin";' | mysql && echo 'CREATE DATABASE automegaapi;' | mysql && echo 'GRANT ALL PRIVILEGES ON automegaapi.* TO "admin"@"localhost";' | mysql

# Clean the apt-get folder
RUN rm -rf /var/lib/apt/lists/*

# Open port 8081 (UI)
EXPOSE 8081/tcp

# Configuring the gradle wrapper permissions and build the project
RUN sed -i -e 's/\r$//' ./gradlew
RUN ./gradlew buildAll

# Get rid of unused files
RUN rm -rf /project/README.MD
RUN rm -rf /project/gradlew.bat
RUN rm -rf /project/requirements.txt
RUN rm -rf /project/docker
RUN rm -rf /project/dockerfile
RUN rm -rf /project/src/main/docker
RUN rm -rf /project/src/test
RUN rm -rf /project/build/reports
RUN rm -rf /project/build/libs
RUN rm -rf /project/build/scripts
RUN rm -rf /project/build/test-results
RUN rm -rf /project/build/generated
RUN rm -rf /project/build/distributions
RUN rm -rf /project/build/tmp

# Start the MySQL service, run the front-end, and run the back-end on container startup
CMD service mysql start & \
    ./gradlew frontendRun & \
    ./gradlew backendRun