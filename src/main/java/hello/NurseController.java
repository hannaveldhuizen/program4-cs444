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
        		nurse.getDeptID() == 0 ? "NULL" : nurse.getDeptID(), 
        		nurse.getRoomNumber() == 0 ? "NULL" : nurse.getRoomNumber());
        return "resultNurse";
    }

    @GetMapping("/deleteNurse")
    public String NurseFormDelete(Model model) {
        model.addAttribute("nurse", new Nurse());
        return "deleteNurse";
    }

    @PostMapping("/deleteNurse")
    public String NurseDelete(@ModelAttribute Nurse nurse) {
      jdbcTemplate.update("delete from lshoemake.nurse where "
      		+ "nid = ? and firstname = ? and lastname = ?", 
    		  nurse.getNID(), nurse.getFirstName(), nurse.getLastName());

      return "deleteNurseResult";
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
