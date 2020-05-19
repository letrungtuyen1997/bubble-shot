package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.exSprite.GShapeSprite;

public class arrow {
    private float ball_x,  ball_y,  mouse_x,  mouse_y;
    final GShapeSprite blackOverlay = new GShapeSprite();
    arrow(float ball_x, float ball_y, float mouse_x, float mouse_y,Group group){
        blackOverlay.createLine(ball_x,ball_y,mouse_x,mouse_y);
        blackOverlay.setColor(1,0,1,0.6f);
        group.addActor(blackOverlay);
    }


}
