package pjAula14;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;

public class ExemploCouchbaseJava {

    // Configurações do seu cluster Couchbase
    private static final String CONNECTION_STRING = "couchbase://localhost:5984";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String BUCKET_NAME = "unisal"; // Deve existir no seu cluster

    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            // 
            
            // 1. Conectar ao Cluster
            cluster = Cluster.connect(CONNECTION_STRING, USERNAME, PASSWORD);

            // 2. Abrir o Bucket e a Coleção Padrão (Default Collection)
            Bucket bucket = cluster.bucket(BUCKET_NAME);
            bucket.waitUntilReady(java.time.Duration.ofSeconds(10)); // Espera o bucket estar pronto
            Collection collection = bucket.defaultCollection();
            
            // --- C: CREATE (Inserir/Upsert) ---
            String documentId = "usuario::123";
            JsonObject perfilUsuario = JsonObject.create()
                .put("nome", "Pedro Alcântara")
                .put("email", "pedro@exemplo.com.br")
                .put("tipo", "usuario") // Campo para indexação/queries N1QL
                .put("ativo", true);

            collection.insert(documentId, perfilUsuario);
            System.out.println("Documento '" + documentId + "' inserido com sucesso!");

            // --- R: READ (Ler) ---
            GetResult getResult = collection.get(documentId);
            JsonObject documentoLido = getResult.contentAsObject();
            System.out.println("\nDocumento lido: " + documentoLido.toString());

            // --- U: UPDATE (Atualizar) ---
            // Modifica o objeto JSON lido
            documentoLido.put("ativo", false);
            documentoLido.put("cidade", "Rio de Janeiro");
            
            collection.replace(documentId, documentoLido);
            System.out.println("\nDocumento '" + documentId + "' atualizado (ativo = false).");
            
            // Confirma a atualização
            System.out.println("Novo documento lido: " + collection.get(documentId).contentAsObject().toString());

            // --- D: DELETE (Deletar) ---
            collection.remove(documentId);
            System.out.println("\nDocumento '" + documentId + "' deletado.");
            
            // Tenta ler o documento deletado
            try {
                collection.get(documentId);
            } catch (DocumentNotFoundException e) {
                System.out.println("Documento '" + documentId + "' não encontrado após remoção (Comportamento esperado).");
            }

        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cluster != null) {
                cluster.disconnect();
                System.out.println("\nConexão com o cluster encerrada.");
            }
        }
    }
}