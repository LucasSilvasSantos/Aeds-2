#include <stdio.h>

int main() {
    int A, B;
    scanf("%d %d", &A, &B);

    int SA[A], SB[B];

    // Lendo sequência SA
    for (int i = 0; i < A; i++) {
        scanf("%d", &SA[i]);
    }

    // Lendo sequência SB
    for (int j = 0; j < B; j++) {
        scanf("%d", &SB[j]);
    }

    // Verificar subsequência
    int i = 0, j = 0;
    while (i < A && j < B) { // O laço continua enquanto não chegamos no fim de SA (i < A) e nem no fim de SB (j < B).Se um deles acaba, não precisamos continuar.
        if (SA[i] == SB[j]) { // Aqui vemos se o elemento atual de SA é igual ao próximo elemento esperado em SB.Se for igual → quer dizer que achamos o número de SB dentro de SA → aumentamos j para procurar o próximo número da subsequência.
        
            j++; 
        }
          i++;// sempre avançamos em SA, independente de termos encontrado ou não o elemento atual de SB. 
    }

    if (j == B) {
        printf("S\n");
    } else {
        printf("N\n");
    }

    return 0;
}
