public class CiframentoCesar {
    public static String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder();
        int key = 3;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '\uFFFD') { 
                encrypted.append(ch);
                continue;
            }
            ch += key;
            encrypted.append(ch);
        }
        return encrypted.toString();
    }

    public static void main(String[] args) {
        boolean continueReading = true;
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (scanner.hasNextLine() && continueReading) {
            String line = scanner.nextLine();
            if (line.equals("FIM")) {
                continueReading = false;
            }
            else {
            StringBuilder result = new StringBuilder();
                result.append(encrypt(line));
            System.out.println(result.toString());
        }
    }
        scanner.close();
    }
}