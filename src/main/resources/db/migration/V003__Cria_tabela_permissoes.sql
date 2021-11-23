CREATE TABLE permissoes (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO permissoes (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissoes (id, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permissoes (id, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissoes (id, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissoes (id, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permissoes (id, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissoes (id, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');