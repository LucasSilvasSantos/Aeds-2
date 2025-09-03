#include <stdio.h>
#include <string.h>
#include <stdlib.h> // para atoi()

int main() {
    char entrada[100];  
    int n, soma;

    while (1) {
        scanf("%s", entrada);

        if (strcmp(entrada, "FIM") == 0) {
            break;  
        }

        // Converte string para inteiro
        n = atoi(entrada); 
        soma = 0;

        while (n > 0) {
            soma += n % 10;
            n /= 10;
        }

        printf("%d\n", soma);
    }

    return 0;
}
