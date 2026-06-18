import java.util.Scanner;

public class recuperação {

    static StringBuilder sb;

    // reconstrói e imprime o pós-fixo a partir dos percursos pré e in
    static void posFixo(char[] pre, char[] in, int pIni, int pFim, int iIni, int iFim) {
        if (pIni > pFim) return;

        char raiz = pre[pIni];

        // localiza a raiz no percurso infixo
        int pos = iIni;
        while (in[pos] != raiz) pos++;

        int tamEsq = pos - iIni; // quantidade de nós na subárvore esquerda

        // subárvore esquerda
        posFixo(pre, in, pIni + 1, pIni + tamEsq, iIni, pos - 1);
        // subárvore direita
        posFixo(pre, in, pIni + tamEsq + 1, pFim, pos + 1, iFim);

        sb.append(raiz); // raiz por último (pós-fixo)
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sb = new StringBuilder();

        while (sc.hasNext()) {
            char[] pre = sc.next().toCharArray();
            char[] in  = sc.next().toCharArray();

            posFixo(pre, in, 0, pre.length - 1, 0, in.length - 1);
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
