package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.commons.TextureAtlasC;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.gameLogic.config.Config;


public class board {
    private Group MainGroup;
    private Group arrowGr = new Group();
    private Group grFireBall = new Group();
    private Group WhiteGr = new Group();
    public ball ball,ballNext, ballMove;
    private Image Gun,btnSwapBall;
    private float mousePosX=0;
    private float mousePosY=0;
    final GShapeSprite WhiteOverLay = new GShapeSprite();
    private Grid grid;
    private int typeBallNext=1;
    public boolean runtime=false;
    private header header;
//    public boolean checkBallBusy = false;
    private int countSkillColor=0;
    private int countSkillBomb=0;
    private int typeBomb=0;
    private arrow arrow;


    public board(Group MainGroup,header header){
        this.header = header;
        ReadyGame();
        this.MainGroup = MainGroup;
        WhiteGr.addActor(arrowGr);
        WhiteGr.addActor(grFireBall);
        Gun();
        WhiteOverLay();
        CreateBallPlay();
        CreateBallNext();
        MouseOver();
        moveBall();
        grid = new Grid(header,this);
        BtnSwapBall();
        setTouch(Touchable.disabled);
        createFireBall();

    }
    private void ReadyGame(){
        Group gr = new Group();
        GStage.addToLayer(GLayer.top,gr);
        Image Ready = GUI.createImage(TextureAtlasC.Boardgame,"ready");
        Ready.setScale(1.5f);
        Ready.setOrigin(Align.center);
        Ready.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
        gr.addActor(Ready);
        SoundEffect.Play(SoundEffect.ready);
        Ready.addAction(Actions.sequence(
               Actions.scaleTo(0,0,1f, Interpolation.swingIn),
                GSimpleAction.simpleAction((d,a)->{
                    Ready.remove();
                    Image Go = GUI.createImage(TextureAtlasC.Boardgame,"go");
                    Go.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,Align.center);
                    gr.addActor(Go);
                    SoundEffect.Play(SoundEffect.go);
                    Go.addAction(Actions.sequence(
                            Actions.moveBy(0,-100,0.5f, Interpolation.swingIn),
                            GSimpleAction.simpleAction((d1,a1)->{
                                gr.clear();
                                gr.remove();
                                header.CountDownTime();
                                setTouch(Touchable.enabled);
                                return true;
                            })
                    ));
                    return true;
                })
        ));
    }
    private void Gun(){
        Gun = GUI.createImage(TextureAtlasC.Boardgame,"gun");
        Gun.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-Gun.getHeight()/2-20, Align.center);
        MainGroup.addActor(Gun);
        Image soc = GUI.createImage(TextureAtlasC.Boardgame,"soc");
        soc.setPosition(Gun.getX()-130,Gun.getY()+Gun.getHeight()/2,Align.center);
        MainGroup.addActor(soc);
    }
    private void BtnSwapBall(){
        btnSwapBall = GUI.createImage(TextureAtlasC.Boardgame,"gun");
        btnSwapBall.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-Gun.getHeight()/2-20, Align.center);
        btnSwapBall.setColor(Color.CLEAR);
        WhiteGr.addActor(btnSwapBall);
        btnSwapBall.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                SwapBall();
            }
        });
    }
    private void SwapBall(){
        int type = ballNext.id;
        typeBallNext = ball.id;
        ball.destroy();
        ballNext.destroy();
        ball = new ball(Gun.getX()+Gun.getWidth()/2,Gun.getY()+Gun.getHeight()/2,type,this);
        ballNext = new ball(Gun.getX()-80,Gun.getY()+Gun.getHeight()*0.6f,typeBallNext,this);
        ballNext.Scale(0.7f);
    }
    private void WhiteOverLay(){
        GStage.addToLayer(GLayer.top,WhiteGr);
        WhiteOverLay.createRectangle(true,0,0,GStage.getWorldWidth(),GStage.getWorldHeight());
        WhiteOverLay.setColor(0,0,0,0f);
        WhiteGr.addActor(WhiteOverLay);
    }
    private void CreateBallPlay(){
                System.out.println("Comboooo: "+Config.combo);
        int type = typeBallNext;
//        /////// skill change color//////
//        if(Config.typeChangeColor!=0){
//            type=Config.typeChangeColor;
//            Config.typeChangeColor=0;
//        }
        /////// skill fire ball//////
//        if(Config.combo%3==0&&Config.combo>0){
//            AniFireBall();
//            type=Config.FireBall;
//            SoundEffect.Play(SoundEffect.fireball);
//            Config.combo++;
//        }
        ball = new ball(Gun.getX()+Gun.getWidth()/2,Gun.getY()+Gun.getHeight()/2,type,this);
        arrow = new arrow(ball.x,ball.y,mousePosX,mousePosY,arrowGr,ball.id);
        arrowGr.setVisible(false);

//        setBallFire();
    }
    public void setBallFire(){
        /////// skill fire ball//////
        if(Config.combo%3==0&&Config.combo>0){
            AniFireBall(grFireBall);
            int type=Config.FireBall;
            SoundEffect.Play(SoundEffect.fireball);
            Config.combo++;
            ball.destroy();
            ball = new ball(Gun.getX()+Gun.getWidth()/2,Gun.getY()+Gun.getHeight()/2,type,this);
            arrowGr.clear();
            arrow = new arrow(ball.x,ball.y,mousePosX,mousePosY,arrowGr,ball.id);
            arrowGr.setVisible(false);


        }
    }
    public void setBallColor(){
        /////// skill change color//////
        if(Config.typeChangeColor!=0){
            int type=Config.typeChangeColor;
            Config.typeChangeColor=0;
            ball.destroy();
            ball = new ball(Gun.getX()+Gun.getWidth()/2,Gun.getY()+Gun.getHeight()/2,type,this);
            arrowGr.clear();
            arrow = new arrow(ball.x,ball.y,mousePosX,mousePosY,arrowGr,ball.id);
            arrowGr.setVisible(false);

        }
    }

    private void CreateBallNext(){
        if(ballNext!=null)
        {
            ballNext.gr.addAction(Actions.sequence(
                    Actions.parallel(
                            Actions.moveTo(Gun.getX()+Gun.getWidth()/2-Config.BALL_RADIUS/2,Gun.getY()+Config.BALL_RADIUS,Config.Dr_Ball_Cre),
                            Actions.scaleTo(1,1,Config.Dr_Ball_Cre)
                    ),
                    GSimpleAction.simpleAction((d,a)->{
                        ballNext.destroy();
                        int type = (int)(Math.random()*5)+1;
//                        int type = 6;
                        //////// skills Color////////
                        countSkillColor++;
                        countSkillBomb++;
                        if(countSkillColor==Config.timeSkillColor)
                        {
                            type = Config.ChangeColor;
                            Config.timeSkillColor+=Config.pecentSkillColor;
                            countSkillColor=0;
                        }
                        /////// skills Bomb//////////
                        if(countSkillBomb==Config.timeSkillBomb)
                        {
                            type = Config.Bomb;
                            Config.timeSkillBomb+=Config.pecentSkillBomb;
                            countSkillBomb=0;
                        }
                        ballNext = new ball(Gun.getX()-80,Gun.getY()+Gun.getHeight()*0.6f,type,this);
                        ballNext.Scale(0.7f);
                        CreateBallPlay();
//                        setBallColor();
                        typeBallNext=type;
                        return true;
                    })
            ));
        }else {
            typeBallNext = (int)(Math.random()*5)+1;

//            typeBallNext = 6;
            ballNext = new ball(Gun.getX()-80,Gun.getY()+Gun.getHeight()*0.6f,typeBallNext,this);
            ballNext.Scale(0.7f);

        }

    }


    private void MouseOver(){
        WhiteOverLay.addListener(new ClickListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                mousePosX=x;
                mousePosY=y;
                arrowGr.setVisible(true);
                arrow.SetArrow(ball.x,ball.y,x,y);
                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mousePosX=x;
                mousePosY=y;
                arrowGr.setVisible(true);
                arrow.SetArrow(ball.x,ball.y,x,y);

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                mousePosX=x;
                mousePosY=y;
                arrowGr.setVisible(true);
                arrow.SetArrow(ball.x,ball.y,x,y);

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
                if(ball.id==Config.FireBall)
                    SoundEffect.Play(SoundEffect.fireFly);
                SoundEffect.Play(SoundEffect.shot);
                setTouch(Touchable.disabled);
                runtime=false;
                //WhiteOverLay.setTouchable(Touchable.disabled);
                Double deg = Math.atan2(ball.y-mousePosY,mousePosX-ball.x);
                ball.deg = (float) Math.toDegrees(deg);
                ball.speedX = Config.BALL_SPEED * (float) Math.cos(deg);
                ball.speedY = Config.BALL_SPEED * (float) Math.sin(deg);
                ball.moveBall();
                ballMove = ball;
                checkRuntime();
                CreateBallNext();

//                checkCreateBall();

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
       // System.out.println("ball check: "+row+"---: "+col);
        if (row<0)
            return 0;
        if(col<0||col>Config.quantityBall-1)
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
//                        SoundEffect.Play(SoundEffect.colision);
                       // System.out.println("this ball: " + grid.arrGridBall.get(i).get(j).row + "--: " + grid.arrGridBall.get(i).get(j).col);
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

                      //  System.out.println("checkRowShift: "+rowShift);
                        if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==1){
                            if(rowShift)
                                col-=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            if(checkRowNull(row-1,col)==1){
                                grid.addBallLeftRight(row-1,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                System.out.println("trai th1!!!!");
                            }else {
                                if(checkRowNull(row-1,col-1)==1){
                                    grid.addBallLeftRight(row-1,col-1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                    System.out.println("trai th2!!!!");
                                    //  System.out.println("kkkkkkkkkkkkkkkkkk");
                                }else {
                                    grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                    System.out.println("trai th3!!!!");

                                }
//                                grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                            }
                            System.out.println("trai tren");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==-1){
                            col-=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                           // System.out.println("ball check: "+row+"---: "+(col));
                            if(checkRowNull(row,col)==1) {
                                grid.addBallHorizontal(row, col, ballMove.id, grid.arrGridBall.get(i).get(j), grid.arrGridBall.get(i).get(j).rowShift);
                            }
                          //  System.out.println("trai ngang");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==2){
                            if(rowShift==false)
                                col+=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                            if(checkRowNull(row-1,col)==1){
                                grid.addBallLeftRight(row-1,col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                System.out.println("trai th1!!!!");
                            }else {
                                if(checkRowNull(row-1,col+1)==1){
                                    grid.addBallLeftRight(row-1,col+1,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                    System.out.println("trai th2!!!!");
                                    //     System.out.println("kkkkkkkkkkkkkkkkkk");
                                }else {
                                    grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);
                                    System.out.println("trai th3!!!!");
//                                    grid.addNewRow(col,ballMove.id,grid.arrGridBall.get(i).get(j),rowShift);

                                }
                            }
                            System.out.println("phai tren");
                        }else if(checkleftright(ballMove,grid.arrGridBall.get(i).get(j))==-2){
                            col+=1;
                            if(col<0)
                                return;
                            if(col>Config.quantityBall-1)
                                return;
                          //  System.out.println("ball check: "+row+"---: "+(col));
                          //  System.out.println("phai ngang");
                            if(checkRowNull(row,col)==1){
                                grid.addBallHorizontal(row,col,ballMove.id,grid.arrGridBall.get(i).get(j),grid.arrGridBall.get(i).get(j).rowShift);
                            }
                        }
                        ballMove.destroy();
                        setTouch(Touchable.enabled);
                        runtime=true;
                        return;
                    }else if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==2){
                        grid.arrGridBall.get(i).get(j).destroy();
                        grid.arrGridBall.get(i).set(j,null);
                    }else if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==3){
                       // System.out.println("here!!!!!!!!!!");
//                        setTouch(Touchable.enabled);
                        grid.ChangeColor(grid.arrGridBall.get(i).get(j));
                        ballMove.destroy();
                        setBallColor();
                        setTouch(Touchable.enabled);
                        runtime=true;
                        return;
                    }else if(checkColision(ballMove,grid.arrGridBall.get(i).get(j))==4){
                        grid.SkillBomb(grid.arrGridBall.get(i).get(j));
                        ballMove.destroy();
                        setTouch(Touchable.enabled);
                        runtime=true;
                        return;
                    }
                }
            }

    }
    public void DropBall(){
        grid.DropBall();
    }
    private int checkleftright(ball ball1, BallGrid ball2){
        float x1 = ball1.gr.getX()+Config.BALL_W/2-Config.SpeedX;
        float y1 = ball1.gr.getY()+Config.BALL_H/2+Config.SpeedY;
        float x2 = ball2.gr.getX()+Config.BALL_W/2;
        float y2 = ball2.gr.getY()+Config.BALL_H/2;
//        System.out.println("check pos: "+"[x1: "+x1+"-y1: "+y1+"]"+"[x2: "+x2+"-y2: "+y2+"]" );
//        System.out.println("offsetXy: "+ball.speedX+"---- "+ball.speedY);
        if(x1<=x2 && (y1-y2)>Config.BALL_RADIUS/2)
            return 1;
        if(x1<=x2 && (y1-y2)<Config.BALL_RADIUS/2)
            return -1;
        if(x1>=x2 && (y1-y2)>Config.BALL_RADIUS/2)
            return 2;
        if(x1>=x2 && (y1-y2)<Config.BALL_RADIUS/2)
            return -2;
        return 0;
    }

    private int checkColision(ball ball1, BallGrid ball2){
        if(ball1.body.overlaps(ball2.body)==true&&ball1.id!=Config.FireBall&&ball1.id!=Config.ChangeColor&&ball1.id!=Config.Bomb)
            return 1;
        else if(ball1.body.overlaps(ball2.body)==true&&ball1.id==Config.FireBall)
            return 2;
        else if(ball1.body.overlaps(ball2.body)==true&&ball1.id==Config.ChangeColor)
            return 3;
        else if(ball1.body.overlaps(ball2.body)==true&&ball1.id==Config.Bomb)
            return 4;

      return -1;
    }
    public void setTouch(Touchable touch){
        WhiteOverLay.setTouchable(touch);
    }
    private void createFireBall(){
        Image img = GUI.createImage(TextureAtlasC.Boardgame,"fireball");
        img.setOrigin(Align.center);
        img.setRotation(180);
        img.setPosition(0,0,Align.center);
        grFireBall.addActor(img);
        grFireBall.setVisible(false);
    }
    private void AniFireBall(Group gr){
        gr.setVisible(true);
        gr.setPosition(GStage.getWorldWidth()/2,GStage.getWorldHeight()-250,Align.center);
        gr.addAction(Actions.sequence(
                Actions.rotateTo(180,0.2f),
                Actions.moveBy(0,-200,1f,Interpolation.swingIn),
                GSimpleAction.simpleAction((d,a)->{
                    gr.setVisible(false);
                    return true;
                })
        ));
    }


}
