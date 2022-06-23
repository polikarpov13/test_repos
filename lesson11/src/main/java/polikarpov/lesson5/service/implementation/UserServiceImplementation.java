package polikarpov.lesson5.service.implementation;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.UserDao;
import polikarpov.lesson5.dao.implementation.UserDaoImplementation;
import polikarpov.lesson5.domain.User;
import polikarpov.lesson5.service.UserService;

public class UserServiceImplementation implements UserService {

	private static Logger LOGGER = Logger.getLogger(UserServiceImplementation.class);
	private static UserServiceImplementation userServiceImplementation;

	private UserDao userDao;

	private UserServiceImplementation() {

		try {
			userDao = new UserDaoImplementation();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}

	}

	public static UserService getUserService() {
		if (userServiceImplementation == null)
			userServiceImplementation = new UserServiceImplementation();
		return userServiceImplementation;
	}

	@Override
	public User create(User t) {
		return userDao.create(t);
	}

	@Override
	public User read(Integer id) {
		return userDao.read(id);
	}

	@Override
	public User update(User t) {
		return userDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	public List<User> readAll() {
		return userDao.readAll();
	}

	@Override
	public User getUserByEmail(String email) {
		
		return userDao.getUserByEmail(email);
	}
}
