import java.io.*;

public class heapsort_v2 {
    
    private static final String MATRICULA = "891378";
    private static long comparacoes = 0;  
    private static long movimentos = 0;
    
    public static void main(String[] args) throws Exception {
        
        BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv"));
        br.readLine(); // pular header
        
        Game[] games = new Game[50000]; // Tamanho fixo grande
        int gamesCount = 0;
        
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] fields = parseCsvLine(linha);
            if (fields == null || fields.length < 14) continue;
            games[gamesCount++] = Game.fromCsvFields(fields);
        }
        br.close();
        
        
        Game[] uniqueGames = new Game[gamesCount];
        int uniqueCount = 0;
        for (int i = 0; i < gamesCount; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueGames[j].id == games[i].id) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueGames[uniqueCount++] = games[i];
            }
        }
        
        
        Game[] arrById = new Game[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            arrById[i] = uniqueGames[i];
        }
        sortById(arrById, uniqueCount);
        
        // Ler IDs da entrada
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        Game[] pesquisa = new Game[100];
        int pesquisaCount = 0;
        
        String idLine;
        while ((idLine = stdin.readLine()) != null) {
            idLine = trim(idLine);
            if (equalsStr(idLine, "FIM")) break;
            if (length(idLine) == 0) continue;
            
            try {
                int idBusca = parseIntSimple(idLine);
                int idx = binarySearchById(arrById, uniqueCount, idBusca);
                if (idx >= 0) {
                    pesquisa[pesquisaCount++] = arrById[idx];
                }
            } catch (Exception e) { }
        }
        stdin.close();
        
        
        long t0 = System.nanoTime();
        if (pesquisaCount > 1) heapSort(pesquisa, pesquisaCount);
        long t1 = System.nanoTime();
        long tempoNano = t1 - t0;
        
       
        for (int i = 0; i < pesquisaCount; i++) {
            if (pesquisa[i] != null) {
                MyIO.println(pesquisa[i].toString());
            }
        }
        
        // Gerar log
        PrintWriter pw = new PrintWriter(new FileWriter(MATRICULA + "_heapsort.txt"));
        pw.println(MATRICULA + "\t" + comparacoes + "\t" + movimentos + "\t" + tempoNano);
        pw.close();
    }
    
    
    private static void heapSort(Game[] a, int n) {
        if (n < 2) return;
        
        
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }
        
        
        for (int i = n - 1; i > 0; i--) {
            
            swap(a, 0, i);
            movimentos += 3;
            
            
            heapify(a, i, 0);
        }
    }
    
    private static void heapify(Game[] a, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        
        if (left < n) {
            comparacoes++;
            if (compareByOwners(a[left], a[largest]) > 0) {
                largest = left;
            }
        }
        
        
        if (right < n) {
            comparacoes++;
            if (compareByOwners(a[right], a[largest]) > 0) {
                largest = right;
            }
        }
        
       
        if (largest != i) {
            swap(a, i, largest);
            movimentos += 3;
            
           
            heapify(a, n, largest);
        }
    }
    
    private static void swap(Game[] a, int i, int j) {
        Game temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    private static int compareByOwners(Game x, Game y) {
        if (x.estimatedOwners < y.estimatedOwners) return -1;
        if (x.estimatedOwners > y.estimatedOwners) return 1;
        if (x.id < y.id) return -1;
        if (x.id > y.id) return 1;
        return 0;
    }
    
    
    private static void sortById(Game[] a, int n) {
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j].id < a[minIdx].id) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Game temp = a[i];
                a[i] = a[minIdx];
                a[minIdx] = temp;
            }
        }
    }
    
    
    private static int binarySearchById(Game[] a, int n, int id) {
        int l = 0, r = n - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m].id == id) return m;
            if (a[m].id < id) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }
    
   
    private static String[] parseCsvLine(String line) {
        if (line == null) return null;
        
        String[] result = new String[20];
        int resultCount = 0;
        
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (int i = 0; i < length(line); i++) {
            char c = charAt(line, i);
            
            if (c == '"') {
                if (inQuotes && i + 1 < length(line) && charAt(line, i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result[resultCount++] = current.toString();
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result[resultCount++] = current.toString();
        
        String[] finalResult = new String[resultCount];
        for (int i = 0; i < resultCount; i++) {
            finalResult[i] = result[i];
        }
        return finalResult;
    }
    
    
    static class Game {
        int id;
        String name;
        String releaseDate;
        int estimatedOwners;
        float price;
        String[] supportedLanguages;
        int metacriticScore;
        float userScore;
        int achievements;
        String[] publishers;
        String[] developers;
        String[] categories;
        String[] genres;
        String[] tags;
        
        static Game fromCsvFields(String[] f) {
            Game g = new Game();
            g.id = parseIntOrDefault(get(f, 0), 0);
            g.name = unquote(get(f, 1));
            g.releaseDate = normalizeDate(get(f, 2));
            g.estimatedOwners = parseIntOrDefault(cleanNumber(get(f, 3)), 0);
            g.price = parsePriceManual(get(f, 4));
            g.supportedLanguages = parseListManual(get(f, 5));
            g.metacriticScore = parseIntOrDefault(get(f, 6), -1);
            g.userScore = parseUserScoreManual(get(f, 7));
            g.achievements = parseIntOrDefault(get(f, 8), 0);
            g.publishers = parseListManual(get(f, 9));
            g.developers = parseListManual(get(f, 10));
            g.categories = parseListManual(get(f, 11));
            g.genres = parseListManual(get(f, 12));
            g.tags = parseListManual(get(f, 13));
            return g;
        }
        
        private static String get(String[] f, int idx) {
            if (idx >= f.length) return "";
            return f[idx] == null ? "" : trim(f[idx]);
        }
        
        private static String unquote(String s) {
            if (s == null) return "";
            s = trim(s);
            if (length(s) >= 2 && charAt(s, 0) == '"' && charAt(s, length(s) - 1) == '"') {
                s = substring(s, 1, length(s) - 1);
            }
            return s;
        }
        
        private static String cleanNumber(String s) {
            if (s == null) return "";
            s = unquote(s);
            s = trim(s);
            
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < length(s); i++) {
                char c = charAt(s, i);
                if (c >= '0' && c <= '9') {
                    result.append(c);
                }
            }
            return result.toString();
        }
        
        private static int parseIntOrDefault(String s, int def) {
            if (s == null) return def;
            s = trim(s);
            if (length(s) == 0) return def;
            
            
            StringBuilder clean = new StringBuilder();
            for (int i = 0; i < length(s); i++) {
                char c = charAt(s, i);
                if (c == '-' && i == 0) {
                    clean.append(c);
                } else if (c >= '0' && c <= '9') {
                    clean.append(c);
                }
            }
            
            String cleanStr = clean.toString();
            if (length(cleanStr) == 0) return def;
            
            try {
                return Integer.parseInt(cleanStr);
            } catch (Exception e) {
                return def;
            }
        }
        
        private static float parsePriceManual(String s) {
            s = unquote(s);
            s = trim(s);
            if (length(s) == 0) return 0.0f;
            
            String lower = toLowerCase(s);
            if (equalsStr(lower, "free to play")) return 0.0f;
            
            
            StringBuilder clean = new StringBuilder();
            for (int i = 0; i < length(s); i++) {
                char c = charAt(s, i);
                if ((c >= '0' && c <= '9') || c == '.' || c == ',') {
                    clean.append(c);
                }
            }
            
            String cleanStr = clean.toString();
            if (length(cleanStr) == 0) return 0.0f;
            
           
            StringBuilder finalStr = new StringBuilder();
            for (int i = 0; i < length(cleanStr); i++) {
                char c = charAt(cleanStr, i);
                if (c == ',') {
                    finalStr.append('.');
                } else {
                    finalStr.append(c);
                }
            }
            
            try {
                return Float.parseFloat(finalStr.toString());
            } catch (Exception e) {
                return 0.0f;
            }
        }
        
        private static float parseUserScoreManual(String s) {
            s = unquote(s);
            s = trim(s);
            s = toLowerCase(s);
            
            if (length(s) == 0 || equalsStr(s, "tbd")) return -1.0f;
            if (equalsStr(s, "0") || equalsStr(s, "0.0")) return 0.0f;
            
            
            StringBuilder clean = new StringBuilder();
            for (int i = 0; i < length(s); i++) {
                char c = charAt(s, i);
                if ((c >= '0' && c <= '9') || c == '.' || c == ',' || c == '-') {
                    clean.append(c);
                }
            }
            
            String cleanStr = clean.toString();
            if (length(cleanStr) == 0) return -1.0f;
            
            
            StringBuilder finalStr = new StringBuilder();
            for (int i = 0; i < length(cleanStr); i++) {
                char c = charAt(cleanStr, i);
                if (c == ',') {
                    finalStr.append('.');
                } else {
                    finalStr.append(c);
                }
            }
            
            try {
                return Float.parseFloat(finalStr.toString());
            } catch (Exception e) {
                return -1.0f;
            }
        }
        
        private static String[] parseListManual(String s) {
            s = unquote(s);
            s = trim(s);
            
            if (length(s) == 0) return new String[0];
            
            // Remover colchetes
            if (length(s) >= 2 && charAt(s, 0) == '[' && charAt(s, length(s) - 1) == ']') {
                s = substring(s, 1, length(s) - 1);
            }
            
            String[] temp = new String[100];
            int count = 0;
            
            StringBuilder current = new StringBuilder();
            boolean inSingleQuote = false;
            
            for (int i = 0; i < length(s); i++) {
                char c = charAt(s, i);
                
                if (c == '\'') {
                    inSingleQuote = !inSingleQuote;
                } else if (c == ',' && !inSingleQuote) {
                    String item = trim(current.toString());
                    item = stripQuotesManual(item);
                    if (length(item) > 0) {
                        temp[count++] = item;
                    }
                    current = new StringBuilder();
                } else {
                    current.append(c);
                }
            }
            
            String last = trim(current.toString());
            last = stripQuotesManual(last);
            if (length(last) > 0) {
                temp[count++] = last;
            }
            
            String[] result = new String[count];
            for (int i = 0; i < count; i++) {
                result[i] = temp[i];
            }
            return result;
        }
        
        private static String stripQuotesManual(String s) {
            if (s == null) return "";
            s = trim(s);
            
            if (length(s) >= 2 && charAt(s, 0) == '"' && charAt(s, length(s) - 1) == '"') {
                s = substring(s, 1, length(s) - 1);
            }
            if (length(s) >= 2 && charAt(s, 0) == '\'' && charAt(s, length(s) - 1) == '\'') {
                s = substring(s, 1, length(s) - 1);
            }
            return trim(s);
        }
        
        private static String normalizeDate(String raw) {
            raw = unquote(raw);
            raw = trim(raw);
            if (length(raw) == 0) return "";
            
            
            int year = -1;
            for (int i = 0; i <= length(raw) - 4; i++) {
                boolean isYear = true;
                for (int j = 0; j < 4; j++) {
                    if (charAt(raw, i + j) < '0' || charAt(raw, i + j) > '9') {
                        isYear = false;
                        break;
                    }
                }
                if (isYear) {
                    String yearStr = substring(raw, i, i + 4);
                    year = parseIntSimple(yearStr);
                    break;
                }
            }
            
            if (year == -1) return raw;
            
            
            return "01/01/" + year;
        }
        
        @Override
        public String toString() {
            return "=> " + id + " ## " +
                name + " ## " +
                releaseDate + " ## " +
                estimatedOwners + " ## " +
                price + " ## " +
                arrayToString(supportedLanguages) + " ## " +
                metacriticScore + " ## " +
                userScore + " ## " +
                achievements + " ## " +
                arrayToString(publishers) + " ## " +
                arrayToString(developers) + " ## " +
                arrayToString(categories) + " ## " +
                arrayToString(genres) + " ## " +
                arrayToString(tags) + " ##";
        }
        
        private static String arrayToString(String[] arr) {
            if (arr == null || arr.length == 0) return "[]";
            
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);
                if (i < arr.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }
    
    
    private static boolean equalsStr(String a, String b) {
        return a.equals(b);
    }
    
    private static char charAt(String s, int i) {
        return s.charAt(i);
    }
    
    private static int length(String s) {
        return s.length();
    }
    
    private static String substring(String s, int start, int end) {
        return s.substring(start, end);
    }
    
    private static String toLowerCase(String s) {
        return s.toLowerCase();
    }
    
    private static String toUpperCase(String s) {
        return s.toUpperCase();
    }
    
    private static int parseIntSimple(String s) {
        return Integer.parseInt(s);
    }
    
    private static float parseFloatSimple(String s) {
        return Float.parseFloat(s);
    }
    
    private static String trim(String s) {
        if (s == null) return "";
        int start = 0;
        int end = length(s);
        
        while (start < end && charAt(s, start) <= ' ') {
            start++;
        }
        while (start < end && charAt(s, end - 1) <= ' ') {
            end--;
        }
        
        return substring(s, start, end);
    }
}


class MyIO {
    public static void println(String s) {
        System.out.println(s);
    }
}
