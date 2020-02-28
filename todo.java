import java.sql.*;
import java.util.Scanner;

import javax.crypto.NullCipher;

public class todo{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lx1107_todo?user=lx1107&password=2363285";
    public static void main(String[] args) throws SQLException{
       todoHelper();
        }

    public static void todoHelper() {
        try{
            Connection conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("Welcome to to-do list macnager! Here are your current items.");
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM Uncompleted"
            );
    
            //pstmt.setString(1, );
            stmt.execute();
            ResultSet results = stmt.getResultSet();
            int i = 0;
            int j = 1;
            while(results.next()){
                // Since the column of results is a VARCHAR(*), we use the Java String type to access it
                
                //Access a column by index (counted from 1, not 0)
                //String person = results.getString(1);
    
                //Access a column by name
                String person = results.getString("title");
                System.out.println(j + ". " + person);
                j++;
                i++;
            }
            
            System.out.println("What would you like to do?");
            if (i == 0){
                System.out.println("You don't have any item yet! type 'add' to add item :))");
            }
            else{
            System.out.println("1-" + i + ": See more information on this item");
            }
            System.out.println("Mark: Mark an item as completed");
            System.out.println("Add: Add an new item");
            System.out.println("Show: See all completed items");
            System.out.println("Stat: Show the statics by how many items are incomplete and how many are completed");
            System.out.println("Quit: Quit");
    
            Scanner keyboard = new Scanner(System.in);
    
            String choice = keyboard.nextLine();
    
            if (choice.equals("Mark") || choice.equals("mark")){
                System.out.println("What file you want to mark completed?");
                int fileNum = keyboard.nextInt();
                if (fileNum > i){
                    System.out.println("This file dosen't exist, please chose from 1 to " + i + ":))");
                }
                else{
                    String markComplete = "INSERT INTO Completed (title, discription, dueDate, createDate) SELECT title, discription, dueDate, createDate FROM Uncompleted WHERE id = " + fileNum + ";";
                    stmt.execute (markComplete);
                    String delete = "DELETE FROM Uncompleted WHERE id = " + fileNum+";";
                    stmt.execute (delete);
                    String update = "UPDATE Uncompleted SET id = id-1 WHERE id >" + fileNum+";";
                    stmt.execute(update);
                    String insertDateComplete = "UPDATE Completed SET completeDate = NOW() WHERE completeDate IS NULL;";
                    stmt.execute (insertDateComplete);
                }  
            }
    
            else if (choice.equals("Add") || choice.equals("add")){
                System.out.println ("Task title: ");
                String title = keyboard.nextLine();
                System.out.println("Task discription: ");
                String discription = keyboard.nextLine();
                System.out.println("Due day: ");
                int day = keyboard.nextInt();
                System.out.println ("Due month: ");
                int month = keyboard.nextInt();
                System.out.println ("Due year: ");
                int year = keyboard.nextInt();
                String date = year + "-" + month + "-" + day;
                String insert = "INSERT INTO Uncompleted (title, discription, dueDate, createDate) VALUES ('" + title + "','" + discription + "','" + date + "',NOW());";
                stmt.execute (insert);
            }
    
            else if (choice.equals("Show") || choice.equals("show")){
                int count = 1;
                String completed = "SELECT * FROM Completed ORDER BY id DESC";
                stmt.execute(completed);
                ResultSet complete = stmt.getResultSet();
                while (complete.next()){
                    String res = complete.getString("title");
                    System.out.println (count + ": " + res);
                    count ++;
                }
            }
    
            else if (choice.equals("Stat") || choice.equals("stat")){
                String statUn = "SELECT COUNT(*) FROM Uncompleted;";
                stmt.execute(statUn);
                ResultSet uncomp = stmt.getResultSet();
                int uncom = 0;
                if(uncomp.next()){
                uncom = uncomp.getInt(1);
                }
                String statCom = "SELECT COUNT(*) FROM Completed;";
                stmt.execute(statCom);
                ResultSet comp = stmt.getResultSet();
                int com = 0;
                if(comp.next()){
                com = comp.getInt(1);
                }
                String statTime = "SELECT SEC_TO_TIME(AVG(createDate - completeDate)) FROM Completed;";
                stmt.execute(statTime);
                ResultSet avgTime= stmt.getResultSet();
                String avgT = "There's no data yet";
                if(avgTime.next()){
                avgT = avgTime.getString(1);
                }
                System.out.println("The number of uncompleted files is : " + uncom);
                System.out.println("The number of completed files is : " + com);
                System.out.println("The average time for you to complete a task is : " + avgT);
            }
            else if (choice.equals("Quit") || choice.equals("quit")){ 
            }
            else {
              System.out.println("Your command is unvalid! Please try again");
                todoHelper();
            }
    
            //stmt.execute("SELECT title FROM Title LIMIT 10");
            //ResultSet results = stmt.getResultSet();
    
            //for each result in the set
            //(results.next()moves to the next row of the results and returns true if there is a next row)
            //while(results.next()){
                // Since the column of results is a VARCHAR(*), we use the Java String type to access it
                
                //Access a column by index (counted from 1, not 0)
                //String title = results.getString(1);
    
                //Access a column by name
                //String title = results.getString("title");
    
                //System.out.println(title);
            //}
    
                // stmt.execute("SELECT Title.title, Rating.avgRating"
                // + "\nFROM Title"
                // + "\nINNER JOIN Rating ON Title.id = Rating.titleId"
                // + "\nWHERE Rating.numVotes > 1000 AND Rating.avgRating >0"
                // + "\nORDER BY Rating.avgRating DESC");
                //var results = stmt.getResultSet();
    
                // while (results.next()){
                //     String title = results.getString(1);
                //     double rating = results.getDouble(2);
                //     System.out.printf("%20s: %.if/n", title, rating);
                // }
    
                // System.out.println("Enter the title of a TV series: ");
                // var scanner = new Scanner(System.in);
                // String seriesTitle = scanner.nextLine();
                
                // stmt.executeQuery("SELECT Episode.title AS title, EpisodeOf.seriesNum AS series, EpisodeOf.episodeNum AS episode"
                //                    +"\nFROM Title AS Series"
                //                    +"\nINNER JOIN EpisodeOf ON Series.id = EpisodeOf.seriesId"
                //                    +"\nINNER JOIN Title AS Episode ON EpisodeOf.episodeId = Episode.id"
                //                    +"\nWHERE Series.title = '"+ ? + "'"
                //                    +"\nORDER BY EpisodeOf.seasonNum, EpisodeOf.episodeNum");
    
                // var results = stmt.getResultSet();
    
                // while (results.next()) {
                //     String title = results.getString("title");
                //     int season = results.getInt("series");
                //     int episode = results.getInt("episode");
    
                //     System.out.printf("%02d-02d% %s\n", season, episode, title);
                // }
                keyboard.close();
                conn.close();
            }
            catch(SQLException e){
                e.printStackTrace();
                return;
            }
    }
}