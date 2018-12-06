/*+----------------------------------------------------------------------
 ||
 ||  Class PaymentController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to payment table
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  None
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  postConstruct(), paymentForm(Model), paymentSubmit(@ModelAttribute Payment)
 ||						paymentFormUpdate(Model), paymentUpdate(@ModelAttribute Payment)
 ||
 ++-----------------------------------------------------------------------*/
package hello;
import java.util.List;
import java.util.ArrayList;
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

    /*---------------------------------------------------------------------
    |  Method paymentForm
    |
    |  Purpose:  Uses a Model object to expose a new Payment to the view template. 
    |				The Payment object in the following code contains fields such
    |				that correspond to the form fields in the payment view,
    |				and will be used to capture the information from the form.
    |				Used to add records to payment.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addPayment"
    *-------------------------------------------------------------------*/
    @GetMapping("/addPayment")
    public String paymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "addPayment";
    }

    /*---------------------------------------------------------------------
    |  Method paymentSubmit
    |
    |  Purpose:  The Payment object in the following code contains fields such
    |				that correspond to the form fields in the payment view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: payment cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Payment payment from paymentForm
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/addPayment")
    public String paymentSubmit(@ModelAttribute Payment payment) {
        jdbcTemplate.update("insert into lshoemake.Payment values (?, ?, ?, ?, ?, ?, ?)", 
        		payment.getPayID(), payment.getPID(), payment.getAmountDue(),
        		payment.getDueDate(), payment.getStatus(), payment.getPaymentDate(),
        		payment.getEID());

        return "success";
    }

    /*---------------------------------------------------------------------
    |  Method paymentFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Payment to the view template. 
    |				The Payment object in the following code contains fields such
    |				that correspond to the form fields in the payment view,
    |				and will be used to capture the information from the form.
    |				Used to update records in payment.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updatePayment"
    *-------------------------------------------------------------------*/
    @GetMapping("/updatePayment")
    public String paymentFormUpdate(Model model) {
        model.addAttribute("payment", new Payment());
        return "updatePayment";
    }
    
    /*---------------------------------------------------------------------
    |  Method paymentUpdate
    |
    |  Purpose:  The Payment object in the following code contains fields such
    |				that correspond to the form fields in the payment view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: payment cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Payment payment from paymentFormUpdate
    |
    |  Returns:  String "success"
    *-------------------------------------------------------------------*/
    @PostMapping("/updatePayment")
    public String paymentUpdate(@ModelAttribute Payment payment) {
    	List<String> strs = new ArrayList<String>();
    	if (payment.getPayID() != 0)
    		strs.add("payid = " + payment.getPayID());
    	if (!payment.getAmountDue().equals(""))
    		strs.add("amountdue = '" + payment.getAmountDue() + "'");
    	if (!payment.getDueDate().equals(""))
    		strs.add("duedate = '" + payment.getDueDate() + "'");
    	if (!payment.getStatus().equals(""))
    		strs.add("status = '" + payment.getStatus() + "'");
    	if (!payment.getPaymentDate().equals(""))
    		strs.add("paymentdate = '" + payment.getPaymentDate() + "'");
    	if (payment.getEID() != 0)
    		strs.add("eid = " + payment.getEID());
    	
    	String stmt = String.format("update lshoemake.payment set %s where payid = %s", 
    					String.join(", ", strs), payment.getPayID());
        jdbcTemplate.update(stmt);
        
        return "success";
    }

}
