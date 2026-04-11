#include <stdio.h>
#include <stdlib.h>

char ciframento(char c, int chave) {
    if (c >= 'a' && c <= 'z') {
        return (c - 'a' + chave) % 26 + 'a';
    } else if (c >= 'A' && c <= 'Z') {
        return (c - 'A' + chave) % 26 + 'A';
    } else {
        return c; // Retorna o caractere original se não for letra
    }
}


int main (){
   chartexto [100];
  






}
