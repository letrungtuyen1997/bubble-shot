package com.ss.scenes;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.objects.SelectSkills;
import com.ss.gameLogic.objects.frmHeader;

public class StartScene extends GScreen {
    private Group gr = new Group();
    private frmHeader frmHeader;
    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,gr);
        renderBg();

    }

    @Override
    public void run() {

    }
    private void renderBg(){
        Image bg = GUI.createImage(TextureAtlasC.Boardgame,"bg");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        gr.addActor(bg);
        frmHeader = new frmHeader();
        new SelectSkills(frmHeader);

    }

}
