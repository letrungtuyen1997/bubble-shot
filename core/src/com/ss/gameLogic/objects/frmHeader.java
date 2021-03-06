package com.ss.gameLogic.objects;

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
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;

public class frmHeader {
    private Group gr = new Group();
    public int lv = GMain.prefs.getInteger("level",1);
    public int lvOld = GMain.prefs.getInteger("levelOld",1);
    public long expMoment= GMain.prefs.getLong("ExpMoment");
    public long expTarget=GMain.prefs.getLong("ExpTarget",100);
    public long Monney= Config.monney;
    private Image frmExp;
    public Array <Label> arrLb = new Array<>();
    public Array <Image> arrIc = new Array<>();
    private long BonusTemp=0;
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private GScreen gScreen;

    public frmHeader(GScreen gScreen){
        this.gScreen = gScreen;
        GStage.addToLayer(GLayer.ui,gr);
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmTitle");
        frm.setPosition(GStage.getWorldWidth()/2,70, Align.center);
        gr.addActor(frm);
        frmExp = GUI.createImage(TextureAtlasC.GameOver,"frmExp");
        frmExp.setPosition(frm.getX()+frm.getWidth()*0.15f,frm.getY()+frm.getHeight()*0.45f,Align.center);
        gr.addActor(frmExp);
        for(int i=0;i<4;i++){
            Image ic = GUI.createImage(TextureAtlasC.GameOver,"icon"+(i+1));
            ic.setPosition((frm.getX()-10)+i*170,frm.getY()-10);
            gr.addActor(ic);
            float padding=100;
            if(i==1)
                padding=260;
            if(i==2)
                padding=220;
            if(i==3)
                padding=210;
            Label lb = new Label("23",new Label.LabelStyle(BitmapFontC.Font_White,null));
            lb.setFontScale(0.5f);
            lb.setOrigin(Align.center);
            lb.setAlignment(Align.center);
            lb.setPosition((frm.getX()+lb.getPrefWidth()/2)+i*padding,frm.getY()+frm.getHeight()/2-lb.getPrefHeight()/3,Align.center);
            gr.addActor(lb);
            arrLb.add(lb);
            arrIc.add(ic);
        }
        updateLvSc((long)0);
        updateMonney((long)0);
        updateAchivement();
        checkHighSc((long)0);
        aniBtnMission(arrIc.get(2));
        eventBtnMission(arrIc.get(2));
        //////// show leaderboard//////
        aniBtnMission(arrIc.get(3));
        eventBtnRank(arrIc.get(3));
        gr.setPosition(0,-100);

        gr.addAction(Actions.moveBy(0,100,1f,Interpolation.swingOut));


    }
    public  void updateMonney(Long Bonus){

        if(arrLb.get(1)!=null){
            Config.monney=(Monney+Bonus);
            arrLb.get(1).setText(""+Config.monney);
        }
        GMain.prefs.putLong("Monney", (Monney+Bonus));
        GMain.prefs.flush();
    }
    public  void updateMonney2(Long Monney){

        Config.monney-=Monney;
        if(arrLb.get(1)!=null){
            arrLb.get(1).setText(""+Config.monney);
        }
        GMain.prefs.putInteger("coinsMM",(int)(GMain.prefs.getInteger("coinsMM")+Monney));
        GMain.prefs.putLong("Monney", Config.monney);
        GMain.prefs.flush();
    }
    public void updateLvSc(Long Bonus){
        checkLv(Bonus);
//        System.out.println("expMoment: "+expMoment);
//        System.out.println("expTarget: "+expTarget);
        frmExp.setScale((float)expMoment/expTarget,1);
        if(arrLb.get(0)!=null){
            arrLb.get(0).setText(lv);
        }
        GMain.prefs.putLong("ExpMoment", expMoment);
        GMain.prefs.flush();
    }
    public void updateAchivement(){
        if(arrLb.get(2)!=null){
            arrLb.get(2).setText(""+GMain.prefs.getInteger("quantityfinish")+"/8");
        }
    }
    public void checkLv(Long Bonus){
        if(Bonus<=expTarget){
            BonusTemp = (Math.abs(Bonus-BonusTemp));
            expMoment += BonusTemp;
            BonusTemp = Bonus;
        }else {
            expMoment=Bonus-expTarget;
        }
        if(expMoment>= expTarget) {
            lv++;
            expTarget = (expTarget * lv/3);
            expMoment = 0;
            GMain.prefs.putInteger("level", lv);
            GMain.prefs.putLong("ExpMoment", expMoment);
            GMain.prefs.putLong("ExpTarget", expTarget);
            GMain.prefs.flush();
        }
    }
    public void checkHighSc(Long sc){
        Long HighSc = GMain.prefs.getLong("HighSc",0);
        if(arrLb.get(3)!=null){
            arrLb.get(3).setText(""+HighSc);
        }
        if(sc>HighSc){
            HighSc = sc;
            if(arrLb.get(3)!=null){
                arrLb.get(3).setText(""+HighSc);
            }
            GMain.prefs.putLong("HighSc", HighSc);
            GMain.prefs.flush();
            GMain.platform.ReportScore(GMain.prefs.getLong("HighSc"));

        }
    }

