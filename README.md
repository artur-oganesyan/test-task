> Примечание  
> Cпециально не стал в тестах использовать JS-инъекцию, чтобы поведение было более приближено к реальному пользователю.  
> Но реализация через JS есть в `utils/JSExecutor`

## Run in Docker-Compose with Selenoid
```shell
chmod +x start_in_docker.sh
./start_in_docker.sh
```
Selenoid UI: http://0.0.0.0:8080/#/

## Local run
```shell
chmod +x start.sh
./start.sh
```

## Report
Allure report is generated in directory `/reports`.   
When run in docker, the generation may take a few minutes.
