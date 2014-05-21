package com.distinct.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.distinct.algorithm.Cluster;
import com.distinct.algorithm.Similarity;
import com.distinct.domain.Author;
import com.distinct.domain.Proceeding;
import com.distinct.domain.Publication;

public class AlgoDB extends DBConnect {
	
	public AlgoDB(){
		super();
	}
	
	static public void loadTuples(){
		try {
			connect();
			String query = "SELECT A.author_key, A.author_name, "
					+ "P.paper_key FROM author A LEFT JOIN publish P "
					+ "ON A.author_key = P.author_key";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			
			rs.next();
			while (!rs.isLast()) {
				Author auth = new Author(rs.getNString("author_name"), rs.getInt("author_key"));
				String pub_key = rs.getString("paper_key");
				Publication pub = (Publication) Similarity.haveNode(pub_key);
				if (pub != null){
					auth.publications.add(pub);
					pub.authors.add(auth);
				} else {
					query = "SELECT * FROM publications where paper_key=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, pub_key);
					ResultSet temprs = pstmt.executeQuery();
					if (temprs.first()){
						pub = new Publication(temprs.getString("paper_key"), temprs.getString("title"));
						String proc_key = temprs.getString("proc_key");
						
						Proceeding proc = (Proceeding) Similarity.haveNode(proc_key);
						if (proc != null){
							auth.publications.add(pub);
							pub.authors.add(auth);
							pub.proceedings.add(proc);
							proc.publications.add(pub);
						} else {
							query = "SELECT * FROM proceedings where proc_key=?";
							pstmt = conn.prepareStatement(query);
							pstmt.setString(1, proc_key);
							temprs = pstmt.executeQuery();
							if (temprs.first()){
								proc = new Proceeding(temprs.getString("proc_key"),
									temprs.getString("year"), temprs.getString("conference"));
								auth.publications.add(pub);
								pub.authors.add(auth);
								pub.proceedings.add(proc);
								proc.publications.add(pub);
							}
						}
					}
				}
				Similarity.authors.add(auth);
				rs.next();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	static public List<String> getNames(){
		try {
			List<String> ls = new ArrayList<String>();
			connect();
			String query = "SELECT *, count(author_name) FROM author "
					+ "GROUP BY author_name HAVING count(author_name) "
					+ "> 1 ORDER BY count(author_name) desc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			rs.next();
			while (!rs.isLast()){
				ls.add(rs.getString("author_name"));
				rs.next();
			}
			
			return ls;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			return null;
		}
	}
	
	static public void mergeAuthors(List<Cluster> lc) {
		for (int i = 0; i < lc.size(); i ++)
			merge(lc.get(i));
	}
	
	static void merge(Cluster c) {
		try {
			if (c.getSize() >= 2){
				connect();
				
				for (int i = 1; i < c.getSize(); i ++) {
					int id1 = c.get(0).getKey();
					int id2 = c.get(i).getKey();
					String sql = "CALL MergeAuthor(?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id1);
					pstmt.setInt(2, id2);
					
					pstmt.execute();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	public static void merge(String id1, String id2) {
		try {
			connect();
			String sql = "CALL MergeAuthor(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id1));
			pstmt.setInt(2, Integer.parseInt(id2));
			
			pstmt.execute();
			
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
}
