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
 else raiz = remover(x, raiz); // caso contrário, chama o método auxiliar para remover o elemento da árvore

}

No remover ( int x , No i ) throws Exception {
 if (i == null) throw new Exception("Erro!"); // se o nó atual for null, lança uma exceção
 else if (x < i.elemento) i.esq = remover(x, i.esq); // se o elemento a ser removido for menor que o elemento do nó atual, chama recursivamente para o filho esquerdo
 else if (x > i.elemento) i.dir = remover(x, i.dir); // se o elemento a ser removido for maior que o elemento do nó atual, chama recursivamente para o filho direito
 else if (i.dir == null) i = i.esq; // se o nó a ser removido não tiver filho direito, substitui o nó pelo seu filho esquerdo
 else if (i.esq == null) i = i.dir; // se o nó a ser removido não tiver filho esquerdo, substitui o nó pelo seu filho direito
 else i.esq = maiorEsq(i, i.esq); // caso contrário, substitui o nó pelo maior elemento da subárvore esquerda
 return i; // retorna o nó atualizado
}


/* CONSULTA */

 boolean pesquisar ( int x ){
 return pesquisar(x, raiz); // chama o método auxiliar para pesquisar o elemento na árvore, começando pela raiz 
 }

boolean pesquisar ( int x , No i ) { 
boolean resp; 
 if (i == null) resp= false; // se o nó atual for null, o elemento não foi encontrado, retorna false
 else if (x == i.elemento) resp= true; // se o elemento do nó atual for igual ao elemento a ser pesquisado, retorna true
 else if (x < i.elemento) resp= pesquisar(x, i.esq); // se o elemento a ser pesquisado for menor que o elemento do nó atual, chama recursivamente para o filho esquerdo
 else resp= pesquisar(x, i.dir); // se o elemento a ser pesquisado for maior que o elemento do nó atual, chama recursivamente para o filho direito
 return resp;
}
  /* Altura da árvore */

int altura ( ){
return altura (raiz); // chama o método auxiliar para calcular a altura da árvore, começando pela raiz
}




}