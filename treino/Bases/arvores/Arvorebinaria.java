import java.util.Scanner;


class No {
 int elemento;
  No esq;
  No dir;
  No( int elemento) {
   this(elemento, null, null); // construtor da classe No, recebe um elemento e inicializa os ponteiros para null 
  }
  No ( int elemento, No esq, No dir) {
   this.elemento = elemento; // inicializa o elemento do nó
   this.esq = esq; // inicializa o ponteiro para o filho esquerdo
   this.dir = dir; // inicializa o ponteiro para o filho direito
  }



}

class ArvoreBinaria{ 
 No raiz; 
 ArvoreBinaria(){ raiz = null; } // construtor da classe ArvoreBinaria, inicializa a raiz como null



/* INSERÇÃO */

void inserir ( int x ) throws Exception {
 if (raiz == null) raiz = new No(x); // se a raiz for null, cria um novo nó com o elemento x e atribui à raiz
 else inserir(x, raiz); // caso contrário, chama o método auxiliar para inserir o elemento na árvore

}

void inserir ( int x , No i ) throws Exception {
if (i == null) {
i = new No(x); // se o no atual for null, eu crio o no passando o elemento x

}

else if (x < i.elemento) inserir(x, i.esq); // se o elemento a ser inserido for menor que o elemento do nó atual, chama recursivamente para o filho esquerdo
else if (x > i.elemento) inserir(x, i.dir); // se o elemento a ser inserido for maior que o elemento do nó atual, chama recursivamente para o filho direito
else throw new Exception("Erro!"); // se o elemento já existe na árvore, lança uma exceção
 return i ; // retorno o nó atual passando null 
}

/* REMOÇÃO */


void remover ( int x ) throws Exception {
 if (raiz == null) throw new Exception("Erro!"); // se a raiz for null, lança uma exceção
 else remover(x, raiz); // caso contrário, chama o método auxiliar para remover o elemento da árvore

}


