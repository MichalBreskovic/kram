package kram.storage.user;

import java.util.List;

import kram.storage.EntityNotFoundException;
import kram.storage.zameranie.Zameranie;

public interface UserDao {
	User saveUser(User user) throws EntityNotFoundException,NullPointerException;
	boolean isTeacher(User user);
	User getByNameUsername(String meno, String heslo);

}
