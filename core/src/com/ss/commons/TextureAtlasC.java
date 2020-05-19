package com.ss.commons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.ss.core.util.GAssetsManager;

public class TextureAtlasC {
  public static TextureAtlas Boardgame;
//  public static TextureAtlas Fottergame;
//  public static TextureAtlas WhellAtlas;

  public static void initAtlas(){
    Boardgame = GAssetsManager.getTextureAtlas("board.atlas");
//    Fottergame = GAssetsManager.getTextureAtlas("fotter.atlas");
//    WhellAtlas = GAssetsManager.getTextureAtlas("wheel.atlas");
  }
}
