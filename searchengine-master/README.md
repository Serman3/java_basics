# SearchEngine ![](img\1655.gif)
## Описание:
Данный поисковый движок представляет из себя
**Spring-приложение** с реализацией простого 
**REST API**, работающее с
локально установленной базой данных **MySQL**,
имеющее простой веб-интерфейс,
через который им можно управлять и получать 
результаты поисковой выдачи, используя поле поиска.

![](img\search.png)<br/>
### Как же это работает? Спросите вы ![](img\3.gif)
Для того чтобы пользователь получал информацию по запросу, 
для начала реализовали:
- индексацию страниц сайта в многопоточном режиме,
используя рекурсивный обход ссылок с помощью
ForkJoinPool. Индексация происходит в отдельном 
потоке для каждого сайта;
- получение html контента каждой страницы с помощью
библиотеки Jsoup;
- извлечение из контента страниц слова, преобразовывание
их в леммы (исходные формы слов), считая количество
вхождений каждой леммы в текст и сохранением этой
информации в базу данных.
### Индексация:
Вкладка Management отвечает за управление процессом
индексации. Индексировать возможно либо отдельно
каждый сайт, введя его в поле. Либо начать 
индексацию списка сайтов, указанных в
**конфигурационном файле - application.yaml**;

![](img\management.png)
### Статистика:
Выводится во вкладке Dashboard.
Общая, и отдельно для каждого сайта в соответствующих 
вкладках. А так же информационный статус процесса.

![](img\dashbord.png)
### Запуск приложения:
Для работы приложения необходимо:
- установить MySQL;
- настроить соединение с базой данных по 
конфигурации - application.yaml.
- логирование процесса парсинга настроено так,
что будут создаваться файлы infoParsing.log и 
errorParsing.log 
- запуск из командной строки `C:\Users\User\Desktop> java -jar "Путь до файла\SearchEngine-1.0-SNAPSHOT.jar"`
```
server:
  port: 8080

spring:
  datasource:
    username: root
    password: password
    url: jdbc:mysql://localhost:3306/search_engine?useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: create
      generate-ddl: true
    show-sql: true

# example values

indexing-settings:
  sites:
    - url: https://сайт.com/
      name: имя.ру
```
### Stack
Java, Spring Boot, Maven, JDBC, Hibernate, SQL, JSOUP, Morphology Library, Lombok, Log4j.
