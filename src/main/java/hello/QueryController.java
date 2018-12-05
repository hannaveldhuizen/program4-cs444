package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
public class QueryController {

	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	// QUERY 1 ////////////////////////////////////////////////////////////////
	
	@GetMapping("/query1Results")
    public String query1(@RequestParam(value = "search", required = true)
    						String firstName,@RequestParam(value = "search1", required = true) String lastName, 
							@RequestParam(value = "search2", required = true) String DOB, Model model) {	
	
		System.out.println("A");
		String fname = firstName;
		String lname = lastName;
		String DoB = DOB;
		
		String query = "select Patient.pid, firstname, lastname, gender, DOB, initialHospDate, reason, treatmentMethod, did ";
		query += "from lshoemake.Patient, lshoemake.RecordVisit, lshoemake.Appointment where RecordVisit.apptnum=Appointment.apptnum and ";
		query += "Appointment.pid=Patient.pid and firstname='" + fname + "' and lastname='" + lname + "' and DOB='" + DoB + "'";
		
		System.out.println(query);
		List<String> allNames = this.jdbcTemplate.query(
        query,
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String pid = rs.getString("pid");
                String first_name = rs.getString("firstname");
				String last_name = rs.getString("lastname");
				String gender = rs.getString("gender");
				String dateOfBirth = rs.getString("DOB");
				String iHP = rs.getString("initialHospDate");
				String reason = rs.getString("reason");
				String tMethod = rs.getString("treatmentMethod");
				String did = rs.getString("did");
				
                return (pid+","+first_name+","+last_name+","+gender+","+dateOfBirth+","+iHP+","+reason+","+tMethod+","+did);
            }
        });
		
		int maxIndex = 0;
		Date maxDate = null;
		Date curDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		for(int i = 0; i < allNames.size(); i++){
			String[] parts = allNames.get(i).split(",");
			try {
				curDate = sdf.parse(parts[5]);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if(maxDate == null){
				maxDate = curDate;
				maxIndex = i;
			}
			else if(curDate.after(maxDate)){
				maxDate = curDate;
				maxIndex = i;
			}
		}
		/*
		List<String> retVal = new ArrayList<String>();
		if(allNames.size() != 0){
			retVal.add(allNames.get(maxIndex));
		}
		*/
		String retVal = "";
		if(allNames.size() != 0){
			//retVal.add(allNames.get(maxIndex));
			retVal += allNames.get(maxIndex);
		}
        model.addAttribute("retVal", retVal);	
		
        return "/query1Results";
    }
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 2 ////////////////////////////////////////////////////////////////
	
	@GetMapping("/query2Results")
    public String query2(@RequestParam(value = "search", required = true)
    						String deptName, Model model) {

		String query = "select Doctor.firstname, Doctor.lastname, Department.officenum, "
				+ "buildingname from lshoemake.Doctor, lshoemake.Department "
				+ "where Doctor.DeptId=Department.DeptId and DeptName='" + deptName + "'";
		
		List<String> allNames = this.jdbcTemplate.query(
        query,
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String first_name = rs.getString("firstname");
				String last_name = rs.getString("lastname");
				String officenum = rs.getString("officenum");
				String buildingname = rs.getString("buildingname");
				
                return (first_name+","+last_name+","+officenum+","+buildingname);
            }
        });
		
        model.addAttribute("search", allNames);	
		
        return "/query2Results";
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 3 ////////////////////////////////////////////////////////////////
	
	@GetMapping("/query3Results")
    public String query3(Model model) {
		System.out.println("C");
		
		String query1 = "select Patient.pid, firstname, lastname, initialHospDate, ExpDischargeDate, HospRoom from lshoemake.Patient, lshoemake.RecordVisit,";
		query1 += " lshoemake.Appointment where RecordVisit.apptnum=Appointment.apptnum and Patient.pid=Appointment.pid";
		query1 += " and RecordVisit.actualDischargeDate is NULL";
		
		System.out.println(query1);
		List<String> q1List = this.jdbcTemplate.query(
        query1,
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String pid = rs.getString("pid");
                String first_name = rs.getString("firstname");
				String last_name = rs.getString("lastname");
				String initialHospDate = rs.getString("initialHospDate");
				String expDischargeDate = rs.getString("ExpDischargeDate");
				String hospRoom = rs.getString("HospRoom");
				
                return (pid+","+first_name+","+last_name+","+initialHospDate+","+expDischargeDate+","+hospRoom);
            }
        });
		// q1List gives list with patients currently hospitalized meaning actualDischargeDate is null
		
		String query2 = "select Patient.pid, amountdue from lshoemake.Patient, lshoemake.Payment where Patient.pid=Payment.pid and status='NOT PAID'";
		System.out.println(query2);
		List<String> q2List = this.jdbcTemplate.query(
		query2,
		new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String pid = rs.getString("pid");
                String amount = rs.getString("amount");
                return (pid+","+amount);
            }
        });
		//q2List gives list with PIDS and amounts due.
		
		// fill staying5Days list with patients from q1List if they staying >=5 days
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date initial = null;
		Date depart = null;
		List<String> staying5Days = new ArrayList<String>();
		for(int i = 0; i < q1List.size(); i++){
			String[] parts = q1List.get(i).split(",");
			try{
				initial = sdf.parse(parts[3]);
				depart = sdf.parse(parts[4]);
			}
			catch(ParseException e){
				e.printStackTrace();
			}
			long diff = depart.getTime() - initial.getTime();
			long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			if(days >= 5){
				staying5Days.add(q1List.get(i));
			}
		}
		
		List<String> retVal = new ArrayList<String>();
		for(int i = 0; i < staying5Days.size(); i++){
			float curSum = 0.0f;
			String[] parts = staying5Days.get(i).split(",");
			for(int j = 0; j < q2List.size(); j++){
				String[] partsQ2 = q2List.get(j).split(",");
				if(parts[0].equals(partsQ2[0])){
					curSum += Float.parseFloat(partsQ2[1]);
				}
			}
			if(curSum > 0.0){
				try{
					initial = sdf.parse(parts[3]);
					depart = sdf.parse(parts[4]);
				}
				catch(ParseException e){
					e.printStackTrace();
				}
				long diff = depart.getTime() - initial.getTime();
				long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				String stringDays = "" + days;
				retVal.add(parts[0]+","+parts[1]+","+parts[2]+","+stringDays+","+parts[5]+","+curSum);
			}
		}
		
        model.addAttribute("retVal", retVal);	
		
        return "/query3Results";
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 4 ////////////////////////////////////////////////////////////////
	
	@GetMapping("/query4Results")
    public String query4(Model model) {
		System.out.println("D");
		
		String query1 = "select Patient.pid, Patient.firstName, Patient.lastName, SupportStaff.eid, Pharmacist.phid from ";
		query1 += "lshoemake.Patient, lshoemake.Medicine, lshoemake.Pharmacist, lshoemake.Appointment, lshoemake.SupportStaff";
		query1 += " where Patient.pid=Medicine.pid and Pharmacist.phid=Medicine.phid and Patient.pid=Appointment.pid and";
		query1 += " SupportStaff.eid=Appointment.eid";
		
		List<String> retVal = this.jdbcTemplate.query(
        query1,
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String pid = rs.getString("pid");
                String first_name = rs.getString("firstname");
				String last_name = rs.getString("lastname");
				String eid = rs.getString("eid");
				String phid = rs.getString("phid");
                return (pid+","+first_name+","+last_name+","+eid+","+phid);
            }
        });
		
        model.addAttribute("retVal", retVal);	
		
        return "/query4Results";
	}
}