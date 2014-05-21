package com.distinct.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.distinct.database.DBConnect;
import com.distinct.domain.Author;
import com.distinct.domain.Publication;

/**
 * Servlet implementation class ResultQueryServlet
 */
@WebServlet("/paper/")
public class ResultQueryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultQueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String key = request.getParameter("id");
		String type = request.getParameter("type");
		if (type.equals("author")){
			Author a = DBConnect.searchAuthor(key);
			List<Publication> lp = DBConnect.getPublications(DBConnect.searchAuthorPaperKeys(key));
			
			request.setAttribute("author", a);
			request.setAttribute("publications", lp);
			getServletContext().getRequestDispatcher("/author.jsp").forward(request, response);
		} else {
			Publication p = DBConnect.getPublication(key);
			List<Author> la = DBConnect.searchAuthorList(key);
			
			request.setAttribute("publication", p);
			request.setAttribute("authors", la);
			getServletContext().getRequestDispatcher("/paper.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
