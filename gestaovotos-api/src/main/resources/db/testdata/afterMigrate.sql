set foreign_key_checks = 0;

lock tables associado write, pauta write, sessao write,
	voto write;

delete from associado;
delete from pauta;
delete from sessao;
delete from voto;

set foreign_key_checks = 1;

alter table associado auto_increment = 1;
alter table pauta auto_increment = 1;
alter table sessao auto_increment = 1;
alter table voto auto_increment = 1;

insert into associado (id, nome, cpf, email) values (1, 'Nikola Souza', "38233542563", 'nikola.s@gmail.com');

insert into pauta (id, descricao) values (1, 'Ref. acordo coletivo do reajuste de 50% no salário dos programadores');

insert into sessao (id, descricao, tempo_abertura, data_inicio, pauta_id) values (1, 'Sessão de Votação - Acordo coletivo entre associados',60, utc_timestamp, 1);

insert into voto (id, opcao_voto, cpf, pauta_id, associado_id) values (1, true, "38233542563", 1, 1);

unlock tables;