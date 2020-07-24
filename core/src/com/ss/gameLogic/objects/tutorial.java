package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GStage;

public class tutorial {
  private Group group;
  GShapeSprite blackOver = new GShapeSprite();
  tutorial(Group group){
    this.group = group;
    blackOver.createRectangle(true,0,0, GStage.getWorldWidth(), GStage.getWorldHeight());
    blackOver.setColor(0,0,0,0.7f);
    group.addActor(blackOver);
  }
  public void setZindex(int zindex){
    blackOver.setZIndex(zindex);
  }
  public void dispose(){
    blackOver.clear();
    blackOver.remove();
  }
}
