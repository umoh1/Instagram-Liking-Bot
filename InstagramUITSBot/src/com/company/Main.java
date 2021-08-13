package com.company;

import java.util.Scanner;
import java.io.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException
    {
        //intro
        System.out.println("WELCOME TO THE UITS INSTAGRAM LIKER BOT!" +
         "\nInstructions \n_______________________\n " +
                "Enter your instagram credentials, the hashtag you want to search, and the number of pictures you want liked. \n" +
                "We will take care of the rest!\n\n");

        try {
            Scanner in = new Scanner(System.in);

            //get credentials
            System.out.println("Please enter your Instagram account username, email address, or phone number.");
            String username = in.nextLine().trim();

            System.out.println("Please enter your instagram password.");
            String password = in.nextLine().trim();

            //get hashtag to be searched
            System.out.println("Please enter the hashtag you would like to search.");
            String HashTag = in.nextLine().trim();

            //get number of pictures bot should like
            System.out.println("Please enter the number of pictures you want the bot to like.");
            int totalLikes = in.nextInt();

            //in.close();

            //initialize bot with user info
            BotRunner bot = new BotRunner(username, password, HashTag, totalLikes);

            //Run Bot
            bot.LogIn();

            //sleep for 15 seconds while I put in 2FA code
            Thread.sleep(15000);

            //Search the hashtag
            bot.Search();

            //Like the entered amount of pictures
            bot.LikePictures();

            //Thank you message
            System.out.println("Thank you so much for running our simple instagram bot!");
        }
        catch(Exception e)
        {
            System.out.println("There was an error: " + e.toString());
        }
    }
}
