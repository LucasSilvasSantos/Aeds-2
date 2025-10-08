import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

/**
 * Uso:
 *   javac Main.java
 *   java Main games.csv
 *
 * Arquivo de exemplo: games.csv com cabeçalho:
 * AppID,Name,Release date,Estimated owners,Price,Supported languages,Metacritic score,User score,Achievements,Publishers,Developers,Categories,Genres,Tags
 */
public class exercicio1 {
    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "/tmp/games.csv";
        List<Game> games = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // ler cabeçalho (registro completo pode ocupar várias linhas)
            String headerRecord = CsvParser.readNextRecord(br);
            if (headerRecord == null) {
                System.out.println("Arquivo vazio ou não encontrado.");
                return;
            }
            List<String> headerFields = CsvParser.parseLine(headerRecord);
            int expectedCols = headerFields.size();

            // ler registros
            String record;
            while ((record = CsvParser.readNextRecord(br)) != null) {
                List<String> fields = CsvParser.parseLine(record);
                if (fields.size() < expectedCols) {
                    // Aviso: linha com número inesperado de colunas (pule/ignore)
                    System.err.println("Linha com colunas inesperadas (pulando): " + record.substring(0, Math.min(80, record.length())) + "...");
                    continue;
                }
                Game g = Game.fromCsvFields(fields);
                games.add(g);
            }

            System.out.println("Jogos carregados: " + games.size());
            // imprime os primeiros 5 para inspeção
            for (int i = 0; i < Math.min(5, games.size()); i++) {
                System.out.println(games.get(i));
                System.out.println("--------------------------------------------------");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Parser CSV simples, mas que trata aspas duplas e vírgulas internas e quebras de linha em campos. */
    public static class CsvParser {
        /** Lê o próximo "registro" do CSV. Se uma linha contiver aspas desequilibradas, concatena linhas até equilibrar. */
        public static String readNextRecord(BufferedReader br) throws IOException {
            String line = br.readLine();
            if (line == null) return null;
            String record = line;
            while (!quotesBalanced(record)) {
                String nxt = br.readLine();
                if (nxt == null) break;
                record += "\n" + nxt;
            }
            return record;
        }

        private static boolean quotesBalanced(String s) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) if (s.charAt(i) == '"') count++;
            return count % 2 == 0;
        }

