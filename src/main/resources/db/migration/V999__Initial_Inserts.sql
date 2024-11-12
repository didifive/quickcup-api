INSERT INTO Empresa (id, nome, email, telefone, valor_entrega, tempo_entrega, cep, logradouro, numero, complemento, bairro, cidade, estado, longitude, latitude)
VALUES (1, 'QuickCup', 'contato@quickcup.com', '(11) 98123-1234', 5.50, '00:15:00', '146260-000', 'Avenida Quatro', 505, null, 'Centro', 'Orlândia', 'SP', -47.88497, -20.720324 );

INSERT INTO Funcionamento (dia_semana, hora_inicio, hora_fim) VALUES
('DOMINGO', '05:30:00', '12:00:00'),
('SEGUNDA', '05:30:00', '19:00:00'),
('TERCA', '05:30:00', '19:00:00'),
('QUARTA', '05:30:00', '19:00:00'),
('QUINTA', '05:30:00', '19:00:00'),
('SEXTA', '05:30:00', '19:00:00'),
('SABADO', '05:30:00', '16:00:00')
ON CONFLICT (dia_semana) DO UPDATE SET
hora_inicio = EXCLUDED.hora_inicio,
hora_fim = EXCLUDED.hora_fim;

INSERT INTO Funcionamento_Especial (nome, data_inicio, data_fim, tipo) VALUES
('Carnaval', '2024-02-10 00:00:00', '2024-02-13 23:59:59', 'FECHADO'),
('Natal', '2024-12-24 00:00:00', '2024-12-25 23:59:59', 'FECHADO'),
('Ano Novo', '2024-12-31 00:00:00', '2025-01-01 23:59:59', 'FECHADO'),
('Black Friday', '2024-11-29 00:00:00', '2024-11-29 23:59:59', 'ABERTO'),
('Promoção de Verão', '2024-12-15 00:00:00', '2024-12-15 23:59:59', 'ABERTO');

INSERT INTO Grupo (nome, descricao) VALUES
('Bebidas Quentes', 'Bebidas quentes'),
('Bebidas Frias', 'Bebidas geladas ou naturais'),
('Doces', 'Doces para acompanhar a bebida e dar energia'),
('Massas', 'Massas deliciosas para acompanhar');

INSERT INTO Produto (codigo, nome, descricao, imagem, valor_original, valor_desconto, enabled, grupo_id) VALUES
('P1G1', 'Café Tradicional sem Acucar', 'Uma xícara de 100ml de café tradicional caseiro sem açúcar',
    'https://th.bing.com/th/id/OIP.slGOk9OzPWtpW52UikR2PgHaEf?rs=1&pid=ImgDetMain',
    3.00, 0.10, TRUE, 1),
('P2G1', 'Café Tradicional Adoçado', 'Uma xícara de 100ml de café tradicional caseiro adoçado',
    'https://th.bing.com/th/id/OIP.DaruWxhmbupqOL_iHs8jQQHaE8?rs=1&pid=ImgDetMain',
    3.00, 0.00, TRUE, 1),
('P3G1', 'Café Expresso', 'Uma xícara de 100ml de café expresso',
    'https://th.bing.com/th/id/R.d3a6927c0a0af9faaf51ecda6f8bfae0?rik=ZW%2bxhEcM089onw&riu=http%3a%2f%2ftodocafe.es%2fwp-content%2fuploads%2f2020%2f10%2fcafe_expreso.jpg&ehk=j5VzZcHJE8sIvUMPVOE56DCBsqMIvZJkgDy%2f3W9XCDY%3d&risl=&pid=ImgRaw&r=0',
     3.10, 0.25, TRUE, 1),
('P4G1', 'Cappuccino tradicional', 'Uma xícara de 200ml de cappuccino tradicional adoçado',
    'https://upload.wikimedia.org/wikipedia/commons/c/c8/Cappuccino_at_Sightglass_Coffee.jpg',
    8.50, 0.50, TRUE, 1),
('P5G1', 'Cappuccino Chocolate', 'Descrição cappucino', 'imagem4.jpg', 250.00, 225.00, FALSE, 1);

-- Produtos para o Grupo 2
INSERT INTO Produto (codigo, nome, descricao, imagem, valor_original, valor_desconto, enabled, grupo_id) VALUES
('P1G2', 'Café gelado', 'Delicioso café gelado em copo de 200ml',
    'https://www.dci.com.br/wp-content/uploads/2021/02/cafe-gelado.jpg',
    8.00, 0.21, TRUE, 2),
('P2G2', 'Frappuccino', 'Delicioso frappuccino em copo de 300ml e chantily',
    'https://th.bing.com/th/id/OIP.m9aNnpDJCThehUHsPAeLZwAAAA?w=474&h=474&rs=1&pid=ImgDetMain',
    18.00, 3.01, TRUE, 2),
('P3G2', 'Produto 3 do Grupo 2', 'Descrição do Produto 3 do Grupo 2',
    'imagem7.jpg',
    210.00, 189.00, FALSE, 2),
('P4G2', 'Produto 4 do Grupo 2', 'Descrição do Produto 4 do Grupo 2',
    'imagem8.jpg',
    260.00, 234.00, FALSE, 2);

-- Produtos para o Grupo 3
INSERT INTO Produto (codigo, nome, descricao, imagem, valor_original, valor_desconto, enabled, grupo_id) VALUES
('P1G3', 'Bomba de chocolate', 'Deliciosa bomba de chocolate',
    'https://th.bing.com/th/id/R.e2f40ce4d512a9907871c44cf6e19323?rik=Ai0P8hyDgQVjvw&pid=ImgRaw&r=0',
    120.00, 1.00, TRUE, 3),
('P2G3', 'Mini sonho recheado', '5 unidades de mini sonhos recheados',
    'https://www.comidaereceitas.com.br/img/sizeswp/1200x675/2018/07/sonho_recheado_creme.jpg',
    14.00, 1.01, TRUE, 3),
('P3G3', 'Produto 3 do Grupo 3', 'Descrição do Produto 3 do Grupo 3', 'imagem11.jpg', 220.00, 198.00, FALSE, 3);


INSERT INTO Cliente (nome,  telefone) VALUES
('João Silva', '11987654321'),
('Maria Oliveira', '11987654322'),
('Carlos Souza', '11987654323');

INSERT INTO Endereco (cliente_id, nome, cep, logradouro, numero, complemento, bairro, cidade, estado, longitude, latitude) VALUES
(1, 'Casa', '14620-000', 'Rua 1', 100, 'Apto 101', 'Centro', 'Orlândia', 'SP', -47.8858187, -20.7175404),
(1, 'Trabalho', '14620-000', 'Avenida 2', 200, null, 'Centro', 'Orlândia', 'SP', -47.88667, -20.72028);

INSERT INTO Endereco (cliente_id, nome, cep, logradouro, numero, complemento, bairro, cidade, estado, longitude, latitude) VALUES
(2, 'Casa', '14620-000', 'Rua 3', 300, 'Casa', 'Centro', 'Orlândia', 'SP', -47.8853, -20.7188);


