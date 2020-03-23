import java.util.Arrays;
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
        data = conn.sendQuery(query, new String[] { "\"game code\"" });
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
        data = conn.sendQuery(query, new String[]{ "name" });
        String teamName = data[0].split("\n")[0];

        //get date of game
        query = "SELECT date FROM game WHERE \"game code\" = '" + game + "';";
        data = conn.sendQuery(query, new String[]{ "date" });
        String date = data[0].trim();

        //output
        formated.format("%s had %s rushing yards against %s during the game on %s.\n", teamName, maxYards, team, date);
    }

    static void q4(Formatter formated) {

    }

    static void q5(Formatter formated, dbConnect conn, String selection) {
        String query = "";
        String[] data;
        String[] towns;

        switch(selection) {
            case("players"):
                query = "SELECT count(DISTINCT \"player code\"), \"home town\" FROM player GROUP BY \"home town\";";
                data = conn.sendQuery(query, new String[] { "count(\"player code\")", "home town" });

                String[] counts = data[0].split("\n");
                towns = data[1].split("\n");

                int max = Integer.MIN_VALUE;
                String town = "";

                for(int i = 0; i < counts.length; i++) {
                    if(Integer.parseInt(counts[i]) > max && !towns[i].equals("null")) {
                        max = Integer.parseInt(counts[i]);
                        town = towns[i];
                    }
                }

                formated.format("There are %s players from %s.", max, town);

                break;
            case("winners"):
                formated.format("winners not yet implemented");

                //get all the towns
                query = "SELECT DISTINCT \"home town\" FROM player;";
                data = conn.sendQuery(query, new String[]{ "\"home town\"" });
                towns = data[0].split("\n");

                //set up the winners array
                int[] winners = new int[towns.length];

                query = "SELECT \"game code\", max(\"play number\"), \"offense team code\", \"defense team code\", max(\"offense points\"), max(\"defense points\"), season FROM play GROUP BY \"game code\", \"offense team code\", \"defense team code\", season;";
                String[] columns = { "game code", "max(\"play number\")", "offense team code", "defense team code", "max(\"offense points\")", "max(\"defense points\")", "season" };

                data = conn.sendQuery(query, columns);

                // split the data into a more usable format
                String[][] splitData = new String[columns.length][];

                for (int i = 0; i < columns.length; i++) {
                    splitData[i] = data[i].split("\n");
                }

                String[] winningTeams = new String[splitData[0].length/2];
                String[] seasons = new String[splitData[0].length/2];

                for(int i = 1; i < splitData[0].length; i = i+2) {
                    if(Integer.parseInt(splitData[4][i]) > Integer.parseInt(splitData[5][i])) {
                        winningTeams[(int) Math.floor(i/2)] = splitData[2][i];
                    }
                    else {
                        winningTeams[(int) Math.floor(i / 2)] = splitData[3][i];
                    }
                    seasons[(int) Math.floor(i/2)] = splitData[6][i];
                }

                for(int i = 0; i < winningTeams.length; i++) {
                    query = "SELECT \"home town\" FROM player WHERE \"team code\" = '" + winningTeams[i] + "' AND season = '" + seasons[i] + "';";
                    data = conn.sendQuery(query, new String[] { "home town" });
                    System.out.println("sent home town query");

                    String[] teamTowns = data[0].split("\n");

                    for (int j = 0; j < towns.length; j++) {
                        if(Arrays.asList(teamTowns).contains(towns[i]) && !towns[i].equals("null")) {
                            winners[i]++;
                            System.out.println("added to winners");
                        }
                    }
                }

                int maxWinners = Integer.MIN_VALUE;
                int idx = 0;

                System.out.println("finding max winners");
                for(idx = 0; idx < winners.length; idx++) {
                    if(winners[idx] > maxWinners) {
                        maxWinners = winners[idx];
                    }
                }

                formated.format("Players from %s have won %s games.", towns[idx], maxWinners);

                break;
        }
    }
}