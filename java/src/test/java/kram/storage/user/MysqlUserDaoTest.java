package kram.storage.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import kram.storage.DaoFactory;
import kram.storage.EntityNotFoundException;
import kram.storage.SHA256;


class MysqlUserDaoTest {

	UserDao userDao;
	User newUser;
	User savedUser;
	
	public MysqlUserDaoTest() {
		userDao = DaoFactory.INSTATNCE.getUserDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		newUser = userDao.saveUser(new User("Peter", "piko" ,"Vysok˝", SHA256.getHash("12345"), true, "a@b"));
		savedUser = userDao.saveUser(newUser);
	}

	@AfterEach
	void tearDown() throws Exception {
		userDao.deleteUser(savedUser.getIdUser());
	}
	
	@Test
    void testGetByUsername() {
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.getByUsername("nemalbybytziadny");
            }
        });
        assertTrue(userDao.getByUsername(savedUser.getUsername()).getUsername().equals(savedUser.getUsername()));
    }

	@Test
	void testCheckUsername() {
		assertFalse(userDao.checkUsername(savedUser.getUsername()));
		assertTrue(userDao.checkUsername("asdfasdfasdfasdfasdfsdfgsdfgsdfg"));
	}
	
	@Test
	void testLogin() {
		assertTrue(savedUser.getIdUser().equals(userDao.login(savedUser.getUsername(), "12345").getIdUser()));
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.login("asdfasdfasdfasdfasdfsdfgsdfgsdfg", "12345");
			}
		});
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.login(savedUser.getName(), "asdfasdfasdfasdfasdfsdfgsdfgsdfg");
			}
		});
	}
	
	@Test
    void testSave() { // INSERT
        assertThrows(EntityNotFoundException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                User user = new User("user2", "user2", "user2", "user2", false, "user2");
                user.setIdUser(0L);
                userDao.saveUser(user);
                userDao.deleteUser(user.getIdUser());

            }
        });

        List<User> originalList = userDao.getAll();//("user","pekny","heslo")
        User user = new User("user2", "user2", "user2", "user2", false, "user2");
        User savedUser = userDao.saveUser(user);
        assertEquals("user2", savedUser.getName());
        assertEquals("user2", savedUser.getHeslo());
        assertEquals("user2", savedUser.getSurname());
        assertEquals("user2", savedUser.getEmail());
        assertEquals("user2", savedUser.getUsername());
        assertEquals(false, savedUser.isTeacher());

        assertNotNull(savedUser.getIdUser());
        assertEquals(originalList.size() + 1, userDao.getAll().size());

        User userr = userDao.getById(savedUser.getIdUser());
        assertEquals("user2", userr.getName());

        assertNotNull(userr.getIdUser());

        assertThrows(NullPointerException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                userDao.saveUser(null);

            }
        });

        userDao.deleteUser(savedUser.getIdUser());
    }
	
	@Test()
	void testUpdate() {
		User savedUser = userDao.saveUser(new User("Janko", "hrusko" ,"NÌzky", SHA256.getHash("654321"), true, "b@a"));
		User newSavedUser = userDao.saveUser(new User(savedUser.getIdUser(), "Maùko", "brusko" ,"Vysok˝", SHA256.getHash("123456"), false, "a@b"));
		
		assertEquals(savedUser.getIdUser(), newSavedUser.getIdUser());
		assertNotEquals(savedUser.getName(), newSavedUser.getName());
		assertNotEquals(savedUser.getUsername(), newSavedUser.getUsername());
		assertNotEquals(savedUser.getSurname(), newSavedUser.getSurname());
		assertNotEquals(savedUser.getHeslo(), newSavedUser.getHeslo());
		assertNotEquals(savedUser.isTeacher(), newSavedUser.isTeacher());
		assertNotEquals(savedUser.getEmail(), newSavedUser.getEmail());

		newSavedUser.setIdUser(-1L);
		
		assertThrows(EntityNotFoundException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.saveUser(newSavedUser);

			}
		});

		assertThrows(NullPointerException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				userDao.saveUser(null);
			}
		});
		
		userDao.deleteUser(savedUser.getIdUser());
	}
	
	@Test()
	void testDelete() {
		userDao.saveUser(new User("Janko", "hrusko" ,"NÌzky", SHA256.getHash("654321"), true, "b@a"));
		List<User> originalList = userDao.getAll();
		userDao.deleteUser(userDao.getByUsername("hrusko").getIdUser());
		
		assertEquals(originalList.size() - 1, userDao.getAll().size());

		assertThrows(EntityNotFoundException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				userDao.deleteUser(-1L);
			}
		});

	}

}
