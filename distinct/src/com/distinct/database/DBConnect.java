package com.distinct.database;

import java.sql.*;
import com.mysql.jdbc.Driver;
import java.util.ArrayList;
import java.util.List;

import com.distinct.domain.Author;
import com.distinct.domain.Proceeding;
import com.distinct.domain.Publication;

public class DBConnect {
	static protected Connection conn = null;
	static protected Statement stmt = null;
	static protected PreparedStatement pstmt = null;
	static protected ResultSet rs = null;
	
	static void connect() {
		try {
			if (conn == null || conn.isClosed()){
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/distinct?" + 
						"user=root&password=luoyunkai");
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void insertProc(Proceeding proc){
		try {
			connect();
			String insql = "INSERT INTO Proceedings"
						 + "(proc_key, conference, year, location) VALUES"
						 + "(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(insql);
			pstmt.setString(1, proc.getKey());
			pstmt.setString(2, proc.getPublisher());
			pstmt.setString(3, proc.getYear());
			pstmt.setString(4, proc.getLocation());
			
//			if (proc.getPublisher() != null 
//					&& stmt.executeQuery("SELECT conference FROM Conferences WHERE publisher="+proc.getPublisher()+"'") == null) {
//				String sql = "INSERT INTO Conferences"
//						 + "(conference, publisher) VALUES"
//						 + "(?, ?)";
//				PreparedStatement pstmt2 = conn.prepareStatement(sql);
//				pstmt2.setString(1, proc.getPublisher());
//				pstmt2.setString(2, proc.getPublisher());
//				pstmt2.executeUpdate();
//			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	static void insertPub(Publication pub){
		try {
			connect();
			
			String insql = "INSERT INTO Publications"
					 + "(paper_key, title, proc_key) VALUES"
					 + "(?, ?, ?)";
			pstmt = conn.prepareStatement(insql);
			pstmt.setString(1, pub.getKey());
			pstmt.setString(2, pub.getTitle());
			pstmt.setString(3, pub.getProcKey());
			
			pstmt.executeUpdate();
			
			for (int i = 0; i < pub.getAuthorList().size(); i ++) {
				insertAuthor(pub.getAuthorList().get(i));
				String sql = "INSERT INTO Publish"
						 + "(author_key, paper_key) VALUES"
						 + "(?, ?)";
				PreparedStatement pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, pub.getAuthorList().get(i).getKey());
				pstmt2.setString(2, pub.getKey());
				pstmt2.executeUpdate();
			}
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	static void insertAuthor(Author auth) {
		try {
			connect();
			String insql = "INSERT INTO Author"
						 + "(author_key, author_name) VALUES"
						 + "(null, ?)";
			pstmt = conn.prepareStatement(insql);
			pstmt.setString(1, auth.getName());
			
			pstmt.executeUpdate();
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SHOW TABLE STATUS LIKE 'author'");
			rs.next();
			auth.setKey(rs.getInt("Auto_increment"));
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public static List<String[]> searchByName(String s) {
		try {
			connect();
			String query = "SELECT T.author_key, T.author_name, T.paper_key, S.title "
					+ "from (select a.author_key, a.author_name, b.paper_key "
					+ "from ((select * from author where author_name=?) a "
					+ "inner join publish b on a.author_key=b.author_key) "
					+ "order by a.author_key) T left join publications S "
					+ "on T.paper_key=S.paper_key";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			
			
			List<String[]> r = new ArrayList<String[]>();
			while (rs.next()) {
				String[] temp = {Integer.toString(rs.getInt("author_key")), rs.getString("author_name"),
						rs.getString("paper_key"), rs.getString("title")};
				r.add(temp);
			}
			return r;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		} 
	}
	
	public static List<String> searchAuthorPaperKeys(String id) {
		try {
			List<String> ls = new ArrayList<String>();
			
			connect();
			String query = "SELECT * FROM publish WHERE author_key=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				ls.add(rs.getString("paper_key"));
			}
			return ls;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static Author searchAuthor(String id) {
		try {
			connect();
			String query = "SELECT * FROM author WHERE author_key=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				Author auth = new Author(rs.getString("author_name"), rs.getInt("author_key"));
				return auth;
			} else
				return null;
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static List<Publication> getPublications(List<String> ids) {
		try {
			connect();
			List<Publication> lp = new ArrayList<Publication>();
			for (int i = 0; i < ids.size(); i ++){
				String id = ids.get(i);
				String query = "SELECT * FROM publications WHERE paper_key=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()){
					lp.add(new Publication(rs.getString("paper_key"), rs.getString("title")));
				}
			}
			
			return lp;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static Publication getPublication(String id) {
		try {
			connect();
			String query = "SELECT * FROM publications WHERE paper_key=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				Publication pub = new Publication(rs.getString("paper_key"), rs.getString("title"));
				pub.setProcKey(rs.getString("proc_key"));
				return pub;
			} else
				return null;
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static List<Author> searchAuthorList(String id) {
		try {
			connect();
			List<String> ids = getAuthorListKeys(id);
			List<Author> la = new ArrayList<Author>();
			
			for (int i = 0; i < ids.size(); i ++){
				String aid = ids.get(i);
				String query = "SELECT * FROM author WHERE author_key=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(aid));
				rs = pstmt.executeQuery();
				
				if (rs.next()){
					la.add(new Author(rs.getString("author_name"), rs.getInt("author_key")));
				}
			}
			
			
			return la;
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	static List<String> getAuthorListKeys(String id) {
		try {
			List<String> ls = new ArrayList<String>();
			connect();
			
			String query = "SELECT * FROM publish WHERE paper_key=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				ls.add(rs.getString("author_key"));
			}
			
			return ls;
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static boolean checkUserLogin(String uname, String passwd) {
		try {
			connect();
			String query = "SELECT * FROM users WHERE user_id=? AND password=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uname);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();
			
			return rs.next();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
	}
	
	public static void insertRequest(String id1, String id2) {
		try {
			connect();
			String sql = "INSERT INTO messages(id1, id2) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id1);
			pstmt.setString(2, id2);
			pstmt.execute();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public static List<String[]> getAuthors() {
		try {
			List<String[]> ls = new ArrayList<String[]>();
			
			String sql = "SELECT * FROM messages WHERE handled=0";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String id1 = rs.getString("id1");
				String id2 = rs.getString("id2");
				String id = Integer.toString(rs.getInt("message_id"));
//				String name1 = searchAuthorName(id1);
//				String name2 = searchAuthorName(id2);
				
				
				String[] temp = {id1, id2, id};
				
				ls.add(temp);
			}
			
			return ls;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static String searchAuthorName(String id) {
		try {
			String query = "SELECT author_name FROM author WHERE author_key=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getString("author_name");
			} else
				return null;
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public static String[] readMessage(String mid) {
		try {
			connect();
			String sql = "UPDATE messages SET handled=1 WHERE message_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(mid));
			pstmt.execute();
			
			sql = "SELECT * FROM messages WHERE message_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(mid));
			rs = pstmt.executeQuery();
			
			
			if (rs.next()){
				String[] ret = {rs.getString("id1"), rs.getString("id2")};
				return ret;
			} else {
				return null;
			}
			
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}

}
