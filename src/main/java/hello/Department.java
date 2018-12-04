/*+----------------------------------------------------------------------
 ||
 ||  Class Department
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  This class is to keep deptID, deptName, buildingName,
 ||						and officeNum for department table.
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

public class Department {
	private int deptID;
	private String deptName;
	private String buildingName;
	private int officeNum;
	
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
	public int getDeptID() {
		return deptID;
	}
	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public int getOfficeNum() {
		return officeNum;
	}
	public void setOfficeNum(int officeNum) {
		this.officeNum = officeNum;
	}

}
