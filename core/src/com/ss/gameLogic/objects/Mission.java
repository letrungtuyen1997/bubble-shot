package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.C;

public class Mission {
    private Group group = new Group();
    private Group grbtnRed = new Group();
    private Group groupScroll = new Group();
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private Table table;
    private  Table tableScroll;
    private Image frm;

    Mission(){
        GStage.addToLayer(GLayer.top,group);
        WhiteOverLay.createRectangle(true,-GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2,GStage.getWorldWidth(),GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.7f);
        group.addActor(WhiteOverLay);
        frm = GUI.createImage(TextureAtlasC.GameOver,"frmMission");
        frm.setPosition(0,0, Align.center);
        group.addActor(frm);
        group.setScale(0);
        group.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
        group.addAction(Actions.scaleTo(1,1,0.2f));
        Label lb = new Label(C.lang.lbAchiment,new Label.LabelStyle(BitmapFontC.Font_White,null));
        lb.setAlignment(Align.center);
        lb.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+lb.getPrefHeight(),Align.center);
        group.addActor(lb);
        Label lb2 = new Label(C.lang.lbComplete+"20/20",new Label.LabelStyle(BitmapFontC.FontGray,null));
        lb2.setFontScale(0.5f);
        lb2.setOrigin(Align.center);
        lb2.setAlignment(Align.center);
        lb2.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.15f,Align.center);
        group.addActor(lb2);
        group.addActor(grbtnRed);
        btn(grbtnRed,frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.9f,"btnRed",C.lang.lbBtnRed);
        renderListView();



    }
    private void renderListView(){
        groupScroll.setWidth(frm.getWidth());
        groupScroll.setHeight(frm.getHeight()*0.65f);
        groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        ///////// scroll table ////
        table = new Table();
        tableScroll = new Table();
        for (int i=0;i<20;i++){
            tableScroll.row().pad(10);
            Group grT = new Group();
            Image tile = GUI.createImage(TextureAtlasC.GameOver,"tile");
            grT.addActor(tile);
            grT.setSize(tile.getWidth(),tile.getHeight());
            tableScroll.add(grT).center();
//            tableScroll.row().pad(10);

        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);

    }
    private void btn(Group grbtn,float x,float y,String type,String lb){
        Image btn = GUI.createImage(TextureAtlasC.GameOver,type);
        btn.setPosition(0,0);
        grbtn.addActor(btn);
        Label lbbtn = new Label(lb,new Label.LabelStyle(BitmapFontC.FontAlert,null));
        lbbtn.setFontScale(0.7f);
        lbbtn.setOrigin(Align.center);
        lbbtn.setAlignment(Align.center);
        lbbtn.setPosition(btn.getX()+btn.getWidth()/2,btn.getY()+btn.getHeight()/2,Align.center);
        grbtn.addActor(lbbtn);
        grbtn.setWidth(btn.getWidth());
        grbtn.setHeight(btn.getHeight());
        grbtn.setOrigin(Align.center);
        grbtn.setPosition(x,y,Align.center);
    }

}
