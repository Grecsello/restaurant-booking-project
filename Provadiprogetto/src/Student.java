public class Student{
	private String name;
	private String email;
	private String matricola;


	public Student(String name, String email, String matricola){
		this.name = name;
		this.email = email;
		this.matricola = matricola;
	}
	//public Student(String name, String email){
	//	this.name = name;
	//	this.email = email;
	//}

	public String getName(){
		return(this.name);
	}
	public String getEmail(){
		return(this.email);
	}
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}

	public String getMatricola(String matricola){
		return(this.matricola);
	}

	public void setMatricola(String matricola){
		this.matricola = matricola;
	}

	public String toString(){
		return(this.name+" ("+this.email+")"+ " " + this.matricola);
	}
}