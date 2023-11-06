import java.util.Scanner;

public class MainSeminar{
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args){
		new MainSeminar();		
	}

	public int nstudenti;

	public MainSeminar(){
		String pnome, pmail;
		String parea = "UKN"; //non trovo un altro modo per inizializzare BACKGROUND dunque ho inserito il valore unknown
		System.out.println("Inserire il nome del professore");
		pnome = sc.next();
		System.out.println("Inserire l'email del professore");
		pmail = sc.next();
		char pazione = ' ';
		while (pazione != 'U') {
		System.out.println("Qual è l'area in cui si applica?");
		System.out.println("  [M] - MATH");
		System.out.println("  [E] - ENGINEERING");
		System.out.println("  [I] - INFORMATICA");
		System.out.println("  [U] - Esci");
		pazione = (char)sc.next().charAt(0);

			switch(pazione){
				case 'M':
					parea = "MATH";
					pazione = 'U';
					break;
				case 'E':
					parea = "ENGINEERING";
					pazione = 'U';
					break;
				case 'I':
					parea = "INFORMATICA";
					pazione = 'U';
					break;
				case 'U':
					return;
				default: 
					System.out.println("** azione sconosciuta");	
					pazione = ' ';				
				}
		} 
		
		Professor professor = new Professor(pnome, pmail, Professor.BACKGROUND.valueOf(parea));

		System.out.println("Qual è il numero massimo di studenti?");
		nstudenti = sc.nextInt();
		Seminar seminar = new Seminar("Machine Learning Overview", professor, nstudenti);

		char azione = ' ';
		while(azione!='U'){
			System.out.println("Qual è l'azione desiderata?");
			System.out.println("  [I] - Iscrivere studenti");
			System.out.println("  [E] - Elencare studenti");
			System.out.println("  [C] - Cancellare studenti");
			System.out.println("  [U] - Esci");
			azione = (char)sc.next().charAt(0);
			switch(azione){
			case 'I':
				iscrivere(seminar);
				break;
			case 'E':
				elencare(seminar);
				break;
			case 'C':
				cancellare(seminar);
				break;
			case 'U':
				return;
			default:
				System.out.println("** azione sconosciuta?");
				azione = ' ';
			}
		}
	}

	public void iscrivere(Seminar seminar){
		String nome, email, matricola;
		System.out.println("-- Iscrizione studenti al Seminario --");
		do{
			System.out.println("Inserisci il nome dello studente:");
			nome = sc.next();
			System.out.println("Inserisci la mail dello studente:");
			email = sc.next();
			System.out.println("Inserisci il numero della matrica");
			matricola = sc.next();
			if(seminar.addStudent(new Student(nome,email, matricola), nstudenti)){
				System.out.println("** successo");
			}else{
				System.out.println("** errore, numero massimo raggiunto");
				break;
			}
			System.out.println("Vuoi aggiungere un altro studente [S/N]?");
		}while(sc.next().equals("S"));
	}

	public void elencare(Seminar seminar){
		System.out.println("-- Elenchi degli studenti del seminario -- " + "(Max " + nstudenti +")");
		System.out.println("-- "+ seminar +" --");
		Student[] students = seminar.listStudent();
		for(int i = 0; i<students.length; i++){
			if(students[i]!=null){
				System.out.println((i+1)+" - "+students[i].toString());
			}
		}
	}

	public void cancellare(Seminar seminar){
		String nome, email, matricola;
		System.out.println("-- Cancellare studenti al Seminario --");
		do{
			System.out.println("Qual è il nome dello studente?");
			nome = sc.next();
			System.out.println("Qual è la mail dello studente?");
			email = sc.next();
			System.out.println("Qual è il numero di matricola dello studente?");
			matricola = sc.next();
			if(seminar.remStudent(new Student(nome,email, matricola))){
				System.out.println("** successo");
			}else{
				System.out.println("** errore");
			}
			System.out.println("Vuoi cancellare un altro studente [S/N]?");
		}while(sc.next().equals("S"));
	}
}