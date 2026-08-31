import java.util.Scanner;


public class InversaoStringR{

    public static String inverter(String s, int i) {
        if (i < 0) {
            return "";
        }
        return s.charAt(i) + inverter(s, i - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String s = sc.nextLine(); // lê uma linha

            if (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
                break;
            }

            String invertida = inverter(s, s.length() - 1);
            System.out.println(invertida);
        }

        sc.close();
    }
}
