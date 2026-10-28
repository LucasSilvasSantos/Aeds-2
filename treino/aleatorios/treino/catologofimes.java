import java.util.Scanner;


class No {
int ano;
String titulo;
No esq;
No dir;

 No (int ano, String titulo, No esq, No dir){

this.esq = esq;
this.dir = dir;
this.ano = ano; 
this.titulo = titulo;

}

}

class ArvoreBinaria (){

No raiz;

ArvoreBinaria() {

raiz = null; 

}





public void inserir (int ano, String titulo){
raiz = inserido(ano, titulo, raiz);
    
}
public No inserido (int ano, String titulo, No i){
if (i == null){
 return new No(ano, titulo, null, null);
}
else {

int comparacao = titulo.compareTo(i.titulo);

}

if(comparacao < 0){

i.esq = inserido(ano,titulo,i.esq);

}
else if (comparacao > 0){

i.dir = inserido(ano,titulo,i.dir);

}
 return i;
}

public void caminharpos (No i){
if(i != null){
i.esq = caminharpos(i.esq);
i.dir = caminharpos (i.dir);
System..out.println (i.ano + " "+ i.titulo);

}

}


}

public class catologofilmes {
public static void main (String [] args ){

Scanner sc = new Scanner(System.in);
ArvoreBinaria arvore = new ArvoreBinaria();


while (sc.hasnext()){
int ano =sc.nextInt();
String titulo =sc.nextline();
arvore.inserido(ano,titulo);

}
arvore.caminharpos();
sc.close();
}





} 