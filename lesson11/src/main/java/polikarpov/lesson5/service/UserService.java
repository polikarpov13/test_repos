package polikarpov.lesson5.service;

import polikarpov.lesson5.domain.User;
import polikarpov.lesson5.shared.AbstractCRUD;

public interface UserService extends AbstractCRUD<User>{
	User getUserByEmail(String email);
}
