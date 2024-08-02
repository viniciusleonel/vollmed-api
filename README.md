# VollMed API 

A VollMed API é uma aplicação desenvolvida em Java com Spring Framework, destinada à gestão de uma clínica médica. Com recursos de autenticação JWT, a API permite o cadastro de médicos, pacientes e consultas, fornecendo endpoints públicos para login e cadastro de usuários.

## Executando o Projeto com Docker

1. Tenha o Docker instalado.
2. Clone este repositório: `git clone https://github.com/viniciusleonel/vollmed-api`
3. Importe o projeto em sua IDE preferida.
4. Configure o banco de dados e as credenciais de acesso em `application.properties`.
5. Escolha seu banco de dados favorito selecionando uma branch.
6. Configure as variaveis de ambiente em .env:  
   DB_HOST=db

   DB_PORT=3306 ou 5432 (MySQL ou PostgreSQL)

   DB_NAME=voll_med_api

   DB_USERNAME=root

   DB_PASSWORD=root123
7. Abra o terminal na pasta raiz que contém os arquivos docker e digite os seguintes comandos:

Construir a imagem:
`docker-compose build`

Iniciar os contêineres:
`docker-compose up`

Feito isso, a API estará disponível em `http://localhost:8080`.

## Como Utilizar

- Cadastre-se como um usuário fazendo uma requisição POST para `/usuarios`.
- Para autenticar-se na API e obter um token JWT, faça uma requisição POST para `/login` enviando as credenciais de usuário.
- Utilize o token JWT recebido nas requisições aos endpoints privados da API, enviando-o no cabeçalho `Authorization`.

## Recursos e Funcionalidades

- **Cadastro de Usuários**: A API oferece a funcionalidade de cadastro de novos usuários, que será o responsável por gerenciar a clínica. O endpoint `/usuarios` permite o registro de novos usuários.
- **Autenticação JWT**: A API utiliza JSON Web Tokens (JWT) para autenticação de usuários. O endpoint `/login` é público e permite que os usuários obtenham um token JWT válido para as próximas requisições.
- **Gerenciamento de Médicos**: Os médicos podem ser gerenciados na API através do endpoint `/medicos`.
- **Gerenciamento de Pacientes**: Os pacientes podem ser gerenciados na API através do endpoint `/pacientes`.
- **Gerenciamento de Consultas**: Os usuários podem gerenciar consultas utilizando o endpoint `/consultas`.

## Endpoints Públicos

- **POST /usuarios/cadastrar**: Endpoint para cadastro de novos usuários. Recebe os dados do usuário a ser cadastrado.
- **POST /login**: Endpoint para autenticação de usuários. Recebe as credenciais do usuário e retorna um token JWT válido.

### Cadastro/Login Usuario POST
- **login**<span style="color: red;">*</span>: <span style="color: green;">string </span>( email )
- **senha**<span style="color: red;">*</span>: <span style="color: green;">string</span>


## Endpoints Privados

### Cadastro Endereço
- **logradouro**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **bairro**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **cep**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  pattern: \d{8}
- **cidade**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **uf**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **numero**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **complemento**: <span style="color: green;">string</span>

    ### Parâmetros de Paginação e Ordenação
  /pacientes?page=0&size=1&sort=nome
- **page**: <span style="color: green;">integer</span>  
  minimum: 0
- **size**: <span style="color: green;">integer</span>  
  minimum: 1
- **sort**: <span style="color: green;">[string]</span>


### Pacientes

- **GET /pacientes**: Lista todos os pacientes cadastrados com paginação.
- **GET /pacientes/{id}**: Retorna os detalhes de um paciente específico.
- **DELETE /pacientes/{id}**: Remove um paciente existente.
- **POST /pacientes**: Cadastra um novo paciente.
- **PUT /pacientes/{id}**: Atualiza os dados de um paciente existente.

