USE sistema_ninja;

INSERT INTO tb_ninja (nome, vila, cla, rank_ninja, natureza_chakra, status) VALUES
('Naruto', 'Konoha', 'Uzumaki', 'Genin', 'Vento', 'Ativo'),
('Sasuke', 'Konoha', 'Uchiha', 'Genin', 'Raio', 'Ativo'),
('Sakura', 'Konoha', 'Haruno', 'Chunin', 'Terra', 'Ativo'),
('Kakashi', 'Konoha', 'Hatake', 'Jounin', 'Raio', 'Ativo'),
('Gaara', 'Suna', 'Kazekage', 'Kage', 'Areia', 'Ativo');

INSERT INTO tb_missao (titulo, descricao, rank_missao, vila_origem, status) VALUES
('Capturar gato perdido', 'Recuperar o gato da esposa do Daimyo.', 'D', 'Konoha', 'Aberta'),
('Escolta de comerciante', 'Acompanhar comerciante ate o pais das ondas.', 'C', 'Konoha', 'Aberta'),
('Defender a fronteira', 'Vigiar e proteger a fronteira de Suna.', 'B', 'Suna', 'Aberta'),
('Investigar Akatsuki', 'Coletar informacoes sobre a organizacao Akatsuki.', 'A', 'Konoha', 'Aberta'),
('Proteger o Kazekage', 'Garantir a seguranca do Kazekage durante a cupula.', 'S', 'Suna', 'Aberta');

INSERT INTO tb_ninja_missao (id_ninja, id_missao, funcao, data_participacao) VALUES
(1, 1, 'Lider', '2024-03-10'),
(2, 1, 'Ataque', '2024-03-10'),
(4, 4, 'Lider', '2024-04-02');
