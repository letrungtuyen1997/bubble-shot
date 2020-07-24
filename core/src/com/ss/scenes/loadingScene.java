package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.BitmapFontC;
import com.ss.commons.PaticleConvert;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;

public class loadingScene extends GScreen {
    Group group = new Group();

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        loading();
        new PaticleConvert();
        TextureAtlasC.loadAtlas();
        SoundEffect.initSound();
        C.init();
        Config.loadjson();

    }

    float waitTime = 3f;
    @Override
    public void run() {
         waitTime-= Gdx.graphics.getDeltaTime();
         if(waitTime<=0) {
             if (!GAssetsManager.isFinished()) {
                 GAssetsManager.update();
             } else {
                 TextureAtlasC.initAtlas();
                 BitmapFontC.initBitmapFont();
                 this.setScreen(new StartScene());
             }
         }

    }
    void loading(){
        Image bg = new Image(new Texture("textureAtlas/bgload.png"));
        bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
        bg.setScale(1,-1);
        bg.setOrigin(Align.center);
        group.addActor(bg);
        Image load = new Image(new Texture("textureAtlas/loadding.png"));
        load.setOrigin(Align.center);
        load.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2+200, Align.center);
        group.addActor(load);
        aniload(load);
    }
    void aniload(Image img){
        img.addAction(Actions.sequence(
                Actions.rotateBy(360,1f),
                GSimpleAction.simpleAction((d, a)->{
                    aniload(img);
                    return true;
                })
        ));
    }
}
