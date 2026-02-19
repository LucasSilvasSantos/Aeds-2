// contar os numero de letras A que tem uma palavra

#include <stdio.h>
#include <stdlib.h>



int main (){
 char *word = (char*) malloc(100 * sizeof(char));
    fgets(word, 100, stdin);
    int count =0 ;
 for( int i =0; word[i] != '\0'; i++){ // pecorre a palavra ate o final dela verificando se tinha acabado o espeaçamento ou seja o "\0"

 if(word[i] == 'a'|| word[i] == 'A') {
     count++;
 }

 }
      printf("%d\n", count);

 return 0;
 
}