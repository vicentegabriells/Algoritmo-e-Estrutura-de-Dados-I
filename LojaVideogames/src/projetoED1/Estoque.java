package projetoED1;

public class Estoque {
    int cod;
    String nome;
    String marca;
    double valorEntrada;
    double valorSaida;
    int qtdEstoque;

    public Estoque(int cod, String nome, String marca, double valorEntrada, double valorSaida, int qtdEstoque) {
        this.cod = cod;
        this.nome = nome;
        this.marca = marca;
        this.valorEntrada = valorEntrada;
        this.valorSaida = valorSaida;
        this.qtdEstoque = qtdEstoque;
    }

    public int getCod() { return cod; }
    public String getNome() { return nome; }
    public String getMarca() { return marca; }
    public double getValorEntrada() { return valorEntrada; }
    public double getValorSaida() { return valorSaida; }
    public int getQtdEstoque() { return qtdEstoque; }

    public void setCod(int cod) { this.cod = cod; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setValorEntrada(double valorEntrada) { this.valorEntrada = valorEntrada; }
    public void setValorSaida(double valorSaida) { this.valorSaida = valorSaida; }
    public void setQtdEstoque(int qtdEstoque) { this.qtdEstoque = qtdEstoque; }

    @Override
    public String toString() {
        return "Cód: " + cod + " | Jogo: " + nome + " | Marca: " + marca + " | Preço: R$" + valorSaida + " | Qtd: " + qtdEstoque;
    }
}