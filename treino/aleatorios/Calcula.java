
/* faça um programa para ler dois valores interios, e depois monstrar 
 na tela a soma desses numeros com uma mensagem explicativa conforme os exemplos  */

// exemplo entrada 10  e 20
// saida 30
 import java.util.Scanner;
public class Calcula {
    public static void main(String[] args ){
       Scanner sc = new Scanner(System.in);
         int number1 =sc.nextInt();
         int number2 = sc.nextInt();
        
         int sum = number1 + number2 ;

         System.out.println("A soma dos numeros é "+ sum);
         sc.close();
    }
}
















