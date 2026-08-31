import java.util.Scanner;
public class somadeditosR{
  public static int somaDigitos(int n) {
    if (n == 0) {
        return 0;
    }
    return n % 10 + somaDigitos(n / 10);
}

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
      while (sc.hasNextLine()) {
     String entrada =sc.nextLine();
        if (entrada.equals("FIM")) {
    break;
}
       int n ,soma;
       n = stringParaInt(entrada);
       soma = somaDigitos(n);
       System.out.println(soma);
      }

sc.close();



      
  }





}