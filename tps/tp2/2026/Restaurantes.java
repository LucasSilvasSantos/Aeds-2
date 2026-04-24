import java.util.Scanner;

public class Restaurantes {
    

    public static Data parseData (String s ){
     
   class Data {
     private int dia;
     private int mes;
     private int ano;

     public Data ( int dia, int mes,int ano){

      this.dia=dia;  // this é usado para referenciar a variável de instância da classe, enquanto dia é o parâmetro do construtor. Isso é necessário para evitar ambiguidades entre a variável de instância e o parâmetro com o mesmo nome.
      this.mes=mes;
      this.ano=ano;
     }
 public static Data parseData (String s ) {

  String[] separador  = s.split("-"); // O método split é usado para dividir a string em partes com base no delimitador "/". Isso resulta em um array de strings, onde cada elemento representa uma parte da data (dia, mês e ano).

int dia = Integer.parseInt(separador[0]); // converto a string para inteiro usando Integer.parseInt. O método parseInt é usado para converter a string que representa o dia em um valor inteiro, permitindo que seja armazenado na variável dia do tipo int.
int mes = Integer.parseInt(separador[1]); 
int ano = Integer.parseInt(separador[2]); 

 return new Data(dia,mes,ano);
 }

  }







    }















}
