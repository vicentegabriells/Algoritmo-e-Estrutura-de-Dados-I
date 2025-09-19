package projetoED1;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EstoqueTableModel extends AbstractTableModel {

    private final String[] colunas = {"Código", "Nome do Jogo", "Marca", "Preço (R$)", "Qtd. Estoque"};

    private List<Estoque> listaDeProdutos;

    public EstoqueTableModel(List<Estoque> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
    }

    public void setListaDeProdutos(List<Estoque> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
        fireTableDataChanged(); 
    }

    @Override
    public int getRowCount() {
        return listaDeProdutos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
 
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Estoque produto = listaDeProdutos.get(rowIndex);
   
        switch (columnIndex) {
            case 0:
                return String.format("%05d" , produto.getCod());
            case 1:
                return produto.getNome();
            case 2:
                return produto.getMarca();
            case 3:
                return String.format("%.2f" , produto.getValorSaida()); 
            case 4:
                return produto.getQtdEstoque();
            default:
                return null;
        }
    }
}
