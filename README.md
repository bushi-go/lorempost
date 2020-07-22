# Lorempost

## Table of Contents

- [About](#about)
- [Getting Started](#getting_started)

## About <a name = "about">Lorempost</a>

This project aims to deliver the 50 first posts in the https://jsonplaceholder.typicode.com/posts api through a spring boot/java based api, over a react-redux front-end


## Getting Started <a name = "getting_started"></a>

You need first to clone the project (no kidding).

Once that's done, you have several options. If you have vscode installed, you can install [the Remote Development Extension](https://code.visualstudio.com/docs/remote/remote-overview) and use the .devcontainer/* files in the ui and the api.
This setup will allow you to start developing over the project in no time.

Otherwise, you will need to install maven 3.6.3, java 11, node v14.5.0 and yarn v1.22.4 on your computer.

Afterwards, get your favorite IDE, and happy coding !

If you want to test the project, you have three options :
   
   - build the project locally using your installation of maven and java at the requested version
      To do that you will need to add a .env file in the ui/ folder, and to add to it the following :
    
      ```
         NODE_ENV=development
         REACT_APP_API_URL=http://localhost:8080/api
      ```
      

   This config suppose you are running the project in local on your computer.
    After adding the .env, just run mvn clean install in the root folder of the project 
    The project will build the front using frontend-maven-plugin, so you don't need to install yarn and node,
    then will copy the output of the build to the relevant folder in api, and then build the executable jar you seek.
   
   - build the project locally using the build.sh script provided.
    This script requires you to have docker installed on your computer, and will build the project using the maven:3.6.3-jdk-11 docker image. 
    After the build has run, you will find the artefact in /api/target
    Just run ./build.sh and afterward launch the jar with `̀java -jar [path to the jar]`
   
   - build the Docker image associated with the project running `̀docker build -t [a name] . `in the root folder of the project. Docker will build the image in a multi-stage fashion, and provide you with an image you can run with docker run (don't forget to publish the 8080 port to your host)      

### Prerequisites

Depending on how you choose to build and run, Docker, maven +java and if you want to dev on it, either vs code or maven +java and node + yarn

