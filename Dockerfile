# Use uma imagem base menor para compilar o projeto
FROM maven:3.8.4-openjdk-17-slim AS build

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo pom.xml e as dependências do projeto para o diretório de trabalho
COPY pom.xml .

# Baixe as dependências do Maven
RUN mvn dependency:go-offline

# Copie o restante do código do projeto para o diretório de trabalho
COPY src ./src

# Compile o projeto, ignorando os testes
RUN mvn package -DskipTests

# Use uma imagem base menor para executar o JAR da aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /voll_med_api

# Copie o arquivo JAR da aplicação gerado no estágio de build anterior
COPY --from=build /app/target/vollmed-0.0.1-SNAPSHOT.jar /voll_med_api/vollmed-0.0.1-SNAPSHOT.jar

# Exponha a porta 8080 para a aplicação
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "vollmed-0.0.1-SNAPSHOT.jar"]