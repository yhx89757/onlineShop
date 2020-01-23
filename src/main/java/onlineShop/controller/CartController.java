package onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.service.CartService;
import onlineShop.service.CustomerService;

@Controller
public class CartController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	// get the current logged in username and then get the current cart
	@RequestMapping(value = "/cart/getCartById", method = RequestMethod.GET)
	public ModelAndView getCartId() {
		ModelAndView modelAndView = new ModelAndView("cart");
		// spring security, get the logged in user info
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		// get the current username
		String username = loggedInUser.getName();
		Customer customer = customerService.getCustomerByUserName(username);
		// add cartId into the model
		modelAndView.addObject("cartId", customer.getCart().getId());
		return modelAndView;
	}
	
	// get the cart by its id
	@RequestMapping(value = "/cart/getCart/{cartId}", method = RequestMethod.GET)
	@ResponseBody
	public Cart getCartItems(@PathVariable(value = "cartId") int cartId) {
		return cartService.getCartById(cartId);
	}
}