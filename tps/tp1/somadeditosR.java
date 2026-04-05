import java.util.Scanner;
public class somadeditosR{
  public static int stringParaInt(String entrada) {
    int numero = 0;
    for (int i = 0; i < entrada.length(); i++) {
        char c = entrada.charAt(i);
        int digito = c - '0';       // converte caractere para número atraves da tabaela ASCII 
        numero = numero * 10 + digito; 
    }
    return numero;
}

  
  public static void main(String[] args) {
      Scanner sc =new Scanner (System.in);
      while (true) {
     String entrada =sc.nextLine();
       int n ,soma = 0;
       n = stringParaInt(entrada);
     while( n > 0) {
     soma+= n%10; // pegando o resto da divisão  e armazenando na variavel soma
     n/=10; // dividindo o numero por 10  com  objetivo de remover o ultimo digito
     }
        if (entrada.equals("FIM")) {
    break;
}        
       System.out.println(soma);
      }





      
  }





}