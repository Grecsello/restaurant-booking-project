public class Interno extends Tavolo {
    
    public Interno(int numtavola, int posto, double prezzo) {
        super('I',numtavola,posto);
        super.setCoperto();
        setPrezzo(prezzo);
    }

    public void setPrezzo(double prezzo){
        super.setPrezzo(prezzo);
     }
}