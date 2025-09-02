
import java.util.Scanner;

public class quadromedalhas {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		sc.nextLine();
		
		String paises[] = new String[n];
		int ouro[] = new int[n*3];
		int prata[] = new int[n*3];
		int bronze[] = new int[n*3];

		for (int i=0; i < n; i++) {
			paises[i] = sc.next();
			ouro[i] = sc.nextInt();
			prata[i] = sc.nextInt();
			bronze[i] = sc.nextInt();
			sc.nextLine();
		}

		for (int i=0; i < n; i++) {
			int pos = i;
			int ourotemp = 0,
			    pratatemp = 0,
			    bronzetemp = 0;
			int maior = ouro[i];
			String paistemp = "";
			boolean trocou = false;
			

			for (int j=i+1; j < n; j++) {
				int temp = 0;
				temp = ouro[j];
				if (temp > maior) {
					maior = temp;
					pos = j;
					trocou = true;
				} 
				else if (temp == maior) {
					if (prata[j] > prata[pos]) {
						pos = j;
						trocou = true;
					}
					else if (prata[j] == prata[pos]) {
						if (bronze[j] > bronze[pos]) {
							pos = j;
							trocou = true;
						}
					}
				}
				
			}

			if (trocou) {
				paistemp = paises[i];
				paises[i] = paises[pos];
				paises[pos] = paistemp;

				ourotemp = ouro[i];
				ouro[i] = ouro[pos];
				ouro[pos] = ourotemp;

				pratatemp = prata[i];
				prata[i] = prata[pos];
				prata[pos] = pratatemp;

				bronzetemp = bronze[i];
				bronze[i] = bronze[pos];
				bronze[pos] = bronzetemp;
			}

		}

		for (int i=0; i < n; i++) {
			System.out.printf("%s %d %d %d%n", paises[i], ouro[i], prata[i], bronze[i]);
		}

		sc.close();
	}
}