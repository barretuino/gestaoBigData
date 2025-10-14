package pjAula10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusinessIntelligenceApp {

    public static void main(String[] args) {
        // 1. DADOS BRUTOS (Fatos)
        List<TransacaoVenda> transacoes = new ArrayList<>();
        // 2024
        transacoes.add(new TransacaoVenda(new Produto("Eletrônicos", "Smartphone X"), new Tempo(2024, "Jan"), 1200.00));
        transacoes.add(new TransacaoVenda(new Produto("Móveis", "Cadeira Gamer"), new Tempo(2024, "Jan"), 550.00));
        transacoes.add(new TransacaoVenda(new Produto("Eletrônicos", "Smartphone X"), new Tempo(2024, "Fev"), 1300.00));
        transacoes.add(new TransacaoVenda(new Produto("Eletrônicos", "Notebook Y"), new Tempo(2024, "Fev"), 3500.00));
        // 2025
        transacoes.add(new TransacaoVenda(new Produto("Eletrônicos", "Smartphone X"), new Tempo(2025, "Jan"), 1500.00));
        transacoes.add(new TransacaoVenda(new Produto("Móveis", "Mesa Escritório"), new Tempo(2025, "Jan"), 800.00));
        transacoes.add(new TransacaoVenda(new Produto("Móveis", "Cadeira Gamer"), new Tempo(2025, "Fev"), 600.00));

        // Cria o Cubo
        CuboVendas cubo = new CuboVendas(transacoes);
        
        System.out.println("----------------------------------------------");
        System.out.println("1. ROLl UP: Vendas Totais por Ano");
        System.out.println("----------------------------------------------");
        
        Map<Integer, Double> vendasAnuais = cubo.rollUpVendasPorAno();
        vendasAnuais.forEach((ano, total) -> 
            System.out.printf("Vendas em %d: R$ %.2f\n", ano, total)
        );
        
        // **VISUALIZAÇÃO / INSIGHT (Conhecimento):** // Observa-se que 2024 (R$ 6550,00) teve vendas totais muito superiores 
        // a 2025 (R$ 2900,00) até agora. (Se os dados fossem anuais completos, 
        // seria uma informação valiosa, senão, apenas parcial).

        System.out.println("\n----------------------------------------------");
        System.out.println("2. SLICE & ROLL UP: Vendas de Eletrônicos por Produto");
        System.out.println("----------------------------------------------");
        
        String categoria = "Eletrônicos";
        Map<String, Double> sliceEletronicos = cubo.slicePorCategoria(categoria);
        sliceEletronicos.forEach((produto, total) -> 
            System.out.printf("Vendas de '%s': R$ %.2f\n", produto, total)
        );
        
        // **VISUALIZAÇÃO / INSIGHT (Conhecimento):**
        // O produto 'Notebook Y' (R$ 3500,00) representa a maior parte das vendas 
        // de 'Eletrônicos' no período analisado, apesar do 'Smartphone X' ter mais transações. 
        // Isso sugere que o Notebook tem um ticket médio mais alto.

        System.out.println("\n----------------------------------------------");
        System.out.println("3. RELATÓRIO DETALHADO (Drill Down Implícito): Mês vs Categoria");
        System.out.println("----------------------------------------------");
        
        Map<String, Map<String, Double>> detalhado = cubo.vendasDetalhadaPorMesECategoria();
        detalhado.forEach((mes, vendasPorCategoria) -> {
            System.out.println("Mês: " + mes);
            vendasPorCategoria.forEach((cat, total) ->
                System.out.printf("  - %s: R$ %.2f\n", cat, total)
            );
        });
    }
}