package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
    Runs the bot
 */
public class BotRunner
{
    //will store user's instagram credentials
    private Account instagramAccount;
    private WebDriver driver1;
    private WebElement element1;
    private int totalLikes;
    private String HashTag;

    /*
        Initializes instagramAccount & driver field, and opens instagram in chrome
     */
    public BotRunner(String user, String pass, String HashTag, int totalLikes)
    {
        //initialize instagram account, which will be used to log in to the platform
        instagramAccount = new Account(user, pass);

        //the hashtag to be searched
        this.HashTag = HashTag;

        //the amount of likes
        this.totalLikes = totalLikes;

        String path = "C:\\Users\\nsika\\Downloads\\chromedriver_win32\\chromedriver.exe"; //path to find chromedriver
        System.setProperty("webdriver.chrome.driver",path);

        //Open up new chrome browser
        driver1 = new ChromeDriver();

        //open instagram, the landing page is the log in page
        driver1.get("https://www.instagram.com/");

    }

    /*
        Method will type in the user inputted: username, password, & click on the log in button
     */
    public void LogIn() throws InterruptedException
    {
        //find the username field & enter the username
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.name("username")));

        element1.click();
        element1.sendKeys(instagramAccount.getUsername());

        //find the password field & enter the password
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.name("password")));

        element1.click();
        element1.sendKeys(instagramAccount.getPassword());

        //click the log in button
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.xpath("/html/body/div[1]/section/main/article/div[2]/div[1]/div/form/div/div[3]/button")));

        //pause for 3 seconds while log in button shows up
        Thread.sleep(3000);

        element1.click();
    }

    /*
        Method that finds the search bar, clicks it, and enters the hashtag to like pictures with
     */
    public void Search() throws InterruptedException
    {
        //click the search button & search the hashtag
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[1]/div/span[2]")));
        element1.click();

        //search hashtag
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver ->driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/input")));
        element1.sendKeys(HashTag);

        //enter
        element1.sendKeys(Keys.ENTER);
        element1.sendKeys(Keys.RETURN);

        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver ->driver.findElement(By.xpath("/html/body/div[1]/section/nav/div[2]/div/div/div[2]/div[3]/div/div[2]/div/div[1]/a/div")));
        element1.click();

        Thread.sleep(10000);
    }

    /*
        Method that locates the first picture in most recent feed of the hashtag, and then likes a certain amount of pictures after that
     */
    public void LikePictures() throws InterruptedException
    {
        //tracks how many pictures have been liked
        int liked = 0;

        //IG feeds are divided into columns of 3, with never ending rows. To get through all of them, you must
        int row = 1;
        int column = 1;

        //the full xpath of the first pic in the most recent photos, allowing us to like fresh pictures
        String firstPicXPath = "/html/body/div[1]/section/main/article/div[2]/div/div[1]/div[1]/a/div";

        //click on the first picture to open the zoomed view where you can like a picture
        element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                .until(driver -> driver.findElement(By.xpath(firstPicXPath)));

        Thread.sleep(3000);
        element1.click();

        //use the actions class to enable double clicking
        Actions action = new Actions(driver1);

        long start = System.currentTimeMillis();
        //keep liking pictures until the users like requirement is met
        while(liked<=totalLikes)
        {
            Thread.sleep(3000);

            //find the like button
          //  element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
           //         .until(driver -> driver1.findElement(By.xpath("/html/body/div[5]/div[2]/div/article/div[3]/section[1]/span[1]/button/div/span/svg")));

            //like button
         //   /html/body/div[5]/div[2]/div/article/div[3]/section[1]/span[1]/button

            //click the like button
            // Thread.sleep(3000);
            // element1.click();

            ///x path of the picture
            String frameXpath = "/html/body/div[5]/div[2]/div/article/div[2]/div/div/div[3]";
            String frameclass = "ZyFrc";

            //find the main picture frame
            element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                     .until(driver -> driver1.findElement(By.className(frameclass)));

            //double click the picture
            Thread.sleep(1000);
            action.doubleClick(element1).perform();

            //sleep for a second
//            Thread.sleep(1000);

            //stores the right arrow xPath
//            String arrowXpath = "/html/body/div[5]/div[1]/div/div/a[2]";

            //find right arrow to move forward & click
//            element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
//                    .until(driver -> driver1.findElement(By.xpath(arrowXpath)));
//            element1.click();

            //sleep for a second
            Thread.sleep(1000);

            //stores the right arrow css selector
            String arrowCss = "body > div._2dDPU.CkGkG > div.EfHg9 > div > div > a._65Bje.coreSpriteRightPaginationArrow";

            //finds the right arrow to move forward & click. Don't know which one works so I have both just in case
            element1 = new WebDriverWait(driver1, Duration.ofSeconds(3))
                    .until(driver -> driver1.findElement(By.cssSelector(arrowCss)));
            element1.click();

            //increase liked count
            liked++;
        }

        long finish = System.currentTimeMillis();

        driver1.quit();
        System.out.println("Our bot liked " + liked + " pictures for you!");
        System.out.println("Our bot took " + timer(start, finish) + " seconds to run.");
    }

    public long timer(long start, long end)
    {
        //return the start time
        return end-start;
    }

}

