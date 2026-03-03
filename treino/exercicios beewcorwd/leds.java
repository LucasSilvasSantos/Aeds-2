import java.util.Scanner;


public class leds {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Prohibited = sc.nextInt();
        for (int i = 0; i < Prohibited; i++) {
            String leds = sc.next();
            int numberleds = 0;
            for (int j = 0; j < leds.length(); j++) {
                char c = leds.charAt(j);
                if (c == '1') numberleds += 2;
                else if (c == '2' || c == '3' || c == '5') numberleds += 5;
                else if (c == '4') numberleds += 4;
                else if (c == '6' || c == '9' || c == '0') numberleds += 6;
                else if (c == '7') numberleds += 3;
                else if (c == '8') numberleds += 7;
            }
            System.out.println(numberleds + " leds");
        }
        sc.close();
    }

}
