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
public class DepartmentController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @GetMapping("/updateDepartment")
    public String DepartmentFormUpdate(Model model) {
        model.addAttribute("department", new Department());
        return "updateDepartment";
    }

    @PostMapping("/updateDepartment") //FIX
    public String DepartmentUpdate(@ModelAttribute Department Department) {
        jdbcTemplate.update("update lshoemake.Department set attr = val, attr2 = val2 where COND)", 
        		Department.getDeptID(), Department.getDeptName(),
        		Department.getBuildingName(), Department.getOfficeNum());

        return "updatedDepartment";
    }

    // FIX
    @GetMapping("/queryResults")
    public String queryResults(Model model) {
      List<String> allNames = this.jdbcTemplate.query(
        "select * from lshoemake.department",
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

