/*+----------------------------------------------------------------------
 ||
 ||  Class NurseController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to nurse table
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
 ||  Inst. Methods:  postConstruct(), nurseForm(Model), nurseSubmit(@ModelAttribute Nurse)
 ||						nurseFormDelete(Model), nurseDelete(@ModelAttribute Nurse)
 ||						nurseFormUpdate(Model), nurseUpdate(@ModelAttribute Nurse)
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

@Controller("/nurse")
public class NurseController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*---------------------------------------------------------------------
    |  Method nurseForm
    |
    |  Purpose:  Uses a Model object to expose a new Nurse to the view template. 
    |				The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and will be used to capture the information from the form.
    |				Used to add records to nurse relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addNurse"
    *-------------------------------------------------------------------*/
    @GetMapping("/addNurse")
    public String nurseForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "addNurse";
    }

    /*---------------------------------------------------------------------
    |  Method nurseSubmit
    |
    |  Purpose:  The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: nurse cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Nurse nurse from nurseForm
    |
    |  Returns:  String "resultNurse"
    *-------------------------------------------------------------------*/
    @PostMapping("/addNurse")
    public String nurseSubmit(@ModelAttribute Nurse nurse) {
    	
        jdbcTemplate.update("insert into lshoemake.nurse values (?, ?, ?, ?, ?, ?)", 
        		nurse.getNID(), nurse.getLastName(), nurse.getFirstName(), nurse.getDOB(), 
        		nurse.getDeptID() == 0 ? null : nurse.getDeptID(), 
        		nurse.getRoomNumber() == 0 ? null : nurse.getRoomNumber());
        return "resultNurse";
    }

    /*---------------------------------------------------------------------
    |  Method nurseFormDelete
    |
    |  Purpose:  Uses a Model object to expose a new Nurse to the view template. 
    |				The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and will be used to capture the information from the form.
    |				used to delete records in nurse relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "deleteNurse"
    *-------------------------------------------------------------------*/
    @GetMapping("/deleteNurse")
    public String nurseFormDelete(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "deleteNurse";
    }

    /*---------------------------------------------------------------------
    |  Method nurseDelete
    |
    |  Purpose:  The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and is used to capture the information from the form
    |				and to delete from the database.
    |
    |  Pre-condition: nurse cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Nurse nurse from nurseFormDelete
    |
    |  Returns:  String "deleteNurseResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/deleteNurse")
    public String nurseDelete(@ModelAttribute Nurse nurse) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (nurse.getNID() != 0)
    		strs.add("nid = " + nurse.getNID());
    	if (!nurse.getLastName().equals(""))
    		strs.add("lastname = '" + nurse.getLastName() + "'");
    	if (!nurse.getFirstName().equals(""))
    		strs.add("firstname = '" + nurse.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.nurse where " + String.join(" and ", strs));

      return "deleteNurseResult";
    }
    
    /*---------------------------------------------------------------------
    |  Method nurseFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Nurse to the view template. 
    |				The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and will be used to capture the information from the form.
    |				Used to update records in nurse relation.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updateNurse"
    *-------------------------------------------------------------------*/
    @GetMapping("/updateNurse")
    public String nurseFormUpdate(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "updateNurse";
    }

    /*---------------------------------------------------------------------
    |  Method nurseUpdate
    |
    |  Purpose:  The Nurse object in the following code contains fields such
    |				that correspond to the form fields in the nurse view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: nurse cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Nurse nurse from nurseFormUpdate
    |
    |  Returns:  String "updateNurseResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/updateNurse")
    public String nurseUpdate(@ModelAttribute Nurse nurse) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (!nurse.getLastName().equals(""))
    		strs.add("lastname = '" + nurse.getLastName() + "'");
    	if (!nurse.getFirstName().equals(""))
    		strs.add("firstname = '" + nurse.getFirstName() + "'");
    	if (nurse.getDeptID() != 0)
    		strs.add("deptid = " + nurse.getDeptID());
    	if (nurse.getRoomNumber() != 0)
    		strs.add("officenum = " + nurse.getRoomNumber());
    		
        String stmt = String.format("update lshoemake.nurse set %s where nid = %s",  
        				String.join(", ", strs), nurse.getNID());
        jdbcTemplate.update(stmt);

        return "updateNurseResult";
    }

}
