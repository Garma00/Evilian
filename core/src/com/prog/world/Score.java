package com.prog.world;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Score
{

    private LocalDate date;
    private LocalTime time;
    private int points;
    
    public Score(int points)
    {
        this.points = points;
        date = java.time.LocalDate.now();
        time = java.time.LocalTime.now();
    }
    
    public Score(int points, LocalDate date, LocalTime time)
    {
        this.date = date;
        this.time = time;
        this.points = points;
    
    }

    
    public int getPoints()
    {
        return this.points;
    }

    public LocalDate getDate()
    {
        return this.date;
    }
    
    public LocalTime getTime()
    {
        return this.time;
    }
    
}
