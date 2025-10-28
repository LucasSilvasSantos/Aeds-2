import java.util.Scanner;
import java.util.Stack;

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

   public void inserirTopo  ( int x) {
     Celula tmp = new Celula (x);
     topo.prox = tmp;
      topo= tmp ;
      tmp = null;
   }
   
    public int somar (){
   
   int total =0;
    Celula i;
   for( i = topo; i != null; i=i.prox ) {
   total =i.elemento ;

   }

 return total;
    }
   
   
   
    public void inserir(int x) {
        Celula tmp = new Celula(x); // crio uma celula temporaria para armazenar um elemento que vai ser inserido 
        tmp.prox = topo;   // faço a proxima celula temporaria apontar para topo 
        topo = tmp; // faço com que a celula topo aponte para a celula temporaria 
        tmp = null; // removo a celula temporaria 
    }

    public int remover() {
        if (topo != null) {
            int elemento = topo.elemento; // crio uma celula  para armazenar o elemento que  vai ser removido e esta no topo 
            Celula tmp = topo; // crio uma celula temporararia  para apontar para o elemento de  topo  
            topo = topo.prox; //aponto para o novo topo 
            tmp.prox = null;// desconecto o elemento da pilha 
            tmp = null; // limpo a celula temporaria 
            return elemento;
        }
        return -1;
    }

     public int soma (){
        int soma =0;
        for (Celula i =topo; i != null; i =i.prox){
            soma+=i.elemento;
                
        }
  return soma;

    }

    public void mostrar() {
        System.out.print("[");
        for (Celula i = topo; i != null; i = i.prox) {
            System.out.print(i.elemento + "");

        }
        System.out.println("]");

    }
}

 
public  static void ordem (Stack<integer> pilha ) { 
     if( pilha.topo == -1 ){
  return;
     }

 int elemento = pilha.pop();
  ordem(pilha);
  System.out.print(elemento + " ");
  pilha.push(elemento);

}







public class PilhaCompleta {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Pilha pilha = new Pilha();
        System.out.println(" digite o numero que vc quer inserir na pilha :      ( para sair digite -1)");
        int numero = sc.nextInt();
        while (numero != -1) {
            pilha.inserir(numero);
            numero = sc.nextInt();
        }
        System.out.println(" elementos da pilha : ");
        pilha.mostrar();
        System.out.println(" soma dos elementos da pilha : " + pilha.soma());
    }
}
