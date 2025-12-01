import java.io.*;
import java.util.*;

/*
 * Solução para: beecrowd 3163 - Controlador de Vôo
 *
 * Regras de enfileiramento (interpretadas a partir do enunciado):
 * - Há quatro pontos cardeais de onde aviões chegam: leste (-4), norte (-3), sul (-2), oeste (-1).
 * - O procedimento de dequeuing (ordem de retirada para a pista) segue um ciclo com prioridade:
 *     1) Oeste (mais prioritário)
 *     2) Norte
 *     3) Sul
 *     4) Leste
 *   Ou seja, repetidamente tenta-se retirar 1 avião de Oeste, depois 1 de Norte, 1 de Sul, 1 de Leste,
 *   pulando os pontos vazios, até que não haja mais aeronaves.
 *
 * Entrada: sequência de tokens (pode conter quebras de linha arbitrárias):
 *   - Um inteiro P indicando o ponto cardeal atual (-4..-1), ou 0 indicando fim da sessão.
 *   - Seguem-se identificadores de aeronaves como "A123" que pertencem ao ponto atual.
 *   - A qualquer momento pode aparecer outro P indicando mudança de ponto.
 *
 * Saída: os identificadores enfileirados seguindo o protocolo, separados por espaço em uma única linha.
 *
 * Implementação: leitura robusta de todos os tokens da entrada, armazenamento em quatro filas
 * (listas com ponteiro de cabeça para pop eficiente) e geração do resultado seguindo o ciclo descrito.
 */
public class voo {

    // Maps dos pontos para índices das filas: 0 = oeste, 1 = norte, 2 = sul, 3 = leste
    private static final int WEST = -1;
    private static final int NORTH = -3;
    private static final int SOUTH = -2;
    private static final int EAST = -4;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Lê toda a entrada como tokens (separados por espaços/linhas em branco)
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(' ');
        }
        br.close();

        String all = sb.toString().trim();
        if (all.isEmpty()) {
            // Sem entrada
            return;
        }

        String[] tokens = all.split("\\s+");

        // Quatro filas para receber aeronaves (mantemos listas e índices de cabeça)
        ArrayList<String>[] queues = new ArrayList[4];
        int[] head = new int[4];
        for (int i = 0; i < 4; i++) {
            queues[i] = new ArrayList<>();
            head[i] = 0;
        }

        // mapeia ponto para índice: oeste=0, norte=1, sul=2, leste=3
        int currentPoint = 0; // default (não usado até ver primeiro ponto)
        boolean hasPoint = false;

        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if (t.length() == 0) continue;

            // Se token é inteiro (ponto) — pode começar com '-'
            if (isIntegerToken(t)) {
                int val = Integer.parseInt(t);
                if (val == 0) {
                    // marca término; ignoramos tokens após 0 (segundo 0 finaliza)
                    // break do loop para processar filas
                    // mas pode haver tokens depois de 0 (por precaução, paramos)
                    // armazenamos i e truncamos
                    String[] rest = Arrays.copyOfRange(tokens, 0, i);
                    tokens = rest;
                    break;
                }
                // identificador do ponto atual
                currentPoint = val;
                hasPoint = true;
            } else {
                // token é identificador de avião, deve pertencer ao ponto atual
                if (!hasPoint) {
                    // Entrada mal formatada: opção defensiva, ignore
                    continue;
                }

                int idx = pointToIndex(currentPoint);
                if (idx >= 0) {
                    queues[idx].add(t);
                }
            }
        }

        // Resultado: percorre em ciclo de prioridades: oeste(0), norte(1), sul(2), leste(3)
        StringBuilder out = new StringBuilder();
        boolean any = true;
        while (any) {
            any = false;
            for (int side = 0; side < 4; side++) {
                if (head[side] < queues[side].size()) {
                    if (out.length() > 0) out.append(' ');
                    out.append(queues[side].get(head[side]));
                    head[side]++;
                    any = true;
                }
            }
        }

        // Imprime a linha final (pode estar vazia)
        System.out.println(out.toString());
    }

    // Verifica se um token representa um inteiro (começa com '-' ou digito)
    private static boolean isIntegerToken(String s) {
        if (s.length() == 0) return false;
        char c = s.charAt(0);
        return c == '-' || (c >= '0' && c <= '9');
    }

    // Mapeia valor do ponto para índice da fila (oeste=0, norte=1, sul=2, leste=3)
    private static int pointToIndex(int p) {
        switch (p) {
            case WEST: return 0;
            case NORTH: return 1;
            case SOUTH: return 2;
            case EAST: return 3;
            default: return -1;
        }
    }
}
