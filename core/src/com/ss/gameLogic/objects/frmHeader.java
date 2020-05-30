package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class frmHeader {
    Group gr = new Group();
    public frmHeader(){
        GStage.addToLayer(GLayer.top,gr);
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmTitle");
        frm.setPosition(GStage.getWorldWidth()/2,100, Align.center);
        gr.addActor(frm);
        for(int i=0;i<4;i++){
            Image ic = GUI.createImage(TextureAtlasC.GameOver,"icon"+(i+1));
            ic.setPosition((frm.getX()-ic.getWidth()/2)+i*160,frm.getY());
            gr.addActor(ic);
        }

    }
}
