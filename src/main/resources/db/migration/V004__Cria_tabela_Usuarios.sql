CREATE TABLE usuarios (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE usuarios_permissoes (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- admin
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 3);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 6);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (1, 8);

-- maria
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (2, 2);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (2, 5);
INSERT INTO usuarios_permissoes (id_usuario, id_permissao) values (2, 8);
