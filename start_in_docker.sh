#!/bin/bash

# prepare data
if [ -d ./reports ]; then
    rm -r ./reports
fi

# start selenoid
docker pull sskorol/selenoid_chromium_vnc:100.0 &&
docker-compose -f docker-compose-selenoid.yml up -d --build &&

# start tests
docker-compose up --build

# clear data
docker-compose -f docker-compose-selenoid.yml down -v --rmi all --remove-orphans
docker-compose down -v --rmi all --remove-orphans
docker rmi sskorol/selenoid_chromium_vnc:100.0
