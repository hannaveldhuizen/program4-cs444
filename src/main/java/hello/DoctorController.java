/*+----------------------------------------------------------------------
 ||
 ||  Class DoctorController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to doctor table
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
 ||  Inst. Methods:  postConstruct(), doctorForm(Model), doctorSubmit(@ModelAttribute Doctor)
 ||						doctorFormDelete(Model), doctorDelete(@ModelAttribute Doctor)
 ||						doctorFormUpdate(Model), doctorUpdate(@ModelAttribute Doctor)
 ||
 ++-----------------------------------------------------------------------*/
package hello;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller("/doctor")
public class DoctorController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*---------------------------------------------------------------------
    |  Method doctorForm
    |
    |  Purpose:  Uses a Model object to expose a new Doctor to the view template. 
    |				The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and will be used to capture the information from the form.
    |				Used to add a record to doctor relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addDoctor"
    *-------------------------------------------------------------------*/
    @GetMapping("/addDoctor")
    public String doctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "addDoctor";
    }

    /*---------------------------------------------------------------------
    |  Method doctorSubmit
    |
    |  Purpose:  The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: doctor cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Doctor doctor from doctorForm
    |
    |  Returns:  String "resultDoctor"
    *-------------------------------------------------------------------*/
    @PostMapping("/addDoctor")
    public String doctorSubmit(@ModelAttribute Doctor doctor) {
    	jdbcTemplate.update("insert into lshoemake.doctor values (?, ?, ?, ?, ?, ?, ?)", 
        		doctor.getDID(), doctor.getLastName(), doctor.getFirstName(),
        		doctor.getDOB(), doctor.getStatus().equals("") ? null : doctor.getStatus(), 
        		doctor.getDeptID() == 0 ? null : doctor.getDeptID(),
        		doctor.getOfficeNumber() == 0 ? null : doctor.getOfficeNumber());
        return "resultDoctor";
    }

    /*---------------------------------------------------------------------
    |  Method doctorFormDelete
    |
    |  Purpose:  Uses a Model object to expose a new Doctor to the view template. 
    |				The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and will be used to capture the information from the form.
    |				Used to delete a record from doctor relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "deleteDoctor"
    *-------------------------------------------------------------------*/
    @GetMapping("/deleteDoctor")
    public String doctorFormDelete(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "deleteDoctor";
    }

    /*---------------------------------------------------------------------
    |  Method doctorDelete
    |
    |  Purpose:  The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and is used to capture the information from the form
    |				and to delete doctor recs from the database.
    |
    |  Pre-condition: doctor cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Doctor doctor from doctorFormDelete
    |
    |  Returns:  String "deleteDoctorResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/deleteDoctor")
    public String doctorDelete(@ModelAttribute Doctor doctor) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (doctor.getDID() != 0)
    		strs.add("did = " + doctor.getDID());
    	if (doctor.getLastName() != null)
    		strs.add("lastname = '" + doctor.getLastName() + "'");
    	if (doctor.getFirstName() != null)
    		strs.add("firstname = '" + doctor.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.doctor where " + String.join(" and ", strs));

      return "deleteDoctorResult";
    }
    
    /*---------------------------------------------------------------------
    |  Method doctorFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Doctor to the view template. 
    |				The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and will be used to capture the information from the form.
    |				Used to update a record in doctor relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updateDoctor"
    *-------------------------------------------------------------------*/
    @GetMapping("/updateDoctor")
    public String doctorFormUpdate(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "updateDoctor";
    }

    /*---------------------------------------------------------------------
    |  Method doctorUpdate
    |
    |  Purpose:  The Doctor object in the following code contains fields such
    |				that correspond to the form fields in the doctor view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: doctor cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Doctor doctor from doctorFormUpdate
    |
    |  Returns:  String "updateDoctorResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/updateDoctor")
    public String doctorUpdate(@ModelAttribute Doctor doctor) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (doctor.getLastName() != null)
    		strs.add("lastname = '" + doctor.getLastName() + "'");
    	if (doctor.getFirstName() != null)
    		strs.add("firstname = '" + doctor.getFirstName() + "'");
    	if (!doctor.getStatus().equals(""))
    		strs.add("status = '" + doctor.getStatus() + "'");
    	if (doctor.getDeptID() != 0)
    		strs.add("deptid = " + doctor.getDeptID());
    	if (doctor.getOfficeNumber() != 0)
    		strs.add("officenum = " + doctor.getOfficeNumber());
    		
        String stmt = String.format("update lshoemake.doctor set %s where did = %s",  
        				String.join(", ", strs), doctor.getDID());
        jdbcTemplate.update(stmt);

        return "updateDoctorResult";
    }

}
