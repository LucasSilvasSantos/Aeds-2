#include <stdio.h>
#include <string.h>
#include <stdbool.h>

int palindromo(char str[]){
    int tam = strlen(str);

    for(int i = 0; i < tam/2; i++){
        if(str[i] != str[tam - i - 1])
            return 0;
    }

    return 1;
}

int main(){
    char word[1000];

    while(true){
        fgets(word, sizeof(word), stdin);

        // Remove o \n
        word[strcspn(word, "\n")] = '\0';

        if(strcmp(word, "FIM") == 0){
            return 0;
        }

        int result = palindromo(word);

        if(result)
            printf("SIM\n");
        else
            printf("NAO\n");
    }
}