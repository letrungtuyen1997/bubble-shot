package com.ss.gameLogic.config;

import com.ss.core.util.GStage;
public class Config {
    public static float ScreenW = GStage.getWorldWidth();
    public static float ScreenH = GStage.getWorldHeight();
    public static boolean checkConnet = true;
    public static boolean checkWheel = false;
    public static float paddingY=90;
    public static float BALL_W=80;
    public static float BALL_H=80;
    public static float BALL_RADIUS = BALL_W/2;
    public static float BALL_DIAMETER = BALL_RADIUS*2;
    public static float BALL_SPEED=70;
    public static float Dr_Ball_Cre=0.05f;
    public static float ROW_HEGHT = (float)(BALL_DIAMETER * Math.sin(Math.PI/3));
    public static int quantityBall =9;
    public static float GridMove=0.1f;
    public static float SpeedX=0;
    public static float SpeedY=0;
    public static float bounce =-100;
    public static float Speed_Drop=15;
    public static float draBounce=0.2f;
    public static float dradestroyball=0.07f;

}
