/*+----------------------------------------------------------------------
 ||
 ||  Class PharmacistController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to pharmacist table
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
 ||  Inst. Methods:  postConstruct(), pharmacistForm(Model), pharmacistSubmit(@ModelAttribute Pharmacist)
 ||						pharmacistFormDelete(Model), pharmacistDelete(@ModelAttribute Pharmacist)
 ||						pharmacistFormUpdate(Model), pharmacistUpdate(@ModelAttribute Pharmacist)
 ||
 ++-----------------------------------------------------------------------*/
package hello;
import java.util.ArrayList;
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
import java.util.List;
@Controller("/pharmacist")
public class PharmacistController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*---------------------------------------------------------------------
    |  Method pharmacistForm
    |
    |  Purpose:  Uses a Model object to expose a new Pharmacist to the view template. 
    |				The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and will be used to capture the information from the form.
    |				Used to add records to pharmacist.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addPharmacist"
    *-------------------------------------------------------------------*/
    @GetMapping("/addPharmacist")
    public String pharmacistForm(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "addPharmacist";
    }

    /*---------------------------------------------------------------------
    |  Method pharmacistSubmit
    |
    |  Purpose:  The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: pharmacist cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Pharmacist pharmacist from pharmacistFormUpdate
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/addPharmacist")
    public String pharmacistSubmit(@ModelAttribute Pharmacist pharmacist) {
    	
        jdbcTemplate.update("insert into lshoemake.pharmacist values (?, ?, ?, ?, ?, ?)", 
        		pharmacist.getPhID(), pharmacist.getLastName(), 
        		pharmacist.getFirstName(), pharmacist.getDOB(), 
        		pharmacist.getDeptID() == 0 ? null : pharmacist.getDeptID(), 
        		pharmacist.getOfficeNumber() == 0 ? null : pharmacist.getOfficeNumber());

        return "success";
    }

    /*---------------------------------------------------------------------
    |  Method pharmacistFormDelete
    |
    |  Purpose:  Uses a Model object to expose a new Pharmacist to the view template. 
    |				The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and will be used to capture the information from the form.
    |				Used to delete records from pharmacist.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "deletePharmacist"
    *-------------------------------------------------------------------*/
    @GetMapping("/deletePharmacist")
    public String pharmacistFormDelete(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "deletePharmacist";
    }

    /*---------------------------------------------------------------------
    |  Method pharmacistDelete
    |
    |  Purpose:  The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and is used to capture the information from the form
    |				and to delete from the database.
    |
    |  Pre-condition: pharmacist cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Pharmacist pharmacist from pharmacistFormDelete
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/deletePharmacist")
    public String pharmacistDelete(@ModelAttribute Pharmacist pharmacist) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (pharmacist.getPhID() != 0)
    		strs.add("phid = " + pharmacist.getPhID());
    	if (!pharmacist.getLastName().equals(""))
    		strs.add("lastname = '" + pharmacist.getLastName() + "'");
    	if (!pharmacist.getFirstName().equals(""))
    		strs.add("firstname = '" + pharmacist.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.pharmacist where " + String.join(" and ", strs));

      return "success";
    }
    
    /*---------------------------------------------------------------------
    |  Method pharmacistFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Pharmacist to the view template. 
    |				The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and will be used to capture the information from the form.
    |				Used to update records in pharmacist.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updatePharmacist"
    *-------------------------------------------------------------------*/
    @GetMapping("/updatePharmacist")
    public String pharmacistFormUpdate(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "updatePharmacist";
    }

    /*---------------------------------------------------------------------
    |  Method pharmacistUpdate
    |
    |  Purpose:  The Pharmacist object in the following code contains fields such
    |				that correspond to the form fields in the pharmacist view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: pharmacist cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Pharmacist pharmacist from pharmacistFormUpdate
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/updatePharmacist")
    public String pharmacistUpdate(@ModelAttribute Pharmacist pharmacist) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (!pharmacist.getLastName().equals(""))
    		strs.add("lastname = '" + pharmacist.getLastName() + "'");
    	if (!pharmacist.getFirstName().equals(""))
    		strs.add("firstname = '" + pharmacist.getFirstName() + "'");
    	if (pharmacist.getDeptID() != 0)
    		strs.add("deptid = " + pharmacist.getDeptID());
    	if (pharmacist.getOfficeNumber() != 0)
    		strs.add("officenum = " + pharmacist.getOfficeNumber());
    		
    	String stmt = String.format("update lshoemake.pharmacist set %s where phid = %s",
    					String.join(", ", strs), pharmacist.getPhID());

    	jdbcTemplate.update(stmt);
        return "success";
    }

}
