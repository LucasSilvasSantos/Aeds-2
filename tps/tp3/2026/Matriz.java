import java.util.Scanner;

public class Matriz {

    static class Celula {
        int valor;
        Celula dir, baixo;
        Celula(int v) { valor = v; }
    }

    int linhas, colunas;
    Celula[][] celulas;

    public Matriz(int l, int c) {
        linhas = l; colunas = c;
        celulas = new Celula[l][c];
        for (int i = 0; i < l; i++)
            for (int j = 0; j < c; j++)
                celulas[i][j] = new Celula(0);
        for (int i = 0; i < l; i++)
            for (int j = 0; j < c - 1; j++)
                celulas[i][j].dir = celulas[i][j + 1];
        for (int i = 0; i < l - 1; i++)
            for (int j = 0; j < c; j++)
                celulas[i][j].baixo = celulas[i + 1][j];
    }

    public Matriz somar(Matriz m) {
        Matriz r = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < colunas; j++)
                r.celulas[i][j].valor = celulas[i][j].valor + m.celulas[i][j].valor;
        return r;
    }

    public Matriz multiplicar(Matriz m) {
        Matriz r = new Matriz(linhas, m.colunas);
        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < m.colunas; j++)
                for (int k = 0; k < colunas; k++)
                    r.celulas[i][j].valor += celulas[i][k].valor * m.celulas[k][j].valor;
        return r;
    }

    public void mostrarDiagonalPrincipal() {
        int dim = Math.min(linhas, colunas);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            if (i > 0) sb.append(' ');
            sb.append(celulas[i][i].valor);
        }
        System.out.println(sb.toString());
    }

    public void mostrarDiagonalSecundaria() {
        int dim = Math.min(linhas, colunas);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            if (i > 0) sb.append(' ');
            sb.append(celulas[i][colunas - 1 - i].valor);
        }
        System.out.println(sb.toString());
    }

    public void mostrar() {
        for (int i = 0; i < linhas; i++) {
            StringBuilder sb = new StringBuilder();
            Celula cur = celulas[i][0];
            while (cur != null) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(cur.valor);
                cur = cur.dir;
            }
            System.out.println(sb.toString());
        }
    }

    static Matriz lerMatriz(Scanner sc, int l, int c) {
        Matriz m = new Matriz(l, c);
        for (int i = 0; i < l; i++)
            for (int j = 0; j < c; j++)
                m.celulas[i][j].valor = sc.nextInt();
        return m;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();
        for (int t = 0; t < casos; t++) {
            int l1 = sc.nextInt(), c1 = sc.nextInt();
            Matriz m1 = lerMatriz(sc, l1, c1);
            Matriz m2 = lerMatriz(sc, l1, c1);

            m1.mostrarDiagonalPrincipal();
            m2.mostrarDiagonalSecundaria();
            m1.somar(m2).mostrar();
            m1.multiplicar(m2).mostrar();
        }
        sc.close();
    }
}
