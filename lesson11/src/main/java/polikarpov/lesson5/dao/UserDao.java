package polikarpov.lesson5.dao;

import polikarpov.lesson5.domain.User;
import polikarpov.lesson5.shared.AbstractCRUD;

public interface UserDao extends AbstractCRUD<User>{
	User getUserByEmail(String email);
}
