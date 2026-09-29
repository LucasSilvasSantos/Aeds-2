import java.util.Scanner;

class Util {
    // Compara duas strings caractere por caractere e só retorna verdadeiro quando conteúdo e tamanho são iguais.
    public static boolean igual(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) return false;
        for (int i = 0; i < a.length(); i++) if (a.charAt(i) != b.charAt(i)) return false;
        return true;
    }

    // Compara duas strings manualmente em ordem lexicográfica, retornando negativo, zero ou positivo.
    public static int comparar(String a, String b) {
        int limite = a.length() < b.length() ? a.length() : b.length();
        for (int i = 0; i < limite; i++) {
            if (a.charAt(i) < b.charAt(i)) return -1;
            if (a.charAt(i) > b.charAt(i)) return 1;
        }
        if (a.length() < b.length()) return -1;
        if (a.length() > b.length()) return 1;
        return 0;
    }

    // Copia manualmente os caracteres entre as posições informadas para formar uma nova string.
    public static String trecho(String s, int inicio, int fim) {
        String resposta = "";
        for (int i = inicio; i < fim; i++) resposta += s.charAt(i);
        return resposta;
    }

    // Conta os separadores e divide a string manualmente, sem usar métodos prontos de divisão.
    public static String[] separar(String s, char separador) {
        int quantidade = 1;
        for (int i = 0; i < s.length(); i++) if (s.charAt(i) == separador) quantidade++;
        String[] partes = new String[quantidade];
        int inicio = 0;
        int posicao = 0;
        for (int i = 0; i <= s.length(); i++) {
            if (i == s.length() || s.charAt(i) == separador) {
                partes[posicao++] = trecho(s, inicio, i);
                inicio = i + 1;
            }
        }
        return partes;
    }

    // Converte uma string numérica em inteiro acumulando cada algarismo e tratando o sinal.
    public static int inteiro(String s) {
        int sinal = 1, i = 0, valor = 0;
        if (s.length() > 0 && s.charAt(0) == '-') { sinal = -1; i = 1; }
        while (i < s.length()) {
            valor = valor * 10 + (s.charAt(i) - '0');
            i++;
        }
        return valor * sinal;
    }

    // Converte uma string em número real, controlando manualmente as partes inteira e fracionária.
    public static double real(String s) {
        double valor = 0.0;
        double divisor = 1.0;
        boolean depoisDoPonto = false;
        int sinal = 1, i = 0;
        if (s.length() > 0 && s.charAt(0) == '-') { sinal = -1; i = 1; }
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '.') {
                depoisDoPonto = true;
            } else if (!depoisDoPonto) {
                valor = valor * 10.0 + (c - '0');
            } else {
                divisor *= 10.0;
                valor += (c - '0') / divisor;
            }
            i++;
        }
        return valor * sinal;
    }

    // Converte um inteiro em texto extraindo seus algarismos do fim para o início.
    public static String inteiroParaString(long valor) {
        if (valor == 0) return "0";
        boolean negativo = valor < 0;
        if (negativo) valor = -valor;
        String resposta = "";
        while (valor > 0) {
            resposta = (char)('0' + (valor % 10)) + resposta;
            valor /= 10;
        }
        return negativo ? "-" + resposta : resposta;
    }

    // Arredonda e formata um real com a quantidade fixa de casas decimais solicitada.
    public static String decimal(double valor, int casas) {
        long fator = 1;
        for (int i = 0; i < casas; i++) fator *= 10;
        long total = (long)(valor * fator + 0.500000001);
        long inteira = total / fator;
        long fracao = total % fator;
        String parteFracionaria = inteiroParaString(fracao);
        while (parteFracionaria.length() < casas) parteFracionaria = "0" + parteFracionaria;
        return inteiroParaString(inteira) + "." + parteFracionaria;
    }
}

class Data {
    private int ano;
    private int mes;
    private int dia;

    // Cria uma data e armazena separadamente ano, mês e dia.
    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    // Devolve o valor de Ano armazenado no objeto, preservando o encapsulamento.
    public int getAno() { return ano; }
    // Devolve o valor de Mes armazenado no objeto, preservando o encapsulamento.
    public int getMes() { return mes; }
    // Devolve o valor de Dia armazenado no objeto, preservando o encapsulamento.
    public int getDia() { return dia; }

