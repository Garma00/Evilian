package com.prog.world;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Score
{
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String date;
    private String time;
    private int points;
    
    public Score(int points)
    {
        this.points = points;
        date = java.time.LocalDate.now().format(dateFormatter);
        time = java.time.LocalTime.now().format(timeFormatter);
    }
    
    public Score(int points, String date, String time)
    {
        this.date = date;
        this.time = time;
        this.points = points;
    
    }

    
    public int getPoints()
    {
        return this.points;
    }

    public String getDate()
    {
        return this.date;
    }
    
    public String getTime()
    {
        return this.time;
    }
    
}
