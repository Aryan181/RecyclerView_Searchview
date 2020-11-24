package com.example.recyclerview;

class Coordinate {
    private String x;
    private String y;

    public Coordinate( String x, String y) {
        this.x = x;
        this.y = y;
    }



    public String getX(){
        return this.x;
    }

    public String getY(){
        return this.y;
    }

    public void setX(String newX){
        this.x = newX;
    }
    public void setY(String newY){
        this.y = newY;
    }

}