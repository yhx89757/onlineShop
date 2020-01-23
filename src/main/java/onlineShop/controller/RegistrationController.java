package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {
	
	// for adding the customer and its info into the database
	@Autowired
	private CustomerService customerService;
	
	// GET request: create and show the registration page to user
	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
		// create a new customer object
		Customer customer = new Customer();
		// send the register page and newly created customer object to the front-end
		return new ModelAndView("register", "customer", customer);
	}
	
	// POST request: register the customer and its info into the database and send feedback to the front-end
	// customer: an object sent from the front-end
	// result: an object containing the form info sent from the front-end
	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	public ModelAndView registerCustomer(@ModelAttribute(value = "customer") Customer customer,
			BindingResult result) {
		// create new ModelAndView object
		ModelAndView modelAndView = new ModelAndView();
		// if there's any error in the form, just return the register page to user
		if (result.hasErrors()) {
			modelAndView.setViewName("register");
			return modelAndView;
		}
		// add customer into the database
		customerService.addCustomer(customer);
		// jump to login page
		modelAndView.setViewName("login");
		// add a successful message into ModelAndView object
		modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
		// send ModelAndView to the front-end
		return modelAndView;
	}
	
}
