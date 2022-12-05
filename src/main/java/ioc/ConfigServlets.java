package ioc;

import com.google.inject.servlet.ServletModule;
import filters.AuthFilter;
import filters.CharsetFilter;
import filters.DataFilter;
import filters.DemoFilter;
import servlets.*;

public class ConfigServlets extends ServletModule {
    @Override
    protected void configureServlets() {
        filter("/*").through(CharsetFilter.class);
        filter("/*").through(DataFilter.class);
        filter("/*").through(AuthFilter.class);
        filter("/*").through(DemoFilter.class);

        serve("/hello").with(HelloServlet.class);
        serve("/register").with(RegisterServlet.class);
        serve("/profile").with(ProfileServlet.class);
        serve("/image/*").with(ImagesServlet.class);
        serve("/confirm/").with(ConfirmEmailServlet.class);
        serve("/").with(ShowcaseServlet.class);
        serve("/showcase").with(ShowcaseServlet.class);
        serve("/car").with(CarServlet.class);
    }
}
