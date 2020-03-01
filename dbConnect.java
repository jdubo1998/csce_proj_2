import java.sql.*;

/*
William Fairleigh - based off of jdbcpostgreSQL.java written by Robert Lightfoot
CSCE 315
2/28/2020 - Original
2/29/2020 - Add makeQuery
*/

public class dbConnect {
    //The connection that is used to talk to the database
    private Connection conn;

    //The url of the database as well as the username and password to get in
    private static String db = "jdbc:postgresql://csce-315-db.engr.tamu.edu/team2_904_cfb";
    private String user;
    private String pswd;

    //Constructor that is used to connect to the database
    dbConnect(String username, String password) {
        user = username;
        pswd = password;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(db, user, pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //Function that will send a query to the database and give data back
    String[] sendQuery(String query, String[] columns) {
        String[] data = new String[columns.length];

        for (int i = 0; i < data.length; i++) {
            data[i] = "";
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                for (int i = 0; i < columns.length; i++) {              
                    data[i] += result.getString(i+1) + "\n";
                }
            }
        } catch (Exception e) {
            System.err.println("Error accessing the database");
        }

        return data;
    }

    //Give the table, array of columns, and season to make a query
    String makeQuery(String[] tables, String[] columns, String joinBy, int firstColumns, int season) {
        //start the query
        String query = "SELECT ";

        //add all the columns to use
        int i = 0;
        for(i = 0; i < columns.length - 1; i++) {
            if (i < firstColumns) {
                query +=  tables[0] + "." + "\"" + columns[i] + "\", ";
            } else {
                query += tables[1] + "." + "\"" + columns[i] + "\", ";
            }
        }
        if (i < firstColumns) {
            query +=  tables[0] + "." + "\"" + columns[i] + "\" ";
        } else {
            query +=  tables[1] + "." + "\"" + columns[i] + "\" ";
        }

        // start adding tables
        query += "FROM " + tables[0];

        if(tables.length > 1) {
            for(i = 1; i < tables.length; i++) {
                query += " JOIN " + tables[i] + " ON " + tables[0] + "." + "\"" + joinBy + "\"=" + tables[i] + "." + "\"" + joinBy + "\"";
            }
        }


        //add the season
        if (season > 0) {
            query += " WHERE " + tables[0] + ".season = " + season + " AND " + tables[1] + ".season = " + season + ";";
        } else {
            query += ";";
        }

        return query;
    }
}