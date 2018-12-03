package hello;

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

    @GetMapping("/addDoctor")
    public String DoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "addDoctor";
    }

    @PostMapping("/addDoctor")
    public String DoctorSubmit(@ModelAttribute Doctor Doctor) {
        jdbcTemplate.update("insert into lshoemake.doctor values (?, ?, ?, ?, ?, ?, ?)", 
        		Doctor.getDID(), Doctor.getLastName(), Doctor.getFirstName(),
        		Doctor.getDOB(), Doctor.getStatus(), 
        		Doctor.getDeptID() == 0 ? "NULL" : Doctor.getDeptID(),
        		Doctor.getOfficeNumber() == 0 ? "NULL" : Doctor.getOfficeNumber());
        return "resultDoctor";
    }

    @GetMapping("/deleteDoctor")
    public String DoctorFormDelete(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "deleteDoctor";
    }

    @PostMapping("/deleteDoctor") // FIX
    public String DoctorDelete(@ModelAttribute Doctor Doctor) {
      jdbcTemplate.update("delete from lshoemake.doctor where firstname = ? and lastname = ?", 
    		  Doctor.getFirstName(), Doctor.getLastName());

      return "deleteDoctorResult";
    }

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.doctor",
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
