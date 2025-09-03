-- Script para criar as tabelas com os novos campos

-- Adicionar campo email na tabela cliente
ALTER TABLE TB_CLIENTE ADD COLUMN email VARCHAR(100);

-- Adicionar campo categoria na tabela produto  
ALTER TABLE TB_PRODUTO ADD COLUMN categoria VARCHAR(50);

-- Criar tabela de estoque
CREATE TABLE TB_ESTOQUE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_codigo VARCHAR(10) NOT NULL UNIQUE,
    quantidade INTEGER NOT NULL DEFAULT 0,
    quantidade_minima INTEGER NOT NULL DEFAULT 5,
    FOREIGN KEY (produto_codigo) REFERENCES TB_PRODUTO(codigo)
);

-- Inserir alguns dados de exemplo para teste
INSERT INTO TB_ESTOQUE (produto_codigo, quantidade, quantidade_minima) VALUES 
('A1', 100, 10),
('B1', 50, 5),
('C1', 25, 8);
