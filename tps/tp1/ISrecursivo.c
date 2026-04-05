#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool ehVogal ( char c){
 return (c== 'a' || c == 'A' ||
         c== 'e' || c == 'E' ||
         c== 'i' || c == 'I' ||
         c== 'o' || c == 'O' ||
)
}

bool ehConsoante ( char c){
 if ((  c >= 'a' && c <='z')  || ( c >= 'A' && c <= 'Z')){
 return !ehVogal(c);
 }
}

bool somenteVogais(char str[]){




}