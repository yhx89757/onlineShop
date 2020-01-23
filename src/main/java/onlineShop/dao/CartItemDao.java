package onlineShop.dao;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;

public interface CartItemDao {
	
	void addCartItem(CartItem carItem);
	
	void removeCartItem(int carItemId);
	
	void removeAllCartItems(Cart cart);
}
