
class Celula {

    public int elemento;
    public Celula sup,inf,dir,esq;

    public Celula(int x) {
        elemento =0;
        sup = null ;
        inf = null; 
        dir = null;
        esq =null;
    }

    public Celula() {
     elemento =0;
       

    }
}

class Matriz {
  private Celula inicio ;
  private int linha,coluna;
      public Matriz (int l ,int c ){
        if( linha <=0  || coluna <= 0) throw new IllegalArgumentException("Dimensões da matriz devem ser positivas");
         this.linha=l;
         this.coluna=c;

      }
   







}
