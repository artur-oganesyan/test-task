#!/bin/bash

if [ -d ./reports ]; then
    rm -r ./reports/*
fi

./gradlew clean test --no-daemon
./gradlew allureReport --no-daemon && cp -r ./build/reports/allure-report/* ./reports/