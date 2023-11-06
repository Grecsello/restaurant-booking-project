import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Scanner;

public class Prenotazione{
    private String nomepersona;
    private char preferenza;
    private LocalTime orario = LocalTime.now();
    private int quantita;
    private int conta=0; //conta prenotazioni totali avvenute nella giornata
    private int numero;
    private boolean successo = false;
    GestioneDati gestione = new GestioneDati();
    Scanner sc = new Scanner(System.in);
    Ristorante ristorante = new Ristorante();

    public Prenotazione() { //prenotazione standard metti una persona in un posto randomico libero
    }

    public Prenotazione(Ristorante ristorante, String nomepersona, LocalTime orario, int quantita, int numero, char preferenza){ //intanto faccio con array poi devo fare tutto con il file csv!!!
        this.nomepersona=nomepersona;
        this.quantita=quantita;
        this.preferenza=preferenza;
        this.orario=orario;
        this.numero=numero;
        this.ristorante =ristorante;
        if(prenotaTavolo(ristorante, numero, preferenza, quantita)){
            System.out.println("\nPrenotazione effettuata con successo!");
            successo = true;
        } else {
            System.out.println("\n***Problema al tavolo!***");
        }
        try {
            conta = gestione.nextNumeroPrenotazione();
            conta++;
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato" + e.getMessage());
        }
    }

    public void setNome(String nome){
        this.nomepersona = nome;
    }

    public void setPreferenza(char preferenza){
        this.preferenza = preferenza;
    }

    public void setOrario(int ora, int minuti){
        this.orario=LocalTime.of(ora, minuti);
    }

    public void setQuantita(int quantita){
        this.quantita = quantita;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }
    
    public char getPreferenza(){
        return this.preferenza;
    }

    public boolean getSuccesso(){
        return this.successo;
    }
    
    public String getNome(){
        return this.nomepersona;
    }

    public LocalTime getOrario(){
        return this.orario;
    }

    public int getNumero(){
        return this.numero;
    }

    public boolean prenotaTavolo(Ristorante ristorante, int numero, char preferenza, int quantita){
        Tavolo[] temp = ristorante.listaTavoli();
        try{
            if(!temp[numero-1].isPrenotato() && temp[numero-1].getPosizione() == preferenza && temp[numero-1].getCapacita() >= quantita){
                temp[numero-1].togglePrenotato();
                return true;
            } else {
                int i=0;
                String tempstring = "";
                boolean check = displayTavoliquantita(ristorante, quantita, preferenza);

                while((tempstring != "S" || i<temp.length) && check){
                    if(temp[i].getPosizione() == preferenza && !temp[i].isPrenotato()){
                        System.out.println("Il tavolo selezionato non è nella posizione corretta o è già prenotato, Tavolo numero " + temp[i].getNumero() + " Disponibile, confermare S/N");
                        tempstring = sc.nextLine();
                            switch(tempstring){
                                case "S":
                                temp[i].togglePrenotato();
                                this.numero = i + 1;
                                return true;
                                case "N":
                                i++;
                                break;
                                case "U":
                                return false;
                                default:
                                System.out.println("AZIONE SCONOSCIUTA");
                                tempstring = " ";
                            }
                        } else {
                            i++;
                        }
                    }
                } 
                System.out.println("Tavoli non disponibili");
                return false;
        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Tavolo non presente nella lista");
            return false;
        }
    }
    
    public boolean displayTavoliquantita(Ristorante ristorante, int quantita, char preferenza){
        Tavolo tav[] = ristorante.listaTavoli();
        String titolo = "\n***** Tavoli disponibili *****\n";
        titolo += "--------------------------------------------\n";
        String stringa = "";
        
        for(int i = 0; i<tav.length; i++){
            if(tav[i].getCapacita() >= quantita && tav[i].getPosizione() != 'B' && !tav[i].isPrenotato() && tav[i].getPosizione() == preferenza){
                stringa += tav[i].toString() + "\n";
            }
        }
        if(stringa.isEmpty()){
            return false;
        } else {
            System.out.println(titolo + stringa);
            return true;
        }
    }


    public String toString(){
        return(this.conta +","+ this.orario +"," + this.nomepersona+","+this.quantita + "," + this.numero + ","+ this.preferenza + "\n");
    }
}
