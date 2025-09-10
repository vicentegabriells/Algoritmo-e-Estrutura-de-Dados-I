package projetoED1;

import java.util.ArrayList;

class Bloco {
    public Object data; 
    public Bloco prox;  
    
    public Bloco(Object data) {
        this.data = data;
        this.prox = null; 
    }
}

public class Lista {
    public Bloco inicio = null; 
    public Bloco fim = null;    

    
    public boolean estaVazia() {
        return inicio == null;
    }
    
    public void inserirFim(Object data) {
        Bloco novo = new Bloco(data);
        if (estaVazia()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.prox = novo; 
            fim = novo;      
        }
    }
    
    
    public boolean inserirProdutoOrdenado(Estoque data) {
        if (buscarProduto(data.getCod()) != null) return false;

        Bloco novo = new Bloco(data);
        if (estaVazia() || ((Estoque) inicio.data).getCod() >= data.getCod()) {
            novo.prox = inicio;
            inicio = novo;
            if (fim == null) fim = novo; 
            return true;
        }

        Bloco atual = inicio;
        while (atual.prox != null && ((Estoque) atual.prox.data).getCod() < data.getCod()) {
            atual = atual.prox;
        }
        novo.prox = atual.prox;
        atual.prox = novo;

        if (novo.prox == null) fim = novo;
        return true;
    }

    public Estoque buscarProduto(int codigo) {
        Bloco atual = inicio;
        while (atual != null) {
            Estoque produto = (Estoque) atual.data;
            if (produto.getCod() == codigo) {
                return produto; 
            }
            atual = atual.prox; 
        }
        return null; 
    }

    public int tamanho() {
        int tamanho = 0;
        Bloco atual = inicio;
        while (atual != null) {
            tamanho++;
            atual = atual.prox;
        }
        return tamanho;
    }
 
    public ArrayList<Estoque> toArrayList() {
        ArrayList<Estoque> arrayList = new ArrayList<>();
        Bloco atual = inicio;
        while (atual != null) {
            arrayList.add((Estoque) atual.data);
            atual = atual.prox;
        }
        return arrayList;
    }
}