package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas Boardgame;
  public static TextureAtlas effectAtlas;
  public static TextureAtlas GameOver;
  public static TextureAtlas Start;
  public static TextureAtlas CrossPanel;
//  public static TextureAtlas WhellAtlas;

  public static void initAtlas(){
    Boardgame = GAssetsManager.getTextureAtlas("board.atlas");
    effectAtlas = GAssetsManager.getTextureAtlas("effect.atlas");
    GameOver = GAssetsManager.getTextureAtlas("gameOver.atlas");
    Start = GAssetsManager.getTextureAtlas("start.atlas");
    CrossPanel = GAssetsManager.getTextureAtlas("crossPanel.atlas");
//    Fottergame = GAssetsManager.getTextureAtlas("fotter.atlas");
//    WhellAtlas = GAssetsManager.getTextureAtlas("wheel.atlas");
  }
  public static void loadAtlas(){
    GAssetsManager.loadTextureAtlas("board.atlas");
    GAssetsManager.loadTextureAtlas("effect.atlas");
    GAssetsManager.loadTextureAtlas("gameOver.atlas");
    GAssetsManager.loadTextureAtlas("start.atlas");
    GAssetsManager.loadTextureAtlas("crossPanel.atlas");

  }
}
