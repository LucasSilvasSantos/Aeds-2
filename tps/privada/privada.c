#include <stdio.h>
#include <stdlib.h>

int main(){
    FILE *file = fopen("pri.out", "rb");

    fseek(file, 0, SEEK_END);
    long tamanho = ftell(file);
    fseek(file, 0, SEEK_SET);

    char *buffer = (char *)malloc(tamanho + 1);

        fread(buffer, 1, tamanho, file);
        buffer[tamanho] = '\0';  // Adiciona o terminador nulo
        // Aqui você pode processar o conteúdo do arquivo conforme necessário
        printf("%s", buffer);
        free(buffer);

    fclose(file);
    return 0;
}