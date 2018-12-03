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

@Controller("/payment")
public class PaymentController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/addPayment")
    public String PaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "addPayment";
    }

    @PostMapping("/addPayment")
    public String PaymentSubmit(@ModelAttribute Payment payment) {
        jdbcTemplate.update("insert into lshoemake.Payment values (?, ?, ?, ?, ?, ?, ?)", 
        		payment.getPayID(), payment.getPID(), payment.getAmountDue(),
        		payment.getDueDate(), payment.getStatus(), payment.getPaymentDate(),
        		payment.getEID());

        return "resultPayment";
    }

    @GetMapping("/updatePayment")
    public String PaymentFormUpdate(Model model) {
        model.addAttribute("payment", new Payment());
        return "updatePayment";
    }

    @PostMapping("/updatePayment") //FIX
    public String PaymentUpdate(@ModelAttribute Payment payment) {
        jdbcTemplate.update("update lshoemake.Payment set attr = val, attr2 = val2 where COND)"
        		);
        return "updatePaymentResult";
    }
    

//    // FIX
//    @GetMapping("/queryResults")
//    public String queryResults(Model model) {
//      List<String> allNames = this.jdbcTemplate.query(
//        "select * from lshoemake.payment",
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
