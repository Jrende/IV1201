package test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.User;

import org.junit.Test;

public class UserTest {

	/**
	 * Find existing user.
	 */
	@Test
	public void findUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").username).isEqualTo("snigel");
			}
		});
	}
	
	/**
	 * Make shore a non existing user does not return some other user.
	 */
	@Test
	public void notFindUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("Litreb")).isNull();
			}
		});
	}
	
	/**
	 * 
	 */
	@Test
	public void badUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").username).isNotEqualTo("fisk");
			}
		});
	}

	/**
	 * Find user by email
	 */
	@Test
	public void findByEmail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByEmail("bertil@example.com").email).isEqualTo("bertil@example.com");
			}
		});
	}
	
	/**
	 * Make shore findByEmail does not return any user for an non existing email.
	 */
	@Test
	public void notFindByEmail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByEmail("litreb@example.com")).isNull();
			}
		});
	}
	

	/**
	 * Test that authentication success for a correct password.
	 */
	@Test
	public void authenticateSuccess() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.authenticate("snigel", "snigel")).isNotNull();
			}
		});
	}
	
	/**
	 * Test that authentication fails for an incorrect password.
	 */
	@Test
	public void authenticateFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.authenticate("snigel", "")).isNull();
			}
		});
	}
	
	/**
	 * Test that an non existing email is available.
	 */
	@Test
	public void emailAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.emailAvailable("litber@example.com")).isTrue();
			}
		});
	}
	
	/**
	 * Test that an existing email is not available.
	 */
	@Test
	public void emailNotAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.emailAvailable("bertil@example.com")).isFalse();
			}
		});
	}
	
	/**
	 * Test that an unsued user name is available.
	 */
	@Test
	public void usernameAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.usernameAvailable("litreb")).isTrue();
			}
		});
	}
	
	/**
	 * Test that a used user name is not available.
	 */
	@Test
	public void usernameNotAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.usernameAvailable("snigel")).isFalse();
			}
		});
	}
	
	/**
	 * Test that validation succeeds for correct new user.
	 */
	@Test
	public void validateSuccess() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				User user = User.findByUsername("snigel");
				user.email = "litreb@example.com";
				user.username = "Litreb";
				user.password = "password";
				user.confirmPassword = "password";
				
				assertThat(user.validate()).isNull();
			}
		});
	}
	
	/**
	 * Test that validation fails for new user, bad password.
	 */
	@Test
	public void validateFailPassword() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				User user = User.findByUsername("snigel");
				user.email = "litreb@example.com";
				user.username = "Litreb";
				user.password = "password";
				user.confirmPassword = "password1";
				
				assertThat(user.validate()).isEqualTo("Your password and confirmation password don't match");
			}
		});
	}
	
	/**
	 * Test that validation fails for new user, existing email.
	 */
	@Test
	public void validateFailEmail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				User user = User.findByUsername("snigel");
				user.email = "bertil@example.com";
				user.username = "Litreb";
				user.password = "password";
				user.confirmPassword = "password";
				
				assertThat(user.validate()).isEqualTo("Email already registered");
			}
		});
	}

	/**
	 * Test that validation fails for new user, existing user name.
	 */
	@Test
	public void validateFailUsername() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				User user = User.findByUsername("snigel");
				user.email = "litreb@example.com";
				user.password = "password";
				user.confirmPassword = "password";
				
				assertThat(user.validate()).isEqualTo("Username already registered");
			}
		});
	}
	
	/**
	 * Test that validation fails for an existing user.
	 */
	@Test
	public void validateFailExisting() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").validate()).isNotNull();
			}
		});
	}
	
	/**
	 * Test encryption
	 */
	@Test
	public void validateEncryptionSuceess() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.encrypt("snigel")).isEqualTo("ymQOxIo1jtNQ+1OcNOluIQ");
			}
		});
	}

	/**
	 * Test encryption
	 */
	@Test
	public void validateEncryptionFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.encrypt("snigel1")).isNotSameAs("ymQOxIo1jtNQ+1OcNOluIQ");
			}
		});
	}
}

