package com.alphacode98.tansportmanager.Util;

import com.alphacode98.tansportmanager.Modal.User;

public class LoggedUser {
    private static User loggedUser;

    private LoggedUser(){

    }

    public static User getLoggedUser(){
        if (loggedUser == null){
            loggedUser = new User();
        }
        return loggedUser;
    }
}
