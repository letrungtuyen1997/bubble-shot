package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.gameLogic.config.Config;

import java.util.ArrayList;

public class board {
    private Group MainGroup;
    private Group arrowGr = new Group();
    private Group WhiteGr = new Group();
    private ball ball, ballMove;
    private float mousePosX=0;
    private float mousePosY=0;
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private Grid grid;
    private boolean runtime=false;

    public board(Group MainGroup){
        this.MainGroup = MainGroup;
        WhiteGr.addActor(arrowGr);
        WhiteOverLay();
        CreateBallPlay();
        MouseOver();
        moveBall();
        grid = new Grid();
        //checkRuntime();
//        Tweens.setTimeout(MainGroup,1f,()->{
//            System.out.println("Pos ballmove: "+ball.gr.getX());
//            System.out.println("Pos ballGrid: "+grid.arrGridBall.get(0).get(1).gr.getX());
//        });

    }
    private void WhiteOverLay(){
        GStage.addToLayer(GLayer.top,WhiteGr);
        WhiteOverLay.createRectangle(true,0,0,GStage.getWorldWidth(),GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0f);
        WhiteGr.addActor(WhiteOverLay);
    }
    private void CreateBallPlay(){
        int type = (int)(Math.random()*5)+1;
        ball = new ball(GStage.getWorldWidth()/2,GStage.getWorldHeight()-100,type);
    }
    private void MouseOver(){
        WhiteOverLay.addListener(new ClickListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                mousePosX=x;
                mousePosY=y;
                arrowGr.clear();
                new arrow(ball.x,ball.y,x,y,arrowGr);
                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mousePosX=x;
                mousePosY=y;
                arrowGr.clear();
                new arrow(ball.x,ball.y,x,y,arrowGr);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                mousePosX=x;
                mousePosY=y;
                arrowGr.clear();
                new arrow(ball.x,ball.y,x,y,arrowGr);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                arrowGr.clear();
            }
        });
    }
    private void moveBall(){
        WhiteOverLay.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                runtime=false;
                WhiteOverLay.setTouchable(Touchable.disabled);
                Double deg = Math.atan2(ball.y-mousePosY,mousePosX-ball.x);
                ball.deg = (float) Math.toDegrees(deg);
                ball.speedX = Config.BALL_SPEED * (float) Math.cos(deg);
                ball.speedY = Config.BALL_SPEED * (float) Math.sin(deg);
                ball.moveBall();
                ballMove = ball;
                checkRuntime();
                Tweens.setTimeout(arrowGr,Config.Dr_Ball_Cre,()->{
                    CreateBallPlay();
                    WhiteOverLay.setTouchable(Touchable.enabled);
                });
            }

        });
    }
    private void checkRuntime(){
        WhiteGr.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(ballMove!=null)
                        Colision();
                    return runtime;
                })
        );
    }

    private int  checkRowNull(int row, int col){
        System.out.println("ball check: "+row+"---: "+col);
        if (row<0)
            return 0;
        if(grid.arrGridBall.get(row).get(col)==null)
            return 1;
        else
            return -1;
    }


    //:todo fix loi trai tren, phai tren
    private void Colision(){
        for(int i=0;i<grid.arrGridBall.size;i++)
            for (int j=0;j<grid.arrGridBall.get(i).size;j++){
                if(grid.arrGridBall.get(i).get(j)!=null){
                    if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==1) {
                        System.out.println("this ball: " + grid.arrGridBall.get(i).get(j).row + "--: " + grid.arrGridBall.get(i).get(j).col);
                        int row = grid.arrGridBall.get(i).get(j).row;
                        int col = grid.arrGridBall.get(i).get(j).col;
                        boolean rowShift=true;
                        if(grid.arrGridBall.get(i).get(j).rowShift){
                            //col = grid.arrGridBall.get(i).get(j).col-1;
                            rowShift=false;
                        }else {
                           // col = grid.arrGridBall.get(i).get(j).col+1;
                            rowShift=true;
                        }

                        System.out.println("checkRowShift: "+rowShift);
                        if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==1){
                            if(rowShift)
                                col-=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            if(checkRowNull(row-1,col)==1){
                                grid.addBallLeftRight(row-1,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }else {
                                grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }
                            System.out.println("trai tren");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==-1){
                            col-=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            System.out.println("ball check: "+row+"---: "+(col));
                            if(checkRowNull(row,col)==1) {
                                grid.addBallHorizontal(row, col, ballMove.id, grid.arrGridBall.get(i).get(j), grid.arrGridBall.get(i).get(j).rowShift);
                            }
                            System.out.println("trai ngang");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==2){
                            if(rowShift==false)
                                col+=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            if(checkRowNull(row-1,col)==1){
                                grid.addBallLeftRight(row-1,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }else {
                                System.out.println("kkkkkkkkkkkkkkkkkk");
                                grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }
                            System.out.println("phai tren");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==-2){
                            col+=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            System.out.println("ball check: "+row+"---: "+(col));
                            System.out.println("phai ngang");
                            if(checkRowNull(row,col)==1){
                                grid.addBallHorizontal(row,col,ballMove.id,grid.arrGridBall.get(i).get(j),grid.arrGridBall.get(i).get(j).rowShift);
                            }
                        }
                        ballMove.destroy();
                        runtime=true;
                        return;
                    }
                }
            }

    }
    private int checkleftright(ball ball1, BallGrid ball2){
        float x1 = ball1.gr.getX()+Config.BALL_W/2-Config.SpeedX;
        float y1 = ball1.gr.getY()+Config.BALL_H/2+Config.SpeedY;
        float x2 = ball2.gr.getX()+Config.BALL_W/2;
        float y2 = ball2.gr.getY()+Config.BALL_H/2;
//        System.out.println("check pos: "+"[x1: "+x1+"-y1: "+y1+"]"+"[x2: "+x2+"-y2: "+y2+"]" );
//        System.out.println("offsetXy: "+ball.speedX+"---- "+ball.speedY);
        if(x1<x2 && (y1-y2)>0)
            return 1;
        if(x1<x2 && (y1-y2)<0)
            return -1;
        if(x1>x2 && (y1-y2)>0)
            return 2;
        if(x1>x2 && (y1-y2)<0)
            return -2;
        return 0;
    }

    private int checkColision(ball ball1, BallGrid ball2){
        if(ball1.body.overlaps(ball2.body))
            return 1;
      return -1;
    }





}
