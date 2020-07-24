package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.scenes.GameScene;
import com.ss.scenes.StartScene;

public class SelectSkills {
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private  Group gr = new Group();
    private  Group grbtnRed = new Group();
    private Array<Group> arrGr = new Array<>();
    private Array<Image> arrTile = new Array<>();
    private Array<Image> arrIcon = new Array<>();
    private Array<Image> arrIconAdd = new Array<>();
    private Array<Label> arrLabel = new Array<>();
    private Array<Long> arrCoins = new Array<>();
    private Array<Integer> arrQuan = new Array<>();
    private frmHeader header;
    private StartScene startScene;

    public SelectSkills(frmHeader header, StartScene startScene){
        SoundEffect.Play(SoundEffect.panel_out);

        this.header = header;
        this.startScene = startScene;
        GStage.addToLayer(GLayer.top,gr);
        WhiteOverLay.createRectangle(true,-GStage.getWorldWidth()/2,-GStage.getWorldHeight()/2, GStage.getWorldWidth(), GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0.7f);
        gr.addActor(WhiteOverLay);
        Image frm = GUI.createImage(TextureAtlasC.GameOver,"frmSkills");
        frm.setPosition(0,0, Align.center);
        gr.addActor(frm);
        gr.setScale(0);
        gr.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2);
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
            Image icAdd = GUI.createImage(TextureAtlasC.GameOver,"add");
            icAdd.setOrigin(Align.center);
            icAdd.setPosition(tile.getX()+tile.getWidth(),tile.getY()+tile.getHeight(),Align.center);
            gric.addActor(icAdd);
            AnibtnGm(icAdd);
            arrIconAdd.add(icAdd);
            ///////// array//////
            arrGr.add(gric);
            arrLabel.add(quantity);
            arrCoins.add(price);
            arrTile.add(tileGreen);
            arrQuan.add(quan);
            arrIcon.add(ic);

        }
        loadData();
        eventSelect();
        eventBtn();

    }
    private void eventSelect(){
        for (Group gr : arrGr){
            gr.addListener(new ClickListener(){
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    if(arrCoins.get(arrGr.indexOf(gr,true))<=Config.monney){
                        int index = arrGr.indexOf(gr,true);
                        addSkills(index,"btn");
                        updateData();
                    }else {
                        int index = arrGr.indexOf(gr,true);
                        AddmoreSkill(index);
                    }
                }
            });
        }
    }
    private void addSkills(int index,String type){
        if(type.equals("btn")){
            header.updateMonney2(arrCoins.get(index));
        }
        aniBuy(arrGr.get(index).getX(),arrGr.get(index).getY());
        arrQuan.set(index,arrQuan.get(index)+1);
        arrLabel.get(index).setText(arrQuan.get(index));
        if(arrQuan.get(index)>0){
            arrTile.get(index).setVisible(true);
            arrIconAdd.get(index).setVisible(false);
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
                GSimpleAction.simpleAction((d, a)->{
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
    private void eventBtn(){
        grbtnRed.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                grbtnRed.setTouchable(Touchable.disabled);
                AniMove(0,()->{
                    AniMove(1,()->{
                        AniMove(2,()->{
                            updateData();
                            startScene.setScreen(new GameScene());
                            SoundEffect.Play(SoundEffect.panel_out);
//                            System.out.println("vao game!!!");
//                            System.out.println("skill time: "+Config.checkSkillTime);
//                            System.out.println("skill color: "+Config.checkSkillColor);
//                            System.out.println("skill bomb: "+Config.checkSkillBomb);
                        });
                    });
                });

            }
        });
    }
    private void AniMove(int index,Runnable runnable){
        if(arrQuan.get(index)>0){
            if(index==0)
                Config.checkSkillTime=true;
            else if(index==1)
                Config.checkSkillColor=true;
            else if(index==2)
                Config.checkSkillBomb=true;
            arrQuan.set(index,arrQuan.get(index)-1);
            arrLabel.get(index).setText(arrQuan.get(index));
            if(arrQuan.get(index)<=0){
                arrTile.get(index).setVisible(false);
            }
            Image icon = GUI.createImage(TextureAtlasC.GameOver,"ic"+(index+1));
            icon.setScale(1.5f);
            icon.setOrigin(Align.center);
            icon.setPosition(arrGr.get(index).getX(),arrGr.get(index).getY(),Align.center);
            gr.addActor(icon);

            icon.addAction(Actions.sequence(
                    Actions.moveTo(grbtnRed.getX()+grbtnRed.getWidth()/2-icon.getWidth()/2,grbtnRed.getY()+grbtnRed.getHeight()/2-icon.getHeight()/2,0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        SoundEffect.Play(SoundEffect.ExpUp);
                        return true;
                    }),
                    Actions.parallel(
                            Actions.scaleTo(3,3,0.3f),
                            Actions.alpha(0,0.3f)
                    ),
                    GSimpleAction.simpleAction((d, a)->{
                        icon.clear();
                        icon.remove();
                        grbtnRed.addAction(Actions.run(runnable));
//                    Actions.run(runnable);
                        return true;
                    })
            ));
        }else {
            grbtnRed.addAction(Actions.run(runnable));
        }

    }
    private void loadData(){
        int quanTime = GMain.prefs.getInteger("SkillsTime");
        int quanColor = GMain.prefs.getInteger("SkillsColor");
        int quanBomb = GMain.prefs.getInteger("SkillsBomb");
        if(arrQuan.size>=3){
            arrQuan.set(0,quanTime);
            arrLabel.get(0).setText(arrQuan.get(0));
            arrQuan.set(1,quanColor);
            arrLabel.get(1).setText(arrQuan.get(1));
            arrQuan.set(2,quanBomb);
            arrLabel.get(2).setText(arrQuan.get(2));
            if(arrQuan.get(0)>0){
                arrTile.get(0).setVisible(true);
            }
            if(arrQuan.get(1)>0){
                arrTile.get(1).setVisible(true);
            }
            if(arrQuan.get(2)>0){
                arrTile.get(2).setVisible(true);
            }
        }

    }
    private void updateData(){
        if(arrQuan.size>=3){
            GMain.prefs.putInteger("SkillsTime",arrQuan.get(0));
            GMain.prefs.putInteger("SkillsColor",arrQuan.get(1));
            GMain.prefs.putInteger("SkillsBomb",arrQuan.get(2));
            GMain.prefs.flush();
        }

    }
    public void AddmoreSkill(int index){
        Group group = new Group();
        GStage.addToLayer(GLayer.top,group);
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GStage.getWorldWidth(),-GStage.getWorldHeight()/2, GStage.getWorldWidth()*2, GStage.getWorldHeight()*2);
        blackOverlay.setColor(0,0,0,0.8f);
        group.addActor(blackOverlay);
        group.setScaleX(0);
        group.setOrigin(Align.center);
        group.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2,Align.center);
        group.addAction(Actions.scaleTo(1,1,0.3f, Interpolation.swingOut));
        ////// Label watch //////
        Label text = new Label(C.lang.locale.get("lbwatch"),new Label.LabelStyle(BitmapFontC.FontAlert,null));
        text.setFontScale(0.5f);
        text.setOrigin(Align.center);
        text.setAlignment(Align.center);
        text.setPosition(0,-100,Align.center);
        group.addActor(text);
        //// button  watch ////
        Image btnWath = GUI.createImage(TextureAtlasC.GameOver,"btnWatch");
        btnWath.setOrigin(Align.center);
        btnWath.setPosition(0,btnWath.getHeight(),Align.center);
        group.addActor(btnWath);
        AnibtnGm(btnWath);
        ///// eventBtnWatch ////
        btnWath.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showAds(group,index);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        //// button  close ////
        blackOverlay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                group.clear();
                group.remove();
            }
        });


    }
    void showAds(Group group,int index){
        if(GMain.platform.isVideoRewardReady()) {
            GMain.platform.ShowVideoReward((boolean success) -> {
                if (success) {
//                    GMain.platform.ShowFullscreen();
                    addSkills(index,"ads");
                    updateData();
                    group.clear();
                    group.remove();
                    SoundEffect.Play(SoundEffect.ChangeColor);

                }else {
                    GMain.platform.ShowFullscreen();
                    group.clear();
                    group.remove();
                }
            });
        }else {
            Label notice = new Label(C.lang.locale.get("lbCheckConnect"),new Label.LabelStyle(BitmapFontC.Font_White, Color.RED));
            notice.setPosition(0,0,Align.center);
            group.addActor(notice);
            notice.addAction(Actions.sequence(
                    Actions.moveBy(0,-50,0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        notice.clear();
                        notice.remove();
                        return true;
                    })
            ));

        }
    }
    private void AnibtnGm(Image btn){
        btn.addAction(Actions.sequence(
                Actions.scaleTo(0.8f,0.8f,0.2f),
                Actions.scaleTo(1f,1f,0.2f),
                Actions.delay(1),
                Actions.moveBy(-5,0,0.1f,Interpolation.bounceIn),
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

}
