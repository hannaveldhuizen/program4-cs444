/*+----------------------------------------------------------------------
 ||
 ||  Class DepartmentController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to department table
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
 ||  Inst. Methods:  postConstruct()
 ||						departmentFormUpdate(Model)
 ||						departmentUpdate(@ModelAttribute Department)
 ||
 ++-----------------------------------------------------------------------*/
package hello;
import java.util.List;
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

@Controller("/department")
public class DepartmentController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /*---------------------------------------------------------------------
    |  Method departmentFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Department to the view template. 
    |				The Department object in the following code contains fields such
    |				that correspond to the form fields in the department view,
    |				and will be used to capture the information from the form.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updateDepartment"
    *-------------------------------------------------------------------*/
    @GetMapping("/updateDepartment")
    public String departmentFormUpdate(Model model) {
        model.addAttribute("department", new Department());
        return "updateDepartment";
    }

    /*---------------------------------------------------------------------
    |  Method departmentUpdate
    |
    |  Purpose:  The Department object in the following code contains fields such
    |				that correspond to the form fields in the department view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: department cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Department department from departmentFormUpdate
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/updateDepartment")
    public String departmentUpdate(@ModelAttribute Department department) {

    	List<String> strs = new ArrayList<String>();

    	if (!department.getDeptName().equals(""))
    		strs.add("deptname = '" + department.getDeptName() + "'");
    	if (!department.getBuildingName().equals(""))
    		strs.add("buildingname = '" + department.getBuildingName() + "'");
    	if (department.getOfficeNum() != 0)
    		strs.add("officenum = " + department.getOfficeNum());
    	
    	String stmt = String.format("update lshoemake.department set %s where deptid = %s",
        				String.join(", ", strs), department.getDeptID());
        jdbcTemplate.update(stmt);
        return "success";
    }

}