        /** Converte uma linha (registro) em lista de campos, respeitando aspas e escapes "" */
        public static List<String> parseLine(String line) {
            List<String> out = new ArrayList<>();
            if (line == null) return out;
            StringBuilder cur = new StringBuilder();
            boolean inQuotes = false;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == '"') {
                    // se próximo também é '"', é um escape e colocamos um " no conteúdo
                    if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        cur.append('"');
                        i++; // pula próximo
                    } else {
                        inQuotes = !inQuotes; // abre/fecha aspa
                    }
                } else if (c == ',' && !inQuotes) {
                    out.add(cur.toString());
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
            }
            out.add(cur.toString());
            return out;
        }
    }

    /** Classe que modela um jogo e tem lógica de normalização a partir dos campos CSV. */
    public static class Game {
        public int id;
        public String name;
        public String releaseDate; // dd/MM/yyyy
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

        /** Cria um Game a partir da lista de colunas do CSV (ordem do enunciado). */
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

        private static String get(List<String> f, int idx) {
            if (idx >= f.size()) return "";
            return f.get(idx) == null ? "" : f.get(idx).trim();
        }

        private static String unquote(String s) {
            if (s == null) return "";
            s = s.trim();
            if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
                s = s.substring(1, s.length()-1);
            }
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
            s = s.replaceAll("[^0-9,.-]", "");
            s = s.replace(',', '.');
            try { return Float.parseFloat(s); } catch (Exception e) { return -1.0f; }
        }

        private static float parsePrice(String s) {
            s = unquote(s).trim();
            if (s.isEmpty()) return 0.0f;
            if (s.equalsIgnoreCase("Free to Play")) return 0.0f;
            // remove símbolos e caracteres indesejados
            String cleaned = s.replaceAll("[^0-9,.-]", "");
            cleaned = cleaned.replace(',', '.');
            if (cleaned.isEmpty()) return 0.0f;
            try { return Float.parseFloat(cleaned); } catch (Exception e) { return 0.0f; }
        }

        /**
         * Converte campos que representam listas:
         * - s pode ser "['English','French']" ou "English, French" ou "Item1,Item2".
         * - preserveSingleQuotes: se true, o parser respeita conteúdo entre aspas simples ao juntar itens.
         */
        private static List<String> parseList(String s, boolean preserveSingleQuotes) {
            s = unquote(s).trim();
            List<String> out = new ArrayList<>();
            if (s.isEmpty()) return out;
            // retira colchetes exteriores [ ... ] se existirem
            if (s.startsWith("[") && s.endsWith("]") && s.length() >= 2) {
                s = s.substring(1, s.length()-1);
            }
            // separar por vírgula MAS respeitar conteúdo entre aspas simples (ex: 'English, US')
            StringBuilder cur = new StringBuilder();
            boolean inSingle = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\'') {
                    inSingle = !inSingle;
                    continue; // removemos as aspas simples do resultado
                }
                if (c == ',' && !inSingle) {
                    String item = cur.toString().trim();
                    item = stripQuotes(item);
                    if (!item.isEmpty()) out.add(item);
                    cur.setLength(0);
                } else {
                    cur.append(c);
                }
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

        /** Tenta normalizar datas para dd/MM/yyyy. Se faltarem dia/mês usa 01 para o dia/mês faltante. */
        private static String normalizeDate(String raw) {
            raw = unquote(raw).trim();
            if (raw.isEmpty()) return "";
            // tenta formatos comuns (inglês e números)
            String[] patterns = {
                "MMM d, uuuu", "MMMM d, uuuu", "MMM dd, uuuu", "MMMM dd, uuuu",
                "d MMM uuuu", "d MMM, uuuu", "uuuu-MM-dd", "MM/dd/uuuu", "dd/MM/uuuu", "uuuu"
            };
            for (String p : patterns) {
                try {
                    if (p.equals("uuuu")) {
                        // só ano
                        Matcher m = Pattern.compile("(\\d{4})").matcher(raw);
                        if (m.find()) {
                            int y = Integer.parseInt(m.group(1));
                            return String.format("01/01/%04d", y);
                        } else continue;
                    } else {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(p, Locale.ENGLISH);
                        LocalDate ld = LocalDate.parse(raw, fmt);
                        return ld.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"));
                    }
                } catch (Exception ex) {
                    // tenta próximo padrão
                }
            }

            // fallback: extrair ano, mês por nome e dia por regex; se faltar dia/mês usa 01
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

            Matcher my = Pattern.compile("(\\d{4})").matcher(raw);
            if (my.find()) year = Integer.parseInt(my.group(1));

            for (String k : meses.keySet()) {
                if (low.contains(k)) { month = meses.get(k); break; }
            }

            Matcher md = Pattern.compile("\\b(\\d{1,2})\\b").matcher(raw);
            while (md.find()) {
                int v = Integer.parseInt(md.group(1));
                if (v == year) continue; // ignora a captura do ano
                if (v >= 1 && v <= 31) { day = v; break; }
            }

            if (year == -1) {
                // se nem ano for encontrado, retorna string raw (ou "" se preferir)
                return raw;
            }
            return String.format("%02d/%02d/%04d", day, month, year);
        }

        @Override
        public String toString() {
            return "Game{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", estimatedOwners=" + estimatedOwners +
                    ", price=" + price +
                    ", supportedLanguages=" + supportedLanguages +
                    ", metacriticScore=" + metacriticScore +
                    ", userScore=" + userScore +
                    ", achievements=" + achievements +
                    ", publishers=" + publishers +
                    ", developers=" + developers +
                    ", categories=" + categories +
                    ", genres=" + genres +
                    ", tags(size)=" + tags.size() +
                    '}';
        }
    }
}
