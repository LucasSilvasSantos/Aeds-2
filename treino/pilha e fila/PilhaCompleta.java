class Celula {
    public int elemento;
    public Celula prox;
    
    public Celula() {
        this(0);
    }
    
    public Celula(int x) {
        this.elemento = x;
        this.prox = null;
    }
}

class Pilha {
    private Celula topo;
    
    public Pilha() {
        topo = null;
    }

    public void inserir(int x) {
    }
    public int remover() throws Exception {}
    public void mostrar() {}
}