    // Separa uma data no formato AAAA-MM-DD e converte cada parte manualmente.
    public static Data parseData(String s) {
        String[] partes = Util.separar(s, '-');
        return new Data(Util.inteiro(partes[0]), Util.inteiro(partes[1]), Util.inteiro(partes[2]));
    }

    // Monta a data em DD/MM/AAAA, acrescentando zeros quando necessário.
    public String format() {
        String d = dia < 10 ? "0" + Util.inteiroParaString(dia) : Util.inteiroParaString(dia);
        String m = mes < 10 ? "0" + Util.inteiroParaString(mes) : Util.inteiroParaString(mes);
        return d + "/" + m + "/" + Util.inteiroParaString(ano);
    }
}

class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String categoria;
    private String[] combustivel;
    private int cilindros;
    private double cilindrada;
    private String transmissao;
    private String tracao;
    private double consumoCidade;
    private double consumoEstrada;
    private double co2;
    private boolean turbo;
    private Data dataRegistro;

    // Cria um veículo e associa cada valor recebido ao respectivo atributo privado.
    public Veiculo(int id, String marca, String modelo, int ano, String categoria,
                   String[] combustivel, int cilindros, double cilindrada,
                   String transmissao, String tracao, double consumoCidade,
                   double consumoEstrada, double co2, boolean turbo, Data dataRegistro) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.categoria = categoria;
        this.combustivel = combustivel;
        this.cilindros = cilindros;
        this.cilindrada = cilindrada;
        this.transmissao = transmissao;
        this.tracao = tracao;
        this.consumoCidade = consumoCidade;
        this.consumoEstrada = consumoEstrada;
        this.co2 = co2;
        this.turbo = turbo;
        this.dataRegistro = dataRegistro;
    }

    // Devolve o valor de Id armazenado no objeto, preservando o encapsulamento.
    public int getId() { return id; }
    // Devolve o valor de Marca armazenado no objeto, preservando o encapsulamento.
    public String getMarca() { return marca; }
    // Devolve o valor de Modelo armazenado no objeto, preservando o encapsulamento.
    public String getModelo() { return modelo; }
    // Devolve o valor de Ano armazenado no objeto, preservando o encapsulamento.
    public int getAno() { return ano; }
    // Devolve o valor de Categoria armazenado no objeto, preservando o encapsulamento.
    public String getCategoria() { return categoria; }
    // Devolve o valor de Combustivel armazenado no objeto, preservando o encapsulamento.
    public String[] getCombustivel() { return combustivel; }
    // Devolve o valor de Cilindros armazenado no objeto, preservando o encapsulamento.
    public int getCilindros() { return cilindros; }
    // Devolve o valor de Cilindrada armazenado no objeto, preservando o encapsulamento.
    public double getCilindrada() { return cilindrada; }
    // Devolve o valor de Transmissao armazenado no objeto, preservando o encapsulamento.
    public String getTransmissao() { return transmissao; }
    // Devolve o valor de Tracao armazenado no objeto, preservando o encapsulamento.
    public String getTracao() { return tracao; }
    // Devolve o valor de ConsumoCidade armazenado no objeto, preservando o encapsulamento.
    public double getConsumoCidade() { return consumoCidade; }
    // Devolve o valor de ConsumoEstrada armazenado no objeto, preservando o encapsulamento.
    public double getConsumoEstrada() { return consumoEstrada; }
    // Devolve o valor de Co2 armazenado no objeto, preservando o encapsulamento.
    public double getCo2() { return co2; }
    // Devolve o valor de Turbo armazenado no objeto, preservando o encapsulamento.
    public boolean getTurbo() { return turbo; }
    // Devolve o valor de DataRegistro armazenado no objeto, preservando o encapsulamento.
    public Data getDataRegistro() { return dataRegistro; }

    // Separa os quinze campos de uma linha do CSV e constrói o veículo correspondente.
    public static Veiculo parseVeiculo(String s) {
        String[] c = Util.separar(s, ',');
        return new Veiculo(
            Util.inteiro(c[0]), c[1], c[2], Util.inteiro(c[3]), c[4],
            Util.separar(c[5], ';'), Util.inteiro(c[6]), Util.real(c[7]),
            c[8], c[9], Util.real(c[10]), Util.real(c[11]), Util.real(c[12]),
            Util.igual(c[13], "true"), Data.parseData(c[14])
        );
    }

    // Monta todos os campos do veículo, inclusive os combustíveis, no formato exato da saída.
    public String format() {
        String listaCombustiveis = "[";
        for (int i = 0; i < combustivel.length; i++) {
            if (i > 0) listaCombustiveis += ",";
            listaCombustiveis += combustivel[i];
        }
        listaCombustiveis += "]";
        return "[" + Util.inteiroParaString(id) + " ## " + marca + " ## " + modelo + " ## " +
            Util.inteiroParaString(ano) + " ## " + categoria + " ## " + listaCombustiveis + " ## " +
            Util.inteiroParaString(cilindros) + " ## " + Util.decimal(cilindrada, 1) + " ## " +
            transmissao + " ## " + tracao + " ## " + Util.decimal(consumoCidade, 2) + " ## " +
            Util.decimal(consumoEstrada, 2) + " ## " + Util.decimal(co2, 1) + " ## " +
            (turbo ? "true" : "false") + " ## " + dataRegistro.format() + "]";
    }
}

