package net.training.unittest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.training.unittest.model.Booking;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

	private class Balance {
		String accountNo;
		double	balance;
		
		public Balance(String accountNo, double balance) {
			super();
			this.accountNo = accountNo;
			this.balance = balance;
		}

		public String getAccountNo() {
			return accountNo;
		}
		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		
	}
	
    @CrossOrigin //(origins = "http://localhost:8080/balances")
	@RequestMapping(value="/balances" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> balances() {
		
		Map<String, Object> results = new HashMap<String, Object>();
		
		List<Balance> balances = new ArrayList<Balance>();
		
 		balances.add(new Balance("ft123", 111.23));
 		balances.add(new Balance("ft456", 222.45));
 		balances.add(new Balance("ft789", 333.67));
 		results.put("theData", balances);
 		
		return results;
	}
	
	
    @CrossOrigin //(origins = "http://localhost:8080/balances")
   	@RequestMapping(value="/balances2" , method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
   	public @ResponseBody String balances2() {
   		
    	return "{ \"data\" :  \"position\"}";
    }
//   	jj
   	
    
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(Model model, ServletRequest servletRequest) {
		model.addAttribute("home_active", "active");
		return "index";
	}
	
//	@RequestMapping(value = "/courses_old", method = RequestMethod.GET)
//	public String courses_old() {
//		return "itinerary";
//	}

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public String courses(Model model) {
		model.addAttribute("course_active", "active");
		Booking bookingForm = new Booking();
		model.addAttribute("bookingForm", bookingForm);
		return "itinerary";
	}

	
	@RequestMapping(value = "/unit_testing", method = RequestMethod.GET)
	public String unitTesting(Model model) {
		model.addAttribute("unit_testing_active", "active");
		return "sections/unit_testing";
	}

	@RequestMapping(value = "/expert_java", method = RequestMethod.GET)
	public String expert_Java(Model model) {
		model.addAttribute("expert_java_active", "active");
		return "sections/expert_java";
	}

	@RequestMapping(value = "/about_us", method = RequestMethod.GET)
	public String aboutUs(Model model) {
		model.addAttribute("about_us_active", "active");
		return "about_us";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Model model) {
		model.addAttribute("contact_us_active", "active");
		return "contact";
	}

	@RequestMapping(value = "/enquiry", method = RequestMethod.POST)
	public String enquiry(@ModelAttribute("booking") Booking booking,
			BindingResult result, Model model) {
		return "about_us";
	}

	@RequestMapping(value = "/ajax/fuckit", method = RequestMethod.POST)
	public @ResponseBody String bookCourse() {
		return "<h2>ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ</h2>";
	}

}
