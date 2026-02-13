// prorgrama conta quantas letras A ele tem em uma palavra digita pelo usuario 


import java.util.Scanner;


public class LetrasA {
    public static void main(String [ ] args ){
     Scanner sc = new Scanner(System.in);
         String word =sc.nextLine();
           int count = 0;
      for (int i =0 ; i < word.length(); i++){
      if(word.charAt(i)== 'a' || word.charAt(i)== 'A'){
       count ++ ;

      }
     System.out.println(" a letra A aparece " + count );       
     sc.close();
        
    }
}
}
