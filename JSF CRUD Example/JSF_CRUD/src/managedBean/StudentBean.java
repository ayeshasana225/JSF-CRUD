package managedBean;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import backingBean.Student;
import db.DbConnection;

@ManagedBean @SessionScoped
public class StudentBean {

	public StudentBean() {
	}
	Student student=new Student();

	public void setStudent(Student student) {
		this.student = student;
	}
	public Student getStudent() {
		return student;
	}

	public ArrayList<StudentBean>studentsListFromDB;


	/* Method To Fetch The Student Records From Database */
	public static ArrayList<Student> getStudentsListFromDB() {
		DbConnection dbConnection = new DbConnection();
		ResultSet resultSet= dbConnection.getRecords();
		ArrayList<Student> studentsList = new ArrayList<Student>();

		try {
			while(resultSet.next()) {
            Student student = new Student();
				student.setId(resultSet.getInt("student_id"));
				student.setName(resultSet.getString("student_name"));
				student.setEmail(resultSet.getString("student_email"));
				student.setPassword(resultSet.getString("student_password"));
				student.setGender(resultSet.getString("student_gender"));
				student.setAddress(resultSet.getString("student_address"));
				studentsList.add(student);
			}
			System.out.println("Total Records Fetched: " + studentsList.size());

		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return studentsList;
	}

	public static String saveStudentDetailsInDB(Student student) {
		int saveResult = 0;
		String navigationResult = "";
		try {
//			pstmt = getConnection().prepareStatement("insert into student_record (student_name, student_email, student_password, student_gender, student_address) values (?, ?, ?, ?, ?)");
			DbConnection dbConnection = new DbConnection();
//			Student student = new Student();
			dbConnection.insertRecord(student.getName(),student.getEmail(),student.getPassword(),student.getGender(),student.getAddress());
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		if(saveResult !=0) {
			navigationResult = "studentsList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createStudent.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}


	/* Method Used To Edit Student Record */
//	public String editStudentRecord(int studentId) {
//		return DatabaseOperation.editStudentRecordInDB(studentId);
//	}
	
	/* Method Used To Update Student Record */
//	public String updateStudentDetails(StudentBean updateStudentObj) {
//		return DatabaseOperation.updateStudentDetailsInDB(updateStudentObj);
//	}
	
	/* Method Used To Delete Student Record */
		public static String deleteStudentRecordInDB(int studentId){
			DbConnection dbConnection = new DbConnection();
		System.out.println("deleteStudentRecordInDB() : Student Id: " + studentId);
		dbConnection.deleteRecord(studentId);
		return "/studentsList.xhtml?faces-redirect=true";
	}

	public static String editStudentRecordInDB(int studentId) {
		Student editRecord = null;
		System.out.println("editStudentRecordInDB() : Student Id: " + studentId);

		Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        DbConnection dbConnection = new DbConnection();
		try {
			ResultSet resultSet =dbConnection.getRecord(studentId);

			if(resultSet != null) {
				resultSet.next();
				editRecord = new Student();
				editRecord.setId(resultSet.getInt("student_id"));
				editRecord.setName(resultSet.getString("student_name"));
				editRecord.setEmail(resultSet.getString("student_email"));
				editRecord.setGender(resultSet.getString("student_gender"));
				editRecord.setAddress(resultSet.getString("student_address"));
				editRecord.setPassword(resultSet.getString("student_password"));
			}
			sessionMapObj.put("editRecordObj", editRecord);
		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/editStudent.xhtml?faces-redirect=true";
	}

	public static String updateStudentDetailsInDB(Student updateStudentObj) {
		DbConnection dbConnection = new DbConnection();
		try {
			dbConnection.updateRecord(updateStudentObj.getName(),updateStudentObj.getEmail(),updateStudentObj.getPassword(),updateStudentObj.getGender(),updateStudentObj.getAddress(),updateStudentObj.getId());

		} catch(Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/studentsList.xhtml?faces-redirect=true";
	}

}