class LeitorCsv {
    // Abre o CSV com Scanner, ignora o cabeçalho, cria os veículos e devolve um vetor do tamanho exato.
    public static Veiculo[] ler(String caminhoArquivo) throws Exception {
        Scanner arquivo;
        try {
            arquivo = new Scanner(new java.io.File(caminhoArquivo));
        } catch (Exception erro) {
            try {
                arquivo = new Scanner(new java.io.File("../veiculos.csv"));
            } catch (Exception outroErro) {
                arquivo = new Scanner(new java.io.File("veiculos.csv"));
            }
        }
        Veiculo[] temporario = new Veiculo[1000];
        int n = 0;
        if (arquivo.hasNextLine()) arquivo.nextLine();
        while (arquivo.hasNextLine()) temporario[n++] = Veiculo.parseVeiculo(arquivo.nextLine());
        arquivo.close();
        Veiculo[] resposta = new Veiculo[n];
        for (int i = 0; i < n; i++) resposta[i] = temporario[i];
        return resposta;
    }

    // Percorre o vetor sequencialmente e retorna o veículo com o identificador procurado.
    public static Veiculo buscarPorId(Veiculo[] base, int id) {
        for (int i = 0; i < base.length; i++) if (base[i].getId() == id) return base[i];
        return null;
    }
}

public class TP02Q07 {
    // Distribui os veículos em dez baldes pela cilindrada, ordena cada balde por inserção e imprime o resultado.
    public static void main(String[] args) throws Exception {
        Veiculo[] base = LeitorCsv.ler("/tmp/veiculos.csv");
        Veiculo[][] baldes = new Veiculo[10][1000];
        int[] tamanhos = new int[10];
        Scanner entrada = new Scanner(System.in);
        while (entrada.hasNextLine()) {
            int id = Util.inteiro(entrada.nextLine());
            if (id == -1) break;
            Veiculo v = LeitorCsv.buscarPorId(base, id);
            int indice = (int)((v.getCilindrada() / 8.1) * 10.0);
            if (indice < 0) indice = 0;
            if (indice > 9) indice = 9;
            baldes[indice][tamanhos[indice]++] = v;
        }
        for (int b = 0; b < 10; b++) {
            for (int i = 1; i < tamanhos[b]; i++) {
                Veiculo atual = baldes[b][i];
                int j = i - 1;
                while (j >= 0 && baldes[b][j].getCilindrada() > atual.getCilindrada()) {
                    baldes[b][j + 1] = baldes[b][j];
                    j--;
                }
                baldes[b][j + 1] = atual;
            }
        }
        for (int b = 0; b < 10; b++)
            for (int i = 0; i < tamanhos[b]; i++)
                System.out.println(baldes[b][i].format());
        entrada.close();
    }
}


