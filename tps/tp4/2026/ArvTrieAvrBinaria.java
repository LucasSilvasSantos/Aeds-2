import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ArvTrieAvrBinaria {

    private static final String MATRICULA = "891378";
    private static long comparacoesPesquisa = 0;

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

    /* -------- Arvore Trie com Arvore Binaria de Busca -------- */
    /* Cada no Trie mantém uma ABB de filhos, com chave = char   */

    /* No da ABB de filhos: chave = char, valor = NoTrie filho */
    static class NoFilho {
        char c;
        NoTrie filho;
        NoFilho esq, dir;
        NoFilho(char c, NoTrie filho) { this.c = c; this.filho = filho; }
    }

    static class NoTrie {
        char c;
        boolean fim;
        Restaurante restaurante;
        NoFilho raizFilhos; /* raiz da ABB de filhos */

        NoTrie(char c) { this.c = c; }
    }

    /* Busca filho com caractere ch na ABB do no */
    static NoTrie buscarFilho(NoFilho raiz, char ch) {
        NoFilho atual = raiz;
        while (atual != null) {
            if (ch == atual.c) return atual.filho;
            else if (ch < atual.c) atual = atual.esq;
            else atual = atual.dir;
        }
        return null;
    }

    /* Insere novo filho com caractere ch na ABB e retorna a nova raiz da ABB */
    static NoFilho inserirFilho(NoFilho raiz, char ch, NoTrie filho) {
        if (raiz == null) return new NoFilho(ch, filho);
        if (ch < raiz.c) raiz.esq = inserirFilho(raiz.esq, ch, filho);
        else if (ch > raiz.c) raiz.dir = inserirFilho(raiz.dir, ch, filho);
        else raiz.filho = filho; /* atualiza filho existente */
        return raiz;
    }

    static void trieInserir(NoTrie raiz, Restaurante r) {
        NoTrie atual = raiz;
        for (int i = 0; i < r.getNome().length(); i++) {
            char c = r.getNome().charAt(i);
            NoTrie filho = buscarFilho(atual.raizFilhos, c);
            if (filho == null) {
                filho = new NoTrie(c);
                atual.raizFilhos = inserirFilho(atual.raizFilhos, c, filho);
            }
            atual = filho;
        }
        atual.fim = true;
        atual.restaurante = r;
    }

    static String triePesquisar(NoTrie raiz, String nome) {
        StringBuilder sb = new StringBuilder();
        NoTrie atual = raiz;
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            NoTrie filho = buscarFilho(atual.raizFilhos, c);
            comparacoesPesquisa++;
            if (filho == null) {
                sb.append(c).append(' ');
                sb.append("NAO");
                return sb.toString();
            }
            sb.append(c).append(' ');
            atual = filho;
        }
        if (atual.fim) {
            sb.append("SIM ").append(atual.restaurante.formatar());
        } else {
            sb.append("NAO");
        }
        return sb.toString();
    }

    /* Traversal em ordem alfabetica: ABB de filhos naturalmente em ordem */
    static void trieEmOrdemFilhos(NoFilho raiz) {
        if (raiz == null) return;
        trieEmOrdemFilhos(raiz.esq);
        trieEmOrdem(raiz.filho);
        trieEmOrdemFilhos(raiz.dir);
    }

    static void trieEmOrdem(NoTrie no) {
        if (no == null) return;
        if (no.fim) System.out.println(no.restaurante.formatar());
        trieEmOrdemFilhos(no.raizFilhos);
    }

    public static void main(String[] args) throws IOException {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] todos = colecao.getRestaurantes();
        int tamanho = colecao.getTamanho();

        NoTrie raiz = new NoTrie('\0'); /* raiz nao representa char */

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            for (int i = 0; i < tamanho; i++) {
                if (todos[i].getId() == id) { trieInserir(raiz, todos[i]); break; }
            }
        }

        long t0 = System.nanoTime();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.length() == 0) continue;
            if (linha.compareTo("FIM") == 0) break;
            System.out.println(triePesquisar(raiz, linha));
        }
        long t1 = System.nanoTime();
        sc.close();

        trieEmOrdem(raiz);

        PrintWriter pw = new PrintWriter(new FileWriter(MATRICULA + "_arvore_trie_arvore.txt"));
        pw.println(MATRICULA + "\t" + comparacoesPesquisa + "\t" + (t1 - t0));
        pw.close();
    }
}
