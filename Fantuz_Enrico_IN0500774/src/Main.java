import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    GestioneDati gestione = new GestioneDati();
    Ristorante ristorante = new Ristorante();
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        String cons;
        
        while(true){
            if(creaoldRistorante()){
                addoldPrenotazione();
                String temp = "\n****Benvenuti nel sistema di prenotazioni del ristorante " + ristorante.getNome() + " da " + ristorante.getProprietario() + "****";
                System.out.println(temp);
                cons = " ";
                cons = scriviMenu();
                if(cons.equals("U")){
                    System.out.println(">> Arrivederci!");
                    System.exit(1);
                } else {
                    temp = "";
                    verificaMenu(cons);
                }   
            } else {
                System.out.println("Benvenuti nel sistema di prenotazioni tavoli, inserire un nuovo ristorante");
                generanewRistorante();
            }
        }
    }

    public void verificaMenu(String cons){
        switch(cons){
            case "P":
            addPrenotazione();
            break;
            case "L":
            listaPrenotazioni();
            break;
            case "T":
            listaRistorante();
            break;
            case "N":
            System.out.println("Confermi la creazione di un nuovo ristorante? S/N");
            if(scan.nextLine().equals("S")){
                generanewRistorante();
                break;
            } else {
                break;
            }
            case "R":
            remPrenotazione();
            break;
            case "C":
            switchCheck();
            break;
            case "RESET":
            System.out.println("Confermi di voler resettare le prenotazioni? S/N");
            if(scan.nextLine().equals("S")){
                gestione.resetPrenotazioni();
                break;
            } else {
                break;
            }
            case "U":
            return;
            default:
            System.out.println("*****Azione non riconosciuta*****");
            cons = " ";
        }
    }

    public String scriviMenu(){
        System.out.println("\nMenù di opzione:");
        System.out.println("---------------------------------");
        System.out.println("[P] - Nuova prenotazione di un tavolo");
        System.out.println("[L] - Lista delle prenotazioni totali presenti");
        System.out.println("[R] - Rimuovi una prenotazione avvenuta");
        System.out.println("[C] - Controlla la presenza di una prenotazione");
        System.out.println("[T] - Lista dei tavoli presenti");
        System.out.println("[N] - Nuovo ristorante");
        System.out.println("[RESET] - Reset completo delle prenotazioni");
        System.out.println("[U] - Termina il programma");
        return(scan.nextLine());
    }

    public void creafileRistorante(Ristorante ristorante){
        try {
            gestione.creaRistorante(ristorante);
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato " + e.getMessage());
        } catch (IOException e) {
            System.out.println("errore nel file " + e.getMessage());
        } 
    }

    public boolean creaoldRistorante(){
        try {
            ristorante = gestione.creaRistorante();
            creafileRistorante(ristorante);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato " + e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("errore nel file " + e.getMessage());
            return false;
        } catch (NoSuchElementException e) {
            System.err.println("errore nel file " + e.getMessage());
            return false;
        }
    }

    public void addPrenotazione(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        String nome;
        LocalTime ora;
        int quantita;
        int numero;
        String preferenza;
        System.out.println("Inserisci il nome di riferimento del cliente");
        try {
            nome = scan.nextLine();
        } catch (NoSuchElementException e){
            System.err.println("Nessuno nome inserito");
            return;
        }
        System.out.println("Inserisci l'ora (separata da due punti :)");
        String stringaOra = scan.nextLine();
        try {
            ora = LocalTime.parse(stringaOra, format);
        } catch (DateTimeParseException err){
            System.out.println("Ora inserita in maniera erronea, ritorno al menù iniziale");
            return;
        }
        System.out.println("Inserire la quantita di persone");
        try {
            quantita = scan.nextInt();
        } catch (InputMismatchException e){
            System.err.println("Il valore inserito non è un numero intero");
            scan.nextLine();
            return;
        }
        scan.nextLine();
        System.out.println("Inserire la preferenza del tavolo (I = interno, E = esterno)");
        preferenza = scan.nextLine();
        System.out.println("Inserire il numero del tavolo");
        try{
            numero = scan.nextInt();
        } catch (InputMismatchException e){
            System.err.println("Il valore inserito non è un numero intero");
            scan.nextLine();
            return;
        }
        scan.nextLine();
        Prenotazione prenotazione = new Prenotazione(ristorante, nome, ora, quantita, numero, preferenza.charAt(0));
        try {
            if(prenotazione.getPreferenza() != 'B' && prenotazione.getSuccesso()){
                gestione.creaPrenotazioni(prenotazione);
                gestione.creaRistorante(ristorante);
            } else {
                System.out.println("***Prenotazione non eseguita!***");
            }
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato " + e.getMessage());
        } catch (IOException e) {
            System.out.println("errore nel file " + e.getMessage());
        }
    }


    public void addoldPrenotazione(){ //genera le vecchie prenotazioni in base se in passato il tavolo era occupato
        try {
            gestione.creaPrenotazioni(ristorante);
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato " + e.getMessage());
        } catch (IOException e) {
            System.out.println("erroe nel file " + e.getMessage());
        }
    }

    public void remPrenotazione(){
        System.out.println("\nInserisci il nome del cliente, [L] per la lista, [U] per uscire");
        String temp = scan.nextLine();
        try {
            while(gestione.rimuoviPrenotazione(temp)){
                if(temp.equals("U")){
                    return;
                } else if(temp.equals("L")){
                    listaPrenotazioni();
                    remPrenotazione();
                }
                System.out.println("Cliente non trovato, riprova");
                temp = scan.nextLine(); 
                    
            }
            creaoldRistorante();
            addoldPrenotazione();
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato " + e.getMessage());
        } catch (IOException e) {
            System.out.println("erroe nel file " + e.getMessage());
        }
    }

    public void listaRistorante(){
        try{
            System.out.println(gestione.listaRistorante());
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        }
    }

    public void listaPrenotazioni(){
        try{
            System.out.println(gestione.listaPrenotazioni());
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        }
    }

    public void switchCheck(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
        System.out.println("\nCheck per le prenotazioni");
        System.out.println("-----------------------------------");
        System.out.println("Inserisci il parametro di ricerca");
        System.out.println("[N] - per nome");
        System.out.println("[O] - per orario");
        System.out.println("[T] - per tavolo");
        System.out.println("[U] - uscire");
        switch(scan.nextLine()){
            case "N":
            System.out.println("Inserisci il nome");
            checkPrenotazione(scan.nextLine());
            break;
            case "O":
            System.out.println("Inserisci l'ora (separata da due punti :)");
            String stringaOra = scan.nextLine();
            LocalTime ora;
            try {
                ora = LocalTime.parse(stringaOra, format);
            } catch (DateTimeParseException err){
                System.out.println("Ora inserita in maniera erronea");
                break;
            }
            checkPrenotazione(ora.getHour(),ora.getMinute());
            break;
            case "T":
            try{
                System.out.println("Inserisci il numero del tavolo");
                checkPrenotazione(scan.nextInt());
                scan.nextLine();
            } catch (InputMismatchException e){
                System.err.println("Input non valido "+ e.getMessage());
                return;
            }
            break;
            case "U":
            return;
            default:
            System.out.println("*****Azione non riconosciuta*****");
        }
    }

    public void checkPrenotazione(String nome) {
        try{
            if(!gestione.getPrenotazionetoString(nome, -1,-1,-1))
                System.out.println("Nessun tavolo prenotato sotto questo parametro");
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        }
    }

    public void checkPrenotazione(int numtavolo){
        try{
            scan.nextLine();
            if(!gestione.getPrenotazionetoString(" ", numtavolo, -1, -1))
                System.out.println("Nessun tavolo prenotato sotto questo parametro");
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        }
    }

    public void checkPrenotazione(int ora, int minuti){
        try{
            if(!gestione.getPrenotazionetoString(" ", -1, ora, minuti))
                System.out.println("Nessun tavolo prenotato sotto questo parametro");
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        }
    }

    public void generanewRistorante(){
        System.out.println("\n********Generazione di un nuovo ristorante********\n");
        System.out.println("Inserisci il nome del ristorante");
        String nome = scan.nextLine();
        System.out.println("Inserisci il nome del proprietario");
        String proprietario = scan.nextLine();
        System.out.println("Quanti coperti avrà il ristorante?");
        int numerotavoli = scan.nextInt();
        scan.nextLine();
        ristorante = new Ristorante(nome, proprietario, numerotavoli);
        char sel = ' ';
        int tempnum1,tempnum2;
        double tempdouble;
        int contatavolo = 1;
        boolean checkb = true;
        while(sel != 'U' && ((contatavolo-1)<numerotavoli)){
            int tavolitot = numerotavoli-(contatavolo-1);
            System.out.println("***** Tavoli rimanenti: " + tavolitot + " *****");
            System.out.println("Seleziona la posizione dei tavoli: ");
            System.out.println("[E] - Esterno");
            System.out.println("[I] - Interno");
            System.out.println("[B] - Al banco");
            System.out.println("[U] - Annulla ed Esci");
            sel = (char)scan.nextLine().charAt(0);
            switch(sel){
                case 'E':
                    System.out.println("Quanti tavoli esterni?");
                    tempnum1 = scan.nextInt() + (contatavolo-1);
                    System.out.println("Quanti posti per tavolo?");
                    tempnum2 = scan.nextInt();
                    System.out.println("A quanto ammonta il prezzo del coperto?");
                    tempdouble = scan.nextDouble();
                    scan.nextLine();
                    while(contatavolo <= tempnum1){
                        Esterno esterno = new Esterno(contatavolo,tempnum2,tempdouble);
                        ristorante.addEsterno(esterno, numerotavoli);
                        contatavolo++;
                    }
                    break;
                case 'I':
                    System.out.println("Quanti tavoli interni?");
                    tempnum1 = scan.nextInt() + (contatavolo-1);
                    System.out.println("Quanti posti per tavolo?");
                    tempnum2 = scan.nextInt();
                    System.out.println("A quanto ammonta il prezzo del coperto?");
                    tempdouble = scan.nextDouble();
                    scan.nextLine();
                    while(contatavolo <= tempnum1){
                        Interno interno = new Interno(contatavolo,tempnum2,tempdouble);
                        ristorante.addInterno(interno, numerotavoli);
                        contatavolo++;
                    } 
                break;
                case 'B':
                    if(checkb){
                        System.out.println("Quanti posti al bancone? (I tavoli al bancone non sono prenotabili)");
                        tempnum1 = scan.nextInt();
                        scan.nextLine();
                        ristorante.addBanco(new Banco(tempnum1), numerotavoli);
                        contatavolo ++;
                        checkb = false;
                    } else {
                        System.out.println("Bancone già pieno!");
                    }
                break;
                case 'U':
                    return;
                default:
                    System.out.println("*** Azione sconosciuta ***");
                    sel = ' ';
            }
        }
        creafileRistorante(ristorante);
        gestione.resetPrenotazioni();
        System.out.println("\n Ristorante "+ nome + " generato con successo!");
    }
    
}
