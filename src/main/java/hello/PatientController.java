package hello;
import java.util.ArrayList;
import java.util.List;
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

@Controller("/patient")
public class PatientController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/addPatient")
    public String PatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @PostMapping("/addPatient")
    public String PatientSubmit(@ModelAttribute Patient patient) {
    	
        jdbcTemplate.update("insert into lshoemake.patient values (?, ?, ?, ?, ?, ?, ?)", 
        	patient.getPID(), patient.getLastName(), patient.getFirstName(),
        	patient.getGender().equals("") ? "NULL" : patient.getGender(), 
        	patient.getDOB(), patient.getAddress(),
        	patient.getContactNumber());

        return "resultPatient";
    }

    @GetMapping("/deletePatient")
    public String PatientFormDelete(Model model) {
        model.addAttribute("patient", new Patient());
        return "deletePatient";
    }

    @PostMapping("/deletePatient")
    public String PatientDelete(@ModelAttribute Patient patient) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (patient.getPID() != 0)
    		strs.add("pid = " + patient.getPID());
    	if (patient.getLastName() != null)
    		strs.add("lastname = '" + patient.getLastName() + "'");
    	if (patient.getFirstName() != null)
    		strs.add("firstname = '" + patient.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.patient where " + String.join(" and ", strs));


      return "deletePatientResult";
    }
    
    @GetMapping("/updatePatient")
    public String PatientFormUpdate(Model model) {
        model.addAttribute("patient", new Patient());
        return "updatePatient";
    }

    @PostMapping("/updatePatient")
    public String PatientUpdate(@ModelAttribute Patient patient) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (patient.getLastName() != null)
    		strs.add("lastname = '" + patient.getLastName() + "'");
    	if (patient.getFirstName() != null)
    		strs.add("firstname = '" + patient.getFirstName() + "'");
    	if (!patient.getGender().equals(""))
    		strs.add("gender = '" + patient.getGender() + "'");
    	if (patient.getAddress() != null)
    		strs.add("address = '" + patient.getAddress() + "'");
    	if (patient.getContactNumber() != null)
    		strs.add("contactnum = '" + patient.getContactNumber() + "'");
    		
    	String stmt = String.format("update lshoemake.patient set %s where pid = %s", 
    			String.join(", ", strs), patient.getPID());
        jdbcTemplate.update(stmt);

        return "updatePatientResult";
    }

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.patient",
//        new RowMapper<String>() {
//            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//                String first_name = rs.getString("first_name");
//                String last_name = rs.getString("last_name");
//                return (first_name + " " + last_name);
//            }
//        });
//        model.addAttribute("names", allNames);
//        return "/queryResults";
//    }

}
