FROM maven:3.6-openjdk-8
RUN mkdir -p mkdir /usr/storage/api
VOLUME /usr/storage/api
WORKDIR /usr/storage/api
EXPOSE 8080
EXPOSE 8787
