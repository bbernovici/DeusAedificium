package com.deusbuilding.util;

public class Bresenham {

    public static void putLineIntoMatrix(int[][] schemaMatrix, double startX, double endX, double startY, double endY, int type) {
        int x, x2, y, y2;
//        if(startX <= endX) {
        x = (int) startX;
        x2 = (int) endX;
//        } else {
//            x = (int) endX;
//            x2 = (int) startX;
//        }
//        if(startY <= endY) {
        y = (int) startY;
        y2 = (int) endY;
//        } else {
//            y = (int) endY;
//            y2 = (int) startY;
//        }
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        for (int i=0;i<=longest;i++) {
            schemaMatrix[x][y] = type;
            schemaMatrix[x+1][y] = type;
            schemaMatrix[x-1][y] = type;
            schemaMatrix[x][y+1] = type;
            schemaMatrix[x][y-1] = type;
            numerator += shortest ;
            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
        }

    }
}
