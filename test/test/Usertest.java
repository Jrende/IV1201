package test;
import org.junit.*;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.*;

public class Usertest {
	@Test
	public void assertion() {
		assertThat(true).isEqualTo(true);
	}
	@Test
	public void findUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").username).isEqualTo("snigel");
			}
		});
	}
}
