/*+----------------------------------------------------------------------
 ||
 ||  Class PatientController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to patient table
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
 ||  Inst. Methods:  postConstruct(), patientForm(Model), patientSubmit(@ModelAttribute Patient)
 ||						patientFormDelete(Model), patientDelete(@ModelAttribute Patient)
 ||						patientFormUpdate(Model), patientUpdate(@ModelAttribute Patient)
 ||
 ++-----------------------------------------------------------------------*/
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

    /*---------------------------------------------------------------------
    |  Method patientForm
    |
    |  Purpose:  Uses a Model object to expose a new Patient to the view template. 
    |				The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and will be used to capture the information from the form.
    |				Used to add records to patient.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addPatient"
    *-------------------------------------------------------------------*/
    @GetMapping("/addPatient")
    public String patientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    /*---------------------------------------------------------------------
    |  Method patientSubmit
    |
    |  Purpose:  The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: patient cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Patient patient from patientForm
    |
    |  Returns:  String "resultPatient"
    *-------------------------------------------------------------------*/
    @PostMapping("/addPatient")
    public String patientSubmit(@ModelAttribute Patient patient) {
    	
        jdbcTemplate.update("insert into lshoemake.patient values (?, ?, ?, ?, ?, ?, ?)", 
        	patient.getPID(), patient.getLastName(), patient.getFirstName(),
        	patient.getGender().equals("") ? null : patient.getGender(), 
        	patient.getDOB(), patient.getAddress(),
        	patient.getContactNumber());

        return "resultPatient";
    }

    /*---------------------------------------------------------------------
    |  Method patientFormDelete
    |
    |  Purpose:  Uses a Model object to expose a new Patient to the view template. 
    |				The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and will be used to capture the information from the form.
    |				used to delete records from patient.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "deletePatient"
    *-------------------------------------------------------------------*/
    @GetMapping("/deletePatient")
    public String patientFormDelete(Model model) {
        model.addAttribute("patient", new Patient());
        return "deletePatient";
    }

    /*---------------------------------------------------------------------
    |  Method patientDelete
    |
    |  Purpose:  The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and is used to capture the information from the form
    |				and to delete from the database.
    |
    |  Pre-condition: patient cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Patient patient from patientFormDelete
    |
    |  Returns:  String "deletePatientResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/deletePatient")
    public String patientDelete(@ModelAttribute Patient patient) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (patient.getPID() != 0)
    		strs.add("pid = " + patient.getPID());
    	if (!patient.getLastName().equals(""))
    		strs.add("lastname = '" + patient.getLastName() + "'");
    	if (!patient.getFirstName().equals(""))
    		strs.add("firstname = '" + patient.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.patient where " + String.join(" and ", strs));

      return "deletePatientResult";
    }
    
    /*---------------------------------------------------------------------
    |  Method patientFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Patient to the view template. 
    |				The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and will be used to capture the information from the form.
    |				used to update records in patient.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updatePatient"
    *-------------------------------------------------------------------*/
    @GetMapping("/updatePatient")
    public String patientFormUpdate(Model model) {
        model.addAttribute("patient", new Patient());
        return "updatePatient";
    }

    /*---------------------------------------------------------------------
    |  Method patientUpdate
    |
    |  Purpose:  The Patient object in the following code contains fields such
    |				that correspond to the form fields in the patient view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: patient cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Patient patient from patientFormUpdate
    |
    |  Returns:  String "updatePatientResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/updatePatient")
    public String patientUpdate(@ModelAttribute Patient patient) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (!patient.getLastName().equals(""))
    		strs.add("lastname = '" + patient.getLastName() + "'");
    	if (!patient.getFirstName().equals(""))
    		strs.add("firstname = '" + patient.getFirstName() + "'");
    	if (!patient.getGender().equals(""))
    		strs.add("gender = '" + patient.getGender() + "'");
    	if (!patient.getAddress().equals(""))
    		strs.add("address = '" + patient.getAddress() + "'");
    	if (!patient.getContactNumber().equals(""))
    		strs.add("contactnum = '" + patient.getContactNumber() + "'");
    		
    	String stmt = String.format("update lshoemake.patient set %s where pid = %s", 
    			String.join(", ", strs), patient.getPID());
        jdbcTemplate.update(stmt);

        return "updatePatientResult";
    }

}
