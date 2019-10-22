package com.etisalat.ops.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etisalat.ops.domain.User;
import com.etisalat.ops.service.UserService;
import com.etisalat.ops.service.UserServiceImpl;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/UserRegistrationServlet")
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service=new UserServiceImpl();   
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {					
			String userName=request.getParameter("username");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			User user = new User();
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmail(email);
			int count = service.addNewUser(user);
			if(count> 0) {				
				HttpSession session=request.getSession();
				session.setAttribute("username", userName);
				request.getRequestDispatcher("views/main_menu.jsp")
				.forward(request,response);
			}else {
				/*response.sendRedirect("http://localhost:9090/WebLoginApplication/login.html");*/
				out.println("<html><body><h1><font color='red'>");
				out.println("Invalid Credentials! Re-enter");
				out.println("</font></h1></body></html>");
				request.getRequestDispatcher("user_registration.html").include(request, response);
			}
			
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
