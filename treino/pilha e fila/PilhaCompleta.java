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
      Celula tmp = new Celula(x);
      tmp.prox= topo;
      topo = tmp;
      tmp = null;
    }
    public int remover() {
     if (topo != null ){
       int elemento = topo.elemento; // crio uma celula  para armazenar o elemento que  vai ser removido e esta no topo 
       Celula tmp = topo ; // crio uma celula temporararia  para apontar para o elemento de  topo  
       topo = topo.prox; //aponto para o novo topo 
       tmp.prox = null;// desconecto o elemento da pilha 
       tmp = null; // limpo a celula temporaria 
        return elemento;
     }
    }
    public void mostrar() {
 System.out.print("[");
  for(Celula i = topo ; i !=null ; i=i.prox){
     System.out.print(i.elemento + "");

  }
  System.out.println("]");




    }
}