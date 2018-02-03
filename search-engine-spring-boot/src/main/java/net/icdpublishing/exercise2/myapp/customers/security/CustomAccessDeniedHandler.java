package net.icdpublishing.exercise2.myapp.customers.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import net.icdpublishing.exercise2.myapp.customers.general.Constants;
import net.icdpublishing.exercise2.myapp.customers.service.CustomerService;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	@Autowired
	private CustomerService customerService;

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException exception)
			throws IOException, ServletException {
		
		String email = req.getParameter(Constants.REQUEST_PARAMETER_EMAIL);
		if (email != null) {
			try {				
				customerService.findCustomerByEmailAddress(email);
			} catch (Exception e) {
				//res.sendError(HttpServletResponse.SC_FORBIDDEN);
				res.sendRedirect(req.getContextPath() + "/403");
				
			}
		}
	}

}
