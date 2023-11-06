# Money transfer

Это сервис, который предоставляет интерфейс для перевода денег с одной карты на другую по заранее описанной
[спецификации](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml).
Запрос к сервису отправляются с FRONT код которого находится в директории webapp.

## Запуск приложения

Приложение работает на порту, заданном в application.properties.
Перед запуском необходимо выполнить команду `gradle build` для сборки jar файла приложения. 
После этого в корне проекта необходимо выполнить команду `docker-compose up`  в результате чего запустится Backend и Frontend<br>
Backend будет доступен по адресу http://localhost:5500 <br>
Frontend будет доступен по адресу http://localhost:3000/card-transfer

## Запуск тестов

### Тестирование контроллера

По пути /src/test/java/ru/netology/moneytransferservice/controller находятся тесты на контроллеры. При запуске тестов
при помощи testContainers поднимается контейнер с приложением money_transfer_back. Чтобы тесты запустились необходимо чтобы
на запускаемой машине присутствовал image c приложением money_transfer_back:latest. Image будет собран при запуске docker-compose.

### Тестирование сервиса

По пути /src/test/java/ru/netology/moneytransferservice/service находятся тесты на сервис.