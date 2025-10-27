import java.io.*;

class Celula{
public int elemento;
public Celula prox;
public Celula ( int x ){
 elemento = x;
  prox = null;
  
}
public Celula ( ){
 elemento = 0 ;
  prox = null;
  
}
}
class Lista {
    private Celula primeiro,ultimo;
    private int n; // contador de elementos na lista
public Lista (){
 primeiro = new Celula();
  ultimo = primeiro ;
}
 public void inserirFim ( int x ){

    ultimo.prox =  new Celula (x);
    ultimo = ultimo.prox;
    

 }
 public int removerInicio() {
  if  ( primeiro == ultimo  ) {
  throw new IllegalArgumentException("Lista vazia");
   }
  Celula tmp = primeiro.prox ; 
    primeiro.prox= tmp.prox;
     tmp.prox = null;
     int elm=tmp.elemento;
    tmp=null;
     return elm;
 }
public int removerFim(){






}
public void inserir (int x, int pos){









}
public int remover ( int pos ){







}


public void mostrar (){
    if (primeiro==ultimo)  throw new IllegalArgumentException("Lista vazia");
         Celula tmp= primeiro.prox;
          for( ; tmp !=null ; tmp = tmp.prox){
         System.out.println(tmp.elemento);
          }    
}


}
  public class Dinamica {
         public static void main (String [] args){
              Lista lista = new Lista ();
                lista.inserirFim(5);
                lista.inserirFim(7);
                lista.inserirFim(9);
                lista.inserirFim(11);

                lista.removerInicio();

                lista.mostrar();





         }
 }





