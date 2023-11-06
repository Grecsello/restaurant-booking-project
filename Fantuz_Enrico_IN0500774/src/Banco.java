public class Banco extends Tavolo {

    public Banco(int capacita){
        super('B',capacita);
        super.setCoperto();
        setPrezzo();
    }

    public void setPrezzo(){
        super.setPrezzo(0);
     }
}