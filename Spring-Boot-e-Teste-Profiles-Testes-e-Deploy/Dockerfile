#Ensinando o Docker a gerar uma imagem da nossa aplicação:

#Pega a imagem base que ele vai utilizar
FROM openjdk:8-jdk-alpine

#Cria um usuario e um grupo spring nesse S.O
RUN addgroup -S spring && adduser -S spring -G spring

#Usa o usuario e o grupo spring
USER spring:spring

#Vai pegar o jar que foi gerado pelo Build do Maven e vai colocar o nome desse arquivo na variável JAR_FILE
ARG JAR_FILE=target/*.jar

#Vai copiar o arquivo para o S.O com o nome app.jar
COPY ${JAR_FILE} app.jar

#Comando que vai ser executado no nosso container
ENTRYPOINT ["java","-jar","/app.jar"]