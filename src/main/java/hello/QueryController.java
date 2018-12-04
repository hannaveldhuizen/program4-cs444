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
	
	//@GetMapping("/query1")
    public String query1Form(Model model) {
        model.addAttribute("patient", new Patient());
        return "query1";
    }
	
	//@PostMapping("/query1")
    public String query1(@ModelAttribute Patient patient) {	
	
		String fname = patient.getFirstName();
		String lname = patient.getLastName();
		String DOB = patient.getDOB();
		
		String query = "select Patient.pid, firstname, lastname, gender, DOB, initialHospDate, reason, treatmentMethod, did ";
		query += "from lshoemake.Patient, lshoemake.RecordVisit, lshoemake.Appointment where RecordVisit.apptnum=Appointment.apptnum and ";
		query += "Appointment.pid=Patient.pid and firstname='" + fname + "' and lastname='" + lname + "' and DOB='" + DOB + "'";
		
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
		
		List<String> retVal = new ArrayList<String>();
		if(allNames.size() != 0){
			retVal.add(allNames.get(maxIndex));
		}
		
        //model.addAttribute("retVal", retVal);	
		
        return "/query1Results";
    }
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 2 ////////////////////////////////////////////////////////////////
	
	//@GetMapping("/query2")
    public String query2Form(Model model) {
        model.addAttribute("department", new Department());
        return "query2";
    }
	
	//@PostMapping("/query2")
    public String query2(@ModelAttribute Department department) {
		String dep = department.getDeptName();
		
		String query = "select Doctor.firstname, Doctor.lastname, Department.officenum, buildingname from lshoemake.Doctor, lshoemake.Department ";
		query += "where Doctor.DeptId=Department.DeptId and DeptName='" + dep + "'";
		
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
		
        //model.addAttribute("names", allNames);	
		
        return "/query2Results";
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 3 ////////////////////////////////////////////////////////////////
	
	//@GetMapping("/query3")
    public String query3(Model model) {
		
		String query1 = "select Patient.pid, firstname, lastname, initialHospDate, ExpDischargeDate, HospRoom from lshoemake.Patient, lshoemake.RecordVisit,";
		query1 += " lshoemake.Appointment where RecordVisit.apptnum=Appointment.apptnum and Patient.pid=Appointment.pid";
		query1 += " and RecordVisit.actualDischargeDate is NULL";
		
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
		
        //model.addAttribute("retVal", retVal);	
		
        return "/query3Results";
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	// QUERY 4 ////////////////////////////////////////////////////////////////
	
}