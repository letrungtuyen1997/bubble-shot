package com.ss.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.objects.frmHeader;

public class GameOverScene extends GScreen {
    Group group = new Group();
    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        Create(400);

    }

    @Override
    public void run() {

    }
    private void Create(long Score){
        Image bg = GUI.createImage(TextureAtlasC.GameOver,"bgGameOver");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        new frmHeader();
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmOver");
        frm.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2, Align.center);
        group.addActor(frm);
        /////// label ////////
        Label lbSattus = new Label("Fantastic!",new Label.LabelStyle(BitmapFontC.FontGray,null));
        lbSattus.setFontScale(0.7f);
        lbSattus.setOrigin(Align.center);
        lbSattus.setAlignment(Align.center);
        lbSattus.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.17f,Align.center);
        group.addActor(lbSattus);
        ////// label your Score ///////
        Label lbYSc = new Label("Your Score",new Label.LabelStyle(BitmapFontC.FontOrange, null));
        lbYSc.setFontScale(0.6f);
        lbYSc.setOrigin(Align.center);
        lbYSc.setAlignment(Align.center);
        lbYSc.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.3f,Align.center);
        group.addActor(lbYSc);
        ////// label Score ///////
        Label lbSc = new Label(""+Score,new Label.LabelStyle(BitmapFontC.Font_Yellow, null));
        lbSc.setFontScale(1f);
        lbSc.setOrigin(Align.center);
        lbSc.setAlignment(Align.center);
        lbSc.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.4f,Align.center);
        group.addActor(lbSc);
        ////// label Reward ///////
        Label lbReward = new Label("Rewards",new Label.LabelStyle(BitmapFontC.Font_White, null));
        lbReward.setFontScale(0.5f);
        lbReward.setOrigin(Align.center);
        lbReward.setAlignment(Align.center);
        lbReward.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.54f,Align.center);
        group.addActor(lbReward);
        for(int i=0;i<2;i++){
            Image ic = GUI.createImage(TextureAtlasC.GameOver,"icon"+(i+1));
            ic.setPosition(GStage.getWorldWidth()/2-150+i*200,frm.getY()+frm.getHeight()*0.65f,Align.center);
            group.addActor(ic);
            Label lb = new Label("200",new Label.LabelStyle(BitmapFontC.FontOrange,null));
            lb.setFontScale(0.6f);
            lb.setAlignment(Align.center);
            lb.setPosition(ic.getX()+ic.getWidth()*2,ic.getY()+ic.getHeight()/2,Align.center);
            group.addActor(lb);
        }
    }

}
