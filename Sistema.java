import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

class Produto {
    private final int codigo;
    private final String nome;
    private final double preco;
    public Produto(int codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
    public int getCodigo() {
        return codigo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPreco() {
        return preco;
    }
}
public class Sistema extends JFrame{
    //inicia a interface gráfica
    public static void main(String[] args) {
        try {
        java.awt.EventQueue.invokeLater(() -> {
            new Sistema().setVisible(true);
        });
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao iniciar o sistema: " + e.getMessage());
    }
    }
    public Sistema() {//Método construtor para criar a interface gráfica do sistema
        setTitle("Sistema de Cadastro de Produtos");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 20, 150, 25);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 80, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 60, 150, 25);
        add(txtNome);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 100, 80, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(100, 100, 150, 25);
        add(txtPreco);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(270, 20, 120, 25);
        btnCadastrar.addActionListener(e -> cadastrarProduto());
        add(btnCadastrar);

        JButton btnListar = new JButton("Listar");
        btnListar.setBounds(270, 60, 120, 25);
        btnListar.addActionListener(e -> acaoListar());
        add(btnListar);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setBounds(270, 100, 120, 25);
        btnRemover.addActionListener(e -> removerProdutoPorCodigo());
        add(btnRemover);

        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.setBounds(270, 140, 120, 25);
        btnExportar.addActionListener(e -> exportarParaCSV());
        add(btnExportar);

        JButton btnImportar = new JButton("Importar CSV");
        btnImportar.setBounds(270, 180, 120, 25);
        btnImportar.addActionListener(e -> importarProdutosCSV());
        add(btnImportar);

        tabela = new JTable(new DefaultTableModel(new Object[]{"Código", "Nome", "Preço"},0));
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(20,220,450,200);
        add(scrollPane);
    }

    private final JTextField txtCodigo;
    private final JTextField txtNome;
    private final JTextField txtPreco;
    private final JTable tabela;
    private final List<Produto> listaProdutos = new ArrayList<>();

private void cadastrarProduto() {//Método para cadastrar um novo produto, solicitando confirmação do usuário antes de adicionar o produto à lista
    int resposta = JOptionPane.showConfirmDialog(null, "Deseja cadastrar um novo produto?", "Cadastro de Produto", JOptionPane.YES_NO_OPTION);
    if (resposta == JOptionPane.YES_OPTION){
        try{
        int cod = Integer.parseInt(txtCodigo.getText());
        String nome = txtNome.getText();
        double preco = Double.parseDouble(txtPreco.getText());

        listaProdutos.add(new Produto(cod,nome,preco));
        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        txtCodigo.setText("");
        txtNome.setText("");
        txtPreco.setText("");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Código e preço devem ser numéricos.");
    }
    } else {
        JOptionPane.showMessageDialog(null, "Cadastro cancelado.");
    }
}
private void acaoListar(){
    DefaultTableModel model = (DefaultTableModel) tabela.getModel();
    model.setRowCount(0);

    for(Produto p : listaProdutos){
        model.addRow(new Object[]{p.getCodigo(), p.getNome(), p.getPreco()});
    }
}
private void removerProdutoPorCodigo() {
    int linhaSelecionada = tabela.getSelectedRow();
    if(linhaSelecionada == -1) {
        JOptionPane.showMessageDialog(null, "Selecione um produto para remover.");
        return;
    }
    int codigoParaRemover = (int) tabela.getValueAt(linhaSelecionada, 0 );
    int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o produto com código " + codigoParaRemover + "?", "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);
    if (confirmacao == JOptionPane.YES_OPTION){
        listaProdutos.removeIf(p -> p.getCodigo() == codigoParaRemover);
        acaoListar();
        JOptionPane.showMessageDialog(null, "Produto Removido com sucesso!");
    } else {
        JOptionPane.showMessageDialog(null, "Remoção cancelada.");
    }
}
private void exportarParaCSV(){//Método para exportar a lista de produtos para um arquivo CSV, solicitando confirmação do usuário antes de realizar a exportação
    
    int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja exportar a lista de produtos para um arquivo CSV?", "Confirmação de Exportação", JOptionPane.YES_NO_OPTION);
    if (confirmacao != JOptionPane.YES_OPTION){
        JOptionPane.showMessageDialog(null, "Exportação cancelada.");
        return;
    }
    String nomeArquivo = "produtos.csv";
    try (java.io.FileWriter writer = new java.io.FileWriter(nomeArquivo)){
        writer.append("Código;Nome;Preço\n");
        for (Produto p : listaProdutos){
            writer.append(String.valueOf(p.getCodigo()))
            .append(";")
            .append(p.getNome())
            .append(";")
            .append(String.valueOf(p.getPreco()))
            .append("\n");
        }
        JOptionPane.showMessageDialog(null, "Arquivo CSV gerado com sucesso: " + nomeArquivo);
    } catch (java.io.IOException e) {
        JOptionPane.showMessageDialog(null, "Erro ao exportar arquivo: " + e.getMessage());
    }
}
    public void importarProdutosCSV(){
        JFileChooser escolherArquivo = new JFileChooser();
        escolherArquivo.setDialogTitle("Selecione o arquivo CSV para importar");

        FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("Arquivo CSV (*.csv)", "csv");
        escolherArquivo.setFileFilter(filtroCSV);

        int resposta = escolherArquivo.showOpenDialog(tabela);

        if (resposta == JFileChooser.APPROVE_OPTION){
            File arquivoSelecionado = escolherArquivo.getSelectedFile();
            try(BufferedReader br = new BufferedReader(new FileReader(arquivoSelecionado))){
                String linha;
                br.readLine();

                while((linha = br.readLine()) !=null){
                    String[] dados = linha.split(";");
                    if(dados.length >= 3){
                       try{
                        int codigo = Integer.parseInt(dados[0].trim());
                        String nome = dados[1].trim();
                        double preco = Double.parseDouble(dados[2].trim());
                        listaProdutos.add(new Produto(codigo, nome, preco));
                    } catch (NumberFormatException e){
                System.out.println("Erro ao processar dados do arquivo: " + e.getMessage());
                   }
                } 
            }
            acaoListar();
                JOptionPane.showMessageDialog(null, "Arquivo CSV importado com sucesso: " + arquivoSelecionado.getName());
            } catch (IOException e){
                JOptionPane.showMessageDialog(null, "Erro ao importar arquivo: " + e.getMessage());
            }
        }
    }
}