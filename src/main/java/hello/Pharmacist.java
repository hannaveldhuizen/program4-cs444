/*+----------------------------------------------------------------------
 ||
 ||  Class Pharmacist
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  This class is to keep attrs for pharmacist table
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  None
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  getters and setters only
 ||
 ++-----------------------------------------------------------------------*/
package hello;

public class Pharmacist {
	
	private int phID;
	private String firstName;
	private String lastName;
	private String DOB;
	private int officeNumber;
	private int deptID;
    
    /*---------------------------------------------------------------------
    |  Method getters and setters
    |
    |  Purpose:  Encapsulation for instance fields
    |
    |  Pre-condition: None.
    |
    |  Post-condition: getters - unchanged. setters- the field is changed.
    |
    |  Parameters:
    |      parameters for setters replace the old field values
    |
    |  Returns:  Getters - the field. Setters - None
    *-------------------------------------------------------------------*/
	public int getPhID() {
		return phID;
	}
	public void setPhID(int phID) {
		this.phID = phID;
	}
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
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
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
