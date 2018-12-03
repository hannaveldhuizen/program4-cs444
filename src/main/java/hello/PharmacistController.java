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

    @GetMapping("/addPharmacist")
    public String PharmacistForm(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "addPharmacist";
    }

    @PostMapping("/addPharmacist")
    public String PharmacistSubmit(@ModelAttribute Pharmacist pharmacist) {
    	
        jdbcTemplate.update("insert into lshoemake.pharmacist values (?, ?, ?, ?, ?, ?)", 
        		pharmacist.getPhID(), pharmacist.getLastName(), 
        		pharmacist.getFirstName(), pharmacist.getDOB(), 
        		pharmacist.getDeptID() == 0 ? null : pharmacist.getDeptID(), 
        		pharmacist.getOfficeNumber() == 0 ? null : pharmacist.getOfficeNumber());

        return "resultPharmacist";
    }

    @GetMapping("/deletePharmacist")
    public String PharmacistFormDelete(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "deletePharmacist";
    }

    @PostMapping("/deletePharmacist")
    public String PharmacistDelete(@ModelAttribute Pharmacist pharmacist) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (pharmacist.getPhID() != 0)
    		strs.add("phid = " + pharmacist.getPhID());
    	if (pharmacist.getLastName() != null)
    		strs.add("lastname = '" + pharmacist.getLastName() + "'");
    	if (pharmacist.getFirstName() != null)
    		strs.add("firstname = '" + pharmacist.getFirstName() + "'");
    	
    	jdbcTemplate.update("delete from lshoemake.pharmacist where " + String.join(" and ", strs));


      return "deletePharmacistResult";
    }
    @GetMapping("/updatePharmacist")
    public String PharmacistFormUpdate(Model model) {
        model.addAttribute("pharmacist", new Pharmacist());
        return "updatePharmacist";
    }

    @PostMapping("/updatePharmacist")
    public String PharmacistUpdate(@ModelAttribute Pharmacist pharmacist) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (pharmacist.getLastName() != null)
    		strs.add("lastname = '" + pharmacist.getLastName() + "'");
    	if (pharmacist.getFirstName() != null)
    		strs.add("firstname = '" + pharmacist.getFirstName() + "'");
    	if (pharmacist.getDeptID() != 0)
    		strs.add("deptid = " + pharmacist.getDeptID());
    	if (pharmacist.getOfficeNumber() != 0)
    		strs.add("officenum = " + pharmacist.getOfficeNumber());
    		
    	String stmt = String.format("update lshoemake.pharmacist set %s where phid = %s",
    					String.join(", ", strs), pharmacist.getPhID());

    	jdbcTemplate.update(stmt);
        return "updatePharmacistResult";
    }

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.pharmacist",
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
