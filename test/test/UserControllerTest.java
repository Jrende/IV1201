package test;

import static org.fest.assertions.Assertions.assertThat;
import play.libs.WS;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

public class UserControllerTest {

    @Test
    public void testIndexWithTestServerRunnable() {
        running(testServer(3333), new Runnable() {
            @Override
            public void run() {
                assertThat(
                        WS.url("http://localhost:3333").get().get().getStatus()
                ).isEqualTo(OK);
            }
        });
    }

}
