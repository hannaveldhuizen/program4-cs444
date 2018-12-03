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

@Controller("/record")
public class RecordController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/addRecord")
    public String RecordForm(Model model) {
        model.addAttribute("record", new Record());
        return "addRecord";
    }

    @PostMapping("/addRecord")
    public String RecordSubmit(@ModelAttribute Record record) {
        jdbcTemplate.update("insert into lshoemake.Record values (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
        		record.getRecordNum() == 0 ? "NULL" : record.getRecordNum(), 
        		record.getApptNum(), record.getInitialHospDate(), 
        		record.getExpDichargeDate(), record.getActualDischargeDate(), 
        		record.getReason(), record.getTreatmentMethod(), 
        		record.getHospRoom() == 0 ? "NULL" : record.getHospRoom(),
        		record.getDID() == 0 ? "NULL" : record.getDID());

        return "resultRecord";
    }

    @GetMapping("/updateRecord")
    public String RecordFormUpdate(Model model) {
        model.addAttribute("record", new Record());
        return "updateRecord";
    }

    @PostMapping("/updateRecord") //FIX
    public String RecordUpdate(@ModelAttribute Record record) {
        jdbcTemplate.update("update lshoemake.record set attr = ?, attr2 = ? where COND)", 
        		record.getApptNum());

        return "updateRecordResult";
    }

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.record",
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
