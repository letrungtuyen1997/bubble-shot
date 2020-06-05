package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ss.core.util.GAssetsManager;

public class BitmapFontC {
  public static BitmapFont timeFont;
  public static BitmapFont FontAlert;
  public static BitmapFont FontScore;
  public static BitmapFont FontGray;
  public static BitmapFont FontOrange;
  public static BitmapFont Font;
  public static BitmapFont Font_Yellow;
  public static BitmapFont FontRed;
  public static BitmapFont Font_White;
  public static BitmapFont Font_Brown;

  public static void initBitmapFont(){
    timeFont = GAssetsManager.getBitmapFont("font_Time.fnt");
    FontAlert = GAssetsManager.getBitmapFont("alert_font.fnt");
    FontScore = GAssetsManager.getBitmapFont("font_Score.fnt");
    FontGray = GAssetsManager.getBitmapFont("font_Gray.fnt");
    FontOrange = GAssetsManager.getBitmapFont("font_Orange.fnt");
    Font = GAssetsManager.getBitmapFont("font2.fnt");
    Font_Yellow = GAssetsManager.getBitmapFont("font_Yellow.fnt");
    FontRed = GAssetsManager.getBitmapFont("font_Red.fnt");
    Font_White =  GAssetsManager.getBitmapFont("font_White.fnt");
    Font_Brown = GAssetsManager.getBitmapFont("font_brown.fnt");
  }
}
