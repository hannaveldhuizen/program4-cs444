package hello;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
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
    public String DoctorSubmit(@ModelAttribute Doctor doctor) {
    	String query = String.format("insert into lshoemake.doctor values (%s, '%s', '%s', '%s', '%s', %s, %s)", 
        		doctor.getDID(), doctor.getLastName(), doctor.getFirstName(),
        		doctor.getDOB(), doctor.getStatus().equals("") ? "NULL" : doctor.getStatus(), 
        		doctor.getDeptID() == 0 ? "NULL" : doctor.getDeptID(),
        		doctor.getOfficeNumber() == 0 ? "NULL" : doctor.getOfficeNumber());
    	jdbcTemplate.update(query);
        return "resultDoctor";
    }

    @GetMapping("/deleteDoctor")
    public String DoctorFormDelete(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "deleteDoctor";
    }

    @PostMapping("/deleteDoctor")
    public String DoctorDelete(@ModelAttribute Doctor doctor) {
    	List<String> strs = new ArrayList<String>();
    	
    	if (doctor.getDID() != 0)
    		strs.add("did = " + doctor.getDID());
    	if (doctor.getLastName() != null)
    		strs.add("lastname = " + doctor.getLastName());
    	if (doctor.getFirstName() != null)
    		strs.add("firstname = " + doctor.getFirstName());
    	
    	jdbcTemplate.update("delete from lshoemake.doctor where ?", String.join(" and ", strs));

      return "deleteDoctorResult";
    }
    
    @GetMapping("/updateDoctor")
    public String DoctorFormUpdate(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "updateDoctor";
    }

    @PostMapping("/updateDoctor")
    public String DoctorUpdate(@ModelAttribute Doctor doctor) {
    	
    	List<String> strs = new ArrayList<String>();
 
    	if (doctor.getLastName() != null)
    		strs.add("lastname = " + doctor.getLastName());
    	if (doctor.getFirstName() != null)
    		strs.add("firstname = " + doctor.getFirstName());
    	if (doctor.getStatus() != null)
    		strs.add("status = " + doctor.getStatus());
    	if (doctor.getDeptID() != 0)
    		strs.add("deptid = " + doctor.getDeptID());
    	if (doctor.getOfficeNumber() != 0)
    		strs.add("officenum = " + doctor.getOfficeNumber());
    		
        jdbcTemplate.update("update lshoemake.doctor set ? where ?",  
        		String.join(", ", strs));

        return "updateDoctorResult";
    }

}
