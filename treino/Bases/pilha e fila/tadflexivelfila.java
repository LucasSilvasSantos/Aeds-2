
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

  public int remover () {
 if(primeiro == ultimo){
     throw new RuntimeException ("Fila Vazia");
 }
Celula tmp =primeiro;
 primeiro = primeiro.prox;
  tmp.prox =null;
  tmp= null;
  int elemento = primeiro.elemento;

  return elemento;

  }
public int maior () {
 if( primeiro == ultimo){
     throw new RuntimeException ("Fila Vazia");
 }
 int maior = primeiro.prox.elemento;
 for( Celula i = primeiro.prox; i != ultimo.prox; i = i. prox){
     if(i.elemento > maior){
         maior = i.elemento;
     }
 }

 return maior; 
}

public int somar () {
   int somar = 0 ;
 if( primeiro == ultimo){
     throw new RuntimeException ("Fila Vazia");
 }
   for( Celula i = primeiro.prox; i != ultimo.prox; i = i. prox){
       somar += i.elemento;
   }

 return somar; 
}

  public void mostrar () {
 for(Celula i = primeiro.prox; i != null; i = i.prox){
        System.out.print(i.elemento + " ");
 }


  }

}



 








