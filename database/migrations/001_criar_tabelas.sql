CREATE DATABASE IF NOT EXISTS sistema_ninja
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE sistema_ninja;

CREATE TABLE tb_ninja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    vila VARCHAR(50) NOT NULL,
    cla VARCHAR(50),
    rank_ninja VARCHAR(20) NOT NULL,
    natureza_chakra VARCHAR(30),
    status VARCHAR(20) NOT NULL DEFAULT 'Ativo'
);

CREATE TABLE tb_missao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao TEXT,
    rank_missao CHAR(1) NOT NULL,
    vila_origem VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Aberta',
    CONSTRAINT chk_rank_missao CHECK (rank_missao IN ('D', 'C', 'B', 'A', 'S'))
);

CREATE TABLE tb_ninja_missao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_ninja INT NOT NULL,
    id_missao INT NOT NULL,
    funcao VARCHAR(30) NOT NULL,
    data_participacao DATE NOT NULL,
    CONSTRAINT fk_nm_ninja FOREIGN KEY (id_ninja) REFERENCES tb_ninja(id),
    CONSTRAINT fk_nm_missao FOREIGN KEY (id_missao) REFERENCES tb_missao(id),
    CONSTRAINT uq_ninja_missao UNIQUE (id_ninja, id_missao)
);

CREATE TABLE tb_totalizador_ninja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    quantidade INT NOT NULL,
    data_geracao DATETIME NOT NULL
);
