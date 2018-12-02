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
public class PatientController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/addPatient")
    public String PatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @PostMapping("/addPatient")
    public String PatientSubmit(@ModelAttribute Patient patient) {
        jdbcTemplate.update("insert into lshoemake.patient values (?, ?, ?, ?, ?, ?, ?)", 
        	patient.getPID(), patient.getLastName(), patient.getFirstName(),
        	patient.getGender(), patient.getDOB(), patient.getAddress(),
        	patient.getContactNumber());

        return "resultPatient";
    }

    @GetMapping("/deletePatient")
    public String PatientFormDelete(Model model) {
        model.addAttribute("patient", new Patient());
        return "deletePatient";
    }

    @PostMapping("/deletePatient")
    public String PatientDelete(@ModelAttribute Patient patient) {
      jdbcTemplate.update("delete from lshoemake.patient "
      		+ "where pid = ? and firstname = ? and lastname = ?", 
    		  patient.getPID(), patient.getFirstName(), patient.getLastName());

      return "deletePatientResult";
    }

    // FIX
    @GetMapping("/queryResults")
    public String queryResults(Model model) {
      List<String> allNames = this.jdbcTemplate.query(
        "select * from lshoemake.patient",
        new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                return (first_name + " " + last_name);
            }
        });
        model.addAttribute("names", allNames);
        return "/queryResults";
    }

}
