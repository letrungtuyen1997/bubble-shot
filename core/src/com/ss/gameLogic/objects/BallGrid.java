package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
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

public class BallGrid {
    public Group gr = new Group();
    private Image ball,Clock;
    public float x,y;
    public int row;
    public int col;
    Grid grid;
    public Circle body;
    final GShapeSprite blackOverlay = new GShapeSprite();
    public int id;
    public boolean rowShift;
    private effectWin ef;
    private header header;

    BallGrid( int id,int row,int col,Group group,Grid grid,float paddingX, int quantity,float y,boolean rowShift,boolean clock,header header){
        this.id = id;
        this.row=row;
        this.col=col;
        this.grid = grid;
        this.rowShift=rowShift;
        this.header = header;
        this.ef=ef;
//        if(paddingX!=0)
//            rowShift=true;
        group.addActor(gr);
        ball = GUI.createImage(TextureAtlasC.Boardgame,""+id);
        ball.setPosition(ball.getWidth()/2,ball.getHeight()/2, Align.center);
        gr.addActor(ball);
        if(clock==true){
            Clock = GUI.createImage(TextureAtlasC.Boardgame,"8");
            Clock.setOrigin(Align.center);
            Clock.setPosition(ball.getX()+ball.getWidth()/2,ball.getY()+ball.getHeight()/2,Align.center);
            gr.addActor(Clock);
        }
        body = new Circle(gr.getX(),gr.getY(),Config.BALL_RADIUS-10);
        body.setPosition(gr.getX()+ball.getWidth()/2,gr.getY()+ball.getHeight()/2);
        blackOverlay.createCircle(true,ball.getX(),ball.getY(),Config.BALL_RADIUS-10);
        blackOverlay.setColor(1,0,1,0.8f);
        blackOverlay.setPosition(body.x,body.y);
//        gr.addActor(blackOverlay);
        gr.setSize(ball.getWidth(),ball.getHeight());
//        gr.setPosition(GStage.getWorldWidth()/2-(Config.BALL_W*quantity/2)+paddingX+Config.BALL_DIAMETER*col,Config.BALL_DIAMETER*row+y,Align.center);
        gr.setPosition(GStage.getWorldWidth()/2+Config.BALL_W/4-(Config.BALL_W*quantity/2)+paddingX+Config.BALL_W*col,y,Align.center);
      //  gr.debug();
        this.x = gr.getX();
//        addListenner();

    }
    public void changeColor(int id){
        ball.remove();
        ball = GUI.createImage(TextureAtlasC.Boardgame,""+id);
        ball.setPosition(ball.getWidth()/2,ball.getHeight()/2, Align.center);
        gr.addActor(ball);
        this.id=id;
        if(Clock!=null){
            int zindex = ball.getZIndex();
            ball.setZIndex(Clock.getZIndex());
            Clock.setZIndex(zindex);
        }
    }
    public void updatePosition(float y){
        gr.setY(gr.getY()+y);
        this.y = gr.getY();
        body.setX(gr.getX());
        body.setY(gr.getY());
    }
    public void destroy(){
        ef = grid.ef();
        if(ef!=null){
            ef.changeSprites(id);
            ef.setPosition(gr.getX()+Config.BALL_RADIUS,gr.getY()+Config.BALL_RADIUS);
            ef.start();
        }
        if(Clock!=null){
            updatetime();
        }else {
            gr.clear();
            gr.remove();
        }
    }
    private void updatetime(){
        ball.clear();
        ball.remove();
        this.header.updateTime();
        Clock.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.scaleTo(5,5,1),
                        Actions.alpha(0,1)
                ),
                GSimpleAction.simpleAction((d,a)->{
                    gr.clear();
                    gr.remove();
                    return true;
                })
        ));
    }
    public void addListenner(){
        gr.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                System.out.println("touch");
                grid.checkAllBall(BallGrid.this);
//                destroy();

            }
        });
    }
    public boolean compare(BallGrid p) {
        return (this.id == p.id);
    }
    public void moveBall(float yDrop){
        gr.addAction(GSimpleAction.simpleAction((d, a)->{

            if(gr.getY()+gr.getHeight()>GStage.getWorldHeight()-Config.BALL_DIAMETER){
//                System.out.println("possssssss: "+gr.getY());
                gr.addAction(Actions.sequence(
                        Actions.moveBy(0,Config.bounce,Config.draBounce),
                        GSimpleAction.simpleAction((d1,a1)->{
                            if(Config.rowDrop!=row){
                                Config.rowDrop=row;
                                grid.CountScoreDrop(gr.getX(),gr.getY());
                            }
                            destroy();
                            return true;
                        })
                ));
                return true;
            }
            gr.setY(gr.getY()+yDrop);
//            gr.setPosition(gr.getX()+speedX,gr.getY()-speedY);
            x=gr.getX();
            y=gr.getY();
            body.setX(gr.getX());
            body.setY(gr.getY());
            return false;
        }));
    }
    public void Bounce(float x,float y){
        gr.addAction(Actions.sequence(
                Actions.moveBy(x,-y,Config.duravibrate, Interpolation.bounceOut),
                Actions.moveBy(-x,y,Config.duravibrate,Interpolation.bounceOut),
                Actions.moveBy(x/2,-y/2,Config.duravibrate,Interpolation.bounceOut),
                Actions.moveBy(-x/2,y/2,Config.duravibrate,Interpolation.bounceOut)
        ));
    }

}
