package hello;

public class Doctor {
	private int DID;
	private String firstName;
	private String lastName;
	private String DOB;
	private String status;
	private int officeNumber;
	private int deptID;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getDID() {
		return DID;
	}
	public void setDID(int dID) {
		DID = dID;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOfficeNumber() {
		return officeNumber;
	}
	public void setOfficeNumber(int officeNumber) {
		this.officeNumber = officeNumber;
	}
	public int getDeptID() {
		return deptID;
	}
	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}
}
