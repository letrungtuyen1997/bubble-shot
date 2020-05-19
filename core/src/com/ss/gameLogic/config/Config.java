package com.ss.gameLogic.config;

import com.ss.core.util.GStage;
public class Config {
    public static float ScreenW = GStage.getWorldWidth();
    public static float ScreenH = GStage.getWorldHeight();
    public static boolean checkConnet = true;
    public static boolean checkWheel = false;
    public static float paddingY=90;
    public static float BALL_W=106;
    public static float BALL_H=106;
    public static float BALL_RADIUS = BALL_W/2;
    public static float BALL_DIAMETER = BALL_RADIUS*2;
    public static float BALL_SPEED=3;
    public static float Dr_Ball_Cre=0.05f;
    public static float ROW_HEGHT = (float)(BALL_DIAMETER * Math.sin(Math.PI/3));
    public static int quantityBall =6;
    public static float GridMove=0.1f;
}
