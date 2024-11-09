INSERT INTO Empresa (id, nome, email, telefone, valor_entrega, tempo_entrega, cep, logradouro, numero, complemento, bairro, cidade, estado, longitude, latitude)
VALUES (1, 'Empresa XYZ', 'contato@empresaxyz.com', '(11) 99999-9999', 5.50, '00:15:00', '01001-000', 'Rua Exemplo', 123, 'Apartamento 1', 'Bairro Exemplo', 'Cidade Exemplo', 'Estado Exemplo', -23.558923, -46.667891);

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

