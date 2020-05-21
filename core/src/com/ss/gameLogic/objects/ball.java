package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.effectWin;
import com.ss.gameLogic.config.Config;

public class ball {
    public Group gr = new Group();
    private Image ball;
    public float x,y;
    public float speedX=0;
    public float speedY=0;
    public Circle body;
    public int id;
    public float deg=0;
    final GShapeSprite blackOverlay = new GShapeSprite();

    ball(float x,float y, int  id){
        this.x=x;
        this.y=y;
        this.id = id;
        GStage.addToLayer(GLayer.ui,gr);
        ball = GUI.createImage(TextureAtlasC.Boardgame,""+id);
        ball.setPosition(ball.getWidth()/2,ball.getHeight()/2,Align.center);
        gr.addActor(ball);
        body = new Circle(gr.getX()+ball.getWidth()/4,gr.getY()+ball.getHeight()/4,Config.BALL_RADIUS-10);
//        body.setPosition(gr.getX(),gr.getY()+ball.getHeight()/2);
        blackOverlay.createCircle(true,body.x,body.y,Config.BALL_RADIUS-10);
        blackOverlay.setColor(1,0,1,0.8f);
        blackOverlay.setPosition(body.x,body.y);
//        gr.addActor(blackOverlay);
        gr.setSize(ball.getWidth(),ball.getHeight());
        gr.setOrigin(Align.center);
        gr.setPosition(x,y,Align.center);
        gr.debug();
    }
    public void moveBall(){
        gr.addAction(GSimpleAction.simpleAction((d,a)->{
            if(gr.getX()<=0 || gr.getX()+gr.getWidth()>=GStage.getWorldWidth()){
                speedX=-speedX;
                deg=-deg;
            }
            if(speedY<0){
                speedY=-speedY;
                speedX=(float)Math.PI-speedX;
            }
            if(gr.getY()+gr.getHeight()<0){
                gr.clear();
                gr.remove();
            }
//            System.out.println("speedX: "+speedX);
//            System.out.println("speedY: "+speedY);
            Config.SpeedX=speedX;
            Config.SpeedY=speedY;
            gr.setX(gr.getX()+speedX);
            gr.setY(gr.getY()-speedY);
//            gr.setPosition(gr.getX()+speedX,gr.getY()-speedY);
            x=gr.getX();
            y=gr.getY();
            body.setX(gr.getX());
            body.setY(gr.getY());
           return false;
        }));
    }
    public void destroy(){
        ball.clear();
        ball.remove();
        effectWin ef = new effectWin(1,id,Config.BALL_RADIUS,Config.BALL_RADIUS);
        gr.addActor(ef);
        ef.start();
        Tweens.setTimeout(gr,1f,()->{
            gr.clear();
            gr.remove();
        });

    }
    public boolean compare(BallGrid p) {
        return (this.id == p.id);
    }



}
