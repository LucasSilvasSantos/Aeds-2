import java.io.*;
import java.nio.file.*;

public class privada {
    public static void main(String[] args) {
        String filename = "pri.out"; // mesmo nome do programa C
        Path p = Paths.get(filename);

        // tentativa de ler tudo na memória (igual ao malloc+fread do C)
        try {
            byte[] data = Files.readAllBytes(p);
            // escreve bytes brutos na saída (equivalente a printf("%s", buffer) em C,
            // mas sem decodificar; preserva bytes nulos e valores inválidos UTF-8)
            System.out.write(data);
            System.out.flush();
            return;
        } catch (OutOfMemoryError oom) {
            System.err.println("Arquivo muito grande para ler de uma vez. Fazendo fallback por streaming...");
        } catch (NoSuchFileException nsf) {
            System.err.println("Arquivo não encontrado: " + filename);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            System.exit(1);
        }

        // fallback por streaming (seguro para arquivos grandes)
        try (InputStream in = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] buf = new byte[8192];
            int r;
            OutputStream out = System.out;
            while ((r = in.read(buf)) != -1) {
                out.write(buf, 0, r);
            }
            out.flush();
        } catch (IOException e) {
            System.err.println("Erro ao ler por streaming: " + e.getMessage());
            System.exit(1);
        }
    }
}