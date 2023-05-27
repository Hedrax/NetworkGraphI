package Data.Database;

import Resource.Node;
import Resource.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Database {
    private static String ListToString(List<String> list){
        if (list == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < list.size();i++) {
            sb.append(list.get(i));
            if(i!= list.size()-1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
    private static List<String> StringToList(String list){
        List<String> myList = new ArrayList<String>(Arrays.asList(list.split(",")));
        return myList;
    }
    static public void addNode(Node node){
        String sql = "insert into nodes values(?,?,?,?,?,?,?,?)";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setString(1,node.userName);
            preparedStatement.setString(2,node.displayName);
            preparedStatement.setString(3,node.bio);
            preparedStatement.setString(4,node.dateOfBirth);
            preparedStatement.setInt(5,node.followerCount);
            preparedStatement.setInt(6,node.followerCount);
            preparedStatement.setString(7,ListToString(node.following));
            preparedStatement.setString(8,ListToString(node.followers));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
    }
    static public void addNodeToNewTable(Node node){
        String sql = "insert into newNodes values(?,?,?,?,?,?,?,?)";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setString(1,node.userName);
            preparedStatement.setString(2,node.displayName);
            preparedStatement.setString(3,node.bio);
            preparedStatement.setString(4,node.dateOfBirth);
            preparedStatement.setInt(5,node.followerCount);
            preparedStatement.setInt(6,node.followerCount);
            preparedStatement.setString(7,ListToString(node.following));
            preparedStatement.setString(8,ListToString(node.followers));
            preparedStatement.executeUpdate();

        } catch (SQLException ignore) {
        }
    }
    public static void deleteAllNodes(){
        String sql = "delete from nodes";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            System.out.println("connected");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
    }
    static public Node selectNode(String userName){
        String sql = "SELECT * FROM newNodes WHERE userName=?";
        Node node = null;
        try(Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);)
        {

            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String username1 = resultSet.getString("userName");
            if (username1 == null)
                return null;
            try{
                if (username1.charAt(0) == '@')
                    username1 = username1.substring(1);
            }catch (Exception ignored){}

            String displayname = resultSet.getString("displayName");
            String bio = resultSet.getString("bio");
            String dateOfBirth = resultSet.getString("dateOfBirth");
            int followingCount = resultSet.getInt("followingCount");
            int followerCount = resultSet.getInt("followerCount");
            String following = resultSet.getString("following");
            String followers = resultSet.getString("followers");
            node = processing(username1,displayname,bio,dateOfBirth,followingCount,followerCount,following,followers);
            connection.close();
            return node;

        } catch (SQLException e) {
            System.out.println("Node is null in db");
            return null;
        }
    }

    private static Node processing(String username, String displayName,String bio, String dateOfBirth, int followingCount, int followersCount ,String following, String followers) {
        checkValue(displayName,username);
        checkValue(bio);
        checkValue(dateOfBirth);
        List <String> followings = checkListValue(following);
        List <String> follower = checkListValue(followers);
        return new Node(username,displayName,bio,dateOfBirth,followingCount,followings,followersCount,follower);

    }
    private static List<String> checkListValue(String list) {
        if (list == null)
            return List.of();
        return StringToList(list);
    }

    private static void checkValue(String value) {
     if (value == null)
         value = "";
    }
    private static void checkValue(String displayName,String username) {
        if (displayName == null)
            displayName = username;
    }



    static public List<Node> SelectNodesRange(int from , int to ){
        String sql = "select * from newNodes where rowid=?";
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;
        for(int y = from ;y<=to;y++){
            try(
                    Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            )
            {
                preparedStatement.setInt(1,y);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                String username = resultSet.getString("userName");
                String displayname = resultSet.getString("displayName");
                String bio = resultSet.getString("bio");
                String dateOfBirth = resultSet.getString("dateOfBirth");
                int followingCount = resultSet.getInt("followingCount");
                int followerCount = resultSet.getInt("followerCount");
                List<String> following = StringToList(resultSet.getString("following"));
                List<String> followers = StringToList(resultSet.getString("followers"));
                node = new Node(username,displayname,bio,dateOfBirth,followingCount,following,followerCount,followers);
                nodes.add(node);

            } catch (SQLException e) {
                Util.Print.dataBaseError();
                Util.Print.printErrot(e);

            }
        }
        return nodes;
    }
    static public void addToUnDiscovered(String userName){
        String sql = "insert into nodeToDiscover values(?)";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();

        } catch (SQLException ignored) {}
    }
    static public void deleteNodeUnDiscovered(String userName){
        String sql = "delete from nodeToDiscover where userName = ?";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setString(1,userName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
    }
    static public void deleteDB(DataBaseTable db){
        String sql = "delete from "+ db.toString();
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
    }


    static public List<String> selectUnDiscoveredRange(int from , int to ){
        String sql = "select * from nodeToDiscover where rowid=?";
        List<String> nodes = new ArrayList<String>();
        for(int y = from ;y<=to;y++){
            try(
                    Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            )
            {
                preparedStatement.setInt(1,y);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                nodes.add(resultSet.getString("userName"));

            } catch (SQLException e) {
                Util.Print.dataBaseError();
                Util.Print.printErrot(e);
            }
        }
        return nodes;
    }
    static public String selectFirstElementInUnDiscovered(){
        String sql = "SELECT * FROM nodeToDiscover WHERE rowid = (SELECT MIN(rowid) FROM nodeToDiscover);";
        String sql2 = "Delete FROM nodeToDiscover WHERE rowid = (SELECT MIN(rowid) FROM nodeToDiscover);";
        String result ="";
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatementDel = connection.prepareStatement(sql2);
        )
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatementDel.executeUpdate();
            resultSet.next();
            result =  resultSet.getString("userName");

        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
        return result;
    }
    static public Boolean nodeExists(String userName){
        String sql = "select * from nodes where userName=?";
        Node node = null;
        try(
                Connection connection= DriverManager.getConnection("jdbc:sqlite:database.db");
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        )
        {
            preparedStatement.setString(1,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            Util.Print.dataBaseError();
            Util.Print.printErrot(e);
        }
        return false;
    }
    public enum DataBaseTable{
        nodes,newNodes,nodeToDiscover
    }
}

