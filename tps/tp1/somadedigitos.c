#include <stdio.h>
#include <string.h>
#include <stdlib.h> // para atoi()
int stringtoint ( char *str ){
   int n =0 ;
   for( int i =0 ; str[i] != '\0'; i++){
         n = n*10 + (str[i] - '0'); // converte caractere para inteiro usando a tabela ASCII
   }
   return n;
}
int main() {
    char entrada[100];  
    int n, soma;

    while (scanf("%s", entrada) == 1) { // Lê a entrada até EOF
           if (strcmp(entrada, "FIM") == 0) {
            break;  
        }
        // Converte string para inteiro
        n = stringtoint(entrada);
        soma = 0;
        while (n > 0) {
            soma += n % 10;
            n /= 10;
        }

        printf("%d\n", soma);
    }

    return 0;
}
