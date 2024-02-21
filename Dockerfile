# Используйте официальный образ базового слоя Java 17 от AdoptOpenJDK
FROM adoptopenjdk/openjdk17:alpine-jre

# Указание рабочей директории внутри контейнера
WORKDIR /app

# Копирование jar-файла вашего приложения в рабочую директорию контейнера
COPY build/libs/your-app.jar app.jar

# Команда, которая будет выполнена при запуске контейнера
ENTRYPOINT ["java", "-jar", "app.jar"]
