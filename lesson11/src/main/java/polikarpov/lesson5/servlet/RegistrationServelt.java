package polikarpov.lesson5.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import polikarpov.lesson5.domain.User;
import polikarpov.lesson5.domain.UserRole;
import polikarpov.lesson5.service.UserService;
import polikarpov.lesson5.service.implementation.UserServiceImplementation;

public class RegistrationServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = UserServiceImplementation.getUserService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(!email.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty()) {
			userService.create(new User(email, firstName, lastName, password, UserRole.USER.toString()));
		}
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("Success");
	}

}
