#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main() {
	int R = 0;
	scanf("%d", &R); // ler rodadas
	
	while (R != 0) {
		int Mi[R]; // Mark
		int Li[R]; // Leti
		int somaMi = 0; 
		int somaLi = 0; 
		bool liberouMi = false; 
		bool liberouLi = false;

		// ler monstros sorteados Mark
		for (int i=0; i < R; i++) {
			scanf("%d", &Mi[i]);
		}

		// ler números de monstros sorteados Leti
		for (int j=0; j < R; j++) {
			scanf("%d", &Li[j]); 
		}

		// loop de verificação campeão
		for (int k=0; k < R; k++) {
			somaMi += Mi[k];
			somaLi += Li[k];

			// verificação se três monstros iguais seguidos foram sorteadas para Mark
			if (!liberouMi && k >= 2) {
				if (Mi[k-2] == Mi[k-1] && Mi[k-1] == Mi[k]) {
					liberouMi = true;
					somaMi += 30;
				}
			}

			// verificação se três monstros iguais seguidos foram sorteadas para Leti

			if (liberouLi && k >= 2) {
				if (Li[k-2] == Li[k-1] && Li[k-1] == Li[k]) {
					liberouLi = true;
					somaLi += 30;
				}
			}

			// se Mark e Leti sortearam três monstros iguais seguidos, os pontos serão anulados
			if (liberouMi && liberouLi) {
				somaMi -= 30;
				somaLi -= 30;
			}
		}

		// verificar Mark Campeão
		if (somaMi > somaLi) {
			printf("M\n");
		}
		// verificar Leti Campeã
		else if (somaLi > somaMi) {
			printf("L\n");
		}
		// empate
		else {
			printf("T\n");
		}

		scanf("%d", &R); // ler mais uma rodada
	}
}
	