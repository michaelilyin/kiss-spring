#!/bin/bash

echo $DOCKER_TOKEN | docker login --username michaelilyin --password-stdin

docker push michaelilyin/kiss-spring-demo-goods:latest