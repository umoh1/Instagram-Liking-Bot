package com.company;

/**
 * Class that holds a User's Instagram Credentials, allowing them to log in.
 */
public class Account
{
    //Username can be either a username, email address, or password (as allowed by instagram)
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
