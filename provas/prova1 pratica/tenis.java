
import java.util.Scanner;

public class tenis {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int contadorV = 0;
        int contadorP = 0;

        for (int i = 0; i < 6; i++) {
            char resultado = sc.next().charAt(0);
            if (resultado != 'V' && resultado != 'P') {
                System.out.println("-1");
                i=6;
            }
            if (resultado == 'V') {
                contadorV++;
            } else if (resultado == 'P') {
                contadorP++;
            }
            

        }
        switch (contadorV) {
                case 5:
                case 6:
                    System.out.println("1");
                    break;

                case 4:
                case 3:
                    System.out.println("2");
                    break;
                case 2:
                case 1:
                    System.out.println("3");
                    break;
            }

    }

}
