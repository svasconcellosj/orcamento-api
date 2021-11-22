CREATE TABLE categorias (
	id BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO categorias (descricao) values ('Lazer');
INSERT INTO categorias (descricao) values ('Alimentação');
INSERT INTO categorias (descricao) values ('Supermercado');
INSERT INTO categorias (descricao) values ('Farmácia');
INSERT INTO categorias (descricao) values ('Outros');