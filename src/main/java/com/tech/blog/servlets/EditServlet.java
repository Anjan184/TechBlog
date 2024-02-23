package com.tech.blog.servlets;
//This is git
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import com.tech.blog.dao.UserDao;
import com.tech.blog.entities.Message;
import com.tech.blog.entities.User;
import com.tech.blog.helper.ConnectionProvider;
import com.tech.blog.helper.Helper;

@MultipartConfig
public class EditServlet extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String userEmail = request.getParameter("user_email");
		String userName = request.getParameter("user_name");
		String userPassword = request.getParameter("user_password");
		String userAbout = request.getParameter("user_about");
		Part part = request.getPart("image");
		String imageName = part.getSubmittedFileName();

		HttpSession s = request.getSession();
		User user = (User) s.getAttribute("currentUser");
		user.setEmail(userEmail);
		user.setName(userName);
		user.setPassword(userPassword);
		user.setAbout(userAbout);
		String oldFile = user.getProfile();
		user.setProfile(imageName);

		// Update database
		UserDao userDao = new UserDao(ConnectionProvider.getConnection());
		boolean updatedSuccessfully = userDao.updateUser(user);

		if (updatedSuccessfully) {
			// Update profile image
			String appPath = request.getServletContext().getRealPath("/");
			String imagePath = appPath + "pics" + File.separator + user.getProfile();
			String pathOldFile = appPath + "pics" + File.separator + oldFile;
			if(!oldFile.equals("default.png")) {
			Helper.deleteFile(pathOldFile);
			}
			
			if (Helper.saveFile(part.getInputStream(), imagePath)) {
				Message msg = new Message("Your profile details and image have been updated successfully.", "success","alert-success");
				s.setAttribute("msg", msg);
			} else {
			
				Message msg = new Message("Failed to update your profile image.", "error", "alert-danger");
				s.setAttribute("msg", msg);
			}
		}

		response.sendRedirect("profile.jsp");
	}

}
