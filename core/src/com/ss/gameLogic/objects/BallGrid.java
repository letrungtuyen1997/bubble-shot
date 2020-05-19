package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;

public class BallGrid {
    public Group gr = new Group();
    private Image ball;
    public float x,y;
    public int row;
    public int col;
    Grid grid;
    public Circle body;
    final GShapeSprite blackOverlay = new GShapeSprite();
    public int id;
    public boolean rowShift;
    BallGrid( int id,int row,int col,Group group,Grid grid,float paddingX, int quantity,float y,boolean rowShift){
        this.id = id;
        this.row=row;
        this.col=col;
        this.grid = grid;
        this.rowShift=rowShift;
//        if(paddingX!=0)
//            rowShift=true;
        group.addActor(gr);
        ball = GUI.createImage(TextureAtlasC.Boardgame,""+id);
        ball.setPosition(ball.getWidth()/2,ball.getHeight()/2, Align.center);
        gr.addActor(ball);
        body = new Circle(gr.getX(),gr.getY(),Config.BALL_RADIUS-15);
        body.setPosition(gr.getX()+ball.getWidth()/2,gr.getY()+ball.getHeight()/2);
        blackOverlay.createCircle(true,ball.getX(),ball.getY(),Config.BALL_RADIUS-15);
        blackOverlay.setColor(1,0,1,0.8f);
        blackOverlay.setPosition(body.x,body.y);
        gr.addActor(blackOverlay);
        gr.setSize(ball.getWidth(),ball.getHeight());
//        gr.setPosition(GStage.getWorldWidth()/2-(Config.BALL_W*quantity/2)+paddingX+Config.BALL_DIAMETER*col,Config.BALL_DIAMETER*row+y,Align.center);
        gr.setPosition(GStage.getWorldWidth()/2+Config.BALL_W/6-(Config.BALL_W*quantity/2)+paddingX+Config.BALL_W*col,y,Align.center);
        gr.debug();
        this.x = gr.getX();
        addListenner();

    }
    public void updatePosition(float y){
        gr.setY(gr.getY()+y);
        this.y = gr.getY();
        body.setX(gr.getX());
        body.setY(gr.getY());
    }
    public void destroy(){
        gr.clear();
        gr.remove();
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

}
