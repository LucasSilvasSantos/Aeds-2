import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ArvBinariaAvrBinaria {

    private static final String MATRICULA = "891378";
    private static long comparacoesPesquisa = 0;

    /* variavel auxiliar usada pela pesquisa na AVL */
    private static boolean avlEncontrado = false;
    private static String avlRestauranteFormatado = null;

    static class Data {
        private int ano, mes, dia;
        public Data(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
        public static Data parseData(String s) {
            int ano = 0, mes = 0, dia = 0, parte = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '-') parte++;
                else if (c >= '0' && c <= '9') {
                    int d = c - '0';
                    if (parte == 0) ano = ano * 10 + d;
                    else if (parte == 1) mes = mes * 10 + d;
                    else dia = dia * 10 + d;
                }
            }
            return new Data(ano, mes, dia);
        }
        public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
    }

    static class Hora {
        private int hora, minuto;
        public Hora(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
        public static Hora parseHora(String s) {
            int hora = 0, minuto = 0, parte = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ':') parte++;
                else if (c >= '0' && c <= '9') {
                    int d = c - '0';
                    if (parte == 0) hora = hora * 10 + d;
                    else minuto = minuto * 10 + d;
                }
            }
            return new Hora(hora, minuto);
        }
        public String formatar() { return String.format("%02d:%02d", hora, minuto); }
    }

    static class Restaurante {
        private int id;
        private String nome, cidade;
        private int capacidade;
        private double avaliacao;
        private String[] tiposCozinha;
        private int faixaPreco;
        private Hora horarioAbertura, horarioFechamento;
        private Data dataAbertura;
        private boolean aberto;

        public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                           String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                           Hora horarioFechamento, Data dataAbertura, boolean aberto) {
            this.id = id; this.nome = nome; this.cidade = cidade;
            this.capacidade = capacidade; this.avaliacao = avaliacao;
            this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco;
            this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento;
            this.dataAbertura = dataAbertura; this.aberto = aberto;
        }

        public int getId() { return id; }
        public String getNome() { return nome; }
        public int getCapacidade() { return capacidade; }

        private static int parseInt(String s) {
            int r = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') r = r * 10 + (c - '0');
            }
            return r;
        }

        private static double parseDouble(String s) {
            double r = 0; boolean dec = false; double div = 10.0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    int d = c - '0';
                    if (!dec) r = r * 10 + d;
                    else { r += d / div; div *= 10; }
                } else if (c == '.') dec = true;
            }
            return r;
        }

        public static Restaurante parseRestaurante(String s) {
            String[] campos = new String[10];
            int campo = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\r' || c == '\n') break;
                if (c == ',') { campos[campo++] = sb.toString(); sb = new StringBuilder(); }
                else sb.append(c);
            }
            campos[campo] = sb.toString();
            int id = parseInt(campos[0]);
            String nome = campos[1], cidade = campos[2];
            int capacidade = parseInt(campos[3]);
            double avaliacao = parseDouble(campos[4]);
            int contTipos = 1;
            for (int i = 0; i < campos[5].length(); i++)
                if (campos[5].charAt(i) == ';') contTipos++;
            String[] tiposCozinha = new String[contTipos];
            int ti = 0; StringBuilder tipo = new StringBuilder();
            for (int i = 0; i < campos[5].length(); i++) {
                char c = campos[5].charAt(i);
                if (c == ';') { tiposCozinha[ti++] = tipo.toString(); tipo = new StringBuilder(); }
                else tipo.append(c);
            }
            tiposCozinha[ti] = tipo.toString();
            int faixaPreco = 0;
            for (int i = 0; i < campos[6].length(); i++)
                if (campos[6].charAt(i) == '$') faixaPreco++;
            int dashIdx = -1;
            for (int i = 0; i < campos[7].length(); i++)
                if (campos[7].charAt(i) == '-') { dashIdx = i; break; }
            StringBuilder horAb = new StringBuilder(), horFech = new StringBuilder();
            for (int i = 0; i < campos[7].length(); i++) {
                if (i < dashIdx) horAb.append(campos[7].charAt(i));
                else if (i > dashIdx) horFech.append(campos[7].charAt(i));
            }
            Data dataAbertura = Data.parseData(campos[8]);
            boolean aberto = campos[9].length() > 0 && campos[9].charAt(0) == 't';
            return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                    faixaPreco, Hora.parseHora(horAb.toString()),
                    Hora.parseHora(horFech.toString()), dataAbertura, aberto);
        }

        public String formatar() {
            StringBuilder sb = new StringBuilder();
            sb.append('[').append(id).append(" ## ").append(nome).append(" ## ").append(cidade)
              .append(" ## ").append(capacidade).append(" ## ")
              .append(String.format("%.1f", avaliacao)).append(" ## [");
            for (int i = 0; i < tiposCozinha.length; i++) {
                if (i > 0) sb.append(',');
                sb.append(tiposCozinha[i]);
            }
            sb.append("] ## ");
            for (int i = 0; i < faixaPreco; i++) sb.append('$');
            sb.append(" ## ").append(horarioAbertura.formatar()).append('-')
              .append(horarioFechamento.formatar()).append(" ## ")
              .append(dataAbertura.formatar()).append(" ## ").append(aberto).append(']');
            return sb.toString();
        }
    }

    static class ColecaoRestaurantes {
        private int tamanho;
        private Restaurante[] restaurantes;
        public ColecaoRestaurantes() { tamanho = 0; restaurantes = new Restaurante[2000]; }
        public int getTamanho() { return tamanho; }
        public Restaurante[] getRestaurantes() { return restaurantes; }
        public void lerCsv(String path) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                br.readLine();
                String linha;
                while ((linha = br.readLine()) != null)
                    if (linha.length() > 0) restaurantes[tamanho++] = Restaurante.parseRestaurante(linha);
                br.close();
            } catch (IOException e) {}
        }
        public static ColecaoRestaurantes lerCsv() {
            ColecaoRestaurantes c = new ColecaoRestaurantes();
            c.lerCsv("/tmp/restaurantes.csv");
            return c;
        }
    }

    /* ---- Arvore AVL (nivel 2, chave: nome) ---- */

    static class NoAVL {
        Restaurante restaurante;
        int altura;
        NoAVL esq, dir;
        NoAVL(Restaurante r) { restaurante = r; altura = 1; }
    }

    static int alturaAVL(NoAVL no) { return no == null ? 0 : no.altura; }

    static int fbAVL(NoAVL no) {
        return no == null ? 0 : alturaAVL(no.esq) - alturaAVL(no.dir);
    }

    static void atualizarAlturaAVL(NoAVL no) {
        if (no != null) {
            int he = alturaAVL(no.esq), hd = alturaAVL(no.dir);
            no.altura = 1 + (he > hd ? he : hd);
        }
    }

    static NoAVL rotDirAVL(NoAVL y) {
        NoAVL x = y.esq; NoAVL t = x.dir;
        x.dir = y; y.esq = t;
        atualizarAlturaAVL(y); atualizarAlturaAVL(x);
        return x;
    }

    static NoAVL rotEsqAVL(NoAVL x) {
        NoAVL y = x.dir; NoAVL t = y.esq;
        y.esq = x; x.dir = t;
        atualizarAlturaAVL(x); atualizarAlturaAVL(y);
        return y;
    }

    static NoAVL balancearAVL(NoAVL no) {
        atualizarAlturaAVL(no);
        int fb = fbAVL(no);
        if (fb > 1  && fbAVL(no.esq) >= 0)  return rotDirAVL(no);
        if (fb > 1  && fbAVL(no.esq) <  0)  { no.esq = rotEsqAVL(no.esq); return rotDirAVL(no); }
        if (fb < -1 && fbAVL(no.dir) <= 0)  return rotEsqAVL(no);
        if (fb < -1 && fbAVL(no.dir) >  0)  { no.dir = rotDirAVL(no.dir); return rotEsqAVL(no); }
        return no;
    }

    static NoAVL inserirAVL(NoAVL raiz, Restaurante r) {
        if (raiz == null) return new NoAVL(r);
        int cmp = r.getNome().compareTo(raiz.restaurante.getNome());
        if (cmp < 0) raiz.esq = inserirAVL(raiz.esq, r);
        else if (cmp > 0) raiz.dir = inserirAVL(raiz.dir, r);
        return balancearAVL(raiz);
    }

    /* Pesquisa na AVL; retorna caminho percorrido (minusculo).
       Preenche avlEncontrado e avlRestauranteFormatado se achou. */
    static String pesquisarAVL(NoAVL raiz, String nome) {
        avlEncontrado = false;
        avlRestauranteFormatado = null;
        if (raiz == null) return "";
        StringBuilder sb = new StringBuilder("raiz");
        NoAVL atual = raiz;
        while (atual != null) {
            comparacoesPesquisa++;
            int cmp = nome.compareTo(atual.restaurante.getNome());
            if (cmp == 0) {
                avlEncontrado = true;
                avlRestauranteFormatado = atual.restaurante.formatar();
                sb.append(" SIM");
                return sb.toString();
            } else if (cmp < 0) { sb.append(" esq"); atual = atual.esq; }
            else                 { sb.append(" dir"); atual = atual.dir; }
        }
        return sb.toString(); /* caminho sem terminal: nao encontrado */
    }

    /* ---- BST externo (nivel 1, chave: capacidade mod 15) ---- */

    static class NoExterno {
        int chave;
        NoAVL raizAvl;
        NoExterno esq, dir;
        NoExterno(int chave) { this.chave = chave; }
    }

    static NoExterno inserirBST(NoExterno raiz, Restaurante r) {
        int chave = r.getCapacidade() % 15;
        if (raiz == null) {
            NoExterno novo = new NoExterno(chave);
            novo.raizAvl = inserirAVL(null, r);
            return novo;
        }
        if (chave < raiz.chave) raiz.esq = inserirBST(raiz.esq, r);
        else if (chave > raiz.chave) raiz.dir = inserirBST(raiz.dir, r);
        else raiz.raizAvl = inserirAVL(raiz.raizAvl, r); /* mesmo chave: insere na AVL */
        return raiz;
    }

    /* Pesquisa por nome: DFS no BST externo; em cada no BST pesquisa AVL interna.
       Caminho: RAIZ/ESQ/DIR (maiusculo) para nivel 1; raiz/esq/dir (minusculo) para nivel 2. */
    static void pesquisarDFS(NoExterno no, String nome, String rotulo,
                              StringBuilder sb, boolean[] encontrado) {
        if (no == null || encontrado[0]) return;

        sb.append(rotulo);

        String avlPath = pesquisarAVL(no.raizAvl, nome);
        sb.append(" ").append(avlPath);

        if (avlEncontrado) {
            sb.append(" ").append(avlRestauranteFormatado);
            encontrado[0] = true;
            return;
        }

        pesquisarDFS(no.esq, nome, " ESQ", sb, encontrado);
        if (encontrado[0]) return;
        pesquisarDFS(no.dir, nome, " DIR", sb, encontrado);
    }

    static String pesquisar(NoExterno raizBST, String nome) {
        StringBuilder sb = new StringBuilder();
        boolean[] encontrado = { false };
        pesquisarDFS(raizBST, nome, "RAIZ", sb, encontrado);
        if (!encontrado[0]) sb.append(" NAO");
        String resultado = sb.toString().trim();
        return resultado.length() > 0 ? resultado : "NAO";
    }

    static void emOrdemAVL(NoAVL raiz) {
        if (raiz == null) return;
        emOrdemAVL(raiz.esq);
        System.out.println(raiz.restaurante.formatar());
        emOrdemAVL(raiz.dir);
    }

    static void emOrdem(NoExterno raiz) {
        if (raiz == null) return;
        emOrdem(raiz.esq);
        emOrdemAVL(raiz.raizAvl);
        emOrdem(raiz.dir);
    }

    public static void main(String[] args) throws IOException {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] todos = colecao.getRestaurantes();
        int tamanho = colecao.getTamanho();

        NoExterno raiz = null;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            for (int i = 0; i < tamanho; i++) {
                if (todos[i].getId() == id) { raiz = inserirBST(raiz, todos[i]); break; }
            }
        }

        long t0 = System.nanoTime();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.length() == 0) continue;
            if (linha.compareTo("FIM") == 0) break;
            System.out.println(pesquisar(raiz, linha));
        }
        long t1 = System.nanoTime();
        sc.close();

        emOrdem(raiz);

        PrintWriter pw = new PrintWriter(new FileWriter(MATRICULA + "_hibrida_arvore_arvore.txt"));
        pw.println(MATRICULA + "\t" + comparacoesPesquisa + "\t" + (t1 - t0));
        pw.close();
    }
}
