package Data.Remote;

import Resource.Node;

import java.util.List;

public class Remote {
    String targetUserName;
    Profile profile;
    public Connection connection;
    //Initialization of the class
    public Remote(String targetUserName,int profileNum){
        this.targetUserName = targetUserName;
        this.profile = new Profile(profileNum);
        connection = new Connection(profile);
    }
    public void setTarget(String targetUserName){this.targetUserName = targetUserName;}
    public Node getNode() throws InterruptedException {
        Node node;
        synchronized (this) {
            node = this.connection.getBasicInfo(this.targetUserName);
            node.followers = this.connection.getFollowers(this.targetUserName,node.type);
            node.following = this.connection.getFollowings(this.targetUserName,node.type);
        }
        return node;
    }
    public Node getBasicNode() throws InterruptedException {
        Node node;
        synchronized (this) {
            node = this.connection.getBasicInfo(this.targetUserName);
            node.followers = List.of();
            node.following = List.of();
            node.userName = targetUserName;
        }
        return node;
    }

    public void login(){
        synchronized (this) {
            connection.clearProfile();
            connection.loginProfile();
        }
    }


    public void ManualControl() {
        connection.openProfile(profile);
    }
}


