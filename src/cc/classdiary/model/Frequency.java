package cc.classdiary.model;

public class Frequency{

	private Long id; 
	private Student student;
	private DisciplineClass disciplineClass;
	private Long classNumber;

	private Character presence;

	public Frequency(Long studentId, Long disciplineId, Long classNumber) {
		Student student = new Student(studentId);
		DisciplineClass disciplineClass = new DisciplineClass(disciplineId);
		this.student = student;
		this.disciplineClass = disciplineClass;
		this.classNumber = classNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public DisciplineClass getDisciplineClass() {
		return disciplineClass;
	}

	public void setDisciplineClass(DisciplineClass disciplineClass) {
		this.disciplineClass = disciplineClass;
	}

	public Long getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(Long classNumber) {
		this.classNumber = classNumber;
	}

	public Character getPresence() {
		return presence;
	}

	public void setPresence(Character presence) {
		this.presence = presence;
	}
}
