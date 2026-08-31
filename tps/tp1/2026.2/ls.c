#include <stdio.h>
#include <stdbool.h>
#include <string.h>


bool ehVogal(char c) {
    return (c == 'a' || c == 'A' ||
            c == 'e' || c == 'E' ||
            c == 'i' || c == 'I' ||
            c == 'o' || c == 'O' ||
            c == 'u' || c == 'U');
}

bool ehConsoante(char c) {
    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
        return !ehVogal(c);
    }
    return false;
}

bool somenteVogais(char str[], int i) {
    if (i == 0 && str[0] == '\0') return false;
    if (str[i] == '\0') return true;
    if (!ehVogal(str[i])) return false;
    return somenteVogais(str, i + 1);
}

bool somenteConsoantes(char str[], int i) {
    if (i == 0 && str[0] == '\0') return false;
    if (str[i] == '\0') return true;
    if (!ehConsoante(str[i])) return false;
    return somenteConsoantes(str, i + 1);
}

bool ehInteiro(char str[], int i) {
    if (i == 0 && str[0] == '\0') return false;
    if (str[i] == '\0') return true;
    if (!(str[i] >= '0' && str[i] <= '9')) return false;
    return ehInteiro(str, i + 1);
}

bool ehReal(char str[], int i, int separador) {
    if (i == 0 && str[0] == '\0') return false;
    if (str[i] == '\0') return true;
    if (str[i] == '.' || str[i] == ',') {
        separador++;
        if (separador > 1) return false;
    } else if (!(str[i] >= '0' && str[i] <= '9')) {
        return false;
    }
    return ehReal(str, i + 1, separador);
}

// === Programa principal ===
int main() {
    char entrada[500];

    while (1) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\n")] = '\0'; // remove \n

        if (strcmp(entrada, "FIM") == 0) {
            break;
        }

        int x1 = somenteVogais(entrada, 0);
        int x2 = somenteConsoantes(entrada, 0);
        int x3 = ehInteiro(entrada, 0);
        int x4 = ehReal(entrada, 0, 0);

        printf("%s %s %s %s\n",
               x1 ? "SIM" : "NAO",
               x2 ? "SIM" : "NAO",
               x3 ? "SIM" : "NAO",
               x4 ? "SIM" : "NAO");
    }

    return 0;
}

