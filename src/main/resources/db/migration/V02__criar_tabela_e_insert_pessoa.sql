--cria a tabela pessoa--
    CREATE TABLE pessoa (
    codigo SERIAL,
    nome VARCHAR(55) NOT NULL,
    logradouro VARCHAR(30),
    numero VARCHAR(30),
    complemento VARCHAR(30),
    bairro VARCHAR(30),
    cep VARCHAR(30),
    cidade VARCHAR(30),
    estado VARCHAR(30),
    ativo BOOLEAN NOT NULL
    );

    --inserir 10 pessoas
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('John Paul', true,'Rua Bonifacio Lopes', '123', 'bloco A Casa C', 'Jardim Campo Belo', '13170000', 'Sumaré', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Steve Satris', true, 'Rua Trevisan', '400', null, 'Bairro Silva Azevedo', '10000777', 'São Paulo', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Joe Vai', true, 'Avenida Estranha', '90', 'Bloco 1 Casa 5', 'Bairro Esquisito', '13131333', 'Lugar Bonito', 'São Sebastião');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Riu Nagawa', true, 'Rua Legal', '99', null, 'Bairro Bacana', '77100900', 'São Roque', 'São São São');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Ken Masters', true, 'Rua Interessantis', '100', 'Bloco 14 Ap 2000', 'Jardim do Mussum', '98777000', 'Favela do Rio', 'Rio de Janeiro');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Kakaroto', true, 'Rua Sayajin', '100', 'Bloco 13 Ap 9000', 'Jardim dos Sayadins Puros', '99100222', 'Hortolândia', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Kuririm', true, 'Rua Humanos', '150', null, 'Jardim dos Humanos', '10100332', 'Sumaré', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Vegeta', true, 'Rua Principes dos Sayajin', '10', 'Bloco 1 Ap 1', 'Jardim dos Sayadins Puros', '99100222', 'Hortolândia', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Gohan', true, 'Rua Sayajin', '100', 'Bloco 13 Ap 9000', 'Jardim dos Sayadins Puros', '99100222', 'Hortolândia', 'São Paulo');
    INSERT INTO pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Trunks', true, 'Rua Sayajin Inpuros', '100', 'Bloco 2 Ap 9000', 'Bairro dos Sayadins Inpuros', '66159951', 'Campinas', 'São Paulo');