public class Professor{
	public enum BACKGROUND {UKN, MATH, ENGINEERING, INFORMATICA};
	
	private String name;
	private String email;
	private BACKGROUND area;	

	public Professor(String name, String email, BACKGROUND area){
		this.name = name;
		this.email = email;
		this.area = area;
	}

	public String toString(){
		return(this.name+" ("+this.email+")\n"+this.area);
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
}