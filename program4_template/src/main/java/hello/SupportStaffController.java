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

@Controller
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
      jdbcTemplate.update("delete from lshoemake.SupportStaff "
      		+ "where eid = ? and firstname = ? and lastname = ?", 
    		  staff.getEID(), staff.getFirstName(), staff.getLastName());

      return "deleteStaffResult";
    }

    // FIX
    @GetMapping("/queryResults")
    public String queryResults(Model model) {
      List<String> allNames = this.jdbcTemplate.query(
        "select * from lshoemake.staff",
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String first_name = rs.getString("firstname");
                String last_name = rs.getString("lastname");
                return (first_name + " " + last_name);
            }
        });
        model.addAttribute("names", allNames);
        return "/queryResults";
    }

}
