package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;

public class header {
    public GLayerGroup grHeader = new GLayerGroup();
    private Label lbTime,lbScore;
    public int time=60;
    private int tic=0,setTime=0;
    public boolean runTime=false;
    private Array<Image> arrTime = new Array<>();
    public header(){
        GStage.addToLayer(GLayer.top,grHeader);
        Image header = GUI.createImage(TextureAtlasC.Boardgame,"header");
        header.setWidth(GStage.getWorldWidth());
        header.setPosition(0,0);
        grHeader.addActor(header);
        Image frmTimer = GUI.createImage(TextureAtlasC.Boardgame,"frmTime");
        frmTimer.setPosition(frmTimer.getWidth()/2,frmTimer.getHeight()/2, Align.center);
        grHeader.addActor(frmTimer);
        /////////// Color Time////////
        for (int i=1;i<13;i++){
            Image color = GUI.createImage(TextureAtlasC.Boardgame,"time"+i);
            color.setPosition(frmTimer.getX()+frmTimer.getWidth()*0.7f,frmTimer.getY()+frmTimer.getHeight()*0.5f,Align.center);
            grHeader.addActor(color);
            if(i!=12)
                color.setVisible(false);
            arrTime.add(color);
        }
        /////// label time/////////
        lbTime = new Label(""+time,new Label.LabelStyle(BitmapFontC.timeFont, null));
        lbTime.setFontScale(0.8f);
        lbTime.setOrigin(Align.center);
        lbTime.setAlignment(Align.center);
        lbTime.setPosition(frmTimer.getX()+frmTimer.getWidth()*0.70f,frmTimer.getY()+frmTimer.getHeight()*0.4f,Align.center);
        grHeader.addActor(lbTime);
//        CountDownTime();
        Image frmsc = GUI.createImage(TextureAtlasC.Boardgame,"frmSc");
        frmsc.setPosition(GStage.getWorldWidth()/2,frmsc.getHeight()/2,Align.center);
        grHeader.addActor(frmsc);
        /////////// label  Score/////
        lbScore = new Label(""+ Config.Score,new Label.LabelStyle(BitmapFontC.FontScore,null));
        lbScore.setFontScale(0.8f);
        lbScore.setOrigin(Align.center);
        lbScore.setPosition(frmsc.getX()+frmsc.getWidth()/2,frmsc.getY()+frmsc.getHeight()*0.6f,Align.center);
        lbScore.setAlignment(Align.center);
        grHeader.addActor(lbScore);
        //BlinkTime(frmsc);

    }
    public void CountDownTime(){
        grHeader.addAction(GSimpleAction.simpleAction((d, a)->{
            tic++;
            if(tic==60){
                tic=0;
                time--;
                if(time%5==0){
                    int index=time/5;
                    setTime(index);
                }
                lbTime.setText(time);
                if(time<=10){
                    Image img=null;
                    if(time==10){
                        SoundEffect.Play(SoundEffect.timeDown);
                        img=arrTime.get(1);
                    }
                    if(time==5){
                        img=arrTime.get(0);
                    }
                    BlinkTime(img);
                }
                if(time==0)
                    runTime=true;
            }
            return runTime;
        }));
    }
    private void setTime(int index){
        for (Image img : arrTime){
            if(index>=0&& arrTime.indexOf(img,true)>=index){
                img.setVisible(false);
            }else {
                img.setVisible(true);

            }
        }
    }
    private void BlinkTime(Image img){
//        System.out.println("blink!!!!!!!!");
        if(img!=null){
           img.addAction(Actions.sequence(
                    Actions.alpha(0,0.2f),
                    Actions.alpha(1,0.2f),
                    GSimpleAction.simpleAction((d, a)->{
                        if(time==0)
                            return true;
                        BlinkTime(img);
                        return true;
                    })
            ));
        }
    }
    public void updateSc(Long sc){
        lbScore.setText(""+sc);
    }
    public void updateTime(){
        if(time<55)
            time+=Config.BonusTime;
        else
            time=60;
    }
    public void setPause(boolean set){
       grHeader.setPause(set);
    }

}
