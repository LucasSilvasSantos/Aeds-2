import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HashDRehash {

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

    /* ---------- Hash Direta com Rehash ---------- */
    /* h1(nome) = soma_ascii(nome) mod 83          */
    /* h2(nome) = (soma_ascii(nome) + 1) mod 83    */

    private static final int TAM_TAB = 83;
    private static Restaurante[] tabela = new Restaurante[TAM_TAB];

    private static int somaAscii(String nome) {
        int soma = 0;
        for (int i = 0; i < nome.length(); i++) soma += (int) nome.charAt(i);
        return soma;
    }

    private static int hash1(String nome) { return somaAscii(nome) % TAM_TAB; }
    private static int hash2(String nome) { return (somaAscii(nome) + 1) % TAM_TAB; }

    static void hashInserir(Restaurante r) {
        int pos1 = hash1(r.getNome());
        if (tabela[pos1] == null) { tabela[pos1] = r; return; }
        int pos2 = hash2(r.getNome());
        if (tabela[pos2] == null) { tabela[pos2] = r; return; }
        /* ambas posicoes ocupadas: colisao sem espaco */
        System.out.println(r.getNome());
    }

    static void hashPesquisar(String nome) {
        int pos1 = hash1(nome);
        if (tabela[pos1] != null) {
            comparacoesPesquisa++;
            if (tabela[pos1].getNome().compareTo(nome) == 0) {
                System.out.println(pos1 + "\t" + tabela[pos1].formatar()); return;
            }
        }
        int pos2 = hash2(nome);
        if (tabela[pos2] != null) {
            comparacoesPesquisa++;
            if (tabela[pos2].getNome().compareTo(nome) == 0) {
                System.out.println(pos2 + "\t" + tabela[pos2].formatar()); return;
            }
        }
        System.out.println(-1);
    }

    public static void main(String[] args) throws IOException {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] todos = colecao.getRestaurantes();
        int tamanho = colecao.getTamanho();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            for (int i = 0; i < tamanho; i++) {
                if (todos[i].getId() == id) { hashInserir(todos[i]); break; }
            }
        }

        long t0 = System.nanoTime();
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.length() == 0) continue;
            if (linha.compareTo("FIM") == 0) break;
            hashPesquisar(linha);
        }
        long t1 = System.nanoTime();
        sc.close();

        PrintWriter pw = new PrintWriter(new FileWriter(MATRICULA + "_hash_rehash.txt"));
        pw.println(MATRICULA + "\t" + comparacoesPesquisa + "\t" + (t1 - t0));
        pw.close();
    }
}
