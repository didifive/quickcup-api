CREATE TABLE Empresa (
    id SMALLINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(255) NOT NULL,
    valor_entrega DECIMAL(10, 2) NOT NULL,
    tempo_entrega TIME NOT NULL,
    cep VARCHAR(10) NOT NULL,
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
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    UNIQUE (telefone)
);

CREATE TABLE Endereco (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    nome VARCHAR(255),
    cep VARCHAR(10) NOT NULL,
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
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    UNIQUE (nome)
);

CREATE TABLE Produto (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    imagem VARCHAR(255),
    valor_original DECIMAL(10, 2) NOT NULL,
    valor_desconto DECIMAL(10, 2) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    grupo_id BIGINT NOT NULL,
    FOREIGN KEY (grupo_id) REFERENCES Grupo(id)
);

CREATE TABLE Pedido (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('NOVO', 'CONFIRMADO', 'CANCELADO', 'EM_PREPARO', 'EM_ENTREGA', 'FINALIZADO')),
    valor_entrega DECIMAL(10, 2) NOT NULL,
    retira BOOLEAN NOT NULL DEFAULT FALSE,
    endereco VARCHAR(255),
    forma_pagamento VARCHAR(14) NOT NULL CHECK (forma_pagamento IN ('DINHEIRO', 'CARTAO_CREDITO', 'CARTAO_DEBITO', 'PIX')),
    observacoes VARCHAR(255),
    data_hora_pedido TIMESTAMP NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id),
    CONSTRAINT chk_endereco_retira CHECK (
        (retira = TRUE AND endereco IS NULL) OR
        (retira = FALSE AND endereco IS NOT NULL)
    )
);

CREATE TABLE Item_Pedido (
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario_original DECIMAL(10, 2) NOT NULL,
    valor_unitario_desconto DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (pedido_id, produto_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id),
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);

CREATE TABLE Funcionamento (
    dia_semana VARCHAR(7) NOT NULL CHECK (dia_semana IN ('DOMINGO', 'SEGUNDA', 'TERCA', 'QUARTA', 'QUINTA', 'SEXTA', 'SABADO')) PRIMARY KEY,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL
);

CREATE TABLE Funcionamento_Especial (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    tipo VARCHAR(7) CHECK (tipo IN ('ABERTO', 'FECHADO')) NOT NULL
);
