-- cria a base de dados
CREATE DATABASE IF NOT EXISTS `etl_aula`;
-- Use o banco de dados
USE `etl_aula`;
-- Tabela de origem (Extract)
CREATE TABLE `vendas_raw` (
    `id_venda` INT PRIMARY KEY,
    `produto` VARCHAR(100),
    `categoria` VARCHAR(50),
    `valor_unitario` DECIMAL(10, 2),
    `quantidade` VARCHAR(10),
    `data_venda` VARCHAR(20),
    `regiao` VARCHAR(50)
);
-- Tabela de transformação (Staging)
CREATE TABLE `vendas_staging` (
    `id_venda` INT PRIMARY KEY,
    `produto` VARCHAR(100),
    `categoria` VARCHAR(50),
    `valor_unitario` DECIMAL(10, 2),
    `quantidade` INT,
    `data_venda` DATE,
    `regiao` VARCHAR(50)
);
-- Tabela de destino (Load)
CREATE TABLE `vendas_final` (
    `id_venda` INT PRIMARY KEY,
    `produto` VARCHAR(100),
    `categoria` VARCHAR(50),
    `valor_total` DECIMAL(10, 2),
    `data_venda` DATE,
    `regiao` VARCHAR(50)
);
-- Insira dados de exemplo na tabela de origem
INSERT INTO `vendas_raw` (`id_venda`, `produto`, `categoria`, `valor_unitario`, `quantidade`, `data_venda`, `regiao`) VALUES
(1, 'Notebook Dell XPS 15', 'Eletrônicos', 12500.00, '1', '01/09/2025', 'Sudeste'),
(2, 'iPhone 15 Pro Max', 'Eletrônicos', 9800.00, '2', '02/09/2025', 'Sul'),
(3, 'Tênis Nike Air Max', 'Calçados', 750.00, '3', '03/09/2025', 'Nordeste'),
(4, 'Mesa de Jantar de Carvalho', 'Móveis', 3200.50, '1', '04/09/2025', 'Norte'),
(5, 'Máquina de Lavar Brastemp', 'Eletrodomésticos', 2150.90, '1', '05/09/2025', 'Centro-Oeste'),
(6, 'Smart TV LG 65 polegadas', 'Eletrônicos', 5899.90, '1', '06/09/2025', 'Sudeste'),
(7, 'Fogão Esmaltec 4 bocas', 'Eletrodomésticos', 950.00, '1', '07/09/2025', 'Sul'),
(8, 'Cadeira Ergonômica', 'Móveis', 1100.25, '2', '08/09/2025', 'Nordeste'),
(9, 'Fones de Ouvido Sony', 'Eletrônicos', 450.00, '4', '09/09/2025', 'Norte'),
(10, 'Refrigerador Consul', 'Eletrodomésticos', 3500.00, '1', '10/09/2025', 'Centro-Oeste'),
(11, 'Sofá Retrátil', 'Móveis', 4800.00, '1', '11/09/2025', 'Sudeste'),
(12, 'Ar-Condicionado Split', 'Eletrodomésticos', 2999.00, '1', '12/09/2025', 'Sul'),
(13, 'Bicicleta Caloi Aro 29', 'Esportes', 1500.00, '1', '13/09/2025', 'Nordeste'),
(14, 'Monitor Gamer Asus', 'Eletrônicos', 2100.75, '2', '14/09/2025', 'Norte'),
(15, 'Guarda-Roupa de Casal', 'Móveis', 2700.50, '1', '15/09/2025', 'Centro-Oeste'),
(16, 'Forno Micro-ondas', 'Eletrodomésticos', 550.00, '2', '16/09/2025', 'Sudeste'),
(17, 'Impressora Multifuncional', 'Eletrônicos', 890.00, '1', '17/09/2025', 'Sul'),
(18, 'Cooktop 5 bocas', 'Eletrodomésticos', 1300.00, '1', '18/09/2025', 'Nordeste'),
(19, 'Sapato Social Masculino', 'Calçados', 350.00, '2', '19/09/2025', 'Norte'),
(20, 'Tablet Samsung Galaxy', 'Eletrônicos', 1900.00, '1', '20/09/2025', 'Centro-Oeste');

truncate vendas_raw;
select * from vendas_raw;

-- ETAPA DE EXTRACT & TRANSFORM
-- Limpa e trata os dados da tabela raw e insere na tabela staging
INSERT INTO `vendas_staging`
SELECT
    id_venda,
    LOWER(produto) AS produto,
    LOWER(categoria) AS categoria,
    ABS(valor_unitario) AS valor_unitario,
    CASE
        WHEN quantidade = 'cinco' THEN 5
        ELSE CAST(quantidade AS SIGNED)
    END AS quantidade,
    STR_TO_DATE(data_venda, '%d/%m/%Y') AS data_venda,
    regiao
FROM `vendas_raw`;

select * from vendas_staging;
