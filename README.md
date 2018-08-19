[![Build Status](https://travis-ci.org/macrusal/ponto-inteligente-api.svg?branch=master)](https://travis-ci.org/macrusal/ponto-inteligente-api)
# Ponto Inteligente
API do sistema de ponto inteligente com Java e Spring Boot

# Arquitetura da Aplicação
* Java 8
* Git
* Spring Boot(com servidor Tomcat embarcado)
* Spring Data
* Docker(MySQL)
* H2DB
* JPA/Hibernate
* Flyway
* DBEaver
* Swagger
* Lombok

# Swagger
http://localhost:8088/swagger-ui.html

# Docker - Mysql
1 - sudo docker pull mysql / sudo docker pull mysql:5.7 1.1 - sudo docker images

2 - sudo docker run --name mysql -p 3306:3306 -v /Users/marcelo/desenvolvimento/database/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_ALLOW_EMPTYPASSWORD=yes -e MYSQL_DATABASE=mysql -e MYSQL_USER=marcelo -e MYSQL_PASSWORD=marcelo -d mysql:5.7 2.2 sudo docker p

3 - Abrir cliente e se conectar o MySQL

4 - Criar o banco de dados que estiver referenciado no application.properties

5 - Acessar linha de comando no banco de dados executando no Container:
	.a. sudo docker exec -it mysql bash -l 
	.b. mysql --user=root --password=root 
	.c. SELECT User,authentication_string FROM mysql.user; 
	.d. DROP USER 'marcelo'@'%'; 
	.e. CREATE USER 'marcelo'@'%' IDENTIFIED WITH mysql_native_password BY ''; 
	.f. GRANT ALL PRIVILEGES ON ponto_inteligente.* TO 'marcelo'@'%' identified by '';
 
6 - Subir a aplicacao

