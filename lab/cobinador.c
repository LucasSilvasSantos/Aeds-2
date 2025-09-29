#include <stdio.h>


void combinador ( char frase1[], char frase2[], char resultado[]){
 int i =0;
 int j =0;
 int k =0;

 while (frase1[i] != '\0' || frase2[j]!='\0' ) {
 if (frase1[i]!= '\0'){
     resultado[k++] = frase1[i++];
     
 }

 if (frase2[j]!= '\0') {
      resultado[k++]=frase2[j++];

 }


 }
    resultado[k]='\0';
}


int main (){
   char frase1[100];
   char frase2[100];
   char resultado[200];
 fgets(frase1,sizeof(frase1),stdin);
 fgets(frase2,sizeof(frase2),stdin);

 combinador(frase1,frase2,resultado);
 printf("%s\n",resultado);
 return 0;

}


