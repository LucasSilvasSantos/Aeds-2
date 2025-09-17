#include <stdio.h>
#include <stdbool.h>
int main (){
 char linha [1000];
  
 while(1){
  
   fgets(linha,sizeof(linha),stdin);
    if ( linha[0] == 'F' && linha[1] == 'I' && linha [2] == 'M'){
break;  
    }
    int contador =0;
    int i =0;
 while(linha[i]!= '\0' && linha[i]!= '\n'){
    if ( linha[i] >= 'A'&& linha[i] <= 'Z'){   
     contador++;
 }
 i++; // andando o array 
 }
 printf("%d\n",contador);
} 
 return 0 ;
}