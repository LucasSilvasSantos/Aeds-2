import java.util.Scanner;

public class camisetas {

    static class No {
        String nome, cor;
        char tamanho;
        No esq, dir;

        No(String nome, String cor, char tamanho) {
            this.nome    = nome;
            this.cor     = cor;
            this.tamanho = tamanho;
            this.esq     = null;
            this.dir     = null;
        }
    }

    static int compareStr(String a, String b) {
        int len = a.length() < b.length() ? a.length() : b.length();
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) != b.charAt(i)) return a.charAt(i) - b.charAt(i);
        }
        return a.length() - b.length();
    }

    // cor ascendente, tamanho descendente (P>M>G), nome ascendente
    static int compare(String corA, char tamA, String nomeA,
                       String corB, char tamB, String nomeB) {
        int c = compareStr(corA, corB);
        if (c != 0) return c;
        c = tamB - tamA; // descendente: inverte a e b
        if (c != 0) return c;
        return compareStr(nomeA, nomeB);
    }

    static No inserir(No raiz, String nome, String cor, char tamanho) {
        if (raiz == null) return new No(nome, cor, tamanho);
        int cmp = compare(cor, tamanho, nome, raiz.cor, raiz.tamanho, raiz.nome);
        if (cmp <= 0) raiz.esq = inserir(raiz.esq, nome, cor, tamanho);
        else          raiz.dir = inserir(raiz.dir, nome, cor, tamanho);
        return raiz;
    }

    static void emOrdem(No raiz, StringBuilder sb) {
        if (raiz == null) return;
        emOrdem(raiz.esq, sb);
        sb.append(raiz.cor).append(" ").append(raiz.tamanho).append(" ").append(raiz.nome).append("\n");
        emOrdem(raiz.dir, sb);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        boolean primeiro = true;

        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            if (n == 0) break;
            sc.nextLine(); // consome o restante da linha do N

            No raiz = null;

            for (int i = 0; i < n; i++) {
                String nome    = sc.nextLine();
                String cor     = sc.next();
                char   tamanho = sc.next().charAt(0);
                sc.nextLine(); // consome o restante da linha
                raiz = inserir(raiz, nome, cor, tamanho);
            }

            if (!primeiro) sb.append("\n");
            primeiro = false;

            emOrdem(raiz, sb);
        }

        System.out.print(sb);
    }
}
