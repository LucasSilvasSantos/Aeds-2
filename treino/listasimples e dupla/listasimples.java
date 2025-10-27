
class Celula {

    public int elemento;
    public Celula prox;

    public Celula(int x) {
        elemento = x;
        prox = null;

    }

    public Celula() {
        elemento = 0;
        prox = null;

    }
}

class Lista {

    private Celula primeiro, ultimo;
    private int n; // contador de elementos na lista

    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }
    
     int tamanho (){
     int contador = 0;
     for (Celula i = primeiro.prox ; i !=null ;  i= i.prox ,contador ++);
         

   return contador;
     }



    public void inserirInicio(int x) {
       Celula tmp = new Celula (x);
        tmp.prox = primeiro.prox; // aqui eu faço meu tmp.prox apontar para a proxima celula que seria depois do meu primeiro
         primeiro.prox = tmp ;
         if ( primeiro == ultimo){
               ultimo = tmp;
         }
         tmp =null;
    }

    public void inserirFim(int x) {

        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;

    }

    public int removerInicio() {
        if (primeiro == ultimo) {
            throw new IllegalArgumentException("Lista vazia");
        }
        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;
        tmp.prox = null;
        int elm = tmp.elemento;
        tmp = null;
        return elm;
    }

    public int removerFim() {
        if (primeiro == ultimo) {
           throw new IllegalArgumentException("Lista vazia");
        }
         Celula i ;
         for (i=primeiro.prox; i.prox != ultimo; i = i.prox );
         int elm = ultimo.elemento;
           ultimo = i;
           i.prox = null;

        return elm;
    }

    public void inserir(int x, int pos) throws Exception { // x numero pos posição 
     int tamanho = tamanho();
 if (pos < 0  || pos > tamanho ) throw new Exception("erro");
 else if ();


    }

    public int remover(int pos) {
    
 
 
  return 0;
    }

    public void mostrar() {
        if (primeiro == ultimo) {
            throw new IllegalArgumentException("Lista vazia");
        }
        Celula tmp = primeiro.prox;
        for (; tmp != null; tmp = tmp.prox) {
            System.out.println(tmp.elemento);
        }
    }

}

public class listasimples {
 
    public static void main(String[] args) {
        Lista lista = new Lista();
        lista.inserirFim(5);
        lista.inserirFim(7);
        lista.inserirFim(9);
        lista.inserirInicio(3);

        lista.removerFim();

        lista.mostrar();

    }
}
