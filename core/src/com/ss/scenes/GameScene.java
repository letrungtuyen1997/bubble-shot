package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.GMain;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.board;

import java.util.ArrayList;

public class GameScene extends GScreen {
    public Group MainGroup = new Group();
    private Group grSetting = new Group();
    private Image bg;


    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        checkconnect();
        initGroup();
        renderBg();
        new board(MainGroup);
//        if(Config.checkConnet){
//            UpdateViewPort();
//            GMain.platform.ShowBanner(true);
//        }
    }
    private void UpdateViewPort(){
//        Config.paddingY*=-1;
//        for (int i=0;i<header.group.getChildren().size;i++){
//            header.group.getChildren().get(i).addAction(Actions.moveBy(0,Config.paddingY));
//        }
        bg.setWidth(Config.ScreenW);
        bg.setHeight(Config.ScreenH-Config.paddingY);
        bg.setPosition(0,Config.paddingY);
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
