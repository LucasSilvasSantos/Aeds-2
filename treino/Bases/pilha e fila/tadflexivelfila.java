
class Celula {

    public int elemento;
    public Celula prox;

    public Celula() {
        this(0); // chamando o construtor passando 0

    }

    public Celula(int x) {
        this.elemento = x;
        this.prox = null;

    } 

}


class Fila {

 private Celula primeiro;
 private Celula ultimo;

  public void inserir (int x) {
     ultimo.prox = new Celula (x);
      ultimo = ultimo.prox;

  }

  public int remover () TR{






  }


}



 








