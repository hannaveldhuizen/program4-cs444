/*+----------------------------------------------------------------------
 ||
 ||  Class SupportStaffController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for updates to supportstaff table
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
 ||  Inst. Methods:  postConstruct(), staffForm(Model), staffSubmit(@ModelAttribute SupportStaff)
 ||						staffFormDelete(Model), staffDelete(@ModelAttribute SupportStaff)
 ||						staffFormUpdate(Model), staffUpdate(@ModelAttribute SupportStaff)
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

@Controller("/supportstaff")
public class SupportStaffController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*---------------------------------------------------------------------
    |  Method staffForm
    |
    |  Purpose:  Uses a Model object to expose a new Staff to the view template. 
    |				The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and will be used to capture the information from the form.
    |				Used to add records to supportstaff.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addStaff"
    *-------------------------------------------------------------------*/
    @GetMapping("/addStaff")
    public String staffForm(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "addStaff";
    }

    /*---------------------------------------------------------------------
    |  Method staffSubmit
    |
    |  Purpose:  The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: staff cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Staff staff from staffForm
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/addStaff")
    public String staffSubmit(@ModelAttribute SupportStaff staff) {
        jdbcTemplate.update("insert into lshoemake.SupportStaff values (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
        		staff.getEID(), staff.getLastName(), staff.getFirstName(),
        		staff.getDOB(), staff.getSalary() == 0 ? null : staff.getSalary(), 
        		staff.getDeptID() == 0 ? null : staff.getDeptID(),
        		staff.getJobTitle(), 
        		staff.getGender().equals("") ? null : staff.getGender(), 
        		staff.getContactNumber());

        return "success";
    }

    /*---------------------------------------------------------------------
    |  Method staffFormDelete
    |
    |  Purpose:  Uses a Model object to expose a new Staff to the view template. 
    |				The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and will be used to capture the information from the form.
    |				Used to delete records from supportstaff.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "deleteStaff"
    *-------------------------------------------------------------------*/
    @GetMapping("/deleteStaff")
    public String staffFormDelete(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "deleteStaff";
    }

    /*---------------------------------------------------------------------
    |  Method staffDelete
    |
    |  Purpose:  The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: staff cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Staff staff from staffFormDelete
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/deleteStaff")
    public String staffDelete(@ModelAttribute SupportStaff staff) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (staff.getEID() != 0)
    		strs.add("eid = " + staff.getEID());
    	if (!staff.getLastName().equals(""))
    		strs.add("lastname = '" + staff.getLastName() + "'");
    	if (!staff.getFirstName().equals(""))
    		strs.add("firstname = '" + staff.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.supportstaff where " + String.join(" and ", strs));


      return "success";
    }
    
    /*---------------------------------------------------------------------
    |  Method staffFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Staff to the view template. 
    |				The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and will be used to capture the information from the form.
    |				Used to update records in supportstaff.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updateStaff"
    *-------------------------------------------------------------------*/
    @GetMapping("/updateStaff")
    public String staffFormUpdate(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "updateStaff";
    }

    /*---------------------------------------------------------------------
    |  Method staffUpdate
    |
    |  Purpose:  The Staff object in the following code contains fields such
    |				that correspond to the form fields in the supportstaff view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: staff cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Staff staff from staffFormUpdate
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/updateStaff")
    public String staffUpdate(@ModelAttribute SupportStaff staff) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (!staff.getLastName().equals(""))
    		strs.add("lastname = '" + staff.getLastName() + "'");
    	if (!staff.getFirstName().equals(""))
    		strs.add("firstname = '" + staff.getFirstName() + "'");
    	if (staff.getSalary() != 0)
    		strs.add("salary = " + staff.getSalary());
    	if (staff.getDeptID() != 0)
    		strs.add("deptid = " + staff.getDeptID());
    	if (!staff.getJobTitle().equals(""))
    		strs.add("jobtitle = '" + staff.getJobTitle() + "'");
    	if (!staff.getGender().equals(""))
    		strs.add("gender = '" + staff.getGender());
    	if (!staff.getContactNumber().equals(""))
    		strs.add("contactnum = '" + staff.getContactNumber() + "'");
    	
    	String stmt = String.format("update lshoemake.supportstaff set %s where eid = %s", 
    			String.join(", ", strs), staff.getEID());
        jdbcTemplate.update(stmt);

        return "success";
    }
   

}
