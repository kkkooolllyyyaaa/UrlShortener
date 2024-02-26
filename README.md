# url-shortener pet project template

### Functional
- REST: urls/shorten, /redirect
  - Нужно спроектировать OpenAPI
  - В идеале подключить Swagger
- KAFKA: urls/shorten - читаем и обрабатываем батчем
- GPRC: то же что и в REST (по желанию)
- В фоне работает задача, которая генерит разные shortUrl и сохраняет их в pg
  - забираем/удаляем оттуда shortUrl, чтобы присвоить её очередному запросу на укорочение ссылки
  - можем достать сразу пачку (батчем) при обработке через kafka
  - не кэшируем, т.к. важна strong consistency и отсутствие дубликатов
- Подумать о TTL коротких ссылок
  - Возможность указывать TTL
  - Фоновая задача, которая удаляет ссылки испортившиеся по TTL
  - При redirect нужно посмотреть, не истек ли TTL

### Persistence
- Храним всё в postgres (подумать над индексами)
- В redis кэшируем shortUrl -> originalUrl
- DDL: Добавление таблиц, индексов и.т.п. делать через миграции liquibase 

### ENV
- Dockerfile для сервиса
- docker-compose с pg, redis, kafka
- EXPOSE порта для REST и kafka produce

### Configuration
- Всё выносим в конфиг, поменьше хардкода

### Test
- Написать тесты на методы controller (SpringBootTest) используя spring MVC и testcontainers
- Написать тест на produce + consume через kafka
