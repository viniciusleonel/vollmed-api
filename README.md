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
