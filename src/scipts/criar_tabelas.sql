-- Script para criar as tabelas do banco de dados

-- Sequências
CREATE SEQUENCE IF NOT EXISTS sq_cliente START 1;
CREATE SEQUENCE IF NOT EXISTS sq_produto START 1;
CREATE SEQUENCE IF NOT EXISTS sq_estoque START 1;

-- Tabela Cliente com campo email adicional
CREATE TABLE IF NOT EXISTS TB_CLIENTE (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cpf BIGINT NOT NULL UNIQUE,
    tel BIGINT,
    endereco VARCHAR(100),
    numero INTEGER,
    cidade VARCHAR(50),
    estado VARCHAR(2),
    email VARCHAR(100) -- Campo adicional
);

-- Tabela Produto com campo categoria adicional
CREATE TABLE IF NOT EXISTS TB_PRODUTO (
    id BIGINT PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2),
    categoria VARCHAR(50) -- Campo adicional
);

-- Tabela Estoque
CREATE TABLE IF NOT EXISTS TB_ESTOQUE (
    id BIGINT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (produto_id) REFERENCES TB_PRODUTO(id),
    UNIQUE(produto_id)
);
