package kram.storage;



public interface UserDao {
	User saveUser(User user);
	boolean isTeacher(User user);
	User login(String meno, String heslo);

}
