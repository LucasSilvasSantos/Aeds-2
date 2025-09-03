import java.util.*;

public class Algebraboleana {

    // Avalia expressão booleana substituindo variáveis pelos valores
    public static boolean avaliarExpressao(String expr, int[] valores) {
        // Substituir A, B, C ... pelos valores
        for (int i = 0; i < valores.length; i++) {
            char var = (char)('A' + i);
            expr = expr.replace(var + "", valores[i] + "");
        }

        // Usar pilha para avaliar expressão
        Stack<String> stack = new Stack<>();
        StringBuilder token = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (c == '(') {
                stack.push("(");
            } else if (c == ')') {
                // Resolver até achar "("
                List<String> args = new ArrayList<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    args.add(stack.pop());
                }
                stack.pop(); // remove "("
                Collections.reverse(args);

                String op = stack.pop(); // operador antes do "("
                boolean result = false;

                if (op.equals("and")) {
                    result = true;
                    for (String a : args) {
                        result = result && (a.equals("1"));
                    }
                } else if (op.equals("or")) {
                    result = false;
                    for (String a : args) {
                        result = result || (a.equals("1"));
                    }
                } else if (op.equals("not")) {
                    result = !(args.get(0).equals("1"));
                }

                stack.push(result ? "1" : "0");
            } else if (Character.isLetterOrDigit(c)) {
                token.append(c);
                // Quando terminar palavra/numero
                if (i + 1 == expr.length() || !Character.isLetterOrDigit(expr.charAt(i + 1))) {
                    stack.push(token.toString());
                    token.setLength(0);
                }
            }
        }

        return stack.pop().equals("1");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        while (n > 0) {
            int[] valores = new int[n];
            for (int i = 0; i < n; i++) {
                valores[i] = sc.nextInt();
            }
            String expr = sc.nextLine().trim();

            boolean resultado = avaliarExpressao(expr, valores);
            System.out.println(resultado ? "1" : "0");

            n = sc.nextInt();
        }
        sc.close();
    }
}
