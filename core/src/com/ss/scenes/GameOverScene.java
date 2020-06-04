package com.ss.scenes;

import com.badlogic.gdx.graphics.Color;
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
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.frmHeader;

public class GameOverScene extends GScreen {
    private Group group = new Group();
    private Group grSc = new Group();
    private Group grBtn = new Group();
    private Group grAni = new Group();
    private long BonusMoney=0;
    private long BonusExp=0;
    private Array<Label> arrLb = new Array<>();
    private frmHeader frmHeader;
    private Label lbBonus, lbSc ;
    private  long Bonus,Score;
    final GShapeSprite WhiteOverLay = new GShapeSprite();

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        GStage.addToLayer(GLayer.ui,group);
        GStage.addToLayer(GLayer.top,grAni);
        GStage.addToLayer(GLayer.top,grBtn);
        Config.Score=400000;
        Create(Config.Score);


    }

    @Override
    public void run() {

    }
    private void Create(long Score){
        String text = "";
        if(Score>0&&Score<100000){
            text ="niceEn" ;
            BonusMoney = 120;
            BonusExp = 100;
        }else if(Score>100000&&Score<150000){
            text="wowEn";
            BonusMoney = 150;
            BonusExp = 120;
        }else if(Score>150000){
            text="fantasticEn";
            BonusMoney = 170;
            BonusExp = 150;

        }
        Image bg = GUI.createImage(TextureAtlasC.GameOver,"bgGameOver");
        bg.setSize(GStage.getWorldWidth(),GStage.getWorldHeight());
        group.addActor(bg);
        WhiteOverLay.createRectangle(true,0,0,GStage.getWorldWidth(),GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.8f);
        group.addActor(WhiteOverLay);
        frmHeader = new frmHeader();
        Bonus = Score*frmHeader.lv/100;


        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmOver");
        frm.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2, Align.center);
        group.addActor(frm);
        ////////////// light ///////////
        effectWin ef = new effectWin(4,GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.3f,group);
        ef.start();
        //////// panel red //////
        Image panel = GUI.createImage(TextureAtlasC.GameOver,"panelRed");
        panel.setPosition(frm.getX()+frm.getWidth()/2,frm.getY()+panel.getHeight()/4,Align.center);
        group.addActor(panel);
        /////// label ////////
        Image lbSattus = GUI.createImage(TextureAtlasC.GameOver,text);
        lbSattus.setOrigin(Align.center);
        lbSattus.setPosition(panel.getX()+panel.getWidth()/2,panel.getY()+lbSattus.getHeight()*0.8f,Align.center);
        group.addActor(lbSattus);

        ////// label your Score ///////
        String type= C.lang.lbYsc;
        Color color = null;
        if(Score+Bonus> GMain.prefs.getLong("HighSc")){
            type = C.lang.lbHsc;
            color = Color.GREEN;
        }
        Label lbYSc = new Label(type,new Label.LabelStyle(BitmapFontC.FontOrange, null));
        lbYSc.setFontScale(0.8f);
        lbYSc.addAction(Actions.alpha(0,0));
        lbYSc.setOrigin(Align.center);
        lbYSc.setAlignment(Align.center);
        lbYSc.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.3f,Align.center);
        group.addActor(lbYSc);
        ////// label Score ///////
        lbSc = new Label("",new Label.LabelStyle(BitmapFontC.Font_Yellow, color));
        lbSc.setFontScale(1.2f);
        lbSc.setOrigin(Align.center);
        lbSc.setAlignment(Align.center);
        lbSc.setPosition(0,0,Align.center);
        grSc.addActor(lbSc);
        grSc.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.45f,Align.center);
        group.addActor(grSc);
        ////// label your Score ///////
        lbBonus = new Label("+"+frmHeader.lv+C.lang.lbBonus+Bonus,new Label.LabelStyle(BitmapFontC.FontOrange, null));
        lbBonus.setFontScale(0.6f);
        lbBonus.addAction(Actions.alpha(0,0));
        lbBonus.setOrigin(Align.center);
        lbBonus.setAlignment(Align.center);
        lbBonus.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.6f,Align.center);
        group.addActor(lbBonus);
        ////// label Reward ///////
        Label lbReward = new Label(C.lang.lbReward,new Label.LabelStyle(BitmapFontC.Font_White, null));
        lbReward.setFontScale(0.7f);
        lbReward.setOrigin(Align.center);
        lbReward.setAlignment(Align.center);
        lbReward.setPosition(GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*0.75f,Align.center);
        group.addActor(lbReward);
        ///////////// btn Done /////////
        btn(grBtn,GStage.getWorldWidth()/2,frm.getY()+frm.getHeight()*1.2f,"btnRed",C.lang.lbBtnRed);
        grBtn.addAction(Actions.alpha(0,0));
        for(int i=0;i<2;i++){
            Image ic = GUI.createImage(TextureAtlasC.GameOver,"icon"+(i+1));
            ic.setPosition(GStage.getWorldWidth()/2-150+i*200,frm.getY()+frm.getHeight()*0.87f,Align.center);
            group.addActor(ic);
            long num = 0;
            if(i==0)
                num = BonusExp;
            else
                num = BonusMoney;
            Label lb = new Label(""+num,new Label.LabelStyle(BitmapFontC.Font_White,null));
            lb.setFontScale(0.6f);
            lb.setAlignment(Align.center);
            lb.setPosition(ic.getX()+ic.getWidth()*2,ic.getY()+ic.getHeight()/2,Align.center);
            group.addActor(lb);
            arrLb.add(lb);
        }

        lbYSc.addAction(Actions.sequence(
                Actions.alpha(1,1),
                GSimpleAction.simpleAction((d,a)->{
                    WhiteOverLay.clear();
                    WhiteOverLay.remove();
                    counterUp(lbSc,Score);
                    return true;
                })
        ));


    }
    public void counterUp(Label object, long target){
        SoundEffect.Play(SoundEffect.ScUp);
        grSc.addAction(
                GTemporalAction.add(1.2f, (percent, actor) -> {
                    object.setText(""+ Math.round(target*percent));
                })
        );
        Tweens.setTimeout(group,1.2f,()->{
            grSc.addAction(Actions.sequence(
                    Actions.scaleTo(1.2f,1.2f,0.2f),
                    Actions.scaleTo(1f,1f,0.2f),
                    GSimpleAction.simpleAction((d,a)->{
                        lbBonus.addAction(Actions.sequence(
                                Actions.alpha(1,1),
                                GSimpleAction.simpleAction((d1,a1)->{
                                    if(Config.Score>0){
                                        BonusSc(lbSc,Bonus);

                                    }else {
                                        eventBtn();
                                    }
                                    return true;
                                })
                        ));
                        return true;
                    })
            ));
        });
    }
    private void BonusSc(Label object, long target){
        SoundEffect.Play(SoundEffect.ScUp);
        grSc.addAction(
                GTemporalAction.add(1.2f, (percent, actor) -> {
                    object.setText(""+(Config.Score+ Math.round(target*percent)));
                })
        );
        Tweens.setTimeout(group,1.2f,()->{
            grSc.addAction(Actions.sequence(
                    Actions.scaleTo(1.2f,1.2f,0.2f),
                    Actions.scaleTo(1f,1f,0.2f),
                    GSimpleAction.simpleAction((d,a)->{
                        Config.Score+=Bonus;
                        frmHeader.checkHighSc(Config.Score);
                        counterUpExp(arrLb.get(0),BonusExp,SoundEffect.ExpUp);
                        return true;
                    })
            ));
        });
    }
    public void counterUpExp(Label object, long target,int sound){
        grSc.addAction(
                GTemporalAction.add(2f, (percent, actor) -> {
                    object.setText(""+ (target-Math.round(target*percent)));
                    frmHeader.updateLvSc( Math.round(target * percent));

                })
        );
        for (int i=0;i<15;i++){
            aniUp("icon1",object.getX()-50,object.getY(),frmHeader.arrIc.get(0).getX(),frmHeader.arrIc.get(0).getY(),i*0.15f);
//            Tweens.setTimeout(group,i*0.15f,()->{
//                SoundEffect.Play(sound);
//            });
        }
        Tweens.setTimeout(group,2f,()->{
            counterUpCoins(arrLb.get(1),BonusMoney,SoundEffect.MonneyUp);
        });
    }
    private void aniUp(String type,float x, float y, float xMove, float yMove,float dura){
        Image img = GUI.createImage(TextureAtlasC.GameOver,type);
        img.setPosition(x,y,Align.center);
        grAni.addActor(img);
        img.addAction(Actions.sequence(
                Actions.moveTo(xMove,yMove,dura, Interpolation.smoother),
                GSimpleAction.simpleAction((d,a)->{
                    SoundEffect.Play(SoundEffect.ExpUp);
                    img.clear();
                    img.remove();
                    return true;
                })
        ));
    }
    private void aniUp2(String type,float x, float y, float xMove, float yMove,float dura){
        Image img = GUI.createImage(TextureAtlasC.GameOver,type);
        img.setPosition(x,y,Align.center);
        grAni.addActor(img);
        img.addAction(Actions.sequence(
                Actions.moveTo(xMove,yMove,dura, Interpolation.smoother),
                GSimpleAction.simpleAction((d,a)->{
                    SoundEffect.Play(SoundEffect.MonneyUp);
                    img.clear();
                    img.remove();
                    return true;
                })
        ));
    }
    public void counterUpCoins(Label object, long target,int sound){
        grSc.addAction(
                GTemporalAction.add(2f, (percent, actor) -> {
                    object.setText(""+ (target-Math.round(target*percent)));
                    frmHeader.updateMonney( Math.round(target * percent));
                })
        );
        for (int i=0;i<15;i++){
            aniUp2("icon2", object.getX() - 50, object.getY(), frmHeader.arrIc.get(1).getX(), frmHeader.arrIc.get(1).getY(), i*0.15f);
        }
        Tweens.setTimeout(group,2f,()->{
            eventBtn();
        });

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
    private void eventBtn(){
        grBtn.addAction(Actions.sequence(
                Actions.alpha(1,1),
                GSimpleAction.simpleAction((d,a)->{
                    Config.Score=0;
                    Config.combo=0;
                    Config.ballType=1;
                    return true;
                })
        ));
        grBtn.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                GameOverScene.this.setScreen(new GameScene());

            }
        });
    }

}
