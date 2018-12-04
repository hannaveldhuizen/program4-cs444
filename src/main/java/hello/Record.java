/*+----------------------------------------------------------------------
 ||
 ||  Class Record
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  This class is to keep attrs for recordvisit table
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

public class Record {

	private int PID;
	private int recordNum;
	private int apptNum;
	private String initialHospDate;
	private String expDischargeDate;
	private String actualDischargeDate;
	private String reason;
	private String treatmentMethod;
	private int hospRoom;
	private int DID;
	
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
	public int getPID() {
		return PID;
	}
	public void setPID(int PID) {
		this.PID = PID;
	}
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
	public int getApptNum() {
		return apptNum;
	}
	public void setApptNum(int apptNum) {
		this.apptNum = apptNum;
	}
	public String getInitialHospDate() {
		return initialHospDate;
	}
	public void setInitialHospDate(String initialHospDate) {
		this.initialHospDate = initialHospDate;
	}
	public String getExpDischargeDate() {
		return expDischargeDate;
	}
	public void setExpDischargeDate(String expDischargeDate) {
		this.expDischargeDate = expDischargeDate;
	}
	public String getActualDischargeDate() {
		return actualDischargeDate;
	}
	public void setActualDischargeDate(String actualDischargeDate) {
		this.actualDischargeDate = actualDischargeDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTreatmentMethod() {
		return treatmentMethod;
	}
	public void setTreatmentMethod(String treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}
	public int getHospRoom() {
		return hospRoom;
	}
	public void setHospRoom(int hospRoom) {
		this.hospRoom = hospRoom;
	}
	public int getDID() {
		return DID;
	}
	public void setDID(int dID) {
		DID = dID;
	}
}
