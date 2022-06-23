package polikarpov.lesson5.service.implementation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.ProductDao;
import polikarpov.lesson5.dao.implementation.ProductDaoImplementation;
import polikarpov.lesson5.domain.Product;
import polikarpov.lesson5.service.ProductService;

public class ProductServiceImplementation implements ProductService {
	
	private static Logger LOGGER = Logger.getLogger(ProductServiceImplementation.class);
	private static ProductServiceImplementation productServiceImplementation;
	
	private ProductDao productDao;

	private ProductServiceImplementation() {

		try {
			productDao = new ProductDaoImplementation();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}

	}
	
	public static ProductService getProductService() {
		if (productServiceImplementation == null)
			productServiceImplementation = new ProductServiceImplementation();
		return productServiceImplementation;
	}

	@Override
	public Product create(Product t) {
		return productDao.create(t);
	}

	@Override
	public Product read(Integer id) {
		return productDao.read(id);
	}

	@Override
	public Product update(Product t) {
		return productDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		productDao.delete(id);
	}

	@Override
	public List<Product> readAll() {
		return productDao.readAll();
	}

	@Override
	public Map<Integer, Product> readAllMap() {
		return readAll().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
	}
}
