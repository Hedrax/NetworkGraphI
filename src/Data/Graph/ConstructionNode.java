package Data.Graph;

import Resource.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConstructionNode extends Node {
    public HashSet<ConstructionNode> following;
    public HashSet<ConstructionNode> followers;
    public boolean visited;

    public ConstructionNode(Node node){
        this.displayName = node.displayName;
        this.bio = node.bio;
        this.visited = true;
        this.userName = node.userName;
        this.following = new HashSet<>();
        this.followers = new HashSet<>();
    }
    public ConstructionNode(String userName){
        this.displayName = userName;
        this.bio = null;
        this.visited = true;
        this.userName = userName;
        this.following = new HashSet<>();
        this.followers = new HashSet<>();
    }
    public void addFollower(ConstructionNode node){this.followers.add(node);}
    public void addFollowing(ConstructionNode node){this.following.add(node);}
}