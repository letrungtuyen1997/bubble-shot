package com.ss.scenes;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.CrossPanel;
import com.ss.gameLogic.objects.SelectSkills;
import com.ss.gameLogic.objects.frmHeader;

public class StartScene extends GScreen {
    private Group gr = new Group();
    private Group grbtnYellow = new Group();
    public com.ss.gameLogic.objects.frmHeader frmHeader;
    public StartScene(){
    }
    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,gr);
        renderBg();
        GMain.platform.ShowBanner(false);
    }

    @Override
    public void run() {

    }
    private void renderBg(){
        SoundEffect.Playmusic(1);
        Config.indexMusic=1;
        Image bg = GUI.createImage(TextureAtlasC.Start,"bgStart");
        bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
        gr.addActor(bg);
        frmHeader = new frmHeader(this);
        frmHeader.CheckLevelUp();
        gr.addActor(grbtnYellow);
        btn(grbtnYellow, GStage.getWorldWidth()/2, GStage.getWorldHeight()*0.62f,"btnYellow", C.lang.lbStart);
        //new SelectSkills(frmHeader,this);
        grbtnYellow.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                new SelectSkills(frmHeader, StartScene.this);

            }
        });
        Image btnGameOther = GUI.createImage(TextureAtlasC.Boardgame,"btnGameOther");
        btnGameOther.setOrigin(Align.center);
        btnGameOther.setPosition(GStage.getWorldWidth()-btnGameOther.getWidth()/2, GStage.getWorldHeight()-btnGameOther.getHeight()/2,Align.center);
        gr.addActor(btnGameOther);
        AnibtnGm(btnGameOther);
        eventBtnGmOther(btnGameOther);
        /////// tutorial ////////
        Image btnTutorial = GUI.createImage(TextureAtlasC.Boardgame,"btnTutorial");
        btnTutorial.setOrigin(Align.center);
        btnTutorial.setPosition(btnTutorial.getWidth()/2, GStage.getWorldHeight()-btnTutorial.getHeight()/2,Align.center);
        gr.addActor(btnTutorial);
        AnibtnGm(btnTutorial);
        boolean checkfirstTime = GMain.prefs.getBoolean("checkFirstTime",true);
        System.out.println("check here: "+checkfirstTime);
        if(checkfirstTime==true){
            Tutorial();
            GMain.prefs.putBoolean("checkFirstTime",false);
            GMain.prefs.flush();
        }
        btnTutorial.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Tutorial();
                return super.touchDown(event, x, y, pointer, button);
            }
        });


    }

    private void btn(Group grbtn,float x,float y,String type,String lb){
        Image btn = GUI.createImage(TextureAtlasC.Start,type);
        btn.setPosition(0,0);
        grbtn.addActor(btn);
        Label lbbtn = new Label(lb,new Label.LabelStyle(BitmapFontC.Font_White,null));
        lbbtn.setFontScale(1f);
        lbbtn.setOrigin(Align.center);
        lbbtn.setAlignment(Align.center);
        lbbtn.setPosition(btn.getX()+btn.getWidth()/2,btn.getY()+btn.getHeight()*0.4f,Align.center);
        grbtn.addActor(lbbtn);
        grbtn.setWidth(btn.getWidth());
        grbtn.setHeight(btn.getHeight());
        grbtn.setOrigin(Align.center);
        grbtn.setPosition(x,y,Align.center);
    }
    private void AnibtnGm(Image btn){
        btn.addAction(Actions.sequence(
                Actions.scaleTo(0.8f,0.8f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.delay(1),
                Actions.moveBy(-5,0,0.1f, Interpolation.bounceIn),
                Actions.moveBy(10,0,0.1f,Interpolation.bounceIn),
                Actions.moveBy(-10,0,0.1f,Interpolation.bounceIn),
                Actions.moveBy(10,0,0.1f,Interpolation.bounceIn),
                Actions.moveBy(-5,0,0.1f,Interpolation.bounceIn),
                GSimpleAction.simpleAction((d, a)->{
                    AnibtnGm(btn);
                    return true;
                }),
                Actions.delay(1)
        ));

    }
    private void eventBtnGmOther(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Play(SoundEffect.ExpUp);
                new CrossPanel();
            }
        });
    }
    private void Tutorial(){
        Group group = new Group();
        GStage.addToLayer(GLayer.top,group);
        Array<Image> arr = new Array<>();
        for (int i=4;i>=1;i--){
            String type ="Vn";
            if(C.lang.idcontry.equals("En")){
                type ="En";
            }
            Image tuto = GUI.createImage(TextureAtlasC.Boardgame,"tuto"+i+type);
            tuto.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
            tuto.setOrigin(Align.center);
            group.addActor(tuto);
            arr.add(tuto);

        }
        for (int i =0;i<arr.size;i++){
            int finalI = i;
            arr.get(i).addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    arr.get(finalI).remove();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }


}
