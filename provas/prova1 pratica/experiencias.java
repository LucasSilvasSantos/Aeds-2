import java.util.Scanner;

public class experiencias {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		int numCobaia[] = new int[N];
		String nomeCobaia = "";
		int total = 0;
		int coelhos = 0;
		int ratos = 0;
		int sapos = 0;

		for (int i=0; i < N; i++) {
			numCobaia[i] = sc.nextInt(); 
			nomeCobaia += sc.next(); // concatenar os chars
			total += numCobaia[i]; // armazenar o total de cobaias

			// verificação e soma de coelhos
			if (nomeCobaia.charAt(i) == 'C') {
				coelhos += numCobaia[i];
			}
			// verificação e soma de ratos
			else if (nomeCobaia.charAt(i) == 'R') {
				ratos += numCobaia[i];
			}
			// verificação e soma de sapos
			else if (nomeCobaia.charAt(i)  == 'S') {
				sapos += numCobaia[i];
			}
		}

		double percCoelho =((double) coelhos / total) * 100; // percentual de coelhos
		double percRatos = ((double) ratos / total) * 100; // percentual de ratos
		double percSapos = ((double) sapos / total) * 100; // percentual de sapoos

		System.out.printf("Total: %d cobaias%n", total);
		System.out.printf("Total de coelhos: %d%n", coelhos);
		System.out.printf("Total de ratos: %d%n", ratos);
		System.out.printf("Total de sapos: %d%n", sapos);

		System.out.printf("Percentual de coelhos: %.2f %% %n", percCoelho); 
		System.out.printf("Percentual de ratos: %.2f %% %n", percRatos);
		System.out.printf("Percentual de coelhos: %.2f %%  %n", percSapos);

		sc.close();
	}
}