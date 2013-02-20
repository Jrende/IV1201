package test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.User;

import org.junit.Test;

public class UserTest extends User {

	/**
	 * Make shore we have users to make test case valid.
	 */
	@Test
	public void userExists() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(getAll().isEmpty()).isFalse();
			}
		});
	}

	/**
	 * Make shore test user exists.
	 */
	@Test
	public void testUserExists() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel")).isNotNull();
			}
		});
	}

	/**
	 * Find existing user and make shore no other user is returned.
	 */
	@Test
	public void findUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : find.all())
					assertThat(User.findByUsername(user.username).username)
							.isEqualTo(user.username);
			}
		});
	}

	/**
	 * Make shore a non existing user return some existing user.
	 */
	@Test
	public void notFindUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("")).isNull();
			}
		});
	}

	/**
	 * Find user by email and make shore no other user is returned.
	 */
	@Test
	public void findByEmail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : find.all())
					assertThat(User.findByEmail(user.email).email).isEqualTo(
							user.email);
			}
		});
	}

	/**
	 * Make shore findByEmail does not return any user for an non existing
	 * email.
	 */
	@Test
	public void notFindByEmail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByEmail("")).isNull();
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
				assertThat(User.emailAvailable("")).isTrue();
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
				for (User user : getAll())
					assertThat(User.emailAvailable(user.email)).isFalse();
			}
		});
	}

	/**
	 * Test that an unused user name is available.
	 */
	@Test
	public void usernameAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				/* User name should be available but not valid */
				assertThat(User.usernameAvailable("")).isTrue();
			}
		});
	}

	/**
	 * Test claimed that claimed user name is not available.
	 */
	@Test
	public void usernameNotAvailable() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : getAll())
					assertThat(User.usernameAvailable(user.username)).isFalse();
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
				User user = getAll().get(0);
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
	public void validatePasswordFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				User user = getAll().get(0);
				user.email = "";
				user.username = "Litreb";
				user.password = "password";
				user.confirmPassword = "password1";

				assertThat(user.validate()).isEqualTo(
						"Your password and confirmation password don't match");
			}
		});
	}

	/**
	 * Test that validation fails for new user, existing email.
	 */
	@Test
	public void validateEmailFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : getAll()) {
					user.username = "";
					user.password = "password";
					user.confirmPassword = "password";

					assertThat(user.validate()).isEqualTo(
							"Email already registered");
				}
			}
		});
	}

	/**
	 * Test that validation fails for new user, existing user name.
	 */
	@Test
	public void validateUsernameFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : getAll()) {
					user.email = "litreb@example.com";
					user.password = "password";
					user.confirmPassword = "password";

					assertThat(user.validate()).isEqualTo(
							"Username already registered");
				}
			}
		});
	}

	/**
	 * Test that validation fails for an existing users.
	 */
	@Test
	public void validateExistingUserFail() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				for (User user : getAll())
					assertThat(user.validate()).isNotNull();
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
				assertThat(User.encrypt("snigel")).isEqualTo(
						"ymQOxIo1jtNQ+1OcNOluIQ");
			}
		});
	}

	/**
	 * Test encryption
	 */
	@Test
	public void validateEncryptionFail1() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.encrypt("")).isNotNull();
			}
		});
	}

	/**
	 * Test encryption
	 */
	@Test
	public void validateEncryptionFail2() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.encrypt("")).isNotSameAs("");
			}
		});
	}

	/**
	 * Test encryption
	 */
	@Test
	public void validateEncryptionFail3() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.encrypt("snigel1")).isNotSameAs(
						"ymQOxIo1jtNQ+1OcNOluIQ");
			}
		});
	}
}
