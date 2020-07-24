package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;

public class Mission {
    private Group group = new Group();
    private Group grbtnRed = new Group();
    private Group groupScroll = new Group();
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private Table table;
    private  Table tableScroll;
    private Image frm;
    private Array<Image> arrSclGray = new Array<>();
    private Array<Image> arrSclYellow = new Array<>();
    private Array<Image> arrIccorect = new Array<>();
    private Array<Integer> arrFinish = new Array<>();
    private int count=0;

    Mission(){
        SoundEffect.Play(SoundEffect.panel_out);
        GStage.addToLayer(GLayer.top,group);
        WhiteOverLay.createRectangle(true,-GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth(), GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.7f);
        group.addActor(WhiteOverLay);
        ////////// frm //////////
        frm = GUI.createImage(TextureAtlasC.GameOver,"frmMission");
        frm.setPosition(0,0,Align.center);
        group.addActor(frm);
        //////// panel red //////
        Image panel = GUI.createImage(TextureAtlasC.GameOver,"panelRed");
        panel.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+panel.getHeight()/5,Align.center);
        group.addActor(panel);
        /////// label ////////
        String text = "AchivementVn";
        if(C.lang.idcontry.equals("En"))
            text = "AchivementEn";
        Image lbSattus = GUI.createImage(TextureAtlasC.GameOver,text);
        lbSattus.setOrigin(Align.center);
        lbSattus.setPosition(panel.getX()+panel.getWidth()/2,panel.getY()+lbSattus.getHeight()*0.8f,Align.center);
        group.addActor(lbSattus);
        group.setScale(0);
        group.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2);
        group.addAction(Actions.scaleTo(1,1,0.2f));
        Label lb2 = new Label(C.lang.lbComplete+""+GMain.prefs.getInteger("quantityfinish")+"/8",new Label.LabelStyle(BitmapFontC.FontGray,null));
        lb2.setFontScale(0.5f);
        lb2.setOrigin(Align.center);
        lb2.setAlignment(Align.center);
        lb2.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.15f,Align.center);
        group.addActor(lb2);
        group.addActor(grbtnRed);
        renderListView();
        btn(grbtnRed,frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.9f,"btnRed",C.lang.lbBtnRed);
        grbtnRed.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                grbtnRed.setTouchable(Touchable.disabled);
                group.clear();
                group.remove();
            }
        });
    }
    private void renderListView(){
        groupScroll.setWidth(frm.getWidth());
        groupScroll.setHeight(frm.getHeight()*0.65f);
        groupScroll.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
        /////// data Achivement///////
        FileHandle js = Gdx.files.internal("data/AchivementVn.json");
        if(C.lang.idcontry.equals("En")){
            js = Gdx.files.internal("data/AchivementEn.json");
        }
        String jsonStr = js.readString();
        String jv2 = GMain.platform.GetConfigStringValue("",jsonStr);
        JsonReader json = new JsonReader();
        JsonValue jv;
        try {
            jv = json.parse(jv2);
//            System.out.println("log:"+jv.get("info").asString());
        }catch (Exception e){
            jv = json.parse(jsonStr);
        }
        ///////// scroll table ////
        table = new Table();
        tableScroll = new Table();
        for (int i=0;i<jv.size;i++){
            tableScroll.row().pad(10);
            Group grT = new Group();
            Image tile = GUI.createImage(TextureAtlasC.GameOver,"tileAchivement");
            grT.addActor(tile);
            //////// icon ////////
            Image icon = GUI.createImage(TextureAtlasC.GameOver,"icA"+(i+1));
            icon.setScale(1,-1);
            icon.setOrigin(Align.center);
            grT.addActor(icon);
            //////// sclGray///////
            Image sclGray = GUI.createImage(TextureAtlasC.GameOver,"sclGray");
            sclGray.setPosition(icon.getX()+icon.getWidth()/2,icon.getY()+sclGray.getHeight(),Align.center);
            grT.addActor(sclGray);
            //////// sclYellow///////
            Image sclYellow = GUI.createImage(TextureAtlasC.GameOver,"sclYellow");
            sclYellow.setPosition(icon.getX()+icon.getWidth()/2,icon.getY()+sclGray.getHeight(),Align.center);
            grT.addActor(sclYellow);
            sclYellow.setOrigin(Align.left);
            sclYellow.setScale(0,1);
            /////// label ////////
            Label lb = new Label(jv.get(i).get("info").asString(),new Label.LabelStyle(BitmapFontC.Font_White,null));
            lb.setFontScale(0.6f,-0.6f);
            lb.setAlignment(Align.left);
            lb.setPosition(icon.getX()+icon.getWidth()*1.1f,icon.getY()+icon.getHeight()*0.7f+lb.getPrefHeight(),Align.left);
            grT.addActor(lb);
            //////// icon corect //////
            Image icCorect = GUI.createImage(TextureAtlasC.GameOver,"Iccorect");
            icCorect.setPosition(tile.getX()+tile.getWidth(),tile.getY()+tile.getHeight(),Align.center);
            icCorect.setScale(1,-1);
            grT.addActor(icCorect);
            //////////// array ///////////
            arrSclGray.add(sclGray);
            arrSclYellow.add(sclYellow);
            arrIccorect.add(icCorect);

            /////////////////////////////
            grT.setSize(tile.getWidth(),tile.getHeight());
            tableScroll.add(grT).center();

        }
        ScrollPane Scroll = new ScrollPane(tableScroll);
        table.setFillParent(true);
        table.add(Scroll).fill().expand();
        groupScroll.setScale(1,-1);
        groupScroll.setOrigin(Align.center);
        groupScroll.addActor(table);
        group.addActor(groupScroll);
        updateAchivement(jv);
        noticeFinish(count);


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
    public void updateAchivement(JsonValue jv){
        Config.BubbleMonment = GMain.prefs.getInteger("bubbleMM");
        Config.highScMomnet = GMain.prefs.getLong("HighSc");
        Config.comboMoment = GMain.prefs.getInteger("comboMM");
        Config.levelMoment = GMain.prefs.getInteger("level");
        Config.coinMonment = GMain.prefs.getInteger("coinsMM");
        Config.wowMoment = GMain.prefs.getInteger("wowMM");
        Config.crazyMoment = GMain.prefs.getInteger("crazyMM");
        Config.fanbulousMoment = GMain.prefs.getInteger("fanblMM");
        Array<Integer> arrMoment= new Array<>();
        arrMoment.add(Config.BubbleMonment);
        arrMoment.add((int)Config.highScMomnet);
        arrMoment.add(Config.comboMoment);
        arrMoment.add(Config.levelMoment);
        arrMoment.add(Config.coinMonment);
        arrMoment.add(Config.wowMoment);
        arrMoment.add(Config.crazyMoment);
        arrMoment.add(Config.fanbulousMoment);
        for (int i=0;i<jv.size;i++){
            int moment = arrMoment.get(i);
            int target = jv.get(i).get("target").asInt();
            if(moment<=target){
//                System.out.println("check: "+arrMoment.get(i));
                arrSclYellow.get(i).setScale((float) moment/target,1);
                arrIccorect.get(i).setVisible(false);
            }else {
                //////// hoan thanh thu thach///////
                arrSclYellow.get(i).setVisible(false);
                arrSclGray.get(i).setVisible(false);
                arrIccorect.get(i).setVisible(true);
                arrFinish.add((i+1));
            }
        }
    }
    public void noticeFinish(int index){

        if(index<arrFinish.size){
            if(GMain.prefs.getInteger("finish"+arrFinish.get(index))==arrFinish.get(index)){
                count++;
                noticeFinish(count);
                return;
            }
            SoundEffect.Play(SoundEffect.mission);
            GMain.prefs.putInteger("quantityfinish",GMain.prefs.getInteger("quantityfinish")+1);
            GMain.prefs.flush();
            Group gr = new Group();
            GStage.addToLayer(GLayer.top,gr);
            Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmNotice");
            frm.setPosition(0,0,Align.center);
            gr.addActor(frm);
            //////// icon ///////
            Image icon = GUI.createImage(TextureAtlasC.GameOver,"icA"+arrFinish.get(index));
            icon.setScale(0.8f);
            icon.setOrigin(Align.center);
            icon.setPosition(frm.getX()+icon.getWidth()/2,frm.getY()+frm.getHeight()/2,Align.center);
            gr.addActor(icon);
            //////// label /////////
            Label lb = new Label(C.lang.lbConratulation,new Label.LabelStyle(BitmapFontC.Font_White,Color.GREEN));
            lb.setFontScale(0.5f);
            lb.setAlignment(Align.center);
            lb.setPosition(icon.getX()+20,icon.getY(),Align.left);
            gr.addActor(lb);
            gr.setPosition(frm.getWidth()*0.6f,frm.getHeight()*0.6f);
            gr.setScale(0);
            gr.setOrigin(Align.center);
            gr.addAction(Actions.alpha(0,0));
            gr.addAction(Actions.sequence(
                    Actions.parallel(
                            Actions.scaleTo(1,1,0.5f),
                            Actions.alpha(1,0.5f)
                    ),
                    Actions.delay(1),
                    Actions.alpha(0,1),
                    GSimpleAction.simpleAction((d, a)->{
                        gr.clear();
                        gr.remove();
                        GMain.prefs.putInteger("finish"+arrFinish.get(index),arrFinish.get(index));
                        GMain.prefs.flush();
                        count++;
                        noticeFinish(count);
                        return true;
                    })
            ));
        }

    }
}
