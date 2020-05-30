package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas Boardgame;
  public static TextureAtlas effectAtlas;
  public static TextureAtlas GameOver;
//  public static TextureAtlas WhellAtlas;

  public static void initAtlas(){
    Boardgame = GAssetsManager.getTextureAtlas("board.atlas");
    effectAtlas = GAssetsManager.getTextureAtlas("effect.atlas");
    GameOver = GAssetsManager.getTextureAtlas("gameOver.atlas");
//    Fottergame = GAssetsManager.getTextureAtlas("fotter.atlas");
//    WhellAtlas = GAssetsManager.getTextureAtlas("wheel.atlas");
  }
}
