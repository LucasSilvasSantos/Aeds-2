public class Is {
    public boolean ehvogal (char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
            c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
            return true;
        }
        return false;
    }
public boolean ehconsoante (char c){
   if(c>='a' && c <= 'z'  && !ehvogal(c) || c> 'A' && c <= 'Z' && !ehvogal(c)){
      return true;

   }

   return false;
}
   public boolean ehinteiro ( char c){
     if(c >= '0' && c <= '9'){
        return true;
     }


  return false;
   }
public boolean ehreal (char c){
   if(c == '.' || c == ','){
      return true;
   }
   return false;



}

    