    private void aniBtnMission(Image btn){
        btn.setOrigin(Align.center);
        btn.addAction(Actions.sequence(
                Actions.scaleTo(0.8f,0.8f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.delay(1f),
                GSimpleAction.simpleAction((d, a)->{
                    aniBtnMission(btn);
                    return true;
                })
        ));
    }
    private void eventBtnMission(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Play(SoundEffect.ExpUp);
                new Mission();
            }
        });
    }
    private void eventBtnRank(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SoundEffect.Play(SoundEffect.ExpUp);
//                gScreen.setScreen(LDBFactory.getLDB());
                GMain.platform.ShowLeaderboard();
            }
        });
    }
    public void CheckLevelUp(){
        if(lvOld<lv){
            GMain.prefs.putInteger("levelOld", lv);
            GMain.prefs.flush();
            leveup();
        }
    }
    private void leveup(){
        Group gr = new Group();
        GStage.addToLayer(GLayer.top,gr);
        WhiteOverLay.createRectangle(true,-GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth(), GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.7f);
        gr.addActor(WhiteOverLay);
        ////////// frm //////////
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmOver");
        frm.setPosition(0,0,Align.center);
        gr.addActor(frm);
        ///////// icon level ///////
        Image icon = GUI.createImage(TextureAtlasC.GameOver,"icLevel");
        icon.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+frm.getHeight()*0.4f,Align.center);
        gr.addActor(icon);
        ///////// label /////////
        Label lb = new Label(""+lv,new Label.LabelStyle(BitmapFontC.Font_White,null));
        lb.setPosition(icon.getX()+icon.getWidth()/2,icon.getY()+icon.getHeight()/2,Align.center);
        gr.addActor(lb);
        //////// panel red //////
        Image panel = GUI.createImage(TextureAtlasC.GameOver,"panelRed");
        panel.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+panel.getHeight()/4,Align.center);
        gr.addActor(panel);
        /////// label ////////
        String text = "levelupVn";
        if(C.lang.idcontry.equals("En"))
            text = "levelupVn";
        Image lbSattus = GUI.createImage(TextureAtlasC.GameOver,text);
        lbSattus.setOrigin(Align.center);
        lbSattus.setPosition(panel.getX()+panel.getWidth()/2,panel.getY()+lbSattus.getHeight()*0.8f,Align.center);
        gr.addActor(lbSattus);
        /////// label bonus score ///////
        Label lbBonus = new Label(C.lang.scoreBonus,new Label.LabelStyle(BitmapFontC.Font_White,null));
        lbBonus.setFontScale(0.8f);
        lbBonus.setAlignment(Align.center);
        lbBonus.setOrigin(Align.center);
        lbBonus.setPosition(0,frm.getY()+frm.getHeight()*0.75f,Align.center);
        gr.addActor(lbBonus);
        /////// label bonus score ///////
        Label lbBonus2 = new Label("+"+lv+"%",new Label.LabelStyle(BitmapFontC.timeFont,null));
        lbBonus2.setFontScale(0.6f);
        lbBonus2.setAlignment(Align.center);
        lbBonus2.setOrigin(Align.center);
        lbBonus2.setPosition(0,frm.getY()+frm.getHeight()*0.87f,Align.center);
        gr.addActor(lbBonus2);
        /////// btn red //////
        Group grbtn = new Group();
        gr.addActor(grbtn);
        btn(grbtn,0,frm.getY()+frm.getHeight()*1.2f,"btnRed",C.lang.lbBtnRed);
        eventBtnRed(grbtn,gr);
        gr.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2,Align.center);
        gr.setScale(0);
        gr.addAction(Actions.sequence(
                Actions.scaleTo(1,1,0.5f, Interpolation.swingOut),
                GSimpleAction.simpleAction((d, a)->{
                    SoundEffect.Play(SoundEffect.levelup);
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
    private void eventBtnRed(Group gr,Group parent){
        gr.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                parent.clear();
                parent.remove();
            }
        });
    }




}

