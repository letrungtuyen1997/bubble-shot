package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.board;
import com.ss.gameLogic.objects.header;


public class GameScene extends GScreen {
    public Group MainGroup = new Group();
    private Group grSetting = new Group();
    private Image bg;
    private com.ss.gameLogic.objects.header header;


    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        SoundEffect.Stopmusic(Config.indexMusic);
        SoundEffect.Playmusic(2);
        Config.indexMusic=2;
        checkconnect();
        initGroup();
        renderBg();
        header = new header();
        new board(MainGroup,header,this);
        showAdsBanner();
//        if(Config.checkConnet){
//            UpdateViewPort();
//            GMain.platform.ShowBanner(true);
//        }
    }
    private void showAdsBanner(){
        GMain.platform.ShowBanner(true);
    }
    private void UpdateViewPort(){
//        Config.paddingY*=-1;
        header.grHeader.moveBy(0, Config.paddingY);
//        for (int i=0;i<header.group.getChildren().size;i++){
//            header.group.getChildren().get(i).addAction(Actions.moveBy(0,Config.paddingY));
//        }
        bg.setWidth(Config.ScreenW);
        bg.setHeight(Config.ScreenH- Config.paddingY);
        bg.setPosition(0, Config.paddingY);
//        System.out.println("checkY: "+header.LbScore.getX());

    }
    private void initGroup(){
        GStage.addToLayer(GLayer.ui,MainGroup);

    }


    @Override
    public void run() {
    }

    @Override
    public void render(float var1) {
        super.render(var1);
//        elements.forEach();
    }

    private void renderBg() {
        bg = GUI.createImage(TextureAtlasC.Boardgame, "bg");
        bg.setWidth(Config.ScreenW);
        bg.setHeight(Config.ScreenH);
        MainGroup.addActor(bg);
//        effectWin ef = new effectWin(1,0,0,MainGroup);
//        bg.addListener(new ClickListener(){
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                super.touchUp(event, x, y, pointer, button);
//                ef.changeSprites(2);
//                ef.setPosition(200,1000);
//                ef.start();
//            }
//        });

    }
    private void checkconnect(){
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl("https://www.facebook.com/");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

            }

            @Override
            public void failed(Throwable t) {
                Config.checkConnet=false;

            }
            @Override
            public void cancelled() {

            }
        });
    }

}
