package controllers;



public class Security extends Secure.Security {


    static boolean authenticate(String username, String password) {
//        return User.connect(username, password) != null;
    	return true;

    }


    static void onDisconnected() {
        Application.index();
    }


    static void onAuthenticated() {

    }



}
