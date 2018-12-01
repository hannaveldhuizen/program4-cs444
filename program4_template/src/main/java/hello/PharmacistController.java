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
    public String PharmacistSubmit(@ModelAttribute Pharmacist Pharmacist) {
        jdbcTemplate.update("insert into lshoemake.pharmacist values (?, ?, ?, ?, ?, ?)", 
        		Pharmacist.getPhID(), Pharmacist.getLastName(), 
        		Pharmacist.getFirstName(), Pharmacist.getDOB(), 
        		Pharmacist.getDeptID() == 0 ? "NULL" : Pharmacist.getDeptID(), 
        		Pharmacist.getOfficeNumber() == 0 ? "NULL" : Pharmacist.getOfficeNumber());

        return "resultPharmacist";
    }

    @GetMapping("/deletePharmacist")
    public String PharmacistFormDelete(Model model) {
        model.addAttribute("Pharmacist", new Pharmacist());
        return "deletePharmacist";
    }

    @PostMapping("/deletePharmacist") // FIX
    public String PharmacistDelete(@ModelAttribute Pharmacist Pharmacist) {
      jdbcTemplate.update("delete from lshoemake.pharmacist where first_name = ? and last_name = ?", 
    		  Pharmacist.getFirstName(), Pharmacist.getLastName());

      return "deletePharmacistResult";
    }

    // FIX
    @GetMapping("/queryResults")
    public String queryResults(Model model) {
      List<String> allNames = this.jdbcTemplate.query(
        "select * from lshoemake.pharmacist",
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
