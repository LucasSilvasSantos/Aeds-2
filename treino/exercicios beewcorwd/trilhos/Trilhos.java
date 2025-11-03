import java.util.Scanner;

 class Celula {
    
    public char elemento;
    public Celula prox;

    public Celula() {
        this(' ');
    }

    public Celula(char x) {
        this.elemento = x;
        this.prox = null;
    }
}

class Pilha {

    private Celula topo;

    public Pilha() {
        topo = null;
    }
    public void inserirTopo (char x ){
    Celula tmp = new Celula (x);
       tmp.prox= topo;
         topo = tmp ;
         tmp = null;

    }

 char remover (){
    Celula tmp =  topo ;
      char elm = topo.elemento;
        topo= topo.prox;
         tmp.prox = null;
         tmp = null;
  return elm;
 }

 public char peek(){
     if(topo == null) return ' ';
     return topo.elemento;
 }

 public boolean isEmpty(){
     return topo == null;
 }

}

class Trilhos {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       while(true) {
           String line = sc.nextLine();
           int N = Integer.parseInt(line.trim());
           if(N == 0) break;
           String entradaStr = sc.nextLine();
           String saidaStr = sc.nextLine();
           String[] entrada = entradaStr.split(",");
           String[] saida = saidaStr.split(",");
           Pilha pilha = new Pilha();
           StringBuilder operacoes = new StringBuilder();
           int idxEntrada = 0;
           int idxSaida = 0;
           boolean possivel = true;
           while(idxSaida < N && possivel){
               // enquanto o topo não seja o desejado, push
               while((pilha.isEmpty() || pilha.peek() != saida[idxSaida].charAt(0)) && possivel){
                   if(idxEntrada >= N){
                       possivel = false;
                       break;
                   }
                   pilha.inserirTopo(entrada[idxEntrada].charAt(0));
                   operacoes.append("I");
                   idxEntrada++;
               }
               if(possivel){
                   // pop
                   pilha.remover();
                   operacoes.append("R");
                   idxSaida++;
               }
           }
           if(possivel){
               System.out.println(operacoes.toString());
           } else {
               System.out.println("Impossible");
           }
       }
   }






}




