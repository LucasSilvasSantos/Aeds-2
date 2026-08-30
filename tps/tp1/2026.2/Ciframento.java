import java.util.Scanner;

public class Ciframento {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!(input.charAt(0)== 'F' && input.charAt(1) == 'I' && input.charAt(2) == 'M')) {

            String resultado = "";

            for (int j = 0; j < input.length(); j++) {

                char c = input.charAt(j);

                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                    c = (char) (c + 3);
                }

                resultado += c;
            }

            System.out.println(resultado);

            input = sc.nextLine();
        }

        sc.close();
    }
}