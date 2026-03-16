import java.util.Scanner;

public class substring {

    public static int longestSubstring(String input) {
        int inicio = 0;       // início da janela atual
        int maxTamanho = 0;   // maior tamanho encontrado

        for (int fim = 0; fim < input.length(); fim++) {
            char atual = input.charAt(fim);

            // Verifica se 'atual' já existe na janela [inicio..fim-1]
            for (int j = inicio; j < fim; j++) {
                if (input.charAt(j) == atual) {
                    // Move o início para depois da repetição
                    inicio = j + 1;
                    break;
                }
            }

            // Tamanho da janela atual = fim - inicio + 1
            int tamanhoAtual = fim - inicio + 1;
            if (tamanhoAtual > maxTamanho) {
                maxTamanho = tamanhoAtual;
            }
        }

        return maxTamanho;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        System.out.println(longestSubstring(input));

        sc.close();
    }
}


/*### Passo a passo com `"abcabcbb"`
```
fim=0  atual='a'  janela="a"    tamanho=1  max=1
fim=1  atual='b'  janela="ab"   tamanho=2  max=2
fim=2  atual='c'  janela="abc"  tamanho=3  max=3
fim=3  atual='a'  'a' repetiu! inicio=1 → janela="bca"  tamanho=3  max=3
fim=4  atual='b'  'b' repetiu! inicio=2 → janela="cab"  tamanho=3  max=3
fim=5  atual='c'  'c' repetiu! inicio=3 → janela="abc"  tamanho=3  max=3
fim=6  atual='b'  'b' repetiu! inicio=4 → janela="cb"   tamanho=2  max=3
fim=7  atual='b'  'b' repetiu! inicio=7 → janela="b"    tamanho=1  max=3

Resultado: 3 ✓ */