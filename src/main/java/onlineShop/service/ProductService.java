package onlineShop.service;
import java.util.List;
import onlineShop.model.Product;

public interface ProductService {
	
	// query product by id
	Product getProductById(int productId);
	
	// delete product by id
	void deleteProduct(int productId);
	
	// add product by product object
	void addProduct(Product product);
	
	// update product info by product object
	void updateProduct(Product product);
	
	// get all the products in the database
	List<Product> getAllProducts();

}
