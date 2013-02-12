package test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.User;

import org.junit.Test;

public class UserTest {

	@Test
	public void findUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").username).isEqualTo("snigel");
			}
		});
	}
	
	@Test
	public void badUser() {
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(User.findByUsername("snigel").username).isNotEqualTo("fisk");
			}
		});
	}

}
