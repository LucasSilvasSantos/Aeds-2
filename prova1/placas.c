#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX 100

int validacaoPlaca(char placa[]) {
	int n = 0;
	if (strlen(placa) == 7) {
		if (placa[0] >= 65 && placa[0] <= 90 && placa[1] >= 65 && placa[1] <= 90
		    && placa[2] >= 65 && placa[2] <= 90 && placa[3] >= '0' && placa[3] <= '9' 
		    && placa[4] >= 65 && placa[4] <= 90 && placa[5] >= '0' && placa[5] <= '9' &&
		    placa[6] >= '0' && placa[6] <= '9') 
		{	
			n = 2;
		}
	}
	else if (strlen(placa) == 8) {
		if (placa[0] >= 65 && placa[0] <= 90 && placa[1] >= 65 && placa[1] <= 90
                     && placa[2] >= 65 && placa[2] <= 90 && placa[3] == '-' && placa[4] >= '0' && placa[4] <= '9' && placa[5] >= '0' && placa[5] <= '9' &&
                     placa[6] >= '0' && placa[6] <= '9' && placa[7] >= '0' && placa[7] <= '9')
		{
			n = 1;
		}

	}

	return n;
}

int main() {
	char placa[MAX];
	
	while (scanf("%s", placa) == 1) {
		printf("%d\n", validacaoPlaca(placa));
	}

	return 0;
}