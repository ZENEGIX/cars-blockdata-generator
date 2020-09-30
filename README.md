# cars-blockdata-generator
The misc application for generate blockData

**Создание образа**

* Склонировать репозиторий
* В корне проекта выполнить команду `mvn clean package`
* Взять cars-blockdata-generator.jar из /target

**Запуск**

Либо простой запуск как приложение, либо через bat-скрипт, пример скрипта

`java -jar cars-blockdata-generator.jar`


**Использование**

Запустить `cars-blockdata-generator.jar` в пустой папке

Будут созданы необходимые файлы для работы

Необходимо вписать нужные данные в соответствующие файлы в папке input, затем запустить приложение и забрать результат из output.txt