create table pauta (
	id bigint not null auto_increment,
    descricao varchar(255) not null,

	primary key (id)
) engine=InnoDB default charset=utf8;