CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(255),
    cep VARCHAR(255),
    logradouro VARCHAR(255),
    numero INTEGER,
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    UNIQUE (telefone)
);

CREATE TABLE Grupo (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE Produto (
    id BIGINT PRIMARY KEY,
    codigo VARCHAR(255),
    nome VARCHAR(255),
    preco DECIMAL(10, 2),
    grupo_id BIGINT,
    FOREIGN KEY (grupo_id) REFERENCES Grupo(id)
);

CREATE TABLE Pedido (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT,
    status VARCHAR(255),
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
    diaSemana VARCHAR(255),
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
