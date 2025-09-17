import java.util.Scanner;
public class Espelho {
  
    public static void espelho(int primeiro, int ultimo){
    
     for (int i = primeiro; i <= ultimo; i ++){
       for ( int j =ultimo ; j>=primeiro;j--){
           System.out.print(j);
       }
     }
    }
 public static void main(String[]args){
    Scanner sc= new Scanner(System.in);
  while (sc.hasNext()){
        int primeiro=sc.nextInt();
        int ultimo =sc.nextInt();
        espelho(primeiro, ultimo);
  }
  
 }

}