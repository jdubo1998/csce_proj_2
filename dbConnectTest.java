import java.util.Scanner;

public class dbConnectTest {
    public static void main (String args[]) {
        Scanner in = new Scanner (System.in);

        //get username and password from terminal
        System.out.print("Enter username: ");
        String user = in.nextLine();
        System.out.print("Enter password: ");
        String pswd = in.nextLine();

        //set up a dbConnect object to talk to the database
        dbConnect conn = new dbConnect(user, pswd);

        //set up a query to use and what columns to use
        String[] columns = {"name", "season"};
        String query = conn.makeQuery("conference", columns, 2009);
        
        //send the query to the database
        String[] data = conn.sendQuery(query, columns);

        //split the data into a more usable format
        String[] names = data[0].split("\n");
        String[] season = data[1].split("\n");

        //print all of the cconference names
        System.out.println("Conferene names: ");
        for (int i = 0; i < names.length; i++) {
            System.out.print(names[i]+" ");
        }
        System.out.println();

        //print all of the conference seasons
        System.out.println("Conference season: ");
        for (int i = 0; i < season.length; i++) {
            System.out.print(season[i] + " ");
        }
        System.out.println();

        in.close();
    }
}