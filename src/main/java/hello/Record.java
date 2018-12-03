package hello;

public class Record {

	private int recordNum;
	private int apptNum;
	private String initialHospDate;
	private String expDichargeDate;
	private String actualDischargeDate;
	private String reason;
	private String treatmentMethod;
	private int hospRoom;
	private int DID;
	
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
	public String getExpDichargeDate() {
		return expDichargeDate;
	}
	public void setExpDichargeDate(String expDichargeDate) {
		this.expDichargeDate = expDichargeDate;
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
