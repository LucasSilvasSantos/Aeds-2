import java.util.Scanner;

public class hashCalc {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < N; t++) {
            int L = sc.nextInt();
            long hash = 0;

            for (int e = 0; e < L; e++) {
                String s = sc.next();
                for (int p = 0; p < s.length(); p++) {
                    hash += (s.charAt(p) - 'A') + e + p;
                }
            }

            sb.append(hash).append('\n');
        }

        System.out.print(sb);
    }
}
