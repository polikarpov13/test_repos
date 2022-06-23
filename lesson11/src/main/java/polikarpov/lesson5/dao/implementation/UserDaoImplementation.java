package polikarpov.lesson5.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.UserDao;
import polikarpov.lesson5.domain.User;
import polikarpov.lesson5.utils.ConnectionUtils;

public class UserDaoImplementation implements UserDao{
	
	private static String readEverything = "select * from user";
	private static String create = "insert into user(email, firstName, lastName, password, role) values (?, ?, ?, ?, ?);";
	private static String readById = "select * from user where id = ?";
	private static String readByEmail = "select * from user where email = ?";
	private static String updateById = "update user set email = ?, fistName = ?, lastName = ?, password = ?, role = ? where id = ?";
	private static String deleteById = "delete from user where id = ?";
	
	private static Logger LOGGER = Logger.getLogger(UserDaoImplementation.class);
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public UserDaoImplementation() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public User create(User user) {
		try {
			preparedStatement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getRole());
			preparedStatement.executeUpdate();
			
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));
			
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return user;
	}

	@Override
	public User read(Integer id) {
		User user = null;
		try {
			preparedStatement = connection.prepareStatement(readById);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			
			Integer userId = result.getInt("id");
			String email = result.getString("email");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String password = result.getString("password");
			String role = result.getString("role");
			
			user = new User(userId, email, firstName, lastName, password, role);
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		
		return user;
	}

	@Override
	public User update(User user) {
		try {
			preparedStatement = connection.prepareStatement(updateById);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getFirstName());
			preparedStatement.setString(3, user.getLastName());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getRole());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return user;
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

	@Override
	public List<User> readAll() {
		List<User> users = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(readEverything);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Integer userId = result.getInt("id");
				String email = result.getString("email");
				String firstName = result.getString("firstName");
				String lastName = result.getString("lastName");
				String password = result.getString("password");
				String role = result.getString("role");
				
				User user = new User(userId, email, firstName, lastName, password, role);
				users.add(user);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return users;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			preparedStatement = connection.prepareStatement(readByEmail);
			preparedStatement.setString(1, email);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			
			Integer userId = result.getInt("id");
			String firstName = result.getString("firstName");
			String lastName = result.getString("lastName");
			String password = result.getString("password");
			String role = result.getString("role");
			
			user = new User(userId, email, firstName, lastName, password, role);
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		
		return user;
	}

}
