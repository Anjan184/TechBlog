package com.tech.blog.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.tech.blog.entities.Message;


public class LogoutServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession s=request.getSession();
	s.removeAttribute("currentUser");
	Message m=new Message("Logout Successfully","success","alert-success");
	
	s.setAttribute("msg", m);
	response.sendRedirect("login_page.jsp");
	
	
	
	}

}
