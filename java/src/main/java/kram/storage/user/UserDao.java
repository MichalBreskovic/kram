package kram.storage.user;

import kram.storage.EntityNotFoundException;

public interface UserDao {
	User saveUser(User user) throws EntityNotFoundException,NullPointerException;
	boolean isTeacher(User user);
	User getByNameUsername(String meno, String heslo);

}
