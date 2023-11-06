public class Ristorante {
    private String nome;
    private String proprietario;
    private Tavolo[] tavoli;
    private int ntavoli=0; //farai un check per quanti metri quadrati esterni ed interni

    public Ristorante(String nome, String proprietario, int numerotavoli){
        this.nome=nome;
        this.proprietario=proprietario;
        this.tavoli = new Tavolo[numerotavoli];
    }

    public Ristorante(){

    }

    public String getNome(){
        return this.nome;
    }

    public String getProprietario(){
        return this.proprietario;
    }

    public void setCoperto(int n){
        if(tavoli[n-1].getCoperto()){
            System.out.println("Tavolo già al coperto");
        } else {
            tavoli[n-1].setCoperto();
        }
    }

    public void unsetCoperto(int n){
        if(tavoli[n-1].getCoperto()){
            tavoli[n-1].setCoperto();
        } else {
            System.out.println("Tavolo già all'aperto");
        }
    }

    public boolean addBanco(Banco banco, int n){ //n è per in numero massimo di tavoli
        if(ntavoli<n){
            this.tavoli[ntavoli++]=banco;
            return true;
        }
        return false;
    }

    public boolean addEsterno(Esterno esterno, int n) {
        if(ntavoli<n){
            this.tavoli[ntavoli++]=esterno;
            return true;
        }
        return false;
    }

    public boolean addInterno(Interno interno, int n) {
        if(ntavoli<n){
            this.tavoli[ntavoli++]=interno;
            return true;
        }
        return false;
    }

    public Tavolo[] listaTavoli(){
        return(this.tavoli);
    }

    public void setTavolo(Tavolo[] tavolo){
        this.tavoli=tavolo;
    }

    public Tavolo getTavolo(int i){
        return(this.tavoli[i]);
    }

    public String fileTavoli(){
        String s_tavoli = nome + "/" + proprietario + "/" + ntavoli + "\n";
        for(int i=0;i<this.ntavoli;i++){
            s_tavoli += tavoli[i].fileString() + "\n";
        }
        return(s_tavoli);
    }

    public void rimuoviTavolo(Tavolo tavolo){
        int aux = 0;
        Tavolo[] temp = new Tavolo[tavoli.length];
        for(int i = 0;i<tavoli.length;i++){
            if(this.tavoli[i] != tavolo){
                temp[aux++]=tavoli[i];
            }
        }
        this.ntavoli--;
        setTavolo(temp);
    }
}
