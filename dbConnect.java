import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Invalid username or password");
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
            System.err.println(e.getMessage());
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

    String Q2MakeQuery (String playerOne, String playerTwo, String[] tables, String[] columns) {
        String query;
        if (tables[0].equals("null")) {
            query = "SELECT DISTINCT (player_game_statistics.\"game code\"), player_game_statistics.\"player code\", player_game_statistics.season, away.name, home.name\n";
            query += "from player_game_statistics\n";
            query += "JOIN game on player_game_statistics.\"game code\"=game.\"game code\"\n";
            query += "JOIN team AS away on game.\"visit team code\"=away.\"team code\"\n";
            query += "JOIN team AS home on game.\"home team code\"=home.\"team code\"\n";
            query += "where player_game_statistics.\"player code\"= '" + playerOne + "'\n";
            query += "OR player_game_statistics.\"player code\"= '" + playerTwo + "'\n";
            query += "ORDER BY player_game_statistics.\"player code\" ASC;";
        }
        
        else {
            query = "SELECT DISTINCT ON (player.\"player code\") player.\"player code\",\n";

            //add all the columns to use
            int i = 0;
            for(i = 1; i < columns.length; i++) {
                if (i == columns.length - 1) {
                    query +=  tables[1] + "." + "\"" + columns[i] + "\"\n"; 
                }
                else {
                    query +=  tables[0] + "." + "\"" + columns[i] + "\", "; 
                }
            }
            // start adding tables
            query += "FROM " + tables[0] + "\n";

            if(!tables[1].equals("null")) {
                query += "JOIN " + tables[1] + " ON " + tables[0] + "." + "\"team code\" = " + tables[1] + "." + "\"team code\" \n";
            }

            // Adding player names to the query
            query += "WHERE player.\"player code\" = '" + playerOne + "'\n";
            query += "OR player.\"player code\" = '" + playerTwo + "'\n";
            query += "AND player.\"home town\" IS NOT NULL;";
        }


        return query;
    }

    // Sends the query and stores the data in a 2D Array
	String[][] Q2sendQuery(String Q2query, String[] Q2Columns) {
        if (Q2Columns[0].equals("null")) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(Q2query);
                
                /*https://stackoverflow.com/questions/24547406/resultset-into-2d-array/245479098*/
                int nCol = result.getMetaData().getColumnCount();
                List<String[]> table = new ArrayList<String[]>();
                while( result.next()) {
                    String[] row = new String[nCol];
                    for( int iCol = 1; iCol <= nCol; iCol++ ){
                            Object obj = result.getObject( iCol );
                            row[iCol-1] = (obj == null) ?null:obj.toString();
                    }
                    table.add( row );
                }
                String[][] data = new String[table.size()][];
                data = table.toArray(data);

                return data;
                
            } 
            catch (Exception e) {
                System.err.println("Error accessing the database");
            }
            return null;
        }
        else {
            String[][] data = new String[2][Q2Columns.length];
            int row = 0, i = 0, j = 0;

            for (i = 0; i < 2; i++) {
                for (j = 0; j < Q2Columns.length; j++) {
                    data[i][j] = "";
                }
            }

            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(Q2query);
                while (result.next()) {
                    for (i = 0; i < Q2Columns.length; i++) {      
                        data[row][i] += result.getString(i+1);
                    }
                    row++;
                }
            } catch (Exception e) {
                System.err.println("Error accessing the database");
            }
            

            return data;
        }
    }
}