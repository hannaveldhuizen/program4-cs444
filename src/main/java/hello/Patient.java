/*+----------------------------------------------------------------------
 ||
 ||  Class Patient
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  This class is to keep attrs for patient table
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

public class Patient {

	private int PID;
	private String firstName;
	private String lastName;
	private String gender;
	private String DOB;
	private String address;
	private String contactNumber;

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

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}


}
