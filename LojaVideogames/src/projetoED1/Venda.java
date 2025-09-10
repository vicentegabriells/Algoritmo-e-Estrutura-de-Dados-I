package projetoED1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Venda {
    private final int codigoProduto;
    private final String nomeProduto;
    private final int quantidadeVendida;
    private final double valorTotal;
    private final LocalDateTime dataDaVenda;

    public Venda(int codigoProduto, String nomeProduto, int quantidadeVendida, double valorTotal) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
        this.dataDaVenda = LocalDateTime.now();
    }
    
    private Venda(int codigoProduto, String nomeProduto, int quantidadeVendida, double valorTotal, LocalDateTime dataDaVenda) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorTotal = valorTotal;
        this.dataDaVenda = dataDaVenda;
    }

    public int getCodigoProduto() { return codigoProduto; }
    public double getValorTotal() { return valorTotal; }
    public int getQuantidadeVendida() { return quantidadeVendida; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Jogo: " + nomeProduto + " (Cód: " + codigoProduto + ")\n" +
               "  - Qtd Vendida: " + quantidadeVendida + "\n" +
               "  - Valor Total: R$" + String.format("%.2f", valorTotal) + "\n" +
               "  - Data: " + dataDaVenda.format(formatador);
    }
    

    public String toCSVString() {
        return String.format("%d;%s;%d;%.2f;%s",
            this.codigoProduto,
            this.nomeProduto.replace(";", ","),
            this.quantidadeVendida,
            this.valorTotal,
            this.dataDaVenda.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }


    public static Venda fromCSVString(String csvLine) {
        String[] dados = csvLine.split(";");
        if (dados.length == 5) {
            try {
                int codigoProduto = Integer.parseInt(dados[0]);
                String nomeProduto = dados[1];
                int quantidadeVendida = Integer.parseInt(dados[2]);
                double valorTotal = Double.parseDouble(dados[3].replace(",", "."));
                LocalDateTime dataDaVenda = LocalDateTime.parse(dados[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                
                return new Venda(codigoProduto, nomeProduto, quantidadeVendida, valorTotal, dataDaVenda);
            } catch (NumberFormatException | DateTimeParseException e) {
                System.err.println("Erro ao parsear a linha da venda no CSV: " + csvLine);
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}