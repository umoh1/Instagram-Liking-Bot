package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException
    {
        //intro
        System.out.println("WELCOME TO THE UITS INSTAGRAM LIKER BOT!" +
         "\nInstructions \n_______________________\n " +
                "Enter your instagram credentials, the hashtag you want to search, and the number of pictures you want liked. \n" +
                "We will take care of the rest!\n\n");

        Scanner in = new Scanner(System.in);

        //get credentials
        System.out.println("Please enter your instagram username, email address, or password.");
        String username = in.nextLine();

        System.out.println("Please enter your instagram password.");
        String password = in.nextLine();

        //get hashtag to be searched
        System.out.println("Please enter the hashtag you would like to search.");
        String HashTag = in.nextLine();

        //get number of pictures bot should like
        System.out.println("Please enter the number of pictures you want the bot to like.");
        int totalLikes = in.nextInt();

        //initialize bot with user info
        BotRunner bot = new BotRunner(username, password, HashTag, totalLikes);

        //Run Bot
        bot.LogIn();

        //sleep for 30 seconds while I put in 2FA code
        Thread.sleep(15000);

        bot.Search();
        bot.LikePictures();
        System.out.println("Thank you so much for running our simple instagram bot!");
    }
}
