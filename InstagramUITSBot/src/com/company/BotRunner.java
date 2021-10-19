package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Class that runs the Bot's processes
 */
public class BotRunner
{
    //Fields
    private Account instagramAccount;       //will store the user's IG credentials
    private WebDriver driver1;              //the WebDriver for location elements
    private WebElement element1;            //the WebElement for interacting with elements
    private int totalLikes;                 //stores the total amount of pictures to like
    private String HashTag;                 //stores the user inputted hashtag
    private int liked;

    /**
        Constructor: Initializes instagramAccount & driver field, and opens instagram in chrome
        @param user: the instagram username
        @param pass: the instagram password
        @param HashTag: the instagram hashtag to be searched
        @param totalLikes: the number of pictures to like
     */
    public BotRunner(String user, String pass, String HashTag, int totalLikes)
    {
        //initialize instagram account, which will be used to log in to the platform
        instagramAccount = new Account(user, pass);

        //the hashtag to be searched
        this.HashTag = HashTag;

        //the amount of likes
        this.totalLikes = totalLikes;

        //set liked count to 0
        this.liked = 0;

        //path to find chromedriver
        String absolutePath = "C:\\Users\\nsika\\OneDrive\\Attachments\\Desktop\\ChromeDrivers\\chromedriver_win32\\chromedriver.exe";
        String path = "Instagram-Liking-Bot\\InstagramUITSBot\\ChromeDrivers\\chromedriver_win32\\chromedriver.exe";

        //Set property to find chrome driver using path where it is stored
        System.setProperty("webdriver.chrome.driver",absolutePath);

        //Open up new chrome browser
        driver1 = new ChromeDriver();

        //open instagram, where the landing page is the log in page
        driver1.get("https://www.instagram.com/");
    }

    /**
        Log In Method will type in the user inputted: username, password,
        & click on the log in button
     */
    public void LogIn() throws InterruptedException
    {
        //find the username field
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.name("username")));

        //click the username field
        element1.click();

        //enter the username
        element1.sendKeys(instagramAccount.getUsername());

        //find the password field
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.name("password")));

        //click the password field
        element1.click();

        //enter the user's password
        element1.sendKeys(instagramAccount.getPassword());

        //find the log in button
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.xpath("/html/body/div[1]/section/main/article/div[2]/div[1]/div/form/div/div[3]/button")));

        //pause for 2 seconds while log in button shows up. Doesn't show up until the username and password is inputted
        Thread.sleep(2000);

        //click the log in button
        element1.click();
    }

    /**
        Method that finds the search bar, clicks it,
        enters the user inputted hashtag, and completes the search to land on the
        hashtag's page.
     */
    public void Search() throws InterruptedException
    {
        //find the search button
//        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
//                .until(driver -> driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[1]/div/span[1]")));

        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.cssSelector("#react-root > section > nav > div._8MQSO.Cx7Bp > div > div > div.LWmhU._0aCwM > div.pbgfb.Di7vw > div > span._6RZXI.coreSpriteSearchIcon")));

        //click the search button
        element1.click();

        //find the search box within the search bar, which is used for entering text
//        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
//                .until(driver ->driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/input")));

        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver ->driver.findElement(By.cssSelector("#react-root > section > nav > div._8MQSO.Cx7Bp > div > div > div.LWmhU._0aCwM > input")));
        
        //enter the hashtag in the search bar
        element1.sendKeys(HashTag);

        //click the enter & return button
        element1.sendKeys(Keys.ENTER);
        element1.sendKeys(Keys.RETURN);


        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver ->driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[3]/div/div[2]/div/div[1]/a/div")));
        element1.click();

        //sleep for 3 secs for syncing
        Thread.sleep(3000);
    }

    /**
        Method that locates the first picture in most recent feed of the hashtag, 
        and then likes a certain amount of pictures after that
     */
    public void LikePictures() throws InterruptedException
    {
        long start = 0, finish = 0;

        try 
        {
            //the full xpath of the first pic in the most recent photo section, allowing us to like freshly posted pictures
            String firstPicXPath = "/html/body/div[1]/section/main/article/div[2]/div/div[1]/div[1]/a/div";

            //find the first picture to open the zoomed view where you can like a picture
            element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                    .until(driver -> driver.findElement(By.xpath(firstPicXPath)));

            //sleep for 2 seconds to give the bot time to sync with the browser
            Thread.sleep(2000);

            //click on the first picture in the most recent photo section
            element1.click();

            //use the actions class to enable double clicking to like pictures
            Actions action = new Actions(driver1);

            //track the start time of the liking process
            start = System.currentTimeMillis();

            //keep liking pictures until the users like requirement is met
            while (liked < totalLikes) {
                //sleep for 2 seconds to sync everything
                Thread.sleep(2000);

                ///x path of the picture
                String frameXpath = "/html/body/div[5]/div[2]/div/article/div[2]/div/div/div[3]";

                //the class name of the ig picture frame
                String frameclass = "ZyFrc";

                //find the main picture frame
                element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                        .until(driver -> driver1.findElement(By.className(frameclass)));

                //wait for a sec & double click the picture
                Thread.sleep(1000);
                action.doubleClick(element1).perform();

                //sleep for a second
                Thread.sleep(1000);

                //stores the right arrow css selector
                String arrowCss = "body > div._2dDPU.CkGkG > div.EfHg9 > div > div > a._65Bje.coreSpriteRightPaginationArrow";

                //finds the right arrow to move forward & click
                element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                        .until(driver -> driver1.findElement(By.cssSelector(arrowCss)));
                element1.click();

                //increase like count
                liked++;
            }
        }
        //Occasionally, the driver accidentally taps on a tagged person, account or hashtag. This will get it back to normal.
        catch(Exception e)
        {
            //refresh the page
            driver1.navigate().refresh();

            //search the hashtag again
            Search();

            //keep liking pictures
            LikePictures();
            
            System.out.println("\nThere was an error with the liking process.\n");
            e.printStackTrace();
        }
        finally
        {
            finish = System.currentTimeMillis();
            //print out stats
            System.out.println("Our bot liked " + liked + " pictures for you!");
            System.out.println("Our bot took " + timer(start, finish) + " seconds to run.");

            driver1.quit();
        }

    }

    /**
     * Times a code snippet
     * @param start: the start time of the process in millisecs
     * @param end: the end time of the process in millisecs
     * @return the total time taken in seconds (converted from millisecs)
     */
    public long timer(long start, long end)
    {
        //return the time taken in seconds
        return (end-start)/1000;
    }

}