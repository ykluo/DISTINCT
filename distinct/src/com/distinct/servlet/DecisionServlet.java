package com.distinct.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.distinct.database.AlgoDB;
import com.distinct.database.DBConnect;

/**
 * Servlet implementation class DecisionServlet
 */
@WebServlet("/confirm/")
public class DecisionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DecisionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Enumeration<String> parameterNames = request.getParameterNames();
		
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues[0].equals("confirm")) {
				String[] read = DBConnect.readMessage(paramName);
				if (read != null)
					AlgoDB.merge(read[0], read[1]);
			} else if (paramValues[0].equals("decline")) {
				DBConnect.readMessage(paramName);
			} 
		}
		
		response.sendRedirect("index.jsp");
	}

}
