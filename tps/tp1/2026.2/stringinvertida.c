#include <stdio.h>
#include <string.h>

int main() {
    char s[1000];

    while (fgets(s, sizeof(s), stdin)) {
        int len = strlen(s);

        // remove o '\n' do final, se houver
        if (len > 0 && s[len - 1] == '\n') {
            s[--len] = '\0';
        }

        // verifica se é "FIM" para parar
        if (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && len == 3) {
            break;
        }

        // inverte manualmente
        char invertida[1000];
        for (int i = 0; i < len; i++) {
            invertida[i] = s[len - 1 - i];
        }
        invertida[len] = '\0';

        printf("%s\n", invertida);
    }

    return 0;
}
