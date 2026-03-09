import java.util.Scanner ;

public class espelho {
    public static void main (String [] args ) {
          Scanner sc = new Scanner(System.in);
          int entrada = sc.nextInt();
        for(int  x=0; x < entrada; x ++) {
          
          int start =  sc.nextInt();
          int finish = sc.nextInt();
          String sequency = "";
        for ( int k =  start ; k <=finish; k ++  ){
                sequency +=  k;
      }   
        System.out.print(sequency);

         for(int j =  sequency.length() - 1 ; j >=0; j -- ){
                System.out.print(sequency.charAt(j));
        }

        System.out.println();
    }
sc.close();
    }
}