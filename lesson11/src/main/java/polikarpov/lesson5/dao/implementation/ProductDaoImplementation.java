package polikarpov.lesson5.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.ProductDao;
import polikarpov.lesson5.domain.Product;
import polikarpov.lesson5.utils.ConnectionUtils;

public class ProductDaoImplementation implements ProductDao{
	
	private static String readEverything = "select * from product";
	private static String create = "insert into product(name, description, price) values (?, ?, ?);";
	private static String readById = "select * from product where id = ?";
	private static String updateById = "update product set name = ?, description = ?, price = ? where id = ?";
	private static String deleteById = "delete from product where id = ?";
	
	private static Logger LOGGER = Logger.getLogger(ProductDaoImplementation.class);
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public ProductDaoImplementation() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public Product create(Product product) {
		try {
			preparedStatement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			product.setId(rs.getInt(1));
			
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return product;
	}

	@Override
	public Product read(Integer id) {
		Product product = null;
		try {
			preparedStatement = connection.prepareStatement(readById);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			
			Integer productId = result.getInt("id");
			String name = result.getString("name");
			String description = result.getString("description");
			Double price = result.getDouble("price");
			
			product = new Product(productId, name, description, price);
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		
		return product;
	}

	@Override
	public Product update(Product product) {
		try {
			preparedStatement = connection.prepareStatement(updateById);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setDouble(3, product.getPrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return product;
	}

	@Override
	public void delete(Integer id) {
		try {
			preparedStatement = connection.prepareStatement(deleteById);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
		}
	}
	
	public List<Product> readAll() {
		List<Product> products = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(readEverything);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Integer productId = result.getInt("id");
				String name = result.getString("name");
				String description = result.getString("description");
				Double price = result.getDouble("price");
				
				Product product = new Product(productId, name, description, price);
				products.add(product);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return products;
	}



}
