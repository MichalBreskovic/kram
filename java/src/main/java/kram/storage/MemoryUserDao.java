package kram.storage;
import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao {
	private long lastID = 0;
	private List<User> users = new ArrayList<User>();

	@Override
	public void addUser(User user) {
		if (user != null) {
			user.setIdUser(++lastID);
			users.add(user);
		}

	}
	






	@Override
	public boolean isTeacher(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User login(String meno, String heslo) {
		// TODO Auto-generated method stub
		return null;
	}

}
