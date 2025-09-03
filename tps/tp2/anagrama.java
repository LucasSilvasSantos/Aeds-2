import java.util.Scanner;

public class anagrama {
    public static void main(String[] args)  {
       

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();

            if (linha.equals("FIM")) {
                break;
            }

            String[] partes = linha.split(" - ");
            if (partes.length != 2) {
                System.out.println("NAO");
                continue;
            }

            String palavra1 = partes[0].trim().toLowerCase();
            String palavra2 = partes[1].trim().toLowerCase();

            if (palavra1.length() != palavra2.length()) {
                System.out.println("NAO");
                continue;
            }

            int[] contagem1 = new int[256];
            int[] contagem2 = new int[256];

            for (int i = 0; i < palavra1.length(); i++) {
                contagem1[palavra1.charAt(i)]++;
                contagem2[palavra2.charAt(i)]++;
            }

            boolean anagrama = true;
            for (int i = 0; i < 256; i++) {
                if (contagem1[i] != contagem2[i]) {
                    anagrama = false;
                    break;
                }
            }

            System.out.println(anagrama ? "SIM" : "NAO");
        }

        sc.close();
    }
}

