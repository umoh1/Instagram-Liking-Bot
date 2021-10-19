package com.company;

import java.util.Scanner;
import java.io.*;

/**
 * The main class that will collect user input, initialize the bot, and run it.
 * @param args
 * @throws InterruptedException
 * @throws IOException
 * @author N.J Umoh
 */
public class Main {

    //fields
    private static String username;
    private static String password;
    private static String HashTag;
    private static int totalLikes;
    private static boolean twoFA;
    private static String twoFactor;

    public static void main(String[] args) throws InterruptedException, IOException
    {
        //intro
        System.out.println("WELCOME TO THE UITS INSTAGRAM LIKER BOT!" +
         "\nInstructions \n_______________________\n " +
                "Enter your instagram credentials, the hashtag you want to search, and the number of pictures you want liked. \n" +
                "We will take care of the rest!\n\nUSER INPUT:\n______________________");

        try 
        {
            //get user input
            getInput();

            System.out.println(twoFA);

            //run the bot
            runBot();

            //Thank you message
            System.out.println("\nThank you so much for running our simple instagram bot!");
        }
        catch(Exception e)
        {
            System.out.println("There was an error with our Instagram bot: " + e.toString());
        } 
    }

    /**
     * Gets the user inputs for the Instagram bot.
     */
    public static void getInput()
    {
        Scanner in = new Scanner(System.in);
        
        //security for password input
        Console console = System.console();

        do
        {
            //get credentials
            System.out.println("\nPlease enter your Instagram account username, email address, or phone number.");
            username = in.nextLine().trim();
         
            password = new String(console.readPassword("Please enter your instagram password: "));

            //get hashtag to be searched
            System.out.println("\nPlease enter the hashtag you would like to search.");
            HashTag = in.nextLine().trim();

            //get number of pictures bot should like
            System.out.println("Please enter the number of pictures you want the bot to like.");

            if(in.hasNextInt())
            {
                totalLikes = in.nextInt();
            }
            else
            {
                totalLikes = 0;
            }
            in.nextLine();

            //ask if they have 2FA to implement a wait.
            System.out.println("Do you have 2FA implemented on your instagram? Please enter Y for yes, and N for no.");
            twoFactor = in.nextLine().toUpperCase();

        } while(!validateInput());

        in.close();
    }

    /**
     * Validates the input given by the user. This follows basic programming validation, in addition
     * to Instagram validation.
     * @return true if validation passes, false if a field doesn't pass.
     */
    public static boolean validateInput()
    {
        //username validation
        if(username.isEmpty()||username.length()>30)
        {
            System.out.println("Please note that your username must be between 1-30 characters long, as instagram permits.\n Please try again.");
            return false;
        }

        //password validation
        if(password.isEmpty()||password.length()<6||password.length()>100)
        {
            System.out.println("Please note that your password must be between 6 & 100 characters long, as instagram permits.\n Please try again.");
            return false;
        }

        //hashtag validation
        if(HashTag.isEmpty()||HashTag.charAt(0)!='#'||HashTag.length() > 50)
        {
            System.out.println("Please note that the hashtag must be between 1-50 characters long, and must start with a #.\n Please try again.");
            return false;
        }

        //total likes validation
        if(totalLikes<5||totalLikes>500)
        {
            System.out.println("Please note that our bot can like between 5 - 500 pictures.\n Please input an appropriate amount of pictures & try again.");
            return false;
        }

        //two factor validation
        if(twoFactor.isEmpty()||(twoFactor.charAt(0)!='Y' && twoFactor.charAt(0)!='N')||twoFactor.length()>3)
        {
            System.out.println("Please make sure to enter either a Yes or a No to whether you have 2FA enabled.\n Please try again.");
            return false;
        }

        twoFA = false;

        //if 2FA is enabled
        if(twoFactor.charAt(0)=='Y')
        {
            System.out.println("You have indicated that 2FA is enabled on your instagram account. "+
            "\nOnce the bot starts, you will have 30 seconds to input the authentication code.");
            twoFA = true;
        }

        // if validation tests are passed
        return true;
    }

    /**
     * Grabs the global inputs & uses it to run the instagram bot.
     */
    public static void runBot() throws InterruptedException, IOException
    {
        //initialize bot with user info
        BotRunner bot = new BotRunner(username, password, HashTag, totalLikes);

        //Run Bot
        bot.LogIn();

        //i 2FA is enabled, it will give the user 30 seconds to 
        if(twoFA)
        { 
            System.out.println("Waiting 30 seconds for 2FA.");
            Thread.sleep(30000);
            System.out.println("Done waiting.");
        }
        
        //Search the hashtag
        bot.Search();

        //Like the entered amount of pictures
        bot.LikePictures();
    }
}
