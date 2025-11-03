


class Data {
public int dia ;
public int mes;
public int ano ;

public Data(){
dia = 0;
mes = 0;
ano = 0;




}
}


class Jogadores {
public  String nome ;
public  String foto ;
public int id;
public  Data Nascimento;
public int times [];
  
  public Jogadores(){
      nome = "";
      foto = "";
      Nascimento = new Data ();
      id =0;
      times = new int [0];

  }
  public void setnome (String nome ){
    this.nome= nome;

  }
    public void setfoto (String foto ){
     this.foto=foto;

    }
    public void setNascimento (Data Nascimento ){
     this.Nascimento = Nascimento;

    }
     public void setid (int id ){
     this.id=id;
    }
 public void settimes ( int times[]){
    this.times= times; 
 }



}
