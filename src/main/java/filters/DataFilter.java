package filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import services.data.DataService;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;

@Singleton
public class DataFilter implements Filter {
    private FilterConfig filterConfig;
    @Inject
    private DataService dataService;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // ((HttpServletRequest)servletRequest).getRequestURI()
        // DataService dataService = new MysqlDataService() ;
        Connection connection = dataService.getConnection();
        if (connection == null) {
            servletRequest.getRequestDispatcher("WEB-INF/static.jsp").forward(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute("DataService", dataService);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
