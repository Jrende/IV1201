package view;

import static org.junit.Assert.*;
import views.*;
import views.html.index;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import org.junit.Test;

import play.mvc.Content;

public class IndexViewTest {
    
    @Test
    public void renderTemplate() {
        //Content html = views.html.main("test", "test", true);
        //assertThat(contentType(html)).isEqualTo("text/html");
        //assertThat(contentAsString(html)).contains("test");
    }

}
