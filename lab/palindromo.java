import java.util.Scanner;

public class palindromo {
    public static boolean isPalindromo( String word ){
         for (int i =0; i< word.length()/2; i++){
                  if (word.charAt(i) != word.charAt(word.length()-1-i)){
                         return false;
                  }

                  else {
                        return true;
                  }



         }
    }
     public static void main(String[] args){
       Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        boolean res = isPalindromo(word);s
        if (res){
            System.out.println("SIM");
        }
        else {
            System.out.println("NÃO");
        }
           if(word.equals("FIM")){
               return;
           }





     }


}