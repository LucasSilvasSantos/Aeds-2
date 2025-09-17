#include <stdio.h>
int main()
{
    char linha[1000];

    while (scanf(" %[^\n]", linha) != EOF)
    {
        int contador = 0;
        int i = 0;

        while (linha[i] != '\0')
        {

            if (linha[i] >= 'A' && linha[i] <= 'Z')
            {
                contador++;
            }
            i++; // andando o array
        }
        printf("%d\n", contador);
    }
    return 0;
}