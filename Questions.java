import java.util.Arrays;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Comparator;
import java.util.Collections;
import java.util.Vector;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
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

    static void q1(Formatter formated, dbConnect conn, String team1, String team2) {
        String team1code, team2code;
        String chain;
        String[] code;

        code = conn.sendQuery("SELECT \"team code\" FROM \"team\" WHERE name='" + team1 + "' GROUP BY \"team code\";", new String[] {"team code"});
        team1code = code[0].replace("\n", "");

        code = conn.sendQuery("SELECT \"team code\" FROM team WHERE name='" + team2 + "' GROUP BY \"team code\";", new String[] {"team code"});
        team2code = code[0].replace("\n", "");

        if (code[0].length() == 0) {
            formated.format("%s", "Invalid username or password.");
            return;
        }

        chain = getTeamChain(conn, team1code, team2code, new ArrayList<String>(), true);

        if (chain.equals("NULL")) {
            formated.format("%s has never beaten any team, thus the chain is null.", team1);
            return;
        }

        formated.format("%s", chain);
        // return chain;
    }

    private static String getTeamChain(dbConnect conn, String team1code, String team2code, ArrayList<String> ignore, boolean first) {
        String[] otherteam;
        String[] compare;
        String[] seasons;
        ignore.add(team1code);

        String[] querryresult = conn.sendQuery("SELECT game.\"season\",htc.\"team code\",vtc.\"team code\" FROM game " +
            "INNER JOIN team_game_statistics AS htc ON htc.\"game code\"=game.\"game code\" AND htc.\"team code\"=game.\"home team code\" " + 
            "INNER JOIN team_game_statistics AS vtc ON vtc.\"game code\"=game.\"game code\" AND vtc.\"team code\"=game.\"visit team code\" " +
            "WHERE " + 
            "(htc.\"team code\"=" + team1code + " AND (htc.\"points\" > vtc.\"points\")) OR " +
            "(vtc.\"team code\"=" + team1code + " AND (vtc.\"points\" > htc.\"points\")) ORDER BY game.\"season\";"
            , new String[] {"season", "htc.\"team code\"", "vtc.\"team code\""});

        if (querryresult[0].equals("")) {
            return "NULL";
        }

        seasons = querryresult[0].split("\n");

        otherteam = querryresult[1].split("\n");
        compare = querryresult[2].split("\n");

        /* Creates and array of each team team 1 beat in every season. */
        for (int i = 0; i < otherteam.length; i++) {
            if (otherteam[i].equals(team1code)) {
                otherteam[i] = compare[i];
            }
        }

        /* Iterates through the array of teams and sees if team 2 is one of those teams, if so returns when team 1 beat them. */
        for (int i = 0; i < otherteam.length; i++) {
            if (otherteam[i].equals(team2code)) {
                String[] name1 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team1code+" GROUP BY \"name\";", new String[] {"name"});
                String[] name2 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team2code+" GROUP BY \"name\";", new String[] {"name"});

                if (first) {
                    return name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") + ".";
                } else {
                    return "and " + name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") + ".";
                }
            }
        }

        /* Iterates through the array of teams and recursivly calls this function again, accept team1 is the next team. Doesn't call duplicate teams. */
        for (int i = 0; i < otherteam.length; i++) {
            if (!ignore.contains(otherteam[i])) {
                String[] name1 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+team1code+" GROUP BY \"name\";", new String[] {"name"});
                String[] name2 = conn.sendQuery("SELECT \"name\" FROM team WHERE \"team code\"="+otherteam[i]+" GROUP BY \"name\";", new String[] {"name"});

                String link = getTeamChain(conn, otherteam[i], team2code, ignore, false);
                if (!link.equals("NULL") && !link.equals("")) {
                    return name1[0].replace("\n", "") + " beat " + name2[0].replace("\n", "") + " " + seasons[i].replace("\n", "") 
                        + ", " + link;
                }
            }
        }

        return "";
    }

    static void q2(Formatter formated, dbConnect conn, String playerOne, String playerTwo) {
        String [] Q2Tables = {"player", "team"};
        String [] Q2Columns = {"player code", "first name", "last name", "home town", "home state", "last school", "name"};
        String Q2query = conn.Q2MakeQuery(playerOne, playerTwo, Q2Tables, Q2Columns);
        String [][] Q2Data = conn.Q2sendQuery(Q2query, Q2Columns);
        int i = 0, j = 0;
        String P1Name = Q2Data[0][1] + " " + Q2Data[0][2];
        String P2Name = Q2Data[1][1] + " " + Q2Data[1][2];

        // Checking if any of the players personal information matches such as same team, hometown, same high school
        if (Q2Data[0][6].equals(Q2Data[1][6])) {
            formated.format(P1Name + " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " both played for " + Q2Data[0][6] + ".");
        }
        else if (Q2Data[0][5].equals(Q2Data[1][5])) {
            formated.format(P1Name+ " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " both attended " + Q2Data[0][5] + ".");
        }
        else if (Q2Data[0][3].equals(Q2Data[1][3]) && Q2Data[0][4].equals(Q2Data[1][4])) {
            formated.format(P1Name + " (" + playerOne + ")" + " and " + P2Name + " (" + playerTwo + ")" + " are both from " + Q2Data[0][3] + ", " + Q2Data[0][4] + ".");
        }
        else {
            for (i = 0; i < Q2Tables.length; i++) {
                Q2Tables[i] = "null";
            }
            for (i = 0; i < Q2Columns.length; i++) {
                Q2Columns[i] = "null";
            }
            Q2query = conn.Q2MakeQuery(Q2Data[0][0], Q2Data[1][0], Q2Tables, Q2Columns);
            System.out.println(Q2query);
            String[][] newData = conn.Q2sendQuery(Q2query, Q2Columns);
            int counter = 0;
            for (i = 0; i < newData.length; i++) {
                for (j = 0; j< newData[i].length; j++) {
                }
                counter++;
            }
            String teamOne = Q2Data[0][6];
            String teamTwo = Q2Data[1][6];
            Boolean foundMatch = false;
            // Checks to see if their teams ever played against each other
            for (i = 0; i < counter; i++) {
                if (newData[i][3].equals(teamOne) && newData[i][4].equals(teamTwo) || newData[i][3].equals(teamTwo) && newData[i][4].equals(teamOne)) {
                    formated.format(P1Name + " (" + playerOne + ")" + " played for " + teamOne + " and " + P2Name + " (" + playerTwo + ")" + " played for " + teamTwo + ". These two team played against each other in " + newData[i][2] + ".");
                    return;
                }
            }
            String temp;
            String opponent;
            int nextIndex;
            j = 3;
            // If the teams never played against each other, this algorithm searches for a common team that both of the teams played
            for (i = 0; i < counter; i++) {
                if (!newData[i][j].equals(teamOne) && !newData[i][j].equals(teamTwo)) {
                    temp = newData[i][j];
                    opponent = newData[i][j + 1];
                    nextIndex = i + 1;
                    while ((nextIndex < counter) && (!foundMatch)) {
                        if (temp.equals(newData[nextIndex][j]) && !opponent.equals(newData[nextIndex][j + 1]) || temp.equals(newData[nextIndex][j + 1]) && !opponent.equals(newData[nextIndex][j])) {
                            foundMatch = true;
                            if (teamOne.equals(opponent)) {
                                formated.format(P1Name + " (" + playerOne + ")" + " played for " + teamOne + ", " + "which played against " + temp + " in " + newData[i][2] + ", which played against " + teamTwo +  " in " + newData[nextIndex][2] + ", which had " + P2Name + " (" + playerTwo + ")" + " on their team.");
                            }
                            else {
                                formated.format(P2Name + " (" + playerTwo + ")" + " played for " + teamTwo + ", " + "which played against " + temp + " in " + newData[i][2] + ", which played against " + teamOne +  " in " + newData[nextIndex][2] + ", which had " + P1Name + " (" + playerOne + ")" + " on their team.");
                            }
                            break;
                        }
                        else {
                            nextIndex++;
                        }
                    }
                }
            }
            
        }
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

    static void q4(Formatter formated, dbConnect conn, String conference) {
        //find the conference code given the string
        String query = "SELECT DISTINCT \"conference code\" FROM conference WHERE conference.name = '" + conference + "';";
        String[] data = conn.sendQuery(query, new String[]{ "conference code" });
        String conference_code = data[0].split("\n")[0];
        // System.out.print(conference_code);

        //find all the teams codes in the conference
        query = "SELECT DISTINCT \"team code\" FROM team WHERE team.\"conference code\" = '" + conference_code + "';";
        data = conn.sendQuery(query, new String[]{ "team code" });
        String[] conference_team_codes = data[0].split("\n");
        // for(int n = 0; n < conference_team_codes.length; n++){
        //     System.out.print(conference_team_codes[n]);
        //     System.out.print("\n");
        // }

        //find all the team codes, Home and Visitor
        query = "select distinct home_code from game2 where game2.home_conference_code = '" + conference_code + "' or game2.visitor_conference_code = '" + conference_code + "' union select distinct visitor_code from game2 where game2.visitor_conference_code = '" + conference_code + "' or game2.home_conference_code = '" + conference_code + "';";
        data = conn.sendQuery(query, new String[]{ "team codes" });
        String[] all_team_codes = data[0].split("\n");
        // for(int n = 0; n < all_team_codes.length; n++){
        //     System.out.print(all_team_codes[n]);
        //     System.out.print("\n");
        // }
        // System.out.println(all_team_codes.length);

        //make an overall_rating for every team in all_team_codes (maybe us a hashmap here)
        /*
            for every team in all_team_codes:
                overall_rating = number_wins / number games
        */
        double overall_rating;
        double number_wins, number_games;
        Hashtable<String, Double> rating_hash = new Hashtable<String, Double>();
        for(int n = 0; n < all_team_codes.length; n++){
            query = "SELECT COUNT(winner) FROM game2 WHERE game2.winner = " + all_team_codes[n] + ";";
            data = conn.sendQuery(query, new String[]{ "winner" });
            number_wins = Double.parseDouble(data[0].split("\n")[0]);
            
            query = "SELECT COUNT(*) FROM game2 WHERE game2.home_code = " + all_team_codes[n] + " or game2.visitor_code = " + all_team_codes[n] + ";";
            data = conn.sendQuery(query, new String[]{ "count" });
            number_games = Double.parseDouble(data[0].split("\n")[0]);
            overall_rating = number_wins / number_games;

            rating_hash.put(all_team_codes[n], overall_rating);
            // System.out.print(number_wins);
            // System.out.print(" / ");
            // System.out.print(number_games);
            // System.out.print(" = ");
            // System.out.println(overall_rating);
        }
        // System.out.print(rating_hash);
        

        //run algorithm for every team in the conference for every home game
        /*
            Sum = 0
            for every team in the conference:
                for every home game:
                    if won:
                        Sum = Sum + Opponent Overall Rating
                    if lose:
                        Sum = Sum - (1 - Opponent Overall Rating)
                        //Sum = Sum - (My rating - Opponent Rating) //dont think this is going to work
            Hashtable HomeRating(key = team_code, value = Sum / Number_Home_Games)
        */
        double rating = 0;
        String[] winner_codes, visitor_codes;
        Hashtable<String, Double> home_ratings = new Hashtable<String, Double>();
        Vector<Integer> ratings_vector = new Vector<Integer>();
        Vector<String> team_names = new Vector<String>();
        for(int n = 0; n < conference_team_codes.length; n++){
            query = "SELECT winner FROM game2 WHERE game2.home_code = " + conference_team_codes[n] + ";";
            data = conn.sendQuery(query, new String[]{ "winner" });
            winner_codes = data[0].split("\n"); 

            query = "SELECT visitor_code FROM game2 WHERE game2.home_code = " + conference_team_codes[n] + ";";
            data = conn.sendQuery(query, new String[]{ "visitor_code" });
            visitor_codes = data[0].split("\n");
           
            for(int j = 0; j < winner_codes.length; j++){
                if(rating_hash.containsKey(visitor_codes[j])){
                    if(winner_codes[j] == conference_team_codes[n]){ // if won
                        rating = rating + rating_hash.get(visitor_codes[j]);
                    }
                    else{ // if lost
                        rating = rating + 1 - rating_hash.get(visitor_codes[j]);
                    }
                }
            }

            home_ratings.put(conference_team_codes[n], rating);
            ratings_vector.add((int)rating);
            rating = 0;
            query = "SELECT DISTINCT name FROM team WHERE team.\"team code\" = " + conference_team_codes[n] + ";";
            data = conn.sendQuery(query, new String[]{ "home_name" });
            team_names.add(data[0].split("\n")[0]);
        }

        for(int n = 0; n < ratings_vector.size(); n++){
            for(int j = 0; j < ratings_vector.size(); j++){
                if(ratings_vector.elementAt(n) > ratings_vector.elementAt(j)){
                    Collections.swap(ratings_vector, j, n);
                    Collections.swap(team_names, j , n);
                }
            }
        }

        for(int n = 0; n < ratings_vector.size(); n++){
            System.out.print(team_names.elementAt(n));
            System.out.print(" : ");
            System.out.println(ratings_vector.elementAt(n));

        }
        
        //Display this information in a table in HomeRating decending order
        // System.out.println(sorted_home_ratings);
        String output = "Team Name : Home Rating \n";
        for(int n = 0; n < team_names.size(); n++){
            output = output + team_names.elementAt(n) + " : " + ratings_vector.elementAt(n) + " \n";
        }

        formated.format(output);

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
            case("quarterbacks"):
                query = "SELECT position, \"home town\" FROM player;";
                data = conn.sendQuery(query, new String[] { "position", "home town" });
                
                String[] pos = data[0].split("\n");
                towns = data[1].split("\n");

                LinkedHashSet<String> removeDups = new LinkedHashSet<>(Arrays.asList(towns));

                String[] uniqueTowns = removeDups.toArray(new String[] {});
                int[] count = new int[uniqueTowns.length];

                for(int i = 0; i < towns.length; i++) {
                    if(pos[i].equals("QB") && !towns[i].equals("null")) {
                        for(int j = 0; j < uniqueTowns.length; j++) {
                            if(towns[i].equals(uniqueTowns[j])) {
                                count[j]++;
                            }
                        }
                    }
                }

                int maxIdx = 0;
                for(int i = 0; i < count.length; i++) {
                    if(count[i] > count[maxIdx]) {
                        maxIdx = i;
                    }
                }

                formated.format("There are %s quarterbacks from %s.", count[maxIdx], uniqueTowns[maxIdx]);

                break;
        }
    }
}