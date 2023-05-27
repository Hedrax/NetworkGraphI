package Resource;

import Data.Graph.ConstructionNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node {
    public String userName;
    public String displayName;
    public String bio;
    public String dateOfBirth;
    public int followingCount;
    public Type type = Type.Normal;
    public int followerCount;
    public List<String> following;
    public List<String> followers;
    public Node (String userName,String displayName, String bio,String dateOfBirth,int followingCount , List<String> following,
                 int followerCount,List<String> followers){
        this.displayName =displayName;this.userName = userName;this.followers = followers;
        this.bio = bio;this.following = following;this.dateOfBirth = dateOfBirth;
        this.followingCount = followingCount;this.followerCount = followerCount;
    }
    public Node() {
        this.displayName =null;this.userName = null;this.followers = null;
        this.bio = null;this.following = null;this.dateOfBirth = null;
        this.followingCount = 0;this.followerCount = 0;
    }
    public Node (ConstructionNode node){
        this.displayName = node.displayName;this.userName = node.userName;
        this.bio = node.bio;this.dateOfBirth = node.dateOfBirth;
        this.followingCount = node.followerCount;this.followerCount = node.followerCount;
        this.following = nodesToStrings(node.following);this.followers = nodesToStrings(node.followers);

    }
    private List<String> nodesToStrings(HashSet<ConstructionNode> list){
        List<String> out = new ArrayList<>();
        list.forEach(element->{
            out.add(element.userName);
        });
        return out;
    }
    public enum Type{
        Normal, Protected, Verified
    }

}
