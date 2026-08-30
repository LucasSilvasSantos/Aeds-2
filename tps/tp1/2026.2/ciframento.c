#include <stdio.h>
#include <string.h>

// cifra um caractere com a chave (cesar) — aplica em todos os caracteres
char cifraChar(char c, int chave) {
    return c + chave;
}

// percorre a string recursivamente, cifrando caractere por caractere
void cifraRecursivo(char *s, int i, int chave) {
    if (s[i] == '\0') return;
    s[i] = cifraChar(s[i], chave);
    cifraRecursivo(s, i + 1, chave);
}

int main() {
    char texto[1000];

    while (fgets(texto, sizeof(texto), stdin)) {
        int len = strlen(texto);

        // remove o '\n' do final, se houver
        if (len > 0 && texto[len - 1] == '\n') {
            texto[--len] = '\0';
        }

        // verifica se é "FIM" para parar
        if (texto[0] == 'F' && texto[1] == 'I' && texto[2] == 'M' && len == 3) {
            break;
        }

        cifraRecursivo(texto, 0, 3);

        printf("%s\n", texto);
    }

    return 0;
}
