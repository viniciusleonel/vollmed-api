# VollMed API

A VollMed API é uma aplicação desenvolvida em Java com Spring Framework, destinada à gestão de uma clínica médica. Com recursos de autenticação JWT, a API permite o cadastro de médicos, pacientes e consultas, fornecendo endpoints públicos para login e cadastro de usuários.

## Recursos e Funcionalidades

- **Cadastro de Usuários**: A API oferece a funcionalidade de cadastro de novos usuários, que será o responsável por gerenciar a clínica. O endpoint `/usuarios` permite o registro de novos usuários.
- **Autenticação JWT**: A API utiliza JSON Web Tokens (JWT) para autenticação de usuários. O endpoint `/login` é público e permite que os usuários obtenham um token JWT válido para as próximas requisições.
- **Cadastro de Médicos**: Os médicos podem ser cadastrados na API através do endpoint `/medicos`.
- **Cadastro de Pacientes**: Os pacientes podem ser cadastrados na API através do endpoint `/pacientes`.
- **Agendamento de Consultas**: Os usuários podem agendar consultas utilizando o endpoint `/consultas`.
- **Listagem de Consultas**: A API fornece endpoints para listar consultas de acordo com diferentes critérios, como por médico, paciente ou data.

## Endpoints Públicos

- **POST /usuarios/cadastrar**: Endpoint para cadastro de novos usuários. Recebe os dados do usuário a ser cadastrado.
- **POST /login**: Endpoint para autenticação de usuários. Recebe as credenciais do usuário e retorna um token JWT válido.

## Endpoints Privados

### Pacientes

- **GET /pacientes**: Lista todos os pacientes cadastrados com paginação.
- **GET /pacientes/{id}**: Retorna os detalhes de um paciente específico.
- **DELETE /pacientes/{id}**: Remove um paciente existente.
- **POST /pacientes**: Cadastra um novo paciente.
- **PUT /pacientes**: Atualiza os dados de um paciente existente.

### Médicos

- **GET /medicos**: Lista todos os médicos cadastrados com paginação.
- **GET /medicos/{id}**: Retorna os detalhes de um médico específico.
- **DELETE /medicos/{id}**: Remove um médico existente.
- **POST /medicos**: Cadastra um novo médico.
- **PUT /medicos**: Atualiza os dados de um médico existente.

### Consultas

- **GET /consultas**: Lista todas as consultas agendadas com paginação.
- **GET /consultas/{id}**: Retorna os detalhes de uma consulta específica.
- **DELETE /consultas/{id}**: Remove uma consulta agendada.
- **POST /consultas**: Agenda uma nova consulta.
- **PUT /consultas**: Atualiza os dados de uma consulta agendada.

## Tecnologias Utilizadas

- Java
- Spring Framework
- Spring Boot
- Spring Security (JWT)
- Hibernate (JPA)
- Banco de Dados Relacional (ex: MySQL, PostgreSQL)

## Como Executar o Projeto

1. Clone este repositório: `git clone https://github.com/seu-usuario/vollmed-api.git`
2. Importe o projeto em sua IDE preferida.
3. Configure o banco de dados e as credenciais de acesso em `application.properties`.
4. Execute a aplicação. A API estará disponível em `http://localhost:8080`.

## Como Utilizar

- Cadastre-se como um usuário fazendo uma requisição POST para `/usuarios`.
- Para autenticar-se na API e obter um token JWT, faça uma requisição POST para `/login` enviando as credenciais de usuário.
- Utilize o token JWT recebido nas requisições aos endpoints privados da API, enviando-o no cabeçalho `Authorization`.

## EM DESENVOLVIMENTO
