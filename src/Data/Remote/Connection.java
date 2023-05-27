package Data.Remote;

import Resource.Node;
import Resource.Util;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connection {
    private Profile profile;
    private final String userDataPath = System.getProperty("user.dir") + "\\src\\Data\\Remote\\";
    private WebDriver driver;
    private ChromeOptions options;
    private int manualControl = 2;

    public Connection(Profile profile) {
        this.profile = profile;
        initDriver();
    }

    private void initDriver() {
        System.setProperty("webdriver.chrome.driver", userDataPath+ "\\chromedriver.exe");
        try {
            options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("user-data-dir=" + userDataPath + profile.userDataPath);
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.managed_default_content_settings.images", manualControl);
            options.setExperimentalOption("prefs", prefs);
            options.setAcceptInsecureCerts(true);
            driver = new ChromeDriver(options);
            navigateDriver("https://www.twitter.com/");
        }catch (Exception ignored){}
    }

    //clearing profile
    private void scrollDown(WebDriver driver){((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 800)");}
    private long pagePosition(WebDriver driver) { return (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");}
    public void clearProfile() {
        synchronized (Thread.currentThread()) {
            try {
                Files.delete(Path.of(userDataPath + profile.userDataPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeDriver() {
        driver.quit();
    }

    //login into profiles
    public void loginProfile() {
        synchronized (Thread.currentThread()) {
            try {
                navigateDriver("https://twitter.com/login");
                Thread.sleep(3000);
                driver.findElement(RemotePaths.usernameInputField).sendKeys(profile.username);
                driver.findElement(RemotePaths.nextLoginButton).click();
                Thread.sleep(1000);
                driver.findElement(RemotePaths.passwordInputField).sendKeys(profile.password);
                driver.findElement(RemotePaths.loginButton).click();
                Thread.sleep(8000);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private void navigateDriver(String url){driver.get(url);}
    public List <String> getFollowings(String targetUserName,Node.Type type) throws InterruptedException {
        if (type == Node.Type.Protected)
            return List.of();
        long newScrollHeight;
        long currentScrollHeight;
        List <String> following = new ArrayList<>(List.of());
        navigateDriver("https://www.twitter.com/"+targetUserName + "/following");
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try{
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            for (int i = 0; i < 100; i++) {
                // Scroll down the page using JavaScript
                currentScrollHeight = pagePosition(driver);

                scrollDown(driver);
                Thread.sleep(1000);

                newScrollHeight = pagePosition(driver);
                //making sure we're not at the end of the page

                List<WebElement> list = driver.findElements(RemotePaths.listOfFollow);
                list.forEach (webElement -> {
                    following.add(webElement.findElement(RemotePaths.userNameInList).getText());
                });

                //making sure we're not at the end of the page
                if(currentScrollHeight == newScrollHeight) break;
            }
        }catch (Exception e){
            System.out.println("Error in the scroll process Getting followings, Element retrieved:"
                    + following.size());
        }

        return following;
    }
    public List <String> getFollowers(String targetUserName,Node.Type type) throws InterruptedException {
        if (type == Node.Type.Protected)
            return List.of();
        long newScrollHeight;
        long currentScrollHeight;
        List <String> followers = new ArrayList<>(List.of());
        navigateDriver("https://www.twitter.com/"+targetUserName + "/followers");
        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Scrolling and checking page position
        try{
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            for (int i = 0; i < 100; i++) {
                // Scroll down the page using JavaScript
                currentScrollHeight = pagePosition(driver);
                scrollDown(driver);
                Thread.sleep(1000);
                newScrollHeight = pagePosition(driver);

                List<WebElement> list = driver.findElements(RemotePaths.listOfFollow);
                list.forEach (webElement -> {
                    followers.add(webElement.findElement(RemotePaths.userNameInList).getText());
                });

                //making sure we're not at the end of the page
                if(currentScrollHeight == newScrollHeight) break;
            }
        }catch (Exception e){
            System.out.println("Error in the scroll process Getting followers, Element retrieved:"
                    + followers.size());
        }

        return followers;
    }

    public Node getBasicInfo(String targetUserName) throws InterruptedException {
        Node node = new Node();
        synchronized (this){
            navigateDriver("https://www.twitter.com/"+targetUserName);
            Thread.sleep(1000);
            node.type = typeIdentifier();
            try {
                node.userName = driver.findElement(RemotePaths.userName).getText();
                node.displayName = driver.findElement(RemotePaths.displayName).getText();
                node.followingCount = Integer.parseInt(driver.findElement(RemotePaths.followingCount).getText());
                node.followerCount = Integer.parseInt(driver.findElement(RemotePaths.followersCount).getText());
            }
            catch (Exception e){Util.Print.retrievalError();}
            //it might don't have a bio or date of birth
            try {
                node.bio = driver.findElement(RemotePaths.bio).getText();
            }
            catch (Exception ignored){}
            try {
            node.dateOfBirth = driver.findElement(RemotePaths.birthDay).getText();
            }
            catch (Exception ignored){}

        }
        return node;
    }

    private Node.Type typeIdentifier() {
        try {
            driver.findElement(RemotePaths.VerifiedNode);
            return Node.Type.Verified;
        } catch (Exception e){
            try {
                driver.findElement(RemotePaths.protectedNode);
                return Node.Type.Protected;
            }catch (Exception ex){return Node.Type.Normal;}
        }
    }

    public void openProfile(Profile profile) {
        manualControl = 1;
        navigateDriver("https://www.twitter.com/");
    }
}
