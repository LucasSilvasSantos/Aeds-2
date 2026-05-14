import java.util.Scanner;

public class TdaRacional {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int entrada = sc.nextInt();

        for (int i = 0; i < entrada; i++) {
            if (!sc.hasNextInt()) break;
            int n1 = sc.nextInt();
            if (!sc.hasNext()) break;
            sc.next(); // consome '/'
            if (!sc.hasNextInt()) break;
            int d1 = sc.nextInt();

            if (!sc.hasNext()) break;
            String op = sc.next();

            if (!sc.hasNextInt()) break;
            int n2 = sc.nextInt();
            if (!sc.hasNext()) break;
            sc.next(); // consome '/'
            if (!sc.hasNextInt()) break;
            int d2 = sc.nextInt();

            int num = 0;
            int den = 1;

            switch (op) {
                case "+":
                    num = n1 * d2 + n2 * d1;
                    den = d1 * d2;
                    break;
                case "-":
                    num = n1 * d2 - n2 * d1;
                    den = d1 * d2;
                    break;
                case "*":
                    num = n1 * n2;
                    den = d1 * d2;
                    break;
                case "/":
                    num = n1 * d2;
                    den = d1 * n2;
                    break;
                default:
                    System.out.println("Operador inválido: " + op);
                    continue;
            }

            if (den == 0) {
                System.out.println("Denominador zero");
                continue;
            }

            int origNum = num;
            int origDen = den;

            int g = gcd(Math.abs(num), Math.abs(den));
            num /= g;
            den /= g;

            if (den < 0) {
                den = -den;
                num = -num;
            }

            System.out.println(origNum + "/" + origDen + " = " + num + "/" + den);
        }

        sc.close();
    }

    private static int gcd(int a, int b) {
        if (a == 0 && b == 0) return 1;
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }
}
