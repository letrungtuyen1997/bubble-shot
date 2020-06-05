package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;

public class SelectSkills {
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private  Group gr = new Group();
    private  Group grbtnRed = new Group();
    private Array<Group> arrGr = new Array<>();
    private Array<Image> arrTile = new Array<>();
    private Array<Label> arrLabel = new Array<>();
    private Array<Long> arrCoins = new Array<>();
    private Array<Integer> arrQuan = new Array<>();
    private frmHeader header;

    public SelectSkills(frmHeader header){
        this.header =header;
        GStage.addToLayer(GLayer.top,gr);
        WhiteOverLay.createRectangle(true,-GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2,GStage.getWorldWidth(),GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.7f);
        gr.addActor(WhiteOverLay);
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmSkills");
        frm.setPosition(0,0, Align.center);
        gr.addActor(frm);
        gr.setScale(0);
        gr.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2);
        gr.addAction(Actions.scaleTo(1,1,0.2f));
        Label lb = new Label(C.lang.boostSkill,new Label.LabelStyle(BitmapFontC.Font_Brown,null));
        lb.setAlignment(Align.center);
        lb.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+lb.getPrefHeight()*1.2f,Align.center);
        gr.addActor(lb);
        gr.addActor(grbtnRed);
        btn(grbtnRed,frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*1.2f,"btnRed",C.lang.lbBtnRed);
        /////////// skills///////
        for(int i=0; i<3;i++){
            Group gric = new Group();
            Image tile= GUI.createImage(TextureAtlasC.GameOver,"tile");
            tile.setOrigin(Align.center);
            tile.setPosition(0,0,Align.center);
            gric.addActor(tile);
            ///////// icon ////////
            Image ic = GUI.createImage(TextureAtlasC.GameOver,"ic"+(i+1));
            ic.setScale(1.5f);
            ic.setOrigin(Align.center);
            ic.setPosition(0,10,Align.center);
            gric.addActor(ic);
            //////// icon coins/////
            Image icon = GUI.createImage(TextureAtlasC.GameOver,"icon2");
            icon.setScale(0.8f);
            icon.setOrigin(Align.center);
            icon.setPosition(tile.getX()+tile.getWidth()*0.2f,tile.getY()+tile.getHeight()*1.2f,Align.center);
            gric.addActor(icon);
            /////// label coins///////
            long price=100*(i+1);
            Label lbCoins = new Label(""+price,new Label.LabelStyle(BitmapFontC.Font_Brown,null));
            lbCoins.setFontScale(0.5f);
            lbCoins.setAlignment(Align.center);
            lbCoins.setPosition(icon.getX()+icon.getWidth()*2f,icon.getY()+icon.getHeight()/2,Align.center);
            gric.addActor(lbCoins);
            ////////// tile green/////////
            Image tileRed = GUI.createImage(TextureAtlasC.GameOver,"tileRed");
            tileRed.setPosition(tile.getX()+tileRed.getWidth()/3,tile.getY()+tileRed.getHeight()/3,Align.center);
            gric.addActor(tileRed);
            Image tileGreen = GUI.createImage(TextureAtlasC.GameOver,"tileGreen");
            tileGreen.setPosition(tile.getX()+tileGreen.getWidth()/3,tile.getY()+tileGreen.getHeight()/3,Align.center);
            gric.addActor(tileGreen);
            tileGreen.setVisible(false);
            ///////// label quantity/////////
            int quan=0;
            Label quantity = new Label(""+quan,new Label.LabelStyle(BitmapFontC.Font_White,null));
            quantity.setFontScale(0.5f);
            quantity.setAlignment(Align.center);
            quantity.setPosition(tileGreen.getX()+tileGreen.getWidth()*0.4f,tileGreen.getY()+tileGreen.getHeight()*0.4f,Align.center);
            gric.addActor(quantity);
            gric.setPosition(frm.getX()+frm.getWidth()*0.22f+(ic.getWidth()*2.1f)*i,frm.getY()+frm.getHeight()*0.47f,Align.center);
            gr.addActor(gric);
            ///////// array//////
            arrGr.add(gric);
            arrLabel.add(quantity);
            arrCoins.add(price);
            arrTile.add(tileGreen);
            arrQuan.add(quan);

        }
        eventSelect();
    }
    private void eventSelect(){
        for (Group gr : arrGr){
            gr.addListener(new ClickListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(arrCoins.get(arrGr.indexOf(gr,true))<=Config.monney){
                        int index = arrGr.indexOf(gr,true);
                        header.updateMonney2(arrCoins.get(index));
                        aniBuy(arrGr.get(index).getX(),arrGr.get(index).getY());
                        arrQuan.set(index,arrQuan.get(index)+1);
                        arrLabel.get(index).setText(arrQuan.get(index));
                        if(arrQuan.get(index)>0){
                            arrTile.get(index).setVisible(true);
                        }
                    }
                }
            });
        }
    }
    private void aniBuy(float x, float y){
        Group grAni = new Group();
        gr.addActor(grAni);
        Label lb = new Label("+1",new Label.LabelStyle(BitmapFontC.FontScore,Color.GREEN));
        lb.setPosition(0,0,Align.center);
        grAni.addActor(lb);
        grAni.setPosition(x,y,Align.center);
        grAni.addAction(Actions.sequence(
                Actions.moveBy(0,-200,1f),
                GSimpleAction.simpleAction((d,a)->{
                    grAni.clear();
                    grAni.remove();
                    return true;
                })
        ));

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
