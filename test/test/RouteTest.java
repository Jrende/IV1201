package test;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;

import org.junit.Test;

import play.mvc.Result;

public class RouteTest {

    @Test
    public void rootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }
    
    @Test
    public void registerRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/register"));
        assertThat(result).isNotNull();
    }
    
    @Test
    public void loginRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/login"));
        assertThat(result).isNotNull();
    }
	
	@Test
	public void badRoute() {
	  Result result = routeAndCall(fakeRequest(GET, "/xxx"));
	  assertThat(result).isNull();
	}

}
