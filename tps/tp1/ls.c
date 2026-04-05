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

bool somenteVogais(char str[]) {
    int i = 0;
    if (str[0] == '\0') return false;
    while (str[i] != '\0') {
        if (!ehVogal(str[i])) return false;
        i++;
    }
    return true;
}

bool somenteConsoantes(char str[]) {
    int i = 0;
    if (str[0]== '\0') return false;
    while (str[i] != '\0') {
        if (!ehConsoante(str[i])) return false;
        i++;
    }
    return true;
}

bool ehInteiro(char str[]) {
    int i = 0;
    if (str[0] == '\0') return false;
    while (str[i] != '\0') {
        if (!(str[i] >= '0' && str[i] <= '9')) return false;
        i++;
    }
    return true;
}

bool ehReal(char str[]) {
    int i = 0, separador = 0;
    if (str[0] == '\0') return false;
    while (str[i] != '\0') {
        if (str[i] == '.' || str[i] == ',') {
            separador++;
            if (separador > 1) return false;
        } else if (!(str[i] >= '0' && str[i] <= '9')) {
            return false;
        }
        i++;
    }
    return (separador == 1);
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

        int x1 = somenteVogais(entrada);
        int x2 = somenteConsoantes(entrada);
        int x3 = ehInteiro(entrada);
        int x4 = ehReal(entrada);

        printf("%s %s %s %s\n",
               x1 ? "SIM" : "NAO",
               x2 ? "SIM" : "NAO",
               x3 ? "SIM" : "NAO",
               x4 ? "SIM" : "NAO");
    }

    return 0;
}

