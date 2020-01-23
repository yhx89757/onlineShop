package onlineShop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Product;
import onlineShop.service.ProductService;

@Controller
public class ProductController {
	
	// inject service object from spring framework
	@Autowired
	private ProductService productService;
	
	// GET request: get all product objects from database and return as a list to the front-end
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public ModelAndView getAllProducts() {
		// use productService to get all the product objcets as a list
		List<Product> products = productService.getAllProducts();
		// send "productList" page and a list of product objects to the fron-end
		return new ModelAndView("productList", "products", products);
	}
	
	// GET request: get a product object by id and sent it back to the front-end
	@RequestMapping(value = "/getProductById/{productId}", method = RequestMethod.GET)
	public ModelAndView getProductById(@PathVariable(value = "productId") int productId) {
		Product product = productService.getProductById(productId);
		return new ModelAndView("productPage", "product", product);
	}
	
	// POST request: get product object from the front-end and write into database and if there's any image, save into local directory
	// product: a product object containing all the product info
	// result: sent from the front-end to tell if there's any error
	@RequestMapping(value = "/admin/product/addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute(value = "productForm") Product product, BindingResult result) {
		// if there's any error, return to the "addProduct" page
		if (result.hasErrors()) {
			return "addProduct";
		}
		// add product into the database
		productService.addProduct(product);
		// get the image from the product object
		MultipartFile image = product.getProductImage();
		// if the image is not empty then save it to the local directory
		if (image != null && !image.isEmpty()) {
			// Mac
			// Path path = Paths.get("Users/xxx/products/" + product.getId() + ".jpg");
			
			// Windows
			Path path = Paths.get("C:\\products\\" + product.getId() + ".jpg");
			try {
				image.transferTo(new File(path.toString()));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		// send "getAllProducts" page to the front-end
		return "redirect:/getAllProducts";
	}
	
	// GET request: delete product by id
	@RequestMapping(value = "admin/delete/{productId}")
	public String deleteProduct(@PathVariable(value = "productId") int productId) {
		// delete corresponding image first
		// for Mac: 
		// Path path = Paths.get("/Users/xxx/products/" + product + ".jpg");
		// for Windows:
		Path path = Paths.get("C:\\products\\" + productId + ".jpg");
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// then delete product from the database
		productService.deleteProduct(productId);
		// send "getAllProducts" page to the front-end
		return "redirect:/getAllProducts";
	}
	
	// GET request: query the product by given id and give back a edit form to the front-end
	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.GET)
	public ModelAndView getEditForm(@PathVariable(value = "productId") int productId) {
		// get the corresponding product
		Product product = productService.getProductById(productId);
		// create ModelAndView object for the front-end
		ModelAndView modelAndView = new ModelAndView();
		// set view name
		modelAndView.setViewName("editProduct");
		// add corresponding product into the model
		modelAndView.addObject("editProductObj", product);
		// add product id into the model
		modelAndView.addObject("productId", productId);
		// send all of them back to the front-end
		return modelAndView;
	}
	
	// POST request: get the edited product object and product id and write them into the database
	// product: a recently edited product object from the front-end
	// productId: the corresponding product id sent from the front-end
	@RequestMapping(value = "/admin/product/editProduct/{productId}", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute(value = "editProductObj") Product product,
			@PathVariable(value = "productId") int productId) {
		// add id into the product object
		product.setId(productId);
		// update product into the database
		productService.updateProduct(product);
		// send "getAllProducts" page to the front-end
		return "redirect:/getAllProducts";
	}
}