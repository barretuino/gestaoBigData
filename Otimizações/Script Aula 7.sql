CREATE TABLE produtos (
   id INT PRIMARY KEY AUTO_INCREMENT,
   nome VARCHAR(255) NOT NULL,
   categoria VARCHAR(100),
   data_cadastro DATE,
   preco DECIMAL(10, 2)
);

-- Inserir milhões de registros (use um script ou ferramenta para isso)
-- Exemplo: inserir 1 milhão de produtos
INSERT INTO produtos (nome, categoria, data_cadastro, preco)
SELECT
   CONCAT('Produto ', FLOOR(1 + (RAND() * 1000000))),
   CASE
	   WHEN RAND() < 0.2 THEN 'Eletrônicos'
	   WHEN RAND() < 0.4 THEN 'Livros'
	   WHEN RAND() < 0.6 THEN 'Roupas'
	   WHEN RAND() < 0.8 THEN 'Alimentos'
	   ELSE 'Esportes'
   END,
   DATE_SUB('2025-09-16', INTERVAL FLOOR(RAND() * 1000) DAY),
   10 + (RAND() * 1000)
FROM
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) a,
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) b,
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) c,
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) d,
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) e,
   (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10) f;

-- Consulta Lenta (Sem Índice)
-- Otimização: Nenhuma.
-- Problema: O banco fará um "Seq Scan" (leitura completa da tabela).
-- Execute e mostre o EXPLAIN. A operação será lenta.
EXPLAIN SELECT * FROM produtos WHERE categoria = 'Livros';

-- Adicione o Índice
-- Criar um índice na coluna 'categoria'
-- Mostre o comando e explique o motivo.
CREATE INDEX idx_categoria ON produtos (categoria);

-- Consulta Otimizada (Com Índice)
-- Otimização: Uso de índice.
-- Mostre o EXPLAIN novamente. A operação agora será um "Index Scan" ou similar.
-- Execute a consulta para mostrar a diferença de velocidade.
EXPLAIN SELECT * FROM produtos WHERE categoria = 'Livros';

-- Consulta Lenta (Selecionando tudo)
-- Otimização: Nenhuma.
-- Problema: Traz todas as colunas, incluindo as que não são usadas na aplicação.
-- Pode consumir mais memória e largura de banda da rede.
SELECT * FROM produtos WHERE categoria = 'Eletrônicos' LIMIT 10;

-- Consulta Otimizada (Selecionando colunas específicas)
-- Otimização: Seleção de colunas.
-- Problema: Traz apenas as colunas 'nome' e 'preco'.
-- Mais eficiente em termos de recursos.
SELECT nome, preco FROM produtos WHERE categoria = 'Eletrônicos' LIMIT 10;

-- Funções em Cláusulas WHERE
-- Este é um erro comum que impede o uso de índices.
-- Consulta Lenta (Com Função)
-- Otimização: Nenhuma.
-- Problema: O uso de YEAR() impede o banco de usar qualquer índice na coluna 'data_cadastro'.
-- O banco de dados precisa calcular a função para cada linha da tabela antes de filtrar.
EXPLAIN SELECT * FROM produtos WHERE YEAR(data_cadastro) = 2024;

-- Consulta Otimizada (Sem Função)      
-- Otimização: Reescrever a condição.
-- O problema é resolvido reescrevendo a consulta para usar um intervalo de datas.
-- Isso permite que o banco de dados use um índice na coluna 'data_cadastro' se ele existir.
EXPLAIN SELECT * FROM produtos WHERE data_cadastro >= '2024-01-01' AND data_cadastro <= '2024-12-31';

-- O problema do LIKE '%valor'
-- Este exemplo mostra como a posição do % afeta a performance.
-- 1. Consulta Lenta (Com % no início)
-- Otimização: Nenhuma.
-- Problema: O '%' no início impede o uso de índices de texto (B-tree).
-- O banco de dados fará um Seq Scan (leitura completa da tabela).
EXPLAIN SELECT * FROM produtos WHERE nome LIKE '%Produto 123%';

-- Consulta Otimizada (Com % no final)       
-- Otimização: Posição do '%'.
-- Problema: O '%' no final permite que o banco de dados use um índice na coluna 'nome'.
EXPLAIN SELECT * FROM produtos WHERE nome LIKE 'Produto 123%';

-- Solução para busca "no meio"
-- Para buscas no meio, uma solução mais robusta é a busca por texto completo (FULL-TEXT SEARCH).         
-- Criar um índice FULL-TEXT (para MySQL)
CREATE FULLTEXT INDEX idx_nome_fulltext ON produtos (nome);

-- Usar a busca FULL-TEXT
SELECT * FROM produtos WHERE MATCH(nome) AGAINST('Produto 123');