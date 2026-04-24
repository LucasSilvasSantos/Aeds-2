import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }

    public static Data parseData(String s) {
        int ano = 0, mes = 0, dia = 0, parte = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                parte++;
            } else if (c >= '0' && c <= '9') {
                int d = c - '0';
                if (parte == 0) ano = ano * 10 + d;
                else if (parte == 1) mes = mes * 10 + d;
                else dia = dia * 10 + d;
            }
        }
        return new Data(ano, mes, dia);
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() { return hora; }
    public int getMinuto() { return minuto; }

    public static Hora parseHora(String s) {
        int hora = 0, minuto = 0, parte = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ':') {
                parte++;
            } else if (c >= '0' && c <= '9') {
                int d = c - '0';
                if (parte == 0) hora = hora * 10 + d;
                else minuto = minuto * 10 + d;
            }
        }
        return new Hora(hora, minuto);
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private Hora horarioAbertura;
    private Hora horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] tiposCozinha, int faixaPreco, Hora horarioAbertura,
                       Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha;
        this.faixaPreco = faixaPreco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; }
    public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; }
    public int getFaixaPreco() { return faixaPreco; }
    public Hora getHorarioAbertura() { return horarioAbertura; }
    public Hora getHorarioFechamento() { return horarioFechamento; }
    public Data getDataAbertura() { return dataAbertura; }
    public boolean isAberto() { return aberto; }

    private static int parseInt(String s) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') result = result * 10 + (c - '0');
        }
        return result;
    }

    private static double parseDouble(String s) {
        double result = 0;
        boolean decimal = false;
        double divisor = 10.0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                int d = c - '0';
                if (!decimal) result = result * 10 + d;
                else { result += d / divisor; divisor *= 10; }
            } else if (c == '.') {
                decimal = true;
            }
        }
        return result;
    }

    public static Restaurante parseRestaurante(String s) {
        String[] campos = new String[10];
        int campo = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\r' || c == '\n') break;
            if (c == ',') {
                campos[campo++] = sb.toString();
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        campos[campo] = sb.toString();

        int id = parseInt(campos[0]);
        String nome = campos[1];
        String cidade = campos[2];
        int capacidade = parseInt(campos[3]);
        double avaliacao = parseDouble(campos[4]);

        int contTipos = 1;
        for (int i = 0; i < campos[5].length(); i++) {
            if (campos[5].charAt(i) == ';') contTipos++;
        }
        String[] tiposCozinha = new String[contTipos];
        int ti = 0;
        StringBuilder tipo = new StringBuilder();
        for (int i = 0; i < campos[5].length(); i++) {
            char c = campos[5].charAt(i);
            if (c == ';') {
                tiposCozinha[ti++] = tipo.toString();
                tipo = new StringBuilder();
            } else {
                tipo.append(c);
            }
        }
        tiposCozinha[ti] = tipo.toString();

        int faixaPreco = 0;
        for (int i = 0; i < campos[6].length(); i++) {
            if (campos[6].charAt(i) == '$') faixaPreco++;
        }

        int dashIdx = -1;
        for (int i = 0; i < campos[7].length(); i++) {
            if (campos[7].charAt(i) == '-') { dashIdx = i; break; }
        }
        StringBuilder horAb = new StringBuilder();
        StringBuilder horFech = new StringBuilder();
        for (int i = 0; i < campos[7].length(); i++) {
            if (i < dashIdx) horAb.append(campos[7].charAt(i));
            else if (i > dashIdx) horFech.append(campos[7].charAt(i));
        }
        Hora horarioAbertura = Hora.parseHora(horAb.toString());
        Hora horarioFechamento = Hora.parseHora(horFech.toString());

        Data dataAbertura = Data.parseData(campos[8]);

        boolean aberto = campos[9].length() > 0 && campos[9].charAt(0) == 't';

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha,
                               faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

    public String formatar() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(id);
        sb.append(" ## ");
        sb.append(nome);
        sb.append(" ## ");
        sb.append(cidade);
        sb.append(" ## ");
        sb.append(capacidade);
        sb.append(" ## ");
        sb.append(String.format("%.1f", avaliacao));
        sb.append(" ## [");
        for (int i = 0; i < tiposCozinha.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(tiposCozinha[i]);
        }
        sb.append("] ## ");
        for (int i = 0; i < faixaPreco; i++) sb.append('$');
        sb.append(" ## ");
        sb.append(horarioAbertura.formatar());
        sb.append('-');
        sb.append(horarioFechamento.formatar());
        sb.append(" ## ");
        sb.append(dataAbertura.formatar());
        sb.append(" ## ");
        sb.append(aberto);
        sb.append(']');
        return sb.toString();
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes() {
        tamanho = 0;
        restaurantes = new Restaurante[2000];
    }

    public int getTamanho() { return tamanho; }
    public Restaurante[] getRestaurantes() { return restaurantes; }

    public void lerCsv(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.length() > 0) {
                    restaurantes[tamanho++] = Restaurante.parseRestaurante(linha);
                }
            }
            br.close();
        } catch (IOException e) {
        }
    }

    public static ColecaoRestaurantes lerCsv() {
        ColecaoRestaurantes c = new ColecaoRestaurantes();
        c.lerCsv("/tmp/restaurantes.csv");
        return c;
    }
}

public class Restaurantes {
    public static void main(String[] args) {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] restaurantes = colecao.getRestaurantes();
        int tamanho = colecao.getTamanho();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int id = sc.nextInt();
            if (id == -1) break;
            for (int i = 0; i < tamanho; i++) {
                if (restaurantes[i].getId() == id) {
                    System.out.println(restaurantes[i].formatar());
                    break;
                }
            }
        }
        sc.close();
    }
}
