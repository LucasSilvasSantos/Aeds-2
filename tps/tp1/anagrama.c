/*Crie um método iterativo que receba duas strings como parâmetros e retorne true se as string são anagramas uma da outra, ou false 
caso contrário. Na saída padrão, para cada par de strings de entrada, escreva uma linha de saída com SIM/NÃO indicando se as string 
são anagramas. As entradas vão vir do pub.in dessa forma: amor - roma. A saída deve ser SIM.*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>  

//Verificar se a string não é FIM.
bool isFim (char str[]) {
    return (str[0] == 'F' && str[1] == 'I' && str[2] == 'M' && str[3] == '\0'); 
}

//Função para ver o tamanho das strings.
int tamanho (char str[]) {
    int i = 0; //Contador para o tamanho da string.
    while (str[i] != '\0') { //Enquanto não chegar no final da string
        i++; //Incrementa o contador.
    }
    return i; //Retorna o tamanho da string.
}

//Função para remover os espaços das strings e o hífen. 
void removeEspaco (char str[]) {
    int i = 0, j = 0; //i = percorre a string original, j = percorre a string sem espaços.
    while (str[i] != '\0') { //Enquanto não chegar no final da string
        if (str[i] != ' ' && str[i] != '-') { //Se o caractere não for espaço ou hífen
            str[j++] = str[i]; //Copia o caractere para a posição j e incrementa j.
        }
        i++; //Incrementa i.
    }
    str[j] = '\0'; //Adiciona o caractere de fim de string.
}

//Função para converter as letras para minúsculas. 
void minusculo (char str[]) { 
    int i = 0; //Contador para percorrer a string.
    while (str[i] != '\0') { //Enquanto não chegar no final da string
        if (str[i] >= 'A' && str[i] <= 'Z') { //Se o caractere for uma letra maiúscula
            str[i] += 32; //Converte para minúscula.
        }
        i++; //Incrementa i.
    }
}

//Função para ordenar as letras das strings.
void ordenar (char str[]) {
    char temp; //Variável temporária para a troca de caracteres.
    int n = tamanho(str); //Tamanho da string = 4, de acordo com meu exemplo, pois "amor" tem 4 letras.
    for (int i = 0; i < n - 1; i++) { //Percorre a string até o penúltimo caractere, pois o último já estará ordenado.
        for (int j = 0; j < n - i - 1; j++) { //Percorre a string até o último caractere não ordenado.
            if (str[j] > str[j + 1]) { //Se o caractere atual for maior que o próximo
                temp = str[j]; //Armazena o caractere atual na variável temporária.
                str[j] = str[j + 1]; //Move o próximo caractere para a posição atual.
                str[j + 1] = temp; //Move o caractere armazenado na variável temporária para a posição do próximo caractere.
            }
        }
    }
}

//Função para separar as duas palavras da string de entrada.
void separar (char str[], char str1[], char str2[]) {
    int i = 0, j = 0; //Contadores para percorrer a string de entrada e as strings de saída. i = contador para a string de entrada, j = contador para as strings de saída.
    while (str[i] != '\0' && str[i] != '-' && str[i] != ' ') { //Enquanto não chegar no final da string ou encontrar o hífen
        str1[j++] = str[i++]; //Copia o caractere para a string str1 e incrementa os contadores.
    }
    str1[j] = '\0'; //Adiciona o caractere de fim de string para str1.
    if (str[i] == '-') { //Se encontrou o hífen
        i++; //Incrementa i para pular o hífen.
    }
    j = 0; //Reinicia o contador para a string str2.
    while (str[i] != '\0') { //Enquanto não chegar no final da string
        str2[j++] = str[i++]; //Copia o caractere para a string str2 e incrementa os contadores.
    }
    str2[j] = '\0'; //Adiciona o caractere de fim de string para str2.
}   

//Função para verificar se as strings são anagramas.
bool anagrama (char str1[], char str2[]) { //Recebe as duas strings. Ex: str1 = "Roma" e str2 = " - Amor", por ja foi separadas. 
    //Remover os espaços e o hífen das strings.
    removeEspaco(str1); //str1= "Roma" mudou nada, ja que nao tem espaço ou hífen. 
    removeEspaco(str2); //str2 = "Amor" mudou, pois removeu o espaço e o hífen.

    //Verificar se as strings têm o mesmo tamanho. 
    if (tamanho(str1) != tamanho(str2)) { //Se os tamanhos forem diferentes
        return false; //Retorna false.
    }

    //Converter as letras para minúsculas.
    minusculo(str1); //str1 = "roma" mudou, pois converteu as letras para minúsculas.
    minusculo(str2); //str2 = "amor" mudou, pois converteu as letras para minúsculas.

    //Ordenar as letras das strings.
    ordenar(str1); //str1 = "amor" mudou, pois ordenou as letras em ordem alfabética.
    ordenar(str2); //str2 = "amor" não mudou, pois as letras já estavam em ordem alfabética.

    //Verificar se as strings são iguais.
    for (int i = 0; i < tamanho(str1); i++) { //Percorre a string, como elas têm o mesmo tamanho, pode usar o tamanho de qualquer uma delas.
        if (str1[i] != str2[i]) { //Se tiver algum caractere diferente. 
            return false;
        }
    }
    return true;
}

//Função principal.
int main() {
    char str[100]; //String para armazenar a entrada. Ex: "Roma - Amor"
    char str1[50], str2[50]; //Strings para armazenar as duas palavras. 

    //Ler a string de entrada.
    scanf(" %[^\n]", str); 

    //Loop para processar as entradas até encontrar "FIM".
    while (!isFim(str)) {
        //Separar as duas palavras da string de entrada.
        separar(str, str1, str2);

        //Verificar se as palavras são anagramas e imprimir o resultado.
        if (anagrama(str1, str2)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        //Ler a próxima string de entrada.
        scanf(" %[^\n]", str);
    }

    return 0;
}