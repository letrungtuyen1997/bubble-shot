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
                        Colision2();
                    return runtime;
                })
        );
    }
    private void Colision(){
        for (int i=0;i<grid.arrGridBall.size;i++){
            for(int j=0;j<grid.arrGridBall.get(i).size;j++){
                if(grid.arrGridBall.get(i).get(j)!=null){
                    if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==1){
                        System.out.println("this ball: "+grid.arrGridBall.get(i).get(j).row+"--: "+grid.arrGridBall.get(i).get(j).col);
                        if(checkleftright(ballMove.deg)==-1){
                            int row = grid.arrGridBall.get(i).get(j).row-1;
                            int col = grid.arrGridBall.get(i).get(j).col;
                            boolean rowShift=true;
                            if(grid.arrGridBall.get(i).get(j).rowShift){
                                col = grid.arrGridBall.get(i).get(j).col+1;
                                rowShift=false;
                            }
                            if(col<0)
                                col=0;
                            if(col>Config.quantityBall-1)
                                col-=1;
                            if(checkRowNull(row,col)==1){
                                System.out.println("add in null");
                                grid.addBallInNull(row,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }
                            else if(checkRowNull(row,col)==-1){
                                if(checkRowNull(row,col-1)==1)
                                    grid.addBallInNull(row,col-1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                else
                                    grid.addBallInNull(row-1,col-1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
//                                grid.addBallInNull(row,col-1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }else
                                grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            System.out.println("right: row["+row+"-"+col+"]");
                        }else if (checkleftright(ballMove.deg)==1){
                            int row = grid.arrGridBall.get(i).get(j).row-1;
                            int col = grid.arrGridBall.get(i).get(j).col-1;
                            boolean rowShift=true;
                            if(grid.arrGridBall.get(i).get(j).rowShift){
                                col = grid.arrGridBall.get(i).get(j).col;
                                rowShift=false;
                            }
                            if(col<0)
                                col=0;
                            if(col>Config.quantityBall-1)
                                col-=1;
                            if(checkRowNull(row,col)==1){
                                System.out.println("add in null");
                                grid.addBallInNull(row,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }else if(checkRowNull(row,col)==-1){
                                if(checkRowNull(row,col+1)==1)
                                    grid.addBallInNull(row,col+1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                else
                                    grid.addBallInNull(row+1,col+1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }
                            else
                                grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                           // grid.addNewRow(grid.arrGridBall.get(i).get(j).col-1,ballMove.id,grid.arrGridBall.get(i).get(j));
                            System.out.println("left: row["+row+"-"+col+"]");
                        }

//                        System.out.println("check: "+ballMove.deg);
//                        System.out.println("Colision "+grid.arrGridBall.get(i).get(j).id);
                        ballMove.destroy();
                        runtime=true;
                        return;
                    }else {
//                    System.out.println("");
                    }
                }
            }
        }
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

    private int checkleftright(float deg){
        if(deg>90)
            return -1;
        else if(deg<90&&deg>0)
            return 1;
        else if(deg<0&&deg>(-90))
            return -1;
        else if(deg<-90&&deg>(-180))
            return 1;
        return 0;
    }
    //:todo fix in here
    private void Colision2(){
        for(int i=0;i<grid.arrGridBall.size;i++)
            for (int j=0;j<grid.arrGridBall.get(i).size;j++){
                if(grid.arrGridBall.get(i).get(j)!=null){
                    if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==1) {
                        System.out.println("this ball: " + grid.arrGridBall.get(i).get(j).row + "--: " + grid.arrGridBall.get(i).get(j).col);
                        if(checkleftright2(ballMove,grid.arrGridBall.get(i).get(j))==1){
                            System.out.println("trai tren");
                        }else if(checkleftright2(ballMove,grid.arrGridBall.get(i).get(j))==-1){
                            System.out.println("trai ngang");
                        }else if(checkleftright2(ballMove,grid.arrGridBall.get(i).get(j))==2){
                            System.out.println("phai tren");
                        }else if(checkleftright2(ballMove,grid.arrGridBall.get(i).get(j))==-2){
                            System.out.println("phai ngang");
                        }

                    }
                }
            }

    }
    private int checkleftright2(ball ball1, BallGrid ball2){
        float x1 = ball1.gr.getX()+Config.BALL_W/2;
        float y1 = ball1.gr.getY()+Config.BALL_H/2;
        float x2 = ball2.gr.getX()+Config.BALL_W/2;
        float y2 = ball2.gr.getY()+Config.BALL_H/2;
        if(x1<x2 && (y1-y2)>Config.BALL_RADIUS)
            return 1;
        if(x1<x2 && (y1-y2)<Config.BALL_RADIUS)
            return -1;
        if(x1>x2 && (y1-y2)>Config.BALL_RADIUS)
            return 2;
        if(x1>x2 && (y1-y2)<Config.BALL_RADIUS)
            return -2;
        return 0;
    }

    private int checkColision(ball ball1, BallGrid ball2){
        if(ball1.body.overlaps(ball2.body))
            return 1;
      return -1;
    }
    private float Distance(ball ball1, BallGrid ball2){
        return (float)Math.sqrt(
                Math.pow(ball1.x+Config.BALL_RADIUS-ball2.x-Config.BALL_RADIUS,2)+ Math.pow(ball1.y+Config.BALL_RADIUS-ball2.y-Config.BALL_RADIUS,2)
        );
    }




}
