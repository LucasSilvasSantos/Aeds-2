import java.util.Scanner;

public class bagukan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int R = sc.nextInt();  // número de rodadas
            if (R == 0) break;     // fim da entrada

            int pontosMark = 0, pontosLeti = 0;
            int ultimoMark = -1, ultimoLeti = -1;
            int streakMark = 0, streakLeti = 0;
            boolean bonusMark = false, bonusLeti = false;

            for (int i = 0; i < R; i++) {
                int mark = sc.nextInt();
                int leti = sc.nextInt();

                // Soma pontos normais
                pontosMark += mark;
                pontosLeti += leti;

                // Verifica sequências consecutivas
                if (mark == ultimoMark) {
                    streakMark++;
                } else {
                    streakMark = 1;
                }

                if (leti == ultimoLeti) {
                    streakLeti++;
                } else {
                    streakLeti = 1;
                }

                // Aplica bônus de 30 pontos se fizer 3 iguais seguidos
                if (streakMark == 3 && !bonusMark && streakLeti < 3) {
                    pontosMark += 30;
                    bonusMark = true;
                } else if (streakLeti == 3 && !bonusLeti && streakMark < 3) {
                    pontosLeti += 30;
                    bonusLeti = true;
                }
                // Se os dois conseguem na mesma rodada → ninguém ganha
                else if (streakMark == 3 && streakLeti == 3 && !bonusMark && !bonusLeti) {
                    bonusMark = true;
                    bonusLeti = true;
                }

                ultimoMark = mark;
                ultimoLeti = leti;
            }

            // Decide vencedor
            if (pontosMark > pontosLeti) {
                System.out.println("M");
            } else if (pontosLeti > pontosMark) {
                System.out.println("L");
            } else {
                System.out.println("T");
            }
        }
    }
}
