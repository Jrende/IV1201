package test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import models.Competence;

import org.junit.Test;

public class CompetenceTest {
	
	@Test
	public void getAll() {
		
		running(fakeApplication(), new Runnable() {
			public void run() {
				assertThat(Competence.getAll().isEmpty()).isFalse();
			}
		});
	}
	
	/**
	 * Find existing competences.
	 */
	@Test
	public void findCompetece() {
		
		running(fakeApplication(), new Runnable() {
			public void run() {
				for(Competence c : Competence.getAll())
					assertThat(Competence.findById(c.competence_id)).isNotNull();
			}
		});
	}
}
