CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(255) NOT NULL,
    UNIQUE (telefone)
);

CREATE TABLE Endereco (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT,
    nome VARCHAR(255),
    cep VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    longitude DECIMAL(10, 8) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Grupo (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE Produto (
    id BIGINT PRIMARY KEY,
    codigo VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    grupo_id BIGINT,
    FOREIGN KEY (grupo_id) REFERENCES Grupo(id)
);

CREATE TABLE Pedido (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT,
    status VARCHAR(255) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE ItemPedido (
    pedido_id BIGINT,
    produto_id BIGINT,
    quantidade INTEGER,
    valor DECIMAL(10, 2),
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

CREATE TABLE Promocao (
    id BIGINT PRIMARY KEY,
    produto_id BIGINT,
    desconto DECIMAL,
    inicio DATE TIME,
    fim DATE TIME,
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

CREATE TABLE Funcionamento (
    id BIGINT PRIMARY KEY,
    diaSemana ENUM('DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO'),
    horaInicio TIME,
    horaFim TIME
);

CREATE TABLE FuncionamentoEspecial (
    id BIGINT PRIMARY KEY,
    dataInicio DATE,
    dataFim DATE,
    horaInicio TIME,
    horaFim TIME
);
