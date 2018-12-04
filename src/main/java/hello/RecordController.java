/*+----------------------------------------------------------------------
 ||
 ||  Class RecordController
 ||
 ||         Author:  Yebin Brandt
 ||
 ||        Purpose:  Serves as a controller for any updates to recordvisit table
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
 ||  Inst. Methods:  postConstruct(), recordForm(Model), recordSubmit(@ModelAttribute Record)
 ||						recordFormUpdate(Model), recordUpdate(@ModelAttribute Record)
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

@Controller("/record")
public class RecordController {

  @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*---------------------------------------------------------------------
    |  Method recordForm
    |
    |  Purpose:  Uses a Model object to expose a new Record to the view template. 
    |				The Record object in the following code contains fields such
    |				that correspond to the form fields in the recordvisit view,
    |				and will be used to capture the information from the form.
    |				Used to add records to recordvisit.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "addRecord"
    *-------------------------------------------------------------------*/
    @GetMapping("/addRecord")
    public String recordForm(Model model) {
        model.addAttribute("record", new Record());
        return "addRecord";
    }

    /*---------------------------------------------------------------------
    |  Method recordSubmit
    |
    |  Purpose:  The Record object in the following code contains fields such
    |				that correspond to the form fields in the record visit view,
    |				and is used to capture the information from the form
    |				and to add to the database.
    |
    |  Pre-condition: record cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Record record from recordForm
    |
    |  Returns:  String "resultRecord"
    *-------------------------------------------------------------------*/
    @PostMapping("/addRecord")
    public String recordSubmit(@ModelAttribute Record record) {
    	
        jdbcTemplate.update("insert into lshoemake.recordvisit values (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
        		record.getRecordNum() == 0 ? null : record.getRecordNum(), 
        		record.getApptNum(), record.getInitialHospDate(), 
        		record.getExpDischargeDate(), record.getActualDischargeDate(), 
        		record.getReason(), record.getTreatmentMethod(), 
        		record.getHospRoom() == 0 ? null : record.getHospRoom(),
        		record.getDID() == 0 ? null : record.getDID());
        
        return "resultRecord";
    }

    /*---------------------------------------------------------------------
    |  Method recordFormUpdate
    |
    |  Purpose:  Uses a Model object to expose a new Record to the view template. 
    |				The Record object in the following code contains fields such
    |				that correspond to the form fields in the recordvisit view,
    |				and will be used to capture the information from the form.
    |				Used to update records in recordvisit.
    |
    |  Pre-condition: model cannot be null
    |
    |  Post-condition: gets mapping
    |
    |  Parameters:
    |      Model model object from server
    |
    |  Returns:  String "updateRecord"
    *-------------------------------------------------------------------*/
    @GetMapping("/updateRecord")
    public String recordFormUpdate(Model model) {
        model.addAttribute("record", new Record());
        return "updateRecord";
    }

    /*---------------------------------------------------------------------
    |  Method recordUpdate
    |
    |  Purpose:  The Record object in the following code contains fields such
    |				that correspond to the form fields in the record visit view,
    |				and is used to capture the information from the form
    |				and to update the database.
    |
    |  Pre-condition: record cannot be null.
    |
    |  Post-condition: posts mapping
    |
    |  Parameters:
    |      @ModelAttribute Record record from recordFormUpdate
    |
    |  Returns:  String "updateRecordResult"
    *-------------------------------------------------------------------*/
    @PostMapping("/updateRecord")
    public String recordUpdate(@ModelAttribute Record record) {
        
        List<String> strs = new ArrayList<String>();
        
    	if (record.getRecordNum() != 0)
    		strs.add("recordnum = " + record.getRecordNum());
    	if (record.getExpDischargeDate() != null)
    		strs.add("expDischargeDate = '" + record.getExpDischargeDate() + "'");
    	if (record.getActualDischargeDate() != null)
    		strs.add("actualDischargeDate = '" + record.getActualDischargeDate() + "'");
    	if (record.getTreatmentMethod() != null)
    		strs.add("treatmentMethod = '" + record.getTreatmentMethod() + "'");
    	if (record.getHospRoom() != 0)
    		strs.add("hosproom = " + record.getHospRoom());
    	if (record.getDID() != 0)
    		strs.add("did = " + record.getDID());
    	
    	String stmt = String.format("update lshoemake.recordvisit set %s where recordnum = %s",
    			String.join(", ", strs), record.getRecordNum());
        jdbcTemplate.update(stmt);
        
        return "updateRecordResult";
    }

}
