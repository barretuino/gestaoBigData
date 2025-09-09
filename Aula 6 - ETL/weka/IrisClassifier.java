package pjAula6;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;

import java.io.File;
import java.util.Random;

/**
 * Projeto de exemplo completo de Data Mining com Java e Weka.
 * Classifica a espécie de uma flor Iris usando um classificador de Árvore de Decisão.
 * --add-opens java.base/java.lang=ALL-UNNAMED -cp "weka.jar:."
 */
/**
 * A Estatística Kappa mede a concordância entre duas classificações de dados, 
 * levando em conta a concordância que seria esperada ao acaso. 
 * O valor de kappa varia de 0 (sem concordância acima do acaso) 
 * a 1 (concordância perfeita). 
 * Ele é usado para avaliar se duas pessoas (avaliadores) 
 * ou dois métodos chegam a conclusões semelhantes, 
 * além da coincidência que ocorreria aleatoriamente. 
 */
public class IrisClassifier {
	
	public static String traduzResposta(String estatisticas) {
        estatisticas = estatisticas.replace("Correctly Classified Instances", "Instâncias Classificadas Corretamente\t");
        estatisticas = estatisticas.replace("Incorrectly Classified Instances", "Instâncias Classificadas Incorretamente\t");
        estatisticas = estatisticas.replace("Kappa statistic","Estatística Kappa\t");
        estatisticas = estatisticas.replace("Mean absolute error","Erro absoluto médio\t");
        estatisticas = estatisticas.replace("Root mean squared error", "Erro quadrático médio\t");
        estatisticas = estatisticas.replace("Relative absolute error", "Erro absoluto relativo\t");
        estatisticas = estatisticas.replace("Root relative squared error","Erro quadrático relativo\t");
        estatisticas = estatisticas.replace("Total Number of Instances", "Número total de instâncias\t");

		return estatisticas;
	}

    public static void main(String[] args) {
        try {
            // Caminho para os arquivos. Use File.separator para compatibilidade de SO.
            String wekaJarPath = "lib" + File.separator + "weka.jar";
            String irisDataPath = "data" + File.separator + "breast-cancer.arff";

            // 1. Carregar o conjunto de dados
            System.out.println("1. Carregando o conjunto de dados Iris...");
            DataSource source = new DataSource(irisDataPath);
            Instances data = source.getDataSet();
            System.out.println("   -> Dados carregados com " + data.numInstances() + " instâncias.");

            // 2. Pré-processamento: Definir o atributo de classe
            // A última coluna é a classe a ser prevista.
            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }
            System.out.println("2. Atributo de classe definido como: '" + data.classAttribute().name() + "'");

            // 3. Treinar o modelo de classificação (J48 - Árvore de Decisão)
            System.out.println("3. Construindo o modelo J48...");
            J48 tree = new J48();
            // A instrução buildClassifier() treina o modelo.
            tree.buildClassifier(data);
            System.out.println("   -> Modelo construído com sucesso.");

            // 4. Avaliar o modelo usando validação cruzada
            System.out.println("4. Avaliando o modelo...");
            Evaluation eval = new Evaluation(data);
            // Executa a validação cruzada de 10 dobras (folds)
            eval.crossValidateModel(tree, data, 10, new Random(1));

            // 5. Imprimir os resultados da avaliação
            System.out.println("\n5. Resultados da Avaliação:");
            // toSummaryString() mostra a acurácia, erro, etc.
            System.out.println(traduzResposta(eval.toSummaryString()));
            
            // toMatrixString() mostra a matriz de confusão, que é crucial para entender o desempenho.
            System.out.println(eval.toMatrixString("Matriz de Confusão\n"));
            
        } catch (Exception e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
