public class Esterno extends Tavolo {
    
    public Esterno(int numtavola, int posto, double prezzo){
        super('E',numtavola,posto);
        setPrezzo(prezzo);
    }

    public void setPrezzo(double prezzo){
       super.setPrezzo(prezzo);
    }
}