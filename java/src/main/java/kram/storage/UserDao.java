package kram.storage;



public interface UserDao {
	void addUser(User user);
	boolean isTeacher(User user);
	User login(String meno, String heslo);

}
