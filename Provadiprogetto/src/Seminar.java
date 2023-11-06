public class Seminar{
	private String title;
	private Professor professor;
	private Student[] students;
	private int iscritti;

	public Seminar(String title, Professor professor, int n){
		this.title = title;
		this.professor = professor;
		this.students = new Student[n];
	}
	
	public boolean addStudent(Student student, int n){
		if(iscritti < n){
			this.students[iscritti++] = student; 
			return(true);
		}
		return(false);
	}

	/*public boolean remStudent(Student student){ // Metodo buggato
		Student[] auxStudents = new Student[students.length];
		int aux = 0;
		for(int i = 0; i<iscritti; i++){
			if(!students[i].getName().equals(student.getName())){
				auxStudents[aux++] = students[i];               
			}
		}
		students = auxStudents;
		iscritti--;
		if(aux==iscritti){
			return(true);
		}
		return(false);
	}*/ 

	public boolean remStudent(Student student) {
		Student[] auxStudents = new Student[students.length];
		int aux = 0;
		boolean removed = false;
		for (int i = 0; i < iscritti; i++) {
			if (!students[i].getName().equals(student.getName())) {
				auxStudents[aux++] = students[i];
			} else {
				removed = true;
			}
		}
		if (removed) {
			students = auxStudents;
			iscritti--;
			return true;
		} else {
			return false;
		}
	}
	

	public Student[] listStudent(){
		return(this.students);
	}

	public String toString(){
		return("'"+this.title+"'\n by "+this.professor.toString());
	}
}