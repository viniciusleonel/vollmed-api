create table pacientes(

    id bigint not null auto_increment,
    ativo tinyint,
    nome varchar(100) not null,
    telefone varchar(20) not null,
    email varchar(100) not null unique,
    cpf varchar(11) not null unique,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,

    primary key(id)

);

update pacientes set ativo = 1;