/* 
 * Author: Chris Quevedo
 * Course: CSC 460
 * Assignment: Prog4
 * Instructors: Lester McCann
 * Graders: Terrence Lim, Bailey Nottingham
 * Due date: 12/4/2018
 * Program Language: Java 1.8
 *
 * QueryController.java -- This file handles queries.  Will get input from
 * web application should a quey require user input and will perform the query and
 * give resulting string(s) to web app to display.
 */
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
	
	/**
	 * public String query1 -- 
	 * This method takes parameters firstName, lastName, and DOB of a patient and 
	 * runs a query to display patient id, patient name, patient gender, DOB, most recent date
	 * of visit, reason for last visit, treatmentMethod, and doctor id of last visit
	 * @param firstName - String firstName of patient
	 * @param lastName - String lastName of Patient
	 * @param DOB - String DOB of patient MM/dd/yyyy
	 */
	@GetMapping("/query1Results")
    public String query1(@RequestParam(value = "search", required = true)
    						String firstName,@RequestParam(value = "search1", required = true) String lastName, 
							@RequestParam(value = "search2", required = true) String DOB, Model model) {	
	
		String fname = firstName;	// firstName of patient
		String lname = lastName;	// lastName of patient
		String DoB = DOB;			// DOB of patient
		
		String query = "select Patient.pid, firstname, lastname, gender, DOB, initialHospDate, reason, treatmentMethod, did ";
		query += "from lshoemake.Patient, lshoemake.RecordVisit, lshoemake.Appointment where RecordVisit.apptnum=Appointment.apptnum and ";
		query += "Appointment.pid=Patient.pid and firstname='" + fname + "' and lastname='" + lname + "' and DOB='" + DoB + "'";
		
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
		
		// intial query returns list of patient's visits so must extract most recent using Java Date
		
		int maxIndex = 0;											// index in allNames where initialHospDate is most in future
		Date maxDate = null;										// the most future date
		Date curDate = null;										// the current date to compare with maxDate
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");	// to convert dateStrings to Date objects
		
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
	
		String retVal = "";						// the String with the fields we want to return		
		if(allNames.size() != 0){
			retVal += allNames.get(maxIndex);
		}
        model.addAttribute("retVal", retVal);	
		
        return "/query1Results";
    }
	
	// QUERY 2 ////////////////////////////////////////////////////////////////
	
	/**
	 * public String query2 -- 
	 * This method takes parameter String deptName and will use it to see the
	 * doctors in that department.  Displays doctor's name, officeNumber, 
	 * and building name.
	 * @param deptName - String name of department to query
	 */
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
	
	// QUERY 3 ////////////////////////////////////////////////////////////////
	
	/**
	 * public String query3 -- 
	 * This method runs a query to list patients who are currently hospitalized,
	 * expected to be hospitalized more than 5 days and have fees to be paid.
	 * Displays PID, Patient name, number of expected hospital days, room number,
	 * and sum of the fees.
	 */
	@GetMapping("/query3Results")
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
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");	// used to convert Stringdate to Date
		Date initial = null;										// initial hospital date
		Date depart = null;											// expected departure date
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
		// staying5Days now has entries from q1List but they staying >= 5 days
		
		// populate retVal with necessary parameters and include sum 
		// of patients fees.
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
	
	// QUERY 4 ////////////////////////////////////////////////////////////////
	
	/**
	 * public String query4 -- 
	 * This method runs a query to list patients who are currently taking medicine.
	 * Will display PID, Patient name, EID of receptionist that helped patient, and
	 * Phid of pharmacist giving patient medicine.
	 */
	@GetMapping("/query4Results")
    public String query4(Model model) {
		
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