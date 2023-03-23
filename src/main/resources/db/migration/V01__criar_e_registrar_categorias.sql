--cria a tabela categoria--
CREATE TABLE categoria (
codigo SERIAL,
nome VARCHAR(50) NOT NULL
); --ENGINE=InnoDB DEFAULT CHARSET=utf8; -- Não funcionou no PostgreSQL

--insere algumas categorias
INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Outros');