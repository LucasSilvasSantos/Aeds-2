import java.util.Scanner;
public class validasenha {
    public  static boolean isValidPassword (String password){
         if(password.length() < 8){
             return false;
         }
          boolean hasUppercase = false; 
          boolean hasLowercase = false;
         boolean hasnumber = false;
         boolean hasSpecialChar = false;
   for( int i =0 ; i < password.length() ; i++){
       char c = password.charAt(i);
       if( c >= 'A' && c <= 'Z'){
           hasUppercase = true;
       } 
       else if (c >= 'a' && c <= 'z'){
           hasLowercase = true;
       } 
       else if (c >= '0' && c <= '9'){
           hasnumber = true;
       } 
       
       else  {
           hasSpecialChar = true;
       }
        

    }
   if(!hasUppercase || !hasLowercase || !hasnumber || !hasSpecialChar){  // verifico se minha senha é valida de acordo com todos os requisitos
       return false;

   }


     return true;
        }
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
          String password =sc.nextLine();
          if(isValidPassword(password)){
              System.out.println("SIM");
          } else {
              System.out.println("NAO");
          }
          sc.close();



    }
}
