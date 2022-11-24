create table voto (
      id bigint not null auto_increment,
      opcao_voto boolean not null,
      cpf varchar(11) not null,
      pauta_id bigint not null,
      associado_id bigint not null,

      primary key (id),
      constraint fk_voto_pauta foreign key (pauta_id) references pauta (id),
      constraint fk_voto_associado foreign key (associado_id) references associado (id)
) engine=InnoDB default charset=utf8;