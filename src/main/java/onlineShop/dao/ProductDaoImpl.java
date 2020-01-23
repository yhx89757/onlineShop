package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	// inject a SessionFacory from spring framework for writing data into the database
	@Autowired
	private SessionFactory sessionFactory;
	
	// add product
	public void addProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	// delete product by id
	public void deleteProduct(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Product product = (Product) session.get(Product.class,  productId);
			session.delete(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	// update product info
	public void updateProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	// query product by id
	public Product getProductById(int productId) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			Product product = (Product) session.get(Product.class,  productId);
			session.getTransaction().commit();
			return product;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// get all the product objects
	public List<Product> getAllProducts() {
		// create an empty list for product objects
		List<Product> products = new ArrayList<Product>();
		// create session
		try (Session session = sessionFactory.openSession()) {
			// begin transaction
			session.beginTransaction();
			// create a query builder
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			// create a query
			CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
			// "root" acts like "from"
			Root<Product> root = criteriaQuery.from(Product.class);
			// select info from whatever "root" is pointing to
			criteriaQuery.select(root);
			// get the product objects into the list
			products = session.createQuery(criteriaQuery).getResultList();
			// commit(finalize) the transaction
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return the product list
		return products;
	}
			
}
