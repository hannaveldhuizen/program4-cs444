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

    @GetMapping("/addNurse")
    public String NurseForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "addNurse";
    }

    @PostMapping("/addNurse")
    public String NurseSubmit(@ModelAttribute Nurse nurse) {
    	
        jdbcTemplate.update("insert into lshoemake.nurse values (?, ?, ?, ?, ?, ?)", 
        		nurse.getNID(), nurse.getLastName(), nurse.getFirstName(), nurse.getDOB(), 
        		nurse.getDeptID() == 0 ? null : nurse.getDeptID(), 
        		nurse.getRoomNumber() == 0 ? null : nurse.getRoomNumber());
        return "resultNurse";
    }

    @GetMapping("/deleteNurse")
    public String NurseFormDelete(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "deleteNurse";
    }

    @PostMapping("/deleteNurse")
    public String NurseDelete(@ModelAttribute Nurse nurse) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (nurse.getNID() != 0)
    		strs.add("nid = " + nurse.getNID());
    	if (nurse.getLastName() != null)
    		strs.add("lastname = '" + nurse.getLastName() + "'");
    	if (nurse.getFirstName() != null)
    		strs.add("firstname = '" + nurse.getFirstName() +"'");
    	
    	jdbcTemplate.update("delete from lshoemake.nurse where " + String.join(" and ", strs));

      return "deleteNurseResult";
    }
    
    @GetMapping("/updateNurse")
    public String NurseFormUpdate(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "updateNurse";
    }

    @PostMapping("/updateNurse")
    public String NurseUpdate(@ModelAttribute Nurse nurse) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (nurse.getLastName() != null)
    		strs.add("lastname = '" + nurse.getLastName() + "'");
    	if (nurse.getFirstName() != null)
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

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.nurse",
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
