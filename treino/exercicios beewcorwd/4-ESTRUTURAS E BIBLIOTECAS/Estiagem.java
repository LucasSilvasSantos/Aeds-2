import java.util.Scanner;



public class Estiagem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       
       
        int entrada = sc.nextInt();
         while (( entrada=sc.nextInt()) != 0) {
          int [] pessoas = new int [201];
          long totalX = 0; Long totalY = 0;
          for( int i = 0; i < entrada; i++){
          int x = sc.nextInt();
          int y =sc.nextInt();
          int consumo = y/x;
           pessoas[consumo]++;
          totalX += x; 

          
          totalY += y;

        }
        sc.close();
    }
