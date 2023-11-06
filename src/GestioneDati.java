import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class GestioneDati {
    File file_ristorante;
    File file_prenotazioni;
    Scanner sc;

    public GestioneDati(){
        file_ristorante = new File("ristorante.csv");
        file_prenotazioni = new File("prenotazioni.csv");
    }

    public void creaRistorante(Ristorante ristorante) throws IOException, FileNotFoundException {
        FileWriter fw = new FileWriter(file_ristorante, false);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(ristorante.fileTavoli());
        bw.flush();
        bw.close();
    }

    public Ristorante creaRistorante() throws IOException, FileNotFoundException, NoSuchElementException {
        int numerotavoli = getNumtoString();
        Ristorante ristorante = new Ristorante(getNomeristoString(), getNomeproptoString(), numerotavoli);
        sc = new Scanner(file_ristorante);
        String linea = sc.nextLine();
        String lineaAux[];
        while(sc.hasNextLine()) {
            linea = sc.nextLine();
            lineaAux = linea.split("/");
            switch(lineaAux[0].charAt(0)){
                case 'E':
                    ristorante.addEsterno(new Esterno(Integer.parseInt(lineaAux[1]),Integer.parseInt(lineaAux[2]),Double.parseDouble(lineaAux[5])), numerotavoli);
                break;
                case 'I':
                    ristorante.addInterno(new Interno(Integer.parseInt(lineaAux[1]),Integer.parseInt(lineaAux[2]),Double.parseDouble(lineaAux[5])), numerotavoli);
                break;
                case 'B':
                    ristorante.addBanco(new Banco(Integer.parseInt(lineaAux[2])), numerotavoli);
                break;
            }
        }
        return ristorante;
    }

    public Tavolo getTavolofile(int numero, char posizione) throws FileNotFoundException{
        sc = new Scanner(file_ristorante);
        Tavolo tavolo = null;
        while(sc.hasNextLine()) {
            String linea = sc.nextLine();
            if(linea.split("/")[0].equals(String.valueOf(posizione)) && Integer.valueOf(linea.split("/")[1]) == numero){
                tavolo = new Tavolo(posizione, numero, Integer.valueOf(linea.split("/")[2]));
            }
        }
        return tavolo;
    }

    public String getNomeristoString() throws FileNotFoundException{
        sc = new Scanner(file_ristorante);
        String linea = sc.nextLine();
        return (linea.split("/")[0]);
    }

    public String getNomeproptoString() throws FileNotFoundException{
        sc = new Scanner(file_ristorante);
        String linea = sc.nextLine();
        return (linea.split("/")[1]);
    }

    public int getNumtoString() throws FileNotFoundException{
        sc = new Scanner(file_ristorante);
        String linea = sc.nextLine().split("/")[2];
        return (Integer.parseInt(linea));
    }

    public boolean getPrenotazionetoString(String nome, int numtavolo, int ora, int minuti) throws FileNotFoundException{
        sc = new Scanner(file_prenotazioni);
        String temp = "";
        while(sc.hasNextLine()){
            String linea = sc.nextLine();
            String lineaAux[] = linea.split(",");
            if(lineaAux[2].equals(nome)){
                temp += "Prenotazione confermata: " + String.valueOf(lineaAux[2]) + " Alle ore " + String.valueOf(lineaAux[1]) + ", Tavolo " + String.valueOf(lineaAux[4]) + "\n";
            } else if(Integer.parseInt(lineaAux[1].split(":")[0]) == ora && Integer.parseInt(lineaAux[1].split(":")[1]) == minuti){
                temp += "Prenotazione confermata: " + String.valueOf(lineaAux[2]) + " Alle ore " + String.valueOf(lineaAux[1]) + ", Tavolo " + String.valueOf(lineaAux[4]) + "\n";
            } else if(Integer.parseInt(lineaAux[4]) == numtavolo){
                temp += "Prenotazione confermata: " + String.valueOf(lineaAux[2]) + " Alle ore " + String.valueOf(lineaAux[1]) + ", Tavolo " + String.valueOf(lineaAux[4]) + "\n";
            } 
        }
        if(temp.isEmpty()){
            return false;
        } else {
            System.out.println(temp);
            return true;
        }
    }

    public String listaRistorante() throws FileNotFoundException{
        sc = new Scanner(file_ristorante);
        String temp = "\n ******************";
        temp += "Elenco tavoli ";
        temp += getNomeristoString();
        temp += "********************* \n";
        temp += "\n";
        while(sc.hasNextLine()) {
            String linea = sc.nextLine();
            switch(linea.split("/")[0]){
                case "E":
                    temp+=(" \n Tavolo esterno, posto: "+ linea.split("/")[1] + " " + linea.split("/")[3] + ", da " + linea.split("/")[2] + " posti");
                    break;
                case "I":
                    temp+=(" \n Tavolo interno, posto: "+ linea.split("/")[1] + " " +linea.split("/")[3] + ", da " + linea.split("/")[2] + " posti");
                    break;
                case "B":
                    int b = 1;
                    temp+=(" \n Tavolo al banco " + b++  + ", da " + linea.split("/")[2] + " posti");
                    break;
            }
        }
        return temp;
    }

    public String listaPrenotazioni() throws FileNotFoundException{
        sc = new Scanner(file_prenotazioni);
        String temp = "\n ********************";
        temp += "Elenco prenotazioni ";
        temp += "************************\n";
        temp += "\n";
        while(sc.hasNextLine()){
            String linea = sc.nextLine();
            String lineaAux[] = linea.split(",");
            temp += "Cliente " + lineaAux[2] + " al tavolo " +lineaAux[5]+ lineaAux[4] +" alle ore " + lineaAux[1] + " (" + lineaAux[3] + " persone)\n"; 
        }
        return temp;
    }

    public void creaPrenotazioni(Prenotazione prenotazione) throws IOException, FileNotFoundException{
        FileWriter fw = new FileWriter(file_prenotazioni, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(prenotazione.toString());
        bw.flush();
        bw.close();
    }

    public void creaPrenotazioni(Ristorante ristorante) throws FileNotFoundException, IOException{
        sc = new Scanner(file_prenotazioni);
        Tavolo tavolo[] = ristorante.listaTavoli();
        while(sc.hasNextLine()){
                tavolo[Integer.parseInt(sc.nextLine().split(",")[4])-1].togglePrenotato();
        }
        creaRistorante(ristorante);
    }

    public int nextNumeroPrenotazione() throws FileNotFoundException{
        sc = new Scanner(file_prenotazioni);
        int numerofinale = 0;
        while(sc.hasNextLine()){
            numerofinale = Integer.parseInt(sc.nextLine().split(",")[0]);
        }
        sc.reset();
        return numerofinale;
    }

    public boolean rimuoviPrenotazione(String nome) throws IOException, FileNotFoundException{ //devo sistemare questo!!! 
        sc = new Scanner(file_prenotazioni);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file_prenotazioni, true));
        String temp = "";
        int i=1;
        boolean check = true;
        while(sc.hasNextLine()){
            String linea = sc.nextLine();
            String lineaAux[] = linea.split(",");
            String stampa = "," + lineaAux[1] +","+ lineaAux[2] +","+ lineaAux[3] +","+ lineaAux[4] +","+ lineaAux[5];
            if(!lineaAux[2].equals(nome)){
                temp += i + stampa + "\n";
                i++;
            } else {
                check = false;
                System.out.println("Prenotazione rimossa con successo");
            }
        }
        //System.out.println(temp);
        resetPrenotazioni();
        bw.write(temp);
        bw.flush();
        bw.close();
        return check;
    }

    public void resetPrenotazioni(){
        try { //cancella il contenuto per poi riscriverlo
            FileWriter fw = new FileWriter(file_prenotazioni);
            fw.write(""); // Scrivi una stringa vuota per cancellare il contenuto del file
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
