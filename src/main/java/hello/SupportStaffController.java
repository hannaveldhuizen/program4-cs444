package hello;

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
    public String StaffSubmit(@ModelAttribute SupportStaff Staff) {
        jdbcTemplate.update("insert into lshoemake.SupportStaff values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
        		Staff.getEID(), Staff.getLastName(), Staff.getFirstName(),
        		Staff.getDOB(), Staff.getSalary() == 0 ? "NULL" : Staff.getSalary(), 
        		Staff.getDeptID() == 0 ? "NULL" : Staff.getDeptID(),
        		Staff.getOfficeNumber() == 0 ? "NULL" : Staff.getOfficeNumber(),
        		Staff.getJobTitle(), Staff.getGender(), Staff.getContactNumber());

        return "resultStaff";
    }

    @GetMapping("/deleteStaff")
    public String StaffFormDelete(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "deleteStaff";
    }

    @PostMapping("/deleteStaff") // FIX
    public String StaffDelete(@ModelAttribute SupportStaff Staff) {
      jdbcTemplate.update("delete from lshoemake.SupportStaff where first_name = ? and last_name = ?", 
    		  Staff.getFirstName(), Staff.getLastName());

      return "deleteStaffResult";
    }

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.staff",
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
