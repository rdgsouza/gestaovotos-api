create table sessao (
	id bigint not null auto_increment,
    descricao varchar(255) not null,
	tempo_abertura bigint not null,
    data_inicio datetime not null,
    pauta_id bigint not null,

    primary key (id),
    constraint fk_sessao_pauta foreign key (pauta_id) references pauta (id)
) engine=InnoDB default charset=utf8;