# VollMed API 

A VollMed API é uma aplicação desenvolvida em Java com Spring Framework, 
destinada à gestão de uma clínica médica. Com recursos de autenticação JWT, 
a API permite o cadastro de médicos, pacientes e consultas, fornecendo 
endpoints públicos para login e cadastro de usuários.

Foi implementado um workflow de Integração Contínua (CI) utilizando GitHub Actions 
para testes e builds em pull requests, e um processo de Entrega Contínua (CD) que 
realiza o deploy automático da aplicação em produção. A aplicação é containerizada 
com Docker e implantada na Azure, garantindo atualizações rápidas e escalabilidade.

[Vollmed API Azure](https://vollmed-api-hza2gbbsedfchrer.eastus2-01.azurewebsites.net/swagger-ui/index.html)

---

## Requisitos:
- Ter o Docker instalado.
- Ter o Java instalado.
- Ter um banco de dados PostgreSQL.
- Clonar este repositório: `git clone https://github.com/viniciusleonel/vollmed-api`

## Configuração

1. Configure as variáveis de ambiente no seu arquivo `.env` ou no seu ambiente:
- POSTGRES_DB_URL
- POSTGRES_DB_TEST_URL
- JWT_SECRET
- PROFILE

2. Agora execute a classe `VollMedApplication` e a API estará pronta para uso.

## Executando o Projeto com Docker

1. Configure as mesmas variáveis de ambiente em `docker-compose`>`environment`

2. Abra o terminal na pasta raiz que contém os arquivos docker e digite os seguintes comandos:

   - Construir a imagem:
     `docker-compose build`

   - Iniciar os contêineres:
     `docker-compose up -d`

    Isso irá iniciar o contêiner em segundo plano.

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
- Spring Boot
- Spring Security
- Spring Doc
- Json Web Token (JWT)
- Auth0
- Hibernate (JPA)
- Swagger
- PostgreSQL
- Docker & DockerHub
- GitHub Actions
- Microsoft Azure

## **Criado por**: [Vinicius Leonel](https://www.linkedin.com/in/viniciuslps/)

## Imagens da API

## Swagger
![Swagger](./assets/images/api-screen-shots/swagger.png)

## Cadastro de Usuário - Post
![Cadastro de Usuário](./assets/images/api-screen-shots/usuario/cadastro/cadastrar-usuario.png)

## Cadastro de Usuário - Post (Tratamento de Erro - Campos Inválidos)
![Cadastro de Usuário Inválido](./assets/images/api-screen-shots/usuario/cadastro/cadastrar-usuario-invalido.png)

## Cadastro de Usuário - Post (Tratamento de Erro - Usuário já Cadastrado)
![Erro de Cadastro de Usuário](./assets/images/api-screen-shots/usuario/cadastro/cadastrar-usuario-erro.png)

## Login com retorno de um Json Web Token - Post
![Login de Usuário](./assets/images/api-screen-shots/usuario/login/login.png)

## Login - Post (Tratamento de Erro - Email Inválido)
![Login Email Inválido](./assets/images/api-screen-shots/usuario/login/login-invalido.png)

## Login - Post (Tratamento de Erro - Credenciais Inválidas)
![Login Credenciais Inválidas](./assets/images/api-screen-shots/usuario/login/login-erro.png)

## Médico - Post 
![Cadastro de Médico](./assets/images/api-screen-shots/medico/post/cadastrar-medico.png)

## Médico - Post (Tratamento de Erro - Campos Inválidos)
![Cadastro de Médico Inválido](./assets/images/api-screen-shots/medico/post/cadastrar-medico-invalido.png)

## Médico - Post (Tratamento de Erro - Médico já Cadastrado)
![Erro de Cadastro de Médico](./assets/images/api-screen-shots/medico/post/cadastrar-medico-erro.png)

## Médico - Get (Listagem de Médicos com paginação e filtros)
![Listagem de Médicos](./assets/images/api-screen-shots/medico/get/listar-medico.png)

## Médico - Get (Buscar Médico por ID)
![Buscar Médico](./assets/images/api-screen-shots/medico/get/buscar-medico.png)

## Médico - Get (Tratamento de Erro - Médico não encontrado)
![Médico não Encontrado](./assets/images/api-screen-shots/medico/get/buscar-medico-erro.png)

## Médico - Put (Atualizar Médico)
![Atualizar Médico](./assets/images/api-screen-shots/medico/put/atualizar-medico.png)

## Médico - Put (Tratamento de Erro - Campos Inválidos)
![Atualizar Médico Inválido](./assets/images/api-screen-shots/medico/put/atualizar-medico-invalido.png)

## Médico - Delete (Deletar Médico)
![Deletar Médico](./assets/images/api-screen-shots/medico/delete/deletar-medico.png)

## Médico - Delete (Tratamento de Erro - Médico não encontrado)
![Médico não Encontrado para Deletar](./assets/images/api-screen-shots/medico/delete/deletar-medico-erro.png)

## Paciente - Post 
![Cadastro de Paciente](./assets/images/api-screen-shots/paciente/post/cadastrar-paciente.png)

## Paciente - Post (Tratamento de Erro - Campos Inválidos)
![Cadastro de Paciente Inválido](./assets/images/api-screen-shots/paciente/post/cadastrar-paciente-invalido.png)

## Paciente - Post (Tratamento de Erro - Paciente já Cadastrado)
![Erro de Cadastro de Paciente](./assets/images/api-screen-shots/paciente/post/cadastrar-paciente-erro.png)

## Paciente - Get (Listagem de Pacientes com paginação e filtros)
![Listagem de Pacientes](./assets/images/api-screen-shots/paciente/get/listar-pacientes.png)

## Paciente - Get (Buscar Paciente por ID)
![Buscar Paciente](./assets/images/api-screen-shots/paciente/get/buscar-paciente.png)

## Paciente - Get (Tratamento de Erro - Paciente não encontrado)
![Paciente não Encontrado](./assets/images/api-screen-shots/paciente/get/buscar-paciente-erro.png)

## Paciente - Put (Atualizar Paciente)
![Atualizar Paciente](./assets/images/api-screen-shots/paciente/put/atualizar-paciente.png)

## Paciente - Put (Tratamento de Erro - Campos Inválidos)
![Atualizar Paciente Inválido](./assets/images/api-screen-shots/paciente/put/atualizar-paciente-invalido.png)

## Paciente - Delete (Deletar Paciente)
![Deletar Paciente](./assets/images/api-screen-shots/paciente/delete/deletar-paciente.png)

## Paciente - Delete (Tratamento de Erro - Paciente não encontrado)
![Paciente não Encontrado para Deletar](./assets/images/api-screen-shots/paciente/delete/deletar-paciente-erro.png)

## Consulta - Post (Agendar Consulta)
![Agendar Consulta](./assets/images/api-screen-shots/consulta/post/agendar-consulta.png)

## Consulta - Post (Agendar Consulta - Tratamento de Erro - Médico ocupado)
![Médico Ocupado](./assets/images/api-screen-shots/consulta/post/agendar-consulta-erro.png)

## Consulta - Post (Agendar Consulta - Tratamento de Erro - Paciente só pode ter uma consulta por dia)
![Erro de Consulta do Paciente](./assets/images/api-screen-shots/consulta/post/agendar-consulta-erro-paciente.png)

## Consulta - Post (Agendar Consulta - Tratamento de Erro - Paciente excluído)
![Paciente Excluído](./assets/images/api-screen-shots/consulta/post/agendar-consulta-erro-paciente-excluido.png)

## Consulta - Post (Agendar Consulta - Tratamento de Erro - Médico excluído)
![Médico Excluído](./assets/images/api-screen-shots/consulta/post/agendar-consulta-erro-medico-excluido.png)

## Consulta - Post (Agendar Consulta - Tratamento de Erro - Data inválida)
![Data Inválida](./assets/images/api-screen-shots/consulta/post/agendar-consulta-data-invalida.png)

## Consulta - Get (Listagem Consultas com paginação e filtros)
![Listagem de Consultas](./assets/images/api-screen-shots/consulta/get/listar-consultas.png)

## Consulta - Get (Buscar Consulta por ID)
![Buscar Consulta](./assets/images/api-screen-shots/consulta/get/buscar-consulta.png)

## Consulta - Get (Buscar Consulta - Tratamento de Erro - Consulta não encontrada)
![Consulta não Encontrada](./assets/images/api-screen-shots/consulta/get/buscar-consulta-erro.png)

## Consulta - Put (Atualizar Consulta)
![Atualizar Consulta](./assets/images/api-screen-shots/consulta/put/atualizar-consulta.png)

## Consulta - Delete (Cancelar Consulta)
![Cancelar Consulta](./assets/images/api-screen-shots/consulta/delete/cancelar-consulta.png)
