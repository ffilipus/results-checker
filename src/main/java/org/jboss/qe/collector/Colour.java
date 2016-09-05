package org.jboss.qe.collector;

/**
 * @author Petr Kremensky pkremens@redhat.com on 08/07/2015
 *
 * https://en.wikipedia.org/wiki/ANSI_escape_code
 */
public enum Colour {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    BLACK_BOLD("\u001B[30;1m"),
    RED("\u001B[31m"),
    RED_BOLD("\u001B[31;1m"),
    GREEN("\u001B[32m"),
    GREEN_BOLD("\u001B[32;1m"),
    YELLOW("\u001B[33m"),
    YELLOW_BOLD("\u001B[33;1m"),
    BLUE("\u001B[34m"),
    BLUE_BOLD("\u001B[34;1m"),
    PURPLE("\u001B[35m"),
    PURPLE_BOLD("\u001B[35;1m"),
    CYAN("\u001B[36m"),
    CYAN_BOLD("\u001B[36;1m"),
    WHITE("\u001B[37m"),
    WHITE_BOLD("\u001B[37;1m"),
    GRAY("\u001B[90m"),
    GRAY_BOLD("\u001B[90;1m");

    String colour;

    Colour(String colour) {
        this.colour = colour;
    }

    public String getColour(){
        return this.colour;
    }
}
