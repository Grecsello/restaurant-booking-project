
public class Tavolo {
    private int capacita;
    private char posizione = ' ';
    private int numerotavolo;
    private boolean prenotato = false;
    private boolean coperto = false;
    private double prezzo;
    Esterno esterno;
    Interno interno;
    Banco banco;

    public Tavolo(char posizione, int numerotavolo, int capacita){ //preferenza specifica
        this.numerotavolo=numerotavolo;
        this.capacita=capacita;
        this.posizione=posizione;
    }

    public Tavolo(char posizione, int capacita) { //per banco
        this.posizione=posizione; 
        this.capacita=capacita;
    }

    public Tavolo(){
    }

    public int getNumero(){
        return this.numerotavolo;
    }

    public int getCapacita(){
        return this.capacita;
    }

    public char getPosizione(){
        return this.posizione;
    }

    public boolean getCoperto(){
        return this.coperto;
    }

    public double getPrezzo(){
        return prezzo;
    }

    public void setNumero(int numerotavolo){
        this.numerotavolo=numerotavolo;
    }

    public void setCapacita(int capacita){
        this.capacita=capacita;
    }

    public void setPosizione(char posizione){
        this.posizione=posizione;
    }

    public void setCoperto(){
        this.coperto=!this.coperto;
    }

    public void setPrezzo(double prezzo){
        this.prezzo=prezzo;
    }

    public boolean isPrenotato(){
        return this.prenotato;
    }

    public void togglePrenotato(){ //mette e toglie la prenotazione da un tavolo
        this.prenotato = !this.prenotato;
    }

    public String toString(){
        return("Tavolo "+this.posizione + "/" +this.numerotavolo+ " -- (Tavolo da " + this.capacita + " posti)");
    }

    public String fileString(){
        return(this.posizione + "/" + this.numerotavolo + "/" + this.capacita +"/" + (this.prenotato ? "Prenotato" : "Libero") + "/" + (this.coperto ? "Coperto" : "Aperto") + "/"+ prezzo);
    }
}