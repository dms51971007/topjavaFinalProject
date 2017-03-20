**Финальный проект TOPJAVA**

Задание:
Design and implement a JSON API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

при выполнении использованы следующие технологии:

Maven/Spring/Security/JPA(Hibernate)/Rest(Jackson)
база данных HSQLDB

для удобства тестирования создан файл PopulateDB.sql заполнения базы начальными данными

интерфейс для управления пользователями не предусмотрен

**Описание API**


пункты 1,2 доступны пользователям с ролью ROLE_ADMIN, пункты 3 только пользователям ROLE_USER

_1. Рестораны_

Получить список ресторанов:

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/restaurants

Получить ресторан по ID:

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/restaurant/300010

Создать ресторан

curl -H "Content-Type: application/json" -X POST -d "{\"name\":\"Test Value\"}" http://localhost:8080/rest/admin/restaurant --user admin@gmail.com:admin

Изменить ресторан

curl -H "Content-Type: application/json" -X PUT -d "{\"name\":\"Test Value1\", \"id\":\"300010\"}" http://localhost:8080/rest/admin/restaurant --user admin@gmail.com:admin

Удалить ресторан

curl --user admin@gmail.com:admin -s -v -X DELETE http://localhost:8080/rest/admin/restaurant/300011

_2. Меню ресторана_

получить меню ресторана на дату, если дата не указана то на текущую дату

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/menus/300010?date=2017-03-10

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/menus/300010

получить элемент меню по ID

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/menu/200010

удалить элемент меню по ID

curl --user admin@gmail.com:admin -s -v -X DELETE http://localhost:8080/rest/admin/menu/200010

добавить элемент меню по ID ресторана, если указывать поля data то подставится текущая

curl -H "Content-Type: application/json" -X PUT -d "{\"name\": \"pizzza\",\"price\": 5.11,\"data\": \"2017-03-10\",\"restaurantId\": 300010}" http://localhost:8080/rest/admin/menu --user admin@gmail.com:admin

изменить элемент меню по ID ресторана, если указывать поля data то подставится текущая

curl -H "Content-Type: application/json" -X POST -d "{\"name\": \"pizzza\",\"price\": 555.11,\"data\": \"2017-03-10\",\"restaurantId\": 300010}" http://localhost:8080/rest/admin/menu --user admin@gmail.com:admin

_3. Голосование_

проголосовать/изменить голос пользователя до 11:00, после 11:00 возможно только голосование

curl --user user@yandex.ru:password -s -v -X POST http://localhost:8080/rest/votes/300011

получить результаты голосования на дату, если дата не указана то за текущую

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/votes/results/?date="2017-03-17"

получить список ресторанов

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/restaurants

получить меню ресторана если дата не указана то на текущую дату

curl --user admin@gmail.com:admin -s -v -X GET http://localhost:8080/rest/admin/menus/300010/?date="2017-03-17"


