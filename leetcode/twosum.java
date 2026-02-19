import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class twosum {
    public static int[] twoSumWithMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) { // pecorro o array de numeros 
            int complement = target - nums[i];  // eu subtraio o traget pelo numero do array que o for pecorre, ou seja se o numero do array for dois e ele tiver em primeiro o dois estará no indice 0
            if (map.containsKey(complement)) {      // se  o complemento existir no map, eu retorno o indice do complemento e o indice atual 
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i); //  se o complemento não existir eu coloco o numero do array e o indice dele no map 
        }
        return new int[] {};  
    }

    public static void main(String[] args) {
        int[] numbers = { 2, 7, 11, 15 };
        int target = 9;

        int[] resMap = twoSumWithMap(numbers, target);
        

        System.out.println("Índices dos números que somam o alvo: " + Arrays.toString(resMap));
    }
}
