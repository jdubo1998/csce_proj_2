import java.util.Formatter;
import java.util.Locale;
import java.lang.StringBuilder;

public class Questions {
    static void normal(Formatter formated, dbConnect conn, String[] tables, String[] columns, String joinBy, 
            int col1, String season) {

        String query = conn.makeQuery(tables, columns, joinBy, col1, season);

        // send the query to the database
        String[] data = conn.sendQuery(query, columns);

        // split the data into a more usable format
        String[][] splitData = new String[columns.length][];

        for (int i = 0; i < columns.length; i++) {
            splitData[i] = data[i].split("\n");
        }

        // find largets string
        int[] maxlengths = new int[columns.length];

        for (int i = 0; i < columns.length; i++) {
            maxlengths[i] = columns[i].length();
        }

        for (int i = 0; i < splitData.length; i++) {
            for (int j = 0; j < splitData[i].length; j++) {
                maxlengths[i] = Math.max(splitData[i][j].length(), maxlengths[i]);
            }
        }

        // Start formatting the output in an even way
        formated.format("Table 1:%20s\tTable2:%20s\n", tables[0], tables[1]);
        for (int i = 0; i < columns.length; i++) {
            formated.format("%-" + maxlengths[i] + "s%2s|%2s", columns[i], " ", " ");
        }
        formated.format("\n");

        for (int i = 0; i < splitData[0].length; i++) {
            for (int j = 0; j < columns.length; j++) {
                formated.format("%-" + maxlengths[j] + "s%2s|%2s", splitData[j][i], " ", " ");
            }
            formated.format("\n");
        }
    }

    static void q1(Formatter formated) {

    }

    static void q2(Formatter formated) {

    }

    static void q3(Formatter formated, dbConnect conn, String team) {
        //get the team code needed
        String query = "SELECT \"team code\" FROM team WHERE name = '" + team + "';";
        String[] data = conn.sendQuery(query, "\"team code\"".split("\n"));
        String teamCode = data[0].split("\n")[0];

        //get the game codes that the team played in
        query = "SELECT \"game code\" FROM game WHERE \"visit team code\" = '" + teamCode + "' OR \"home team code\" = '" + teamCode + "';";
        data = conn.sendQuery(query, "\"game code\"".split("\n"));
        String[] gameCodes = data[0].split("\n");

        int maxYards = Integer.MIN_VALUE;
        String game = "";
        String bestTeam = "";

        //set up the columns
        String[] columns = new String[2];
        columns[0] = "rush yard";
        columns[1] = "team code";

        //find best team and game
        for (int i = 0; i < gameCodes.length; i++) {
            query = "SELECT \"rush yard\", \"team code\" FROM team_game_statistics WHERE NOT \"team code\" = '" + teamCode + "' AND \"game code\" = '" + gameCodes[i] + "';";

            data = conn.sendQuery(query, columns);

            int yards = Integer.parseInt(data[0].trim());
            if (yards > maxYards) {
                maxYards = yards;
                game = gameCodes[i];
                bestTeam = data[1].trim();
            }
        }

        //get name of best team
        query = "SELECT name FROM team WHERE \"team code\" = '" + bestTeam + "';";
        data = conn.sendQuery(query, "name".split("\n"));
        String teamName = data[0].split("\n")[0];

        //get date of game
        query = "SELECT date FROM game WHERE \"game code\" = '" + game + "';";
        data = conn.sendQuery(query, "date".split("\n"));
        String date = data[0].trim();

        //output
        formated.format("%s had %s rushing yards against %s during the game on %s.\n", teamName, maxYards, team, date);
    }

    static void q4(Formatter formated) {

    }
}