import java.io.*;      
import java.util.*;    

public class mergesort {

    // [0] Constantes e contadores
    // - MATRICULA: identificação para o arquivo de log
    // - comparações / movements: contadores usados para relatório do algoritmo
    private static final String MATRICULA = "891378";
    private static long comparações = 0;  
    private static long movements = 0;     

    // [1] main()
    // - fluxo principal: lê CSV, cria objetos Game, remove duplicatas,
    //   prepara array ordenado por ID, lê IDs da entrada, monta vetor de pesquisa,
    //   ordena por preço (mergeSort), imprime top5 (caros/baratos) e escreve log.
    public static void main(String[] args) throws Exception {
        // [1.1] Leitura do CSV
        // - Abre /tmp/games.csv com BufferedReader
        // - Para cada linha: CsvParser.parseLine -> Game.fromCsvFields -> adiciona em lista
        BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"));
        String header = br.readLine(); 
        List<Game> lista = new ArrayList<>();
        String linha;
        while ((linha = br.readLine()) != null) {
            List<String> fields = CsvParser.parseLine(linha);
            if (fields.size() < 14) continue;
            lista.add(Game.fromCsvFields(fields));
        }
        br.close();

        // [1.2] Detecção rápida de duplicatas (por id)
        // - conta ocorrências com HashMap para DEBUG (imprime em System.err)
        Map<Integer,Integer> counts = new HashMap<>();
        for (Game g : lista) counts.put(g.id, counts.getOrDefault(g.id, 0) + 1);
        for (Map.Entry<Integer,Integer> e : counts.entrySet()) {
            if (e.getValue() > 1) System.err.println("Duplicado AppID=" + e.getKey() + " vezes=" + e.getValue());
        }

        // [1.3] Remover duplicatas mantendo primeira ocorrência
        // - LinkedHashMap preserva ordem de inserção; transforma em array arr
        Map<Integer, Game> uniq = new LinkedHashMap<>();
        for (Game g : lista) {
            if (!uniq.containsKey(g.id)) uniq.put(g.id, g);
        }
        Game[] arr = uniq.values().toArray(new Game[0]);

        // [1.4] Preparar array ordenado por ID (para busca binária)
        // - copia arr para arrById e usa Arrays.sort com Comparator por id
        Game[] arrById = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrById, new Comparator<Game>() {
            @Override public int compare(Game a, Game b) { return Integer.compare(a.id, b.id); }
        });

        // [1.5] Ler IDs da entrada padrão e montar vetor de pesquisa
        // - usa Scanner(System.in) para ler linhas até "FIM"
        // - converte cada linha em int e busca em arrById via binarySearchById
        Scanner sc = new Scanner(System.in);
        Game pesquisaTemp[] = new Game[100]; // OBS: tamanho fixo — considerar ArrayList
        int pesquisaAux = 0;
        while (sc.hasNextLine()) {
            String buscaId = sc.nextLine().trim();
            if (buscaId.equals("FIM")) break;
            if (buscaId.isEmpty()) continue;
            try {
                int idBusca = Integer.parseInt(buscaId);
                int idx = binarySearchById(arrById, idBusca);
                if (idx >= 0) pesquisaTemp[pesquisaAux++] = arrById[idx];
            } catch (NumberFormatException e) { /* ignora */ }
        }
        sc.close();

        // [1.6] Copiar pesquisados e ordenar por preço (mergeSort)
        // - cria array pesquisa sem nulls; mede tempo com System.nanoTime()
        Game[] pesquisa = Arrays.copyOf(pesquisaTemp, pesquisaAux);
        long t0 = System.nanoTime();
        if (pesquisa.length > 1) mergeSort(pesquisa); // chamada para ordenar por price,id
        long t1 = System.nanoTime();
        long tempoNano = t1 - t0;

