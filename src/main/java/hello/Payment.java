/*+----------------------------------------------------------------------
 ||
 ||  Class Payment
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  This class is to keep attrs for payment table
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

public class Payment {
	
	private int payID;
	private int PID;
	private String amountDue;
	private String dueDate;
	private String status;
	private String paymentDate;
	private int EID;
	
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
	public int getPayID() {
		return payID;
	}
	public void setPayID(int payID) {
		this.payID = payID;
	}
	public int getPID() {
		return PID;
	}
	public void setPID(int pID) {
		PID = pID;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public int getEID() {
		return EID;
	}
	public void setEID(int eID) {
		EID = eID;
	}
}
