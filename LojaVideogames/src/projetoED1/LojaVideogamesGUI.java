package projetoED1;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LojaVideogamesGUI {


    private final JFrame quadro;
    private final JTable tabelaProdutos;
    private final EstoqueTableModel modeloTabela; 

    private final Lista estoque;
    private final Lista historicoDeVendas;

    public LojaVideogamesGUI() {
        estoque = new Lista();
        historicoDeVendas = new Lista();

        quadro = new JFrame("Estoque - Loja de Videogames");
        quadro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quadro.setSize(800, 600);
        quadro.setLocationRelativeTo(null);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        modeloTabela = new EstoqueTableModel(new ArrayList<>());
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane painelRolagem = new JScrollPane(tabelaProdutos);
        painel.add(painelRolagem, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(4, 3, 5, 5));

        JButton botaoAdicionar = new JButton("Adicionar Jogo");
        botaoAdicionar.addActionListener(e -> adicionarProduto());
        painelBotoes.add(botaoAdicionar);

        JButton botaoEditar = new JButton("Editar Jogo");
        botaoEditar.addActionListener(e -> editarProduto());
        painelBotoes.add(botaoEditar);

        JButton botaoVender = new JButton("Vender Jogo");
        botaoVender.addActionListener(e -> venderProduto());
        painelBotoes.add(botaoVender);

        JButton botaoRepor = new JButton("Repor Estoque");
        botaoRepor.addActionListener(e -> reporEstoque());
        painelBotoes.add(botaoRepor);

        JButton botaoBuscar = new JButton("Buscar Jogo");
        botaoBuscar.addActionListener(e -> buscarProduto());
        painelBotoes.add(botaoBuscar);

        JButton botaoOrdenar = new JButton("Ordenar Produtos...");
        botaoOrdenar.addActionListener(e -> ordenarProdutos());
        painelBotoes.add(botaoOrdenar);

        JButton botaoRelatorioVendas = new JButton("Relatório de Vendas");
        botaoRelatorioVendas.addActionListener(e -> relatorioVenda());
        painelBotoes.add(botaoRelatorioVendas);

        JButton botaoRelatorioEstoque = new JButton("Relatório de Estoque");
        botaoRelatorioEstoque.addActionListener(e -> relatorioEstoque());
        painelBotoes.add(botaoRelatorioEstoque);

        JButton botaoSalvar = new JButton("Salvar Dados (CSV)");
        botaoSalvar.addActionListener(e -> salvarDados());
        painelBotoes.add(botaoSalvar);
        
        JButton botaoCarregar = new JButton("Carregar Dados (CSV)");
        botaoCarregar.addActionListener(e -> carregarDados());
        painelBotoes.add(botaoCarregar);
        
        JButton botaoLimpar = new JButton("Limpar Dados");
        botaoLimpar.addActionListener(e -> limparDadosAtuais());
        painelBotoes.add(botaoLimpar);

        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> sairDoPrograma());
        painelBotoes.add(botaoSair);

        painel.add(painelBotoes, BorderLayout.SOUTH);
        quadro.add(painel);
        
        quadro.setVisible(true);

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setListaDeProdutos(estoque.toArrayList());
    }
    

    private void adicionarProduto() {
        JTextField campoCodigo = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoValorEntrada = new JTextField();
        JTextField campoValorSaida = new JTextField();
        JTextField campoQtdEstoque = new JTextField();

        JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
        painelDialogo.add(new JLabel("Código:"));
        painelDialogo.add(campoCodigo);
        painelDialogo.add(new JLabel("Nome do Jogo:"));
        painelDialogo.add(campoNome);
        painelDialogo.add(new JLabel("Marca:"));
        painelDialogo.add(campoMarca);
        painelDialogo.add(new JLabel("Valor de Compra (R$):"));
        painelDialogo.add(campoValorEntrada);
        painelDialogo.add(new JLabel("Valor de Venda (R$):"));
        painelDialogo.add(campoValorSaida);
        painelDialogo.add(new JLabel("Quantidade em Estoque:"));
        painelDialogo.add(campoQtdEstoque);

        int resultado = JOptionPane.showConfirmDialog(quadro, painelDialogo, "Adicionar Novo Jogo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int cod = Integer.parseInt(campoCodigo.getText());
                String nome = campoNome.getText();
                String marca = campoMarca.getText();
                double valorEntrada = Double.parseDouble(campoValorEntrada.getText());
                double valorSaida = Double.parseDouble(campoValorSaida.getText());
                int qtdEstoque = Integer.parseInt(campoQtdEstoque.getText());

                if (nome.trim().isEmpty() || marca.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(quadro, "Nome e Marca não podem ser vazios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (valorSaida <= valorEntrada) {
                    JOptionPane.showMessageDialog(quadro, "O valor de venda deve ser maior que o valor de compra.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Estoque produto = new Estoque(cod, nome, marca, valorEntrada, valorSaida, qtdEstoque);

                if (!estoque.inserirProdutoOrdenado(produto)) {
                    JOptionPane.showMessageDialog(quadro, "Erro: Código já existe no estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    atualizarTabela();
                    JOptionPane.showMessageDialog(quadro, "Jogo adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(quadro, "Erro: Verifique se os campos numéricos estão corretos.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void editarProduto() {
        String entrada = JOptionPane.showInputDialog(quadro, "Digite o código do jogo a ser editado:");
        if (entrada == null) return; // Usuário cancelou

        try {
            int codigo = Integer.parseInt(entrada);
            Estoque produto = estoque.buscarProduto(codigo);

            if (produto == null) {
                JOptionPane.showMessageDialog(quadro, "Erro: Jogo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField campoNome = new JTextField(produto.getNome());
            JTextField campoMarca = new JTextField(produto.getMarca());
            JTextField campoValorEntrada = new JTextField(String.valueOf(produto.getValorEntrada()));
            JTextField campoValorSaida = new JTextField(String.valueOf(produto.getValorSaida()));
            JTextField campoQtdEstoque = new JTextField(String.valueOf(produto.getQtdEstoque()));

            JPanel painelDialogo = new JPanel(new GridLayout(0, 2, 5, 5));
            painelDialogo.add(new JLabel("Nome do Jogo:"));
            painelDialogo.add(campoNome);
            painelDialogo.add(new JLabel("Marca:"));
            painelDialogo.add(campoMarca);
            painelDialogo.add(new JLabel("Valor de Compra (R$):"));
            painelDialogo.add(campoValorEntrada);
            painelDialogo.add(new JLabel("Valor de Venda (R$):"));
            painelDialogo.add(campoValorSaida);
            painelDialogo.add(new JLabel("Quantidade em Estoque:"));
            painelDialogo.add(campoQtdEstoque);

            int resultado = JOptionPane.showConfirmDialog(quadro, painelDialogo, "Editando Jogo: " + produto.getNome(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                double novoValorEntrada = Double.parseDouble(campoValorEntrada.getText());
                double novoValorSaida = Double.parseDouble(campoValorSaida.getText());

                if (novoValorSaida <= novoValorEntrada) {
                    JOptionPane.showMessageDialog(quadro, "O valor de venda deve ser maior que o valor de compra.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                produto.setNome(campoNome.getText());
                produto.setMarca(campoMarca.getText());
                produto.setValorEntrada(novoValorEntrada);
                produto.setValorSaida(novoValorSaida);
                produto.setQtdEstoque(Integer.parseInt(campoQtdEstoque.getText()));

                atualizarTabela();
                JOptionPane.showMessageDialog(quadro, "Produto atualizado com sucesso!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(quadro, "Erro: Código inválido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void venderProduto() {
        String entradaCodigo = JOptionPane.showInputDialog(quadro, "Digite o código do produto para vender:");
        if (entradaCodigo == null) return;

        try {
            int codigo = Integer.parseInt(entradaCodigo);
            Estoque produto = estoque.buscarProduto(codigo);

            if (produto == null) {
                JOptionPane.showMessageDialog(quadro, "Erro: Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String entradaQtd = JOptionPane.showInputDialog(quadro, "Jogo: " + produto.getNome() + "\nEstoque atual: " + produto.getQtdEstoque() + "\n\nQuantidade a vender:");
            if (entradaQtd == null) return;

            int quantidade = Integer.parseInt(entradaQtd);

            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(quadro, "A quantidade deve ser positiva.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (produto.getQtdEstoque() < quantidade) {
                JOptionPane.showMessageDialog(quadro, "Estoque insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);

            double valorTotalVenda = quantidade * produto.getValorSaida();
            Venda novaVenda = new Venda(codigo, produto.getNome(), quantidade, valorTotalVenda);
            historicoDeVendas.inserirFim(novaVenda);

            JOptionPane.showMessageDialog(quadro, "Venda realizada com sucesso!");
            atualizarTabela();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(quadro, "Erro: Entrada inválida. Use apenas números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void reporEstoque() {
        String entradaCodigo = JOptionPane.showInputDialog(quadro, "Digite o código do produto para repor estoque:");
        if (entradaCodigo == null) return;

        try {
            int codigo = Integer.parseInt(entradaCodigo);
            Estoque produto = estoque.buscarProduto(codigo);

            if (produto == null) {
                JOptionPane.showMessageDialog(quadro, "Erro: Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String entradaQtd = JOptionPane.showInputDialog(quadro, "Jogo: " + produto.getNome() + "\nEstoque atual: " + produto.getQtdEstoque() + "\n\nQuantidade a adicionar:");
            if (entradaQtd == null) return;

            int quantidade = Integer.parseInt(entradaQtd);
            if (quantidade <= 0) {
                JOptionPane.showMessageDialog(quadro, "A quantidade deve ser positiva.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            produto.setQtdEstoque(produto.getQtdEstoque() + quantidade);
            JOptionPane.showMessageDialog(quadro, "Estoque atualizado!");
            atualizarTabela();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(quadro, "Erro: Código ou quantidade inválida.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProduto() {
        String entrada = JOptionPane.showInputDialog(quadro, "Digite o código do jogo a ser buscado:");
        if (entrada == null) return;

        try {
            int codigo = Integer.parseInt(entrada);
            Estoque produto = estoque.buscarProduto(codigo);

            if (produto == null) {
                JOptionPane.showMessageDialog(quadro, "Jogo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(quadro, produto.toString(), "Jogo Encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(quadro, "Erro: Código inválido. Digite apenas números.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ordenarProdutos() {
        if (estoque.estaVazia() || estoque.tamanho() <= 1) {
            JOptionPane.showMessageDialog(quadro, "Não há jogos suficientes para ordenar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object[] options = {"Por Código", "Por Nome"};
        int escolha = JOptionPane.showOptionDialog(quadro, "Como você deseja ordenar a lista de jogos?", "Escolha o Critério de Ordenação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (escolha == JOptionPane.CLOSED_OPTION) {
            return;
        }

        ArrayList<Estoque> listaTemporaria = estoque.toArrayList();
        
        if (escolha == 0) { 
            listaTemporaria.sort(Comparator.comparing(Estoque::getCod));
        } else {
            listaTemporaria.sort(Comparator.comparing(Estoque::getNome, String.CASE_INSENSITIVE_ORDER));
        }
        
        estoque.inicio = null;
        estoque.fim = null;
        for (Estoque p : listaTemporaria) {
            estoque.inserirFim(p);
        }
        
        atualizarTabela();
        JOptionPane.showMessageDialog(quadro, "Jogos ordenados com sucesso!", "Ordenação Concluída", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void relatorioVenda() {
        if (historicoDeVendas.estaVazia()) {
            JOptionPane.showMessageDialog(quadro, "Nenhuma venda registrada ainda.", "Relatório de Vendas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextArea areaTexto = new JTextArea(20, 50);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder relatorio = new StringBuilder("--- RELATÓRIO DE VENDAS ---\n\n");
        double totalGeral = 0;
        int qtdTotal = 0;

        Bloco atual = historicoDeVendas.inicio;
        while(atual != null) {
            Venda venda = (Venda) atual.data;
            relatorio.append(venda.toString()).append("\n---------------------------------\n");
            totalGeral += venda.getValorTotal();
            qtdTotal += venda.getQuantidadeVendida();
            atual = atual.prox;
        }
        
        relatorio.append("\nResumo Geral:\n");
        relatorio.append("   - Quantidade Total de Jogos Vendidos: ").append(qtdTotal).append("\n");
        relatorio.append(String.format("   - Valor Total Arrecadado: R$ %.2f", totalGeral));
        
        areaTexto.setText(relatorio.toString());
        
        JOptionPane.showMessageDialog(quadro, new JScrollPane(areaTexto), "Relatório de Vendas", JOptionPane.PLAIN_MESSAGE);
    }
 
    private void relatorioEstoque() {
        if (estoque.estaVazia()) {
            JOptionPane.showMessageDialog(quadro, "O estoque está vazio.", "Relatório de Estoque", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JTextArea areaTexto = new JTextArea(20, 70);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder relatorio = new StringBuilder("--- RELATÓRIO DE ESTOQUE ATUAL ---\n\n");
        double valorTotalEstoque = 0;
        int itensTotais = 0;
        
        Bloco atual = estoque.inicio;
        while (atual != null) {
            Estoque p = (Estoque) atual.data;
            relatorio.append(p.toString()).append("\n");
            valorTotalEstoque += p.getQtdEstoque() * p.getValorEntrada();
            itensTotais += p.getQtdEstoque();
            atual = atual.prox;
        }
        
        relatorio.append("\n------------------------------------------------------------\n");
        relatorio.append("Resumo do Estoque:\n");
        relatorio.append("  - Número de Títulos Diferentes: ").append(estoque.tamanho()).append("\n");
        relatorio.append("  - Quantidade Total de Itens: ").append(itensTotais).append("\n");
        relatorio.append(String.format("  - Custo Total do Estoque: R$ %.2f", valorTotalEstoque));

        areaTexto.setText(relatorio.toString());
        JOptionPane.showMessageDialog(quadro, new JScrollPane(areaTexto), "Relatório de Estoque", JOptionPane.PLAIN_MESSAGE);
    }


    private void limparDadosAtuais() {
        int confirmar = JOptionPane.showConfirmDialog(
            quadro, 
            "Isso irá apagar todos os dados da sessão atual (estoque e vendas).\n" +
            "Os arquivos salvos em disco não serão afetados após sua escolha.\n\n" +
            "Deseja continuar?", 
            "Confirmar Limpeza de Dados", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            estoque.inicio = null;
            estoque.fim = null;

            historicoDeVendas.inicio = null;
            historicoDeVendas.fim = null;

            atualizarTabela();

            JOptionPane.showMessageDialog(quadro, 
                "Os dados da sessão foram limpos.\n\n" +
                "Para limpar os arquivos CSV, clique em 'Salvar Dados' e\n" +
                "sobrescreva os arquivos existentes.", 
                "Dados Limpos", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void salvarDados() {
        JFileChooser selecionador = new JFileChooser(".");
        selecionador.setDialogTitle("Escolha um diretório para salvar");
        selecionador.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int escolha = selecionador.showSaveDialog(quadro);

        if (escolha == JFileChooser.APPROVE_OPTION) {
            File diretorio = selecionador.getSelectedFile();
            File arquivoEstoque = new File(diretorio, "estoque.csv");
            File arquivoVendas = new File(diretorio, "vendas.csv");

            try {
                salvarEstoqueCSV(arquivoEstoque);
                salvarVendasCSV(arquivoVendas);
                JOptionPane.showMessageDialog(quadro, "Dados salvos com sucesso em:\n" + diretorio.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(quadro, "Erro ao salvar os dados: " + e.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private void carregarDados() {
        int confirm = JOptionPane.showConfirmDialog(quadro, 
            "Isso substituirá todos os dados atuais em memória. Deseja continuar?", 
            "Confirmar Carregamento", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        JFileChooser selecionador = new JFileChooser(".");
        selecionador.setDialogTitle("Selecione os arquivos CSV para carregar");
        selecionador.setFileFilter(new FileNameExtensionFilter("Arquivos CSV", "csv"));
        selecionador.setMultiSelectionEnabled(true);

        int escolha = selecionador.showOpenDialog(quadro);
        
        if (escolha == JFileChooser.APPROVE_OPTION) {
            File[] arquivos = selecionador.getSelectedFiles();
            boolean estoqueCarregado = false;
            boolean vendasCarregadas = false;

            try {
                for (File arquivo : arquivos) {
                    if (arquivo.getName().equalsIgnoreCase("estoque.csv")) {
                        carregarEstoqueCSV(arquivo);
                        estoqueCarregado = true;
                    } else if (arquivo.getName().equalsIgnoreCase("vendas.csv")) {
                        carregarVendasCSV(arquivo);
                        vendasCarregadas = true;
                    }
                }
                
                atualizarTabela();
                String msg = "";
                if(estoqueCarregado) msg += "Estoque carregado.\n";
                if(vendasCarregadas) msg += "Histórico de vendas carregado.";
                if(msg.isEmpty()) msg = "Nenhum arquivo 'estoque.csv' ou 'vendas.csv' foi selecionado.";

                JOptionPane.showMessageDialog(quadro, msg, "Resultado do Carregamento", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException | NumberFormatException e) {
                 JOptionPane.showMessageDialog(quadro, "Ocorreu um erro ao ler um dos arquivos:\n" + e.getMessage(), "Erro de Carga", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }


    private void salvarEstoqueCSV(File arquivo) throws IOException {
        try (PrintWriter saida = new PrintWriter(new FileWriter(arquivo))) {
            saida.println("codigo;nome;marca;valorEntrada;valorSaida;qtdEstoque");
            Bloco atual = estoque.inicio;
            while (atual != null) {
                Estoque p = (Estoque) atual.data;
                saida.printf("%d;%s;%s;%.2f;%.2f;%d\n",
                        p.getCod(),
                        p.getNome().replace(";", ","),
                        p.getMarca().replace(";", ","),
                        p.getValorEntrada(),
                        p.getValorSaida(),
                        p.getQtdEstoque());
                atual = atual.prox;
            }
        }
    }

    private void carregarEstoqueCSV(File arquivo) throws IOException, NumberFormatException {
        if (!arquivo.exists()) return;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            estoque.inicio = null; 
            estoque.fim = null;
            leitor.readLine(); 
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    int cod = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String marca = dados[2];
                    double valorEntrada = Double.parseDouble(dados[3].replace(",", "."));
                    double valorSaida = Double.parseDouble(dados[4].replace(",", "."));
                    int qtdEstoque = Integer.parseInt(dados[5]);
                    Estoque produto = new Estoque(cod, nome, marca, valorEntrada, valorSaida, qtdEstoque);
                    estoque.inserirFim(produto); 
                }
            }
        }
    }

    private void salvarVendasCSV(File arquivo) throws IOException {
        try (PrintWriter saida = new PrintWriter(new FileWriter(arquivo))) {
            saida.println("codigoProduto;nomeProduto;quantidadeVendida;valorTotal;dataDaVenda");
            Bloco atual = historicoDeVendas.inicio;
            while (atual != null) {
                Venda v = (Venda) atual.data;
                saida.println(v.toCSVString());
                atual = atual.prox;
            }
        }
    }

    private void carregarVendasCSV(File arquivo) throws IOException, NumberFormatException {
        if (!arquivo.exists()) return;

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            historicoDeVendas.inicio = null;
            historicoDeVendas.fim = null;
            leitor.readLine();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                Venda venda = Venda.fromCSVString(linha);
                if(venda != null) {
                    historicoDeVendas.inserirFim(venda);
                }
            }
        }
    }

    private void sairDoPrograma() {
        int confirmar = JOptionPane.showConfirmDialog(
            quadro, "Tem certeza que deseja sair?\nNão se esqueça de salvar os dados antes.", "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            quadro.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LojaVideogamesGUI::new);
    }
}