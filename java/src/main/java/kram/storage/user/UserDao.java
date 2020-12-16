package kram.storage.user;

import java.util.List;

import kram.storage.EntityNotFoundException;
import kram.storage.zameranie.Zameranie;

public interface UserDao {
	
	User saveUser(User user) throws EntityNotFoundException,NullPointerException;
	User login(String meno, String heslo) throws EntityNotFoundException;
	User getById(Long id) throws EntityNotFoundException;
	User deleteUser(Long id) throws EntityNotFoundException;

}
