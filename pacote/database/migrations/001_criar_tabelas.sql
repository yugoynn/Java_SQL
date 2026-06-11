-- ============================================================
-- Script de criação do banco de dados
-- Execute este script no seu PostgreSQL local
-- ============================================================

-- Criação do banco (execute separado se necessário)
-- CREATE DATABASE nome_do_banco;

-- Exemplo de tabela — substitua pelo modelo do seu projeto
CREATE TABLE IF NOT EXISTS exemplo (
    id      SERIAL PRIMARY KEY,
    nome    VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
