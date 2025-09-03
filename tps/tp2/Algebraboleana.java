import java.util.*;

public class Algebraboleana {

	public static boolean getAnd(String str, int valores[], int i) {
		boolean resp = false;

		for (i=0; i < str.length(); i++) {
			if (str.charAt(i) == 'n' && str.charAt(i+1) == 'o' && str.charAt(i+2) == 't') {

			}
		}

		return resp;
	}

	public static boolean valorBooleano(String str, int valores[]) {
		boolean resp = false;
		int cont = 0;

		for (int i=0; i < str.length(); i++) {
			if (str.charAt(i) == 'a' && str.charAt(i+1) == 'n' && str.charAt(i+2) == 'd') {
				resp = getAnd(str, valores, i+4);
			} 
			else if (str.charAt(i) == 'o' && str.charAt(i+1) == 'r') {
				cont++;
			}
			else if (str.charAt(i) == 'n' && ){
                
            }
		}

		return resp;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		while(n > 0) {
			int valores[] = new int[n];
			for (int i=0; i < n; i++) {
				int temp = sc.nextInt();
				valores[i] = temp;
			}
			String str = sc.nextLine();

			System.out.println((valorBooleano(str, valores)) ? "1" : "0");

			n = sc.nextInt();
		}


		sc.close();
	}
}