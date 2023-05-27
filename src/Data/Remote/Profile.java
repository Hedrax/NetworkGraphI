package Data.Remote;

public class Profile {
    String username;
    String userDataPath;
    String password;
    public static int numberOfProfiles = 10;

    // TODO set the userName and passwords of Twitter accounts
    public Profile(int profile){
        switch (profile) {
            case 0 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#1";
            }
            case 1 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#2";
            }
            case 2 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#3";
            }
            case 3 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#4";
            }
            case 4 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#5";
            }
            case 5 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#6";
            }
            case 6 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#7";
            }
            case 7 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#8";
            }
            case 8 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#9";
            }
            case 9 -> {
                this.username = "";
                this.password = "";
                this.userDataPath = "cache\\userData#10";
            }
        }
    }
}
