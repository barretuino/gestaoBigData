package pjAula14;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.manager.bucket.BucketManager;
import com.couchbase.client.java.manager.bucket.BucketSettings;
import java.util.Map;
import java.util.Set;

public class ListarBucketsCouchbase {

    private static final String CONNECTION_STRING = "couchbase://localhost:5984";
    private static final String USERNAME = "root"; // Usuário com permissão de leitura de Buckets
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            // 1. Conectar ao Cluster com as credenciais administrativas
            cluster = Cluster.connect(CONNECTION_STRING, USERNAME, PASSWORD);
            
            // 2. Acessar o Manager de Buckets
            // O BucketManager é a ferramenta usada para tarefas administrativas como 
            // listar, criar, ou deletar Buckets.
            BucketManager bucketManager = cluster.buckets();
            
            System.out.println("--- 🔎 Buckets Disponíveis no Cluster Couchbase ---");
            
            // 3. Obter o mapa de todos os Buckets e suas configurações
            Map<String, BucketSettings> buckets = bucketManager.getAllBuckets();
            
            // 4. Extrair e imprimir apenas os nomes dos Buckets (as chaves do mapa)
            Set<String> bucketNames = buckets.keySet();
            
            if (bucketNames.isEmpty()) {
                System.out.println("Nenhum Bucket encontrado.");
            } else {
                for (String name : bucketNames) {
                    System.out.println("-> " + name);
                }
            }

        } catch (Exception e) {
            System.err.println("\n🚫 Erro ao conectar ou listar Buckets. Verifique se o servidor Couchbase está rodando e se as credenciais têm permissão de 'Bucket Management'.");
            e.printStackTrace();
        } finally {
            if (cluster != null) {
                cluster.disconnect();
                System.out.println("\nConexão com o cluster encerrada.");
            }
        }
    }
}