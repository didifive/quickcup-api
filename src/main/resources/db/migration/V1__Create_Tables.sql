CREATE TABLE Empresa (
    id SMALLINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(255) NOT NULL,
    cep VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero INTEGER NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    longitude DECIMAL(10, 8) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL
);

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
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    UNIQUE (nome)
);

CREATE TABLE Produto (
    id BIGINT PRIMARY KEY,
    codigo VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    imagem VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    grupo_id BIGINT,
    FOREIGN KEY (grupo_id) REFERENCES Grupo(id)
);

CREATE TABLE Pedido (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT,
    status VARCHAR(10) CHECK (status IN ('NOVO', 'CONFIRMADO', 'CANCELADO', 'EM_PREPARO', 'EM_ENTREGA', 'FINALIZADO')),
    valor_original DECIMAL(10, 2) NOT NULL,
    valor_desconto DECIMAL(10, 2),
    total DECIMAL(10, 2) NOT NULL,
    data_hora TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE Item_Pedido (
    pedido_id BIGINT,
    produto_id BIGINT,
    quantidade INTEGER NOT NULL,
    valor_unitario_original DECIMAL(10, 2) NOT NULL,
    valor_unitario_desconto DECIMAL(10, 2),
    valor_unitario DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

CREATE TABLE Promocao (
    id BIGINT PRIMARY KEY,
    produto_id BIGINT,
    desconto DECIMAL,
    inicio TIMESTAMP,
    fim TIMESTAMP,
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

CREATE TABLE Funcionamento (
    id BIGINT PRIMARY KEY,
    dia_semana VARCHAR(7) CHECK (dia_semana IN ('DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO')),
    hora_inicio TIME,
    hora_fim TIME,
    UNIQUE (dia_semana)
);

CREATE TABLE Funcionamento_Especial (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255),
    data_inicio TIMESTAMP,
    data_fim TIMESTAMP,
    tipo VARCHAR(7) CHECK (tipo IN ('ABERTO', 'FECHADO'))
);
