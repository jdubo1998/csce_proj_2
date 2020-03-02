import java.sql.*;

/**
 * Class that is used to be able to connect to a preset psql server
 * @author William Fairleigh
 * 
 * based off of jdbcpostgreSQL.java written by Robert Lightfoot
 * 
 * 2/28/2020 - Original
 * 2/29/2020 - Add makeQuery and bugfixes
 * 
 */

public class dbConnect {
    //The connection that is used to talk to the database
    private Connection conn;

    //The url of the database as well as the username and password to get in
    private static String db = "jdbc:postgresql://csce-315-db.engr.tamu.edu/team2_904_cfb";
    private String user;
    private String pswd;

    /**
     * 
     * Constructor that sets up a connection to team2_904_cfb
     * 
     * @param username the username used to connect to the database
     * @param password the password used to connect to the database
     */
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

    /**
     * @param query the query that will be sent to the database
     * @param columns the columns that should be returned from the database
     * @return a string array of data in each columns that has be returned
     */
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

    /**
     * @param tables the tables to get data from
     * @param columns the columns that hold the needed data
     * @param joinBy what to join the two tables by
     * @param firstColumns how many columns are from the first table
     * @param season what season to get the data from
     * @return a string that conntains a query that will be sent to the database
     */
    String makeQuery(String[] tables, String[] columns, String joinBy, int firstColumns, String season) {
        //start the query
        String query = "SELECT ";

        //add all the columns to use
        int i = 0;
        for(i = 0; i < columns.length - 1; i++) {
            if (i < firstColumns) {
                query +=  tables[0] + "." + "\"" + columns[i] + "\", ";
            } else if  (!tables[1].equals("null")) {
                query += tables[1] + "." + "\"" + columns[i] + "\", ";
            }
        }

        if (i < firstColumns) {
            query +=  tables[0] + "." + "\"" + columns[i] + "\" ";
        } else if (!tables[1].equals("null")) {
            query +=  tables[1] + "." + "\"" + columns[i] + "\" ";
        }

        // start adding tables
        query += "FROM " + tables[0];

        if(!tables[1].equals("null")) {
            for(i = 1; i < tables.length; i++) {
                query += " JOIN " + tables[i] + " ON " + tables[0] + "." + "\"" + joinBy + "\"=" + tables[i] + "." + "\"" + joinBy + "\"";
            }
        }


        //add the season
        if (!season.equals("All") && !tables[1].equals("null")) {
            query += " WHERE " + tables[0] + ".season = " + season + " AND " + tables[1] + ".season = " + season + ";";
        }
        else if (!season.equals("All")) {
            query += " WHERE " + tables[0] + ".season = " + season + ";";
        }
         else {
            query += ";";
        }

        return query;
    }
}