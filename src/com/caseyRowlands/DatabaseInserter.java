package com.caseyRowlands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class DatabaseInserter {
	
	public void insertAndReadFromDB(Map<String, Integer> map) {
		String databaseURL = "jdbc:mysql://localhost:3306/testdb";
	    String username = "root";
	    String password = "rootroot"; 
	    
		try {
            // Connect to the MySQL database
            Connection connection = DriverManager.getConnection(databaseURL, username, password);
            
            String dropTableQuery = "DROP TABLE IF EXISTS word_occurences";
            PreparedStatement dropTableStmt = connection.prepareStatement(dropTableQuery);
            dropTableStmt.executeUpdate();

            // Create the table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS word_occurences ("
                    + "word VARCHAR(255) PRIMARY KEY,"
                    + "count INT)";
            
            PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery);
            createTableStmt.executeUpdate();

            // Insert each word and its count into the database
            String insertQuery = "INSERT INTO word_occurences (word, count) VALUES (?, ?)";
            
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String word = entry.getKey();
                int count = entry.getValue();
                insertStmt.setString(1, word);
                insertStmt.setInt(2, count);
                insertStmt.executeUpdate();
            }
            
            System.out.println("Data inserted successfully.");
            
            // SELECT query to fetch words and counts in descending order of counts
            String selectQuery = "SELECT word, count FROM word_counts ORDER BY count DESC";
            PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStmt.executeQuery();

            // Print the results
            System.out.println("Word Occurences");
            System.out.println("--------------------");
            while (resultSet.next()) {
                String word = resultSet.getString("word");
                int count = resultSet.getInt("count");
                System.out.println(word + "\t\t" + count);
            }

            // Close resources
            insertStmt.close();
            createTableStmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
