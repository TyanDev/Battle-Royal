package net.tyan.battleroyal.reference;

import sun.rmi.server.UnicastServerRef;

/**
 * by Kevin on 23.06.2015.
 */

public class Reference {

    public static final String HOST;

    public static final int PORT;

    public static final String USER;

    public static final String PASSWORD;

    public static final String DATABASE;

    public static final String PREFIX;

    static {
        HOST = "localhost";
        PORT = 3306;
        USER = "battleroyal";
        PASSWORD = "123456";
        DATABASE = "battleroyal";
        PREFIX = "§6>> §eBATTLE ROYAL §6| ";
    }


}
