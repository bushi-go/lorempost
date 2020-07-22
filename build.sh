#!/bin/bash

docker run --user $UID:$UID  -it --rm --name lorempost -v "$(pwd):/usr/src/lorempost" -w /usr/src/lorempost maven:3.6.3-jdk-11 mvn clean install
rm -rf "$(pwd)/ui/target"