-- 1. Cria o banco de dados de origem
CREATE DATABASE IF NOT EXISTS db_oltp;

-- 2. Seleciona o banco de dados
USE db_oltp;

-- 3. Cria a tabela de vendas (com a coluna DATA para filtro na extração)
CREATE TABLE IF NOT EXISTS vendas_oltp (
    id INT NOT NULL AUTO_INCREMENT,
    produto VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    data DATE NOT NULL,
    
    PRIMARY KEY (id)
);

-- 1. Cria o banco de dados de destino
CREATE DATABASE IF NOT EXISTS db_dw;

-- 2. Seleciona o banco de dados
USE db_dw;

-- 3. Cria a tabela de fatos
CREATE TABLE IF NOT EXISTS fatos_vendas_dw (
    id_venda INT NOT NULL, -- Chave que referencia a origem
    produto VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL, -- Coluna resultado da Transformação (T)
    data DATE NOT NULL,
    
    PRIMARY KEY (id_venda)
);

USE db_oltp;

INSERT INTO vendas_oltp (produto, quantidade, preco_unitario, data) VALUES
('Notebook Gamer X', 1, 4500.00, '2024-06-15'),
('Mouse Pad XL', 3, 50.00, '2024-06-15'),
('Monitor Ultra HD', 1, 1800.00, '2024-06-16'),
('Teclado Mecânico', 2, 350.00, '2024-06-16'),
('Webcam Full HD', 5, 120.00, '2024-06-17'),
('Licença Software BI', 1, 999.90, '2023-12-30'), -- Este dado está FORA da extração (WHERE data >= '2024-01-01')
('Notebook Gamer X', 1, 4500.00, '2024-06-17'); 

-- Crie mais alguns registros para simular o crescimento de dados (e testar o filtro de data)
INSERT INTO vendas_oltp (produto, quantidade, preco_unitario, data) VALUES
('Acessório USB C', 10, 25.00, '2024-06-18'),
('Fone de Ouvido Premium', 4, 250.00, '2024-06-18');

-- Conecta ao Data Warehouse e verifica os resultados
USE db_dw;

-- Vê todos os registros carregados no DW
SELECT * FROM fatos_vendas_dw;

-- Verifica o resultado da transformação (Valor Total)
SELECT 
    produto, 
    SUM(valor_total) AS total_vendido
FROM 
    fatos_vendas_dw
GROUP BY 
    produto;