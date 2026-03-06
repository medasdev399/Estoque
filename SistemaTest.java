public class SistemaTest {
    public static void main(String[] args) {
        testProduto();
        testProdutoComPrecoNegativo();
        testProdutoComNomeVazio();
        testProdutoComCodigoZero();
        testMultiplosProdutos();
        System.out.println("\nTodos os testes passaram!");
    }
    
    public static void testProduto() {
        Produto produto = new Produto(1, "Produto A", 10.0);
        
        assert produto.getCodigo() == 1 : "Código incorreto";
        assert produto.getNome().equals("Produto A") : "Nome incorreto";
        assert produto.getPreco() == 10.0 : "Preço incorreto";
        
        System.out.println("testProduto passou");
    }
    
    public static void testProdutoComPrecoNegativo() {
        Produto produto = new Produto(2, "Produto B", -5.0);
        assert produto.getPreco() == -5.0 : "Preço negativo não foi aceito";
        System.out.println("testProdutoComPrecoNegativo passou");
    }
    
    public static void testProdutoComNomeVazio() {
        Produto produto = new Produto(3, "", 15.0);
        assert produto.getNome().isEmpty() : "Nome vazio não foi aceito";
        System.out.println("testProdutoComNomeVazio passou");
    }
    
    public static void testProdutoComCodigoZero() {
        Produto produto = new Produto(0, "Produto C", 20.0);
        assert produto.getCodigo() == 0 : "Código zero não foi aceito";
        System.out.println("testProdutoComCodigoZero passou");
    }
    
    public static void testMultiplosProdutos() {
        Produto p1 = new Produto(1, "Produto 1", 10.0);
        Produto p2 = new Produto(2, "Produto 2", 20.0);
        Produto p3 = new Produto(3, "Produto 3", 30.0);
        
        assert p1.getCodigo() != p2.getCodigo() : "Códigos duplicados";
        assert p2.getPreco() > p1.getPreco() : "Comparação de preço falhou";
        assert p3.getNome().contains("Produto") : "Nome não contém 'Produto'";
        
        System.out.println("testMultiplosProdutos passou");
    }
}