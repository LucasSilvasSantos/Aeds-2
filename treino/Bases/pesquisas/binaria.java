import java.util.Scanner;

public class binaria {
    static boolean binarySearch(int[] arr, int target){
         int right = arr.length -1 ; // o right tende a começar no ultimo numero do array, ou seja o indice do ultimo numero do array
         int left = 0;
            while(left <= right){ // enquanto o left for menor ou igual ao right, ou seja, enquanto o left não ultrapassar o right
                int mid = left + (right - left) / 2; // o mid é o meio do array, ou seja, o indice do meio do array
                if(arr[mid] == target){ // se o numero do meio for igual ao target, eu retorno true
                    return true;
                } else if(arr[mid] < target){ // se o numero do meio for menor que o target, eu movo o left para a direita, ou seja, para o indice do meio + 1
                    left = mid + 1;
                } else { // se o numero do meio for maior que o target, eu movo o right para a esquerda, ou seja, para o indice do meio - 1
                    right = mid - 1;
                }
            }
            return false;
     }
public static void main(String[] args) {
      Scanner sc = new Scanner (System.in);
        System.out.println("Digite o tamanho do array: ");
        int length1 = sc.nextInt();
        int[] arr = new int [length1];// crio um array do tamanho que o usuario digitou
         System.err.println("Digite os numeros que voce quer colocar no array:");
           for(int i = 0; i < length1; i++){
            arr[i]= sc.nextInt();
           }
    System.out.println("Digite o numero que voce quer procurar no array: ");
          int target =sc.nextInt();
          boolean result = binarySearch(arr,target);
        if(result){
            System.out.println("O numero " + target + " foi encontrado no array.");
        } else {
            System.out.println("O numero " + target + " nao foi encontrado no array.");
        }



     }

}
