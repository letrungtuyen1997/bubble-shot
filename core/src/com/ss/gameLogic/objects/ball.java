package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
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
    private com.ss.gameLogic.objects.board board;
    final GShapeSprite blackOverlay = new GShapeSprite();
    private boolean shark=false;

    ball(float x, float y, int  id, com.ss.gameLogic.objects.board board){
        this.x=x;
        this.y=y;
        this.id = id;
        this.board = board;
        GStage.addToLayer(GLayer.ui,gr);
        ball = GUI.createImage(TextureAtlasC.Boardgame,""+id);
        ball.setOrigin(Align.center);
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
//        gr.debug();
        if(id==Config.FireBall)
            fireball();
        if(id==Config.ChangeColor)
            aniRotate(ball);
    }
    public void moveBall(){
        gr.addAction(GSimpleAction.simpleAction((d, a)->{
            if(gr.getX()<=0 || gr.getX()+gr.getWidth()>= GStage.getWorldWidth()){
                speedX=-speedX;
                deg=-deg;
            }
            if(speedY<0){
                speedY=-speedY;
                speedX=(float)Math.PI-speedX;
            }
            if(gr.getY()+gr.getHeight()<Config.BALL_RADIUS){
                SoundEffect.Play(SoundEffect.vutmat);
                gr.clear();
                gr.remove();
                this.board.DropBall();
                this.board.setTouch(Touchable.enabled);
                this.board.SetTouchSwap(Touchable.enabled);
                shark=true;
                this.board.runtime=true;
//                this.board.checkBallBusy=true;
                if(id!=Config.FireBall){
                    Config.combo=0;
                }
                return true;
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
    private void fireball(){
        aniRotate(ball);
        shakScene();
    }
    private void shakScene(){
        GLayer.ui.getGroup().addAction(Actions.sequence(
                Actions.moveBy(-3,3,0.05f),
                Actions.moveBy(6,-6,0.05f),
                Actions.moveBy(-3,3,0.05f),
                Actions.moveBy(3,-3,0.05f),
                Actions.moveBy(-3,3,0.05f),
                GSimpleAction.simpleAction((d, a)->{
                    if(shark==true)
                        return true;
                    shakScene();
                    return true;
                })
        ));
    }
    private void aniRotate(Image img){
        img.addAction(Actions.sequence(
                Actions.rotateBy(360,0.5f),
                GSimpleAction.simpleAction((d, a)->{
                    aniRotate(img);
                    return true;
                })
        ));
    }
    public void Scale(float scale){
        gr.setScale(scale);
    }
    public void destroy(){
        gr.clear();
        gr.remove();

    }
    public boolean compare(BallGrid p) {
        return (this.id == p.id);
    }



}