### Cadastro de Paciente POST
- **nome**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **email**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **telefone**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: \(\d{2}\) \d{5}-\d{4}</span> (xx) xxxxx-xxxx
- **cpf**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: ^\d{11}$</span>
- **endereco**<span style="color: red;">*</span>: DadosEndereco<span style="color: green;">{...}</span>

### Atualização de Paciente PUT
- **nome**: <span style="color: green;">string</span>
- **telefone**: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: \(\d{2}\) \d{5}-\d{4}</span> (xx) xxxxx-xxxx
- **endereco**: DadosEndereco<span style="color: green;">{...}</span>

Qualquer outro campo enviado retornará um erro.

### Médicos

- **GET /medicos**: Lista todos os médicos cadastrados com paginação.
- **GET /medicos/{id}**: Retorna os detalhes de um médico específico.
- **DELETE /medicos/{id}**: Remove um médico existente.
- **POST /medicos**: Cadastra um novo médico.
- **PUT /medicos/{id}**: Atualiza os dados de um médico existente.

### Cadastro de Médico POST
- **nome**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **email**<span style="color: red;">*</span>: <span style="color: green;">string</span>
- **telefone**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: \(\d{2}\) \d{5}-\d{4}</span>
- **crm**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: \d{4,6}</span>
- **especialidade**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">Enum: [ Ortopedia, Cardiologia, Ginecologia, Dermatologia, Odontologia ]</span>
- **endereco**<span style="color: red;">*</span>: DadosEndereco<span style="color: green;">{...}</span>

### Atualização de Médico PUT
- **nome**: <span style="color: green;">string</span>
- **telefone**: <span style="color: green;">string</span>  
  <span style="color: blue;">pattern: \(\d{2}\) \d{5}-\d{4}</span>
- **endereco**: DadosEndereco<span style="color: green;">{...}</span>

Qualquer outro campo enviado retornará um erro.


### Consultas

- **POST /consultas**: Agenda uma nova consulta.
- **GET /consultas**: Lista todas as consultas agendadas com paginação.
- **GET /consultas/{id}**: Retorna os detalhes de uma consulta específica.
- **PUT /consultas/{id}**: Atualiza a data ou o status da consulta.
- **DELETE /consultas**: Remove uma consulta agendada.

[//]: # (- **PUT /consultas**: Atualiza os dados de uma consulta agendada.)

### Agendamento de Consulta POST
- **idMedico**: <span style="color: green;">integer</span>
- **idPaciente**<span style="color: red;">*</span>: <span style="color: green;">integer</span>
- **data**<span style="color: red;">*</span>: <span style="color: green;">string($date-time)</span>
- **especialidade**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">Enum: [ Ortopedia, Cardiologia, Ginecologia, Dermatologia, Odontologia ]</span>

Caso o ID do médico não seja informado, será selecionado um médico que estiver livre aleatóriamente.

### Atualizar Consulta PUT
- **data**: <span style="color: green;">string($date-time)</span>
- **status**: <span style="color: blue;">Enum: [ PACIENTE_DESISTIU, MEDICO_CANCELOU, CONSULTA_ATIVA, CONSULTA_EFETUADA, OUTROS ]</span>



### Cancelar Consulta DELETE
- **idConsulta**<span style="color: red;">*</span>: <span style="color: green;">integer</span>
- **motivo**<span style="color: red;">*</span>: <span style="color: green;">string</span>  
  <span style="color: blue;">Enum: [ PACIENTE_DESISTIU, MEDICO_CANCELOU, CONSULTA_ATIVA, OUTROS ]</span>

## Tecnologias Utilizadas

- Java
- Spring Framework
- Spring Boot
- Spring Security (JWT)
- Spring Doc
- Auth0
- Hibernate (JPA)
- Banco de Dados Relacional: MySQL

## [Link - Imagens do Projeto](https://github.com/viniciusleonel/vollmed-api/tree/main/assets/images/api-screen-shots)