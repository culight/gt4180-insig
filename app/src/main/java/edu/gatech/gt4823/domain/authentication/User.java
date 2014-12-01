package edu.gatech.gt4823.domain.authentication;

/**
 * Created by Demerrick Moton on 11/8/2014.
 */

import edu.gatech.gt4823.data.utilities.MongoDBClient;

/**
 *
 * @author
 *
 * 		Function group:	Model
 * 		Created for:
 * 		Modifications:
 *
 * 		Purpose: User model
 *
 */

public class User {
    private static String username, firstName, lastName;
    private static int lastSync = -1;
    private static String svcHash_emr, svcHash_img, svcHash_tele;
    private static boolean valid = true;   // true to skip authentication

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        User.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        User.lastName = lastName;
    }

    public static String getName() {
        return firstName + " " + lastName;
    }

    public static int getLastSync() {
        return lastSync;
    }

    public static void setLastSync(int lastSync) {
        User.lastSync = lastSync;
    }

    public static String getSvcHash_emr() {
        return svcHash_emr;
    }

    public static void setSvcHash_emr(String svcHash_emr) {
        User.svcHash_emr = svcHash_emr;
    }

    public static String getSvcHash_img() {
        return svcHash_img;
    }

    public static void setSvcHash_img(String svcHash_img) {
        User.svcHash_img = svcHash_img;
    }

    public static String getSvcHash_tele() {
        return svcHash_tele;
    }

    public static void setSvcHash_tele(String svcHash_tele) {
        User.svcHash_tele = svcHash_tele;
    }

    public static boolean isValid() {
        return valid;
    }

    public static void signOut() {
        username = null;
        firstName = null;
        lastName = null;
        lastSync = -1;
        svcHash_emr = null;
        svcHash_img = null;
        svcHash_tele = null;
        valid = false;
    }

    public static boolean checkLoginCredentials(String username, String password) {
        MongoDBClient.queryAccountLogin(username, password);
        if (getUsername() != null) {
            valid = true;
            return true;
        }
        valid = false;
        return false;
    }

}