        // [1.7] Impressão: 5 preços mais caros
        // - varre do fim para o começo (array ordenado asc por preço)
        // - pula IDs já impressos (usedIds) para evitar duplicatas
        System.out.println("| 5 preços mais caros |");
        Set<Integer> usedIds = new HashSet<>();
        int printed = 0;
        for (int i = pesquisa.length - 1; i >= 0 && printed < 5; i--) {
            Game g = pesquisa[i];
            if (g == null) continue;
            if (usedIds.contains(g.id)) continue;
            System.out.println(g.toString());
            usedIds.add(g.id);
            printed++;
        }
        System.out.println();

        // [1.8] Impressão: 5 preços mais baratos
        // - varre do começo para o fim, mesma lógica de pular IDs duplicados
        System.out.println("| 5 preços mais baratos |");
        usedIds.clear();
        printed = 0;
        for (int i = 0; i < pesquisa.length && printed < 5; i++) {
            Game g = pesquisa[i];
            if (g == null) continue;
            if (usedIds.contains(g.id)) continue;
            System.out.println(g.toString());
            usedIds.add(g.id);
            printed++;
        }

        // [1.9] Geração do arquivo de log
        // - escreve MATRICULA, comparações, movements, tempoNano em arquivo
        String logName = MATRICULA + "_mergesort.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(logName))) {
            pw.printf("%s %d %d %d\n", MATRICULA, comparações, movements, tempoNano);
        }
    }

    // [2] mergeSort (entrada)
    // - cria array auxiliar e chama mergesort recursivo
    private static void mergeSort(Game[] a) {
        if (a == null || a.length < 2) return;
        Game[] aux = new Game[a.length];
        mergesort(a, aux, 0, a.length - 1);
    }

    // [3] mergesort (recursivo)
    // - divide recursivamente o array em metades
    private static void mergesort(Game[] a, Game[] aux, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergesort(a, aux, left, mid);
        mergesort(a, aux, mid + 1, right);
        merge(a, aux, left, mid, right);
    }

    // [4] merge (mesclagem)
    // - copia intervalo para aux e mescla comparando com compareAux
    // - atualiza contadores (comparações / movements)
    private static void merge(Game[] a, Game[] aux, int left, int mid, int right) {
        for (int k = left; k <= right; k++) {
            aux[k] = a[k];
        }
        int i = left;
        int j = mid + 1;
        int k = left;
        while (i <= mid && j <= right) {
            comparisons++;                  // contador de comparações (atenção: nome da variável)
            if (compareAux(aux[i], aux[j]) <= 0) {
                a[k++] = aux[i++];
                movements++;
            } else {
                a[k++] = aux[j++];
                movements++;
            }
        }
        while (i <= mid) { a[k++] = aux[i++]; movements++; }
        while (j <= right) { a[k++] = aux[j++]; movements++; }
    }

    // [5] compareAux
    // - ordena por price (float) asc; em empate desempata por id asc
    // - usar este comparator + imprimir do fim p/ começo resulta em price desc, id desc
    private static int compareAux(Game x, Game y) {
        if (Float.compare(x.price, y.price) < 0) return -1;
        if (Float.compare(x.price, y.price) > 0) return 1;
        if (x.id < y.id) return -1;
        if (x.id > y.id) return 1;
        return 0;
    }

    // [6] CsvParser.parseLine
    // - parser CSV que trata aspas duplas e vírgulas dentro de campos
    // - retorna List<String> com os campos da linha
    public static class CsvParser {
        public static List<String> parseLine(String line) {
            List<String> out = new ArrayList<>();
            if (line == null) return out;
            StringBuilder cur = new StringBuilder();
            boolean inQuotes = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') {
                    if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"'); i++;
                    } else { inQuotes = !inQuotes; }
                } else if (c == ',' && !inQuotes) {
                    out.add(cur.toString()); cur.setLength(0);
                } else { cur.append(c); }
            }
            out.add(cur.toString());
            return out;
        }
    }

    // [7] Classe Game e helpers
    // - modelo que representa os campos do CSV
    // - métodos estáticos auxiliares (fromCsvFields, parsePrice, normalizeDate, etc.)
    public static class Game {
        public int id;
        public String name;
        public String releaseDate;
        public int estimatedOwners;
        public float price;
        public List<String> supportedLanguages = new ArrayList<>();
        public int metacriticScore;
        public float userScore;
        public int achievements;
        public List<String> publishers = new ArrayList<>();
        public List<String> developers = new ArrayList<>();
        public List<String> categories = new ArrayList<>();
        public List<String> genres = new ArrayList<>();
        public List<String> tags = new ArrayList<>();

        // fromCsvFields: instancia Game a partir de uma lista de campos CSV
        public static Game fromCsvFields(List<String> f) {
            Game g = new Game();
            g.id = parseIntDefault(get(f,0), 0);
            g.name = unquote(get(f,1));
            g.releaseDate = normalizeDate(get(f,2));
            g.estimatedOwners = parseIntDefault(cleanNumber(get(f,3)), 0);
            g.price = parsePrice(get(f,4));
            g.supportedLanguages = parseList(get(f,5), true);
            g.metacriticScore = parseIntOrDefault(get(f,6), -1);
            g.userScore = parseUserScore(get(f,7));
            g.achievements = parseIntOrDefault(get(f,8), 0);
            g.publishers = parseList(get(f,9), false);
            g.developers = parseList(get(f,10), false);
            g.categories = parseList(get(f,11), false);
            g.genres = parseList(get(f,12), false);
            g.tags = parseList(get(f,13), false);
            return g;
        }

        // --- helpers de parsing e normalização (resumido) ---
        private static String get(List<String> f, int idx) {
            if (idx >= f.size()) return "";
            return f.get(idx) == null ? "" : f.get(idx).trim();
        }
        private static String unquote(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length()-1);
            return s;
        }
        private static String cleanNumber(String s) {
            if (s == null) return "";
            s = unquote(s).trim();
            return s.replaceAll("[^0-9]", "");
        }
        private static int parseIntDefault(String s, int def) {
            if (s == null) return def;
            s = s.trim();
            if (s.isEmpty()) return def;
            try { return Integer.parseInt(s); } catch (Exception e) { return def; }
        }
        private static int parseIntOrDefault(String s, int def) {
            s = unquote(s).trim();
            if (s.isEmpty()) return def;
            s = s.replaceAll("[^0-9-]", "");
            if (s.isEmpty()) return def;
            try { return Integer.parseInt(s); } catch (Exception e) { return def; }
        }
        private static float parseUserScore(String s) {
            s = unquote(s).trim().toLowerCase();
            if (s.isEmpty() || s.equals("tbd")) return -1.0f;
            if (s.equals("0") || s.equals("0.0")) return 0.0f;
            s = s.replaceAll("[^0-9,.-]", "");
            s = s.replace(',', '.');
            if (s.isEmpty()) return -1.0f;
            try { return Float.parseFloat(s); } catch (Exception e) { return -1.0f; }
        }
        private static float parsePrice(String s) {
            s = unquote(s).trim();
            if (s.isEmpty()) return 0.0f;
            if (s.equalsIgnoreCase("Free to Play")) return 0.0f;
            String cleaned = s.replaceAll("[^0-9,.-]", "");
            cleaned = cleaned.replace(',', '.');
            if (cleaned.isEmpty()) return 0.0f;
            try { return Float.parseFloat(cleaned); } catch (Exception e) { return 0.0f; }
        }
        private static List<String> parseList(String s, boolean preserveSingleQuotes) {
            s = unquote(s).trim();
            List<String> out = new ArrayList<>();
            if (s.isEmpty()) return out;
            if (s.startsWith("[") && s.endsWith("]") && s.length() >= 2) s = s.substring(1, s.length()-1);
            StringBuilder cur = new StringBuilder();
            boolean inSingle = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') { inSingle = !inSingle; continue; }
                if (c == ',' && !inSingle) {
                    String item = cur.toString().trim();
                    item = stripQuotes(item);
                    if (!item.isEmpty()) out.add(item);
                    cur.setLength(0);
                } else { cur.append(c); }
            }
            String last = cur.toString().trim();
            last = stripQuotes(last);
            if (!last.isEmpty()) out.add(last);
            return out;
        }
        private static String stripQuotes(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) s = s.substring(1, s.length()-1);
            if (s.length() >= 2 && s.startsWith("'") && s.endsWith("'")) s = s.substring(1, s.length()-1);
            return s.trim();
        }

        // normalizeDate: tenta vários formatos e faz fallback por regex
        private static String normalizeDate(String raw) {
            raw = unquote(raw).trim();
            if (raw.isEmpty()) return "";
            String[] patterns = {
                "MMM d, uuuu", "MMMM d, uuuu", "MMM dd, uuuu", "MMMM dd, uuuu",
                "d MMM uuuu", "d MMM, uuuu", "uuuu-MM-dd", "MM/dd/uuuu", "dd/MM/uuuu", "uuuu"
            };
            for (String p : patterns) {
                try {
                    if (p.equals("uuuu")) {
                        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4})").matcher(raw);
                        if (m.find()) {
                            int y = Integer.parseInt(m.group(1));
                            return String.format("01/01/%04d", y);
                        } else continue;
                    } else {
                        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern(p, Locale.ENGLISH);
                        java.time.LocalDate ld = java.time.LocalDate.parse(raw, fmt);
                        return ld.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                    }
                } catch (Exception ex) {}
            }
            Map<String,Integer> meses = new HashMap<>();
            meses.put("jan",1); meses.put("january",1);
            meses.put("feb",2); meses.put("february",2);
            meses.put("mar",3); meses.put("march",3);
            meses.put("apr",4); meses.put("april",4);
            meses.put("may",5);
            meses.put("jun",6); meses.put("june",6);
            meses.put("jul",7); meses.put("july",7);
            meses.put("aug",8); meses.put("august",8);
            meses.put("sep",9); meses.put("sept",9); meses.put("september",9);
            meses.put("oct",10); meses.put("october",10);
            meses.put("nov",11); meses.put("november",11);
            meses.put("dec",12); meses.put("december",12);
            String low = raw.toLowerCase();
            int year = -1, month = 1, day = 1;
            java.util.regex.Matcher my = java.util.regex.Pattern.compile("(\\d{4})").matcher(raw);
            if (my.find()) year = Integer.parseInt(my.group(1));
            for (String k : meses.keySet()) {
                if (low.contains(k)) { month = meses.get(k); break; }
            }
            java.util.regex.Matcher md = java.util.regex.Pattern.compile("\\b(\\d{1,2})\\b").matcher(raw);
            while (md.find()) {
                int v = Integer.parseInt(md.group(1));
                if (v == year) continue;
                if (v >= 1 && v <= 31) { day = v; break; }
            }
            if (year == -1) return raw;
            return String.format("%02d/%02d/%04d", day, month, year);
        }

        @Override
        public String toString() { return mergesort.gameToString(this); }
    }

    // [8] gameToString
    // - monta a string de saída no formato requerido
    public static String gameToString(Game g) {
        return "=> " + g.id + " ## " +
            g.name + " ## " +
            g.releaseDate + " ## " +
            g.estimatedOwners + " ## " +
            g.price + " ## " +
            auxiliarMostrar(g.supportedLanguages) + " ## " +
            g.metacriticScore + " ## " +
            g.userScore + " ## " +
            g.achievements + " ## " +
            auxiliarMostrar(g.publishers) + " ## " +
            auxiliarMostrar(g.developers) + " ## " +
            auxiliarMostrar(g.categories) + " ## " +
            auxiliarMostrar(g.genres) + " ## " +
            auxiliarMostrar(g.tags) + " ##";
    }

    // [9] auxiliarMostrar
    // - formata listas como "[a, b, c]"
    private static String auxiliarMostrar(List<String> array) {
        String result = "[";
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                result += array.get(i);
                if (i < array.size() - 1) result += ", ";
            }
        }
        result += "]";
        return result;
    }

    // [10] binarySearchById
    // - busca binária em arr ordenado por id
    private static int buscabinordid(Game[] a, int id) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = (l + r) >>> 1;
            if (a[m].id == id) return m;
            if (a[m].id < id) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }
}




