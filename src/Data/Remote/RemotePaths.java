package Data.Remote;

import org.openqa.selenium.By;

public class RemotePaths {
    static By protectedNode = By.cssSelector("svg.r-1nao33i.r-4qtqp9.r-yyyyoo.r-1xvli5t.r-9cviqr.r-f9ja8p.r-og9te1.r-bnwqim.r-1plcrui.r-lrvibr");
    static By VerifiedNode = By.cssSelector("svg.r-1cvl2hr.r-4qtqp9.r-yyyyoo.r-1xvli5t.r-f9ja8p.r-og9te1.r-bnwqim.r-1plcrui.r-lrvibr");
    static By usernameInputField =By.name("text");
    static By nextLoginButton = By.cssSelector("div.css-18t94o4.css-1dbjc4n.r-sdzlij.r-1phboty.r-rs99b7.r-ywje51.r-usiww2.r-2yi16.r-1qi8awa.r-1ny4l3l.r-ymttw5.r-o7ynqc.r-6416eg.r-lrvibr.r-13qz1uu");
    static By passwordInputField = By.name("password");
    static By loginButton = By.cssSelector("div.css-18t94o4.css-1dbjc4n.r-1sw30gj.r-sdzlij.r-1phboty.r-rs99b7.r-19yznuf.r-64el8z.r-1ny4l3l.r-1dye5f7.r-o7ynqc.r-6416eg.r-lrvibr");
    static By userName = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[2]/div[1]/div/div[2]/div/div/div/span");
    static By displayName = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[2]/div/div/div[1]/div/div/span/span[1]");
    static By bio = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[3]/div/div/span");
    static By birthDay = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[4]/div/span[2]/span");
    static By followingCount = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[5]/div[1]/a/span[1]/span");
    static By followersCount = By.xpath("/html/body/div[1]/div/div/div[2]/main/div/div/div/div/div/div[3]/div/div/div/div/div[5]/div[2]/a/span[1]/span");
    static By listOfFollow = By.cssSelector("div.css-1dbjc4n.r-1awozwy.r-18u37iz.r-1wbh5a2");
    static By userNameInList = By.cssSelector("span.css-901oao.css-16my406.r-poiln3.r-bcqeeo.r-qvutc0");
}

    //Todo hey there
    // also u need to add logic to the following and followers extraction process if the node was protected
    // try catch conditions to all the find element and also to driver cause I'm getting sick of the red warning  messages
