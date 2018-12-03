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

    @GetMapping("/addStaff")
    public String StaffForm(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "addStaff";
    }

    @PostMapping("/addStaff")
    public String StaffSubmit(@ModelAttribute SupportStaff staff) {
        jdbcTemplate.update("insert into lshoemake.SupportStaff values (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
        		staff.getEID(), staff.getLastName(), staff.getFirstName(),
        		staff.getDOB(), staff.getSalary() == 0 ? "NULL" : staff.getSalary(), 
        		staff.getDeptID() == 0 ? "NULL" : staff.getDeptID(),
        		staff.getJobTitle(), staff.getGender(), staff.getContactNumber());

        return "resultStaff";
    }

    @GetMapping("/deleteStaff")
    public String StaffFormDelete(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "deleteStaff";
    }

    @PostMapping("/deleteStaff")
    public String StaffDelete(@ModelAttribute SupportStaff staff) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (staff.getEID() != 0)
    		strs.add("eid = " + staff.getEID());
    	if (staff.getLastName() != null)
    		strs.add("lastname = " + staff.getLastName());
    	if (staff.getFirstName() != null)
    		strs.add("firstname = " + staff.getFirstName());
    	
    	jdbcTemplate.update("delete from lshoemake.supportstaff where ?", String.join(" and ", strs));


      return "deleteStaffResult";
    }
    
    @GetMapping("/updateStaff")
    public String StaffFormUpdate(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "updateStaff";
    }

    @PostMapping("/updateStaff")
    public String StaffUpdate(@ModelAttribute SupportStaff staff) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (staff.getLastName() != null)
    		strs.add("lastname = " + staff.getLastName());
    	if (staff.getFirstName() != null)
    		strs.add("firstname = " + staff.getFirstName());
    	if (staff.getSalary() != 0)
    		strs.add("salary = " + staff.getSalary());
    	if (staff.getDeptID() != 0)
    		strs.add("deptid = " + staff.getDeptID());
    	if (staff.getJobTitle() != null)
    		strs.add("jobtitle = " + staff.getJobTitle());
    	if (staff.getGender() != null)
    		strs.add("gender = " + staff.getGender());
    	if (staff.getContactNumber() != null)
    		strs.add("contactnum = " + staff.getContactNumber());
    		
        jdbcTemplate.update("update lshoemake.supportstaff set ? where ?",  
        		String.join(", ", strs));

        return "updateStaffResult";
    }
   

}
