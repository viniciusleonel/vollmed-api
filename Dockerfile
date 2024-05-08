# Use a imagem base que possui suporte para Java e Maven
FROM openjdk:17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /voll_med_api

# Copie o arquivo JAR da sua aplicação para o diretório de trabalho no contêiner
COPY target/*.jar /voll_med_api/api-0.0.1-SNAPSHOT.jar

# Exponha a porta 8080 para a aplicação
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
