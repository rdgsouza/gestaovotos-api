create table associado (
	id bigint not null auto_increment,
    nome varchar(80) not null,
    cpf varchar(11) not null,
    email varchar(255),

	primary key (id)
) engine=InnoDB default charset=utf8;