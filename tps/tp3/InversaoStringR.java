import java.util.Scanner;


public class InversaoStringR{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       boolean continueReading = true;
        while (sc.hasNextLine() && continueReading) {
            String s = sc.nextLine(); // lê uma linha
            String invertida = "";
            
            for (int i = s.length() - 1; i >= 0; i--) {
                invertida += s.charAt(i);
            }
          if (s.charAt(0)== 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M') {
                break;
            }
             System.out.println(invertida);
        }
 
        sc.close();
    }
}
