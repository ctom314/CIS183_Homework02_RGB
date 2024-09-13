package com.ctom314.homework02_program1;

public class ColorInfo
{
    // Vars
    private int red;
    private int green;
    private int blue;
    private String hex;

    // Constructor
    public ColorInfo(int r, int g, int b)
    {
        red = r;
        green = g;
        blue = b;

        // Convert RGB to hex
        // %02X: A format specifier for a two-digit hexadecimal integer, like FF or 00
        // Result: #RRGGBB using the ints red, green, and blue
        hex = String.format("#%02X%02X%02X", red, green, blue);
    }

    // Getters
    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public String getHex()
    {
        return hex;
    }

    // Setters
    public void setRed(int red)
    {
        this.red = red;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
    }

    public void setHex(String hex)
    {
        this.hex = hex;
    }
}
