import java.util.scanner;

class No {
int elemento;
No esq;
No dir;
No( int elemento ){

this(elemento, null, null ); // faço o construtor da classe no 


}

No(int elemento, No esq, No dir){

this.elemento = elemento;
this.esq = esq;
this.dir = dir;
}


}


class ArvoreBinaria {
No raiz;
ArvoreBinaria(){raiz = null; } // construtor da classe arvore, no raiz começa apontando para null


void inseririncio ( int x ) {
if (raiz == null) new No (x);

}
void inserir ( int x, No pos) throws Exception {
if(pos == null) {
pos = new No (x); // se o no atual for null, eu crio um outro no passando o elemento 
}

else if(x < pos.elemento) inserir(x,esq);
else if(x > pos.elemento) inserir(x,dir);
else throw new Exception ("Erro");

return pos; 


}


boolean pesquisar(int x) {
return pesquisar(x,raiz);

}

boolean pesquisar ( int x, No pos){
  boolean resp;

  if(pos == null) {resp false;System.out.println ("N")};
  else if( x == pos.elemento){ resp true;System.out.println ("S")};
  else if (x < pos.elemento) resp = pesquisar(x, pos.esq);
  else {
    resp = pesquisar(x, pos.dir);
  } 
 return resp;


}

void caminharcentral () {

caminharcentral(raiz); // passa raiz que é onde o no se inicia 

}


void caminharcentral (No i){
if( i == null) System.out.prinln("V");
if( i != null ) {
caminharcentral(i.esq);
System.out.println(i.elemento + "");
caminharcentral(i.dir);

}

}

void caminharpreordem () {

caminharpreordem(raiz); // passa raiz que é onde o no se inicia 

}


void caminharpreordem (No i){
if( i == null) System.out.prinln("V");
if( i != null ) {
System.out.println(i.elemento + "");  
caminharpreordem(i.esq);
caminharpreordem(i.dir);

}
}

void caminharposordem () {

caminharposordem(raiz); // passa raiz que é onde o no se inicia 

}


void caminharposordem (No i){
if( i == null) System.out.prinln("V");
if( i != null ) {  
caminharposordem(i.esq);
caminharposordem(i.dir);
System.out.println(i.elemento + "");  
}
}


}

public static void main ( String [] args){
Scanner sc = new Scanner (System.in);
String options= sc.next();
int x =sc.nextint ();


if (charAt.options(0) == "P" || charAt.options(0) == "p"){
ArvoreBinaria.pesquisar(x);
}

else if (charAt.options(0) == "E" && charAt.options(1)== "M" || charAt.options(0) == "e" && charAt.options(1)== "m" ){
ArvoreBinaria.caminharcentral();
}

else if (charAt.options(0) == "P" && charAt.options(1)== "R" && charAt.options(2) == "E" ){
ArvoreBinaria.caminharpreordem();
}


else if (charAt.options(0) == "P" && charAt.options(1)== "O" && charAt.options(2) == "S" ){
ArvoreBinaria.caminharposordem();
}

else if (charAt.options(0) == "I" || charAt.options(0) == "i"){
ArvoreBinaria.inserir(x);
}


}