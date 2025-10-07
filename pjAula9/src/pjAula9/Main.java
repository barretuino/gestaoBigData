package pjAula9;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		ProdutoDAO produtoDAO = new ProdutoDAO();

		System.out.println("### Buscando todos os produtos (sem filtros) ###");
		List<Produto> todosProdutos = produtoDAO.buscarProdutos(null, null);
		//todosProdutos.forEach(System.out::println);

		System.out.println("\n---");

		System.out.println("### Buscando produtos com 'Laptop' no nome ###");
		List<Produto> produtosPorNome = produtoDAO.buscarProdutos("Alimentos", null);
		produtosPorNome.forEach(System.out::println);

		System.out.println("\n---");

		System.out.println("### Buscando produtos da categoria 'Eletrônicos' ###");
		List<Produto> produtosPorCategoria = produtoDAO.buscarProdutos(null, "Eletrônicos");
		//produtosPorCategoria.forEach(System.out::println);

		System.out.println("\n---");

		System.out.println("### Buscando produtos com 'Fone' no nome e categoria 'Acessórios' ###");
		List<Produto> produtosCombinados = produtoDAO.buscarProdutos("Fone", "Acessórios");
		produtosCombinados.forEach(System.out::println);
	}
}