package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CartDao;
import onlineShop.model.Cart;


@Service
public class CartServiceImpl {
	@Autowired
	private CartDao cartDao;
	
	public Cart getCartById(int cartId) {
		return cartDao.getCartById(cartId);
	}
}
