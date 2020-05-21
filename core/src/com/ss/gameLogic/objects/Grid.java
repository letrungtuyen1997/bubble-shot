package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.gameLogic.config.Config;

public class Grid {
    public float x,y;
    private final GShapeSprite LineGrid = new GShapeSprite();
    public Group grGrid = new Group();
//    private  int row=0;
    private int quantity = Config.quantityBall;
    public Array<Array<BallGrid>> arrGridBall = new Array<>();
    public Array<BallGrid> arrBallSame = new Array<>();
    public Array<BallGrid> arrDropBall = new Array<>();
    public Array<BallGrid> arrDropBall2 = new Array<>();
    public Array<BallGrid> arrDropFinal = new Array<>();
    private boolean ismoveGrid=false;
    private int countRowTop=0;
    private int center=0;
    private int exploxe=0;


    Grid(){
        GStage.addToLayer(GLayer.ui,grGrid);
        createLine();
        moveGrid();
//        createNewRowTest();
    }
    private void createNewRowTest(){
//        arrGridBall = new BallGrid[][100];

        int row = 7;
        int col=6;
        float paddingX = 0;

        for(int i=0;i<row;i++){
            Array<BallGrid> NewrowBalls = new Array<>();
            if(i%2!=0){
                paddingX=Config.BALL_W/2;
            }
            else{
                paddingX=0;

            }
            for(int j=0;j<col;j++){

                int type = (int)(Math.random()*5)+1;
                BallGrid ballGrid = new BallGrid(type,i,j,grGrid,this,paddingX,col,0,false);
               // System.out.print(" "+"[row: "+i+"   col: "+j+"]");
                NewrowBalls.add(ballGrid);
            }
          //  System.out.println();
            arrGridBall.add(NewrowBalls);
        }

    }
    private void createLine(){

        LineGrid.createLine(0,0, GStage.getWidth(),0);
        LineGrid.setColor(1,0,1,1f);
        grGrid.addActor(LineGrid);
    }
    private void moveGrid(){
        grGrid.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    center++;
                    if(center>=120){
                        Config.GridMove=0.1f;
                    }else
                        Config.GridMove=2f;
                    setPosBallGrid(Config.GridMove);
                    if(havetoCreateNewRow()){
                        createNewRow();
//                        System.out.println("create");

                    }
                    if(checkGameOver()){
                        ismoveGrid= true;
                        System.out.println("gameover: "+arrGridBall.size);
//                        arrGridBall.get(1).get(0).gr.addAction(Actions.moveBy(20,20,0.5f));
                    }
                    return ismoveGrid;
                })
        );
    }
    private void setPosBallGrid(float y){
        LineGrid.setPosition(0,LineGrid.getY()+y);
        for (int i=0;i<arrGridBall.size;i++){
            for (int j=0;j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)!=null){
                    arrGridBall.get(i).get(j).updatePosition(y);

                }
            }
        }
    }
    private void updateLineGrid(float y){
        LineGrid.setPosition(0,LineGrid.getY()+y);
    }
    private int numRows(){
        return arrGridBall.size;
    }
    private boolean havetoCreateNewRow(){
        if(LineGrid.getY()>(numRows()*Config.ROW_HEGHT))
            return true;
        return false;
    }
    private void createNewRow(){
        int row = arrGridBall.size;
        countRowTop++;
        float paddingX = 0;
        boolean rowShift=false;
        if(countRowTop%2!=0){
            paddingX=Config.BALL_W/2;
            rowShift=true;
        }
        Array<BallGrid> NewrowBalls = new Array<>();
        for(int j=0;j<quantity;j++){
//            if(j!=0&&j!=quantity){
                int type = (int)(Math.random()*5)+1;
                BallGrid ballGrid = new BallGrid(type,row,j,grGrid,this,paddingX,quantity,-Config.BALL_RADIUS,rowShift);
                NewrowBalls.add(ballGrid);
//            }else
//                NewrowBalls.add(null);
        }
        arrGridBall.add(NewrowBalls);
//        setPosBallGrid(0.5f);
    }
    private boolean checkGameOver(){
        if(LineGrid.getY()>GStage.getWorldHeight()-Config.ROW_HEGHT)
            return true;
        return false;
    }
    public void checkAllBall(BallGrid ball){
        arrBallSame.clear();
//        System.out.println("Ball: [row: "+ball.row+"  col: "+ball.col);
        BallAround(arrGridBall,ball,ball.row,ball.col);
//        System.out.println("check: "+arrBallSame.size);
        if(arrBallSame.size>1){
//            System.out.println("set");
            for (BallGrid p: arrBallSame){
//                System.out.println("Ball: [row: "+p.row+"  col: "+p.col);
                arrGridBall.get(p.row).set(p.col,null);
//                setArrGridBall(p);
                p.destroy();
            }
        }
    }
    private void setArrGridBall(BallGrid p){
        for (int i=0;i<arrGridBall.size;i++){
            for (int j=0; j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)!=null){
                    if(p.row==arrGridBall.get(i).get(j).row && p.col == arrGridBall.get(i).get(j).col){
                        arrGridBall.get(i).set(j,null);
                    }
                }
            }
        }
    }
    public void BallAround(Array<Array<BallGrid>> input, BallGrid ball,int row, int col){
        if (row < 0 || row >= input.size || col < 0 || col >= input.get(row).size)
            return;
        if(input==null)
            return;
        if (input.get(row).get(col)==null)
            return;
        for (BallGrid p: arrBallSame){
            if(p.row==row && p.col==col)
                return;
        }
        if(ball.compare(input.get(row).get(col))){
            arrBallSame.add(input.get(row).get(col));
            int even=0,odd=0;
            if(arrGridBall.get(row).get(col).rowShift){
                odd=1;
            }else {
                even=1;
            }
            BallAround(input,ball,row-1,col-even);
            BallAround(input,ball,row-1,col+odd);
            BallAround(input,ball,row,col-1);
            BallAround(input,ball,row,col+1);
            BallAround(input,ball,row+1,col-even);
            BallAround(input,ball,row+1,col+odd);

        }
    }
    public void addNewRow(int col,int type,BallGrid ball, boolean rowShift){
        arrBallSame.clear();
        BallGrid ballGrid = null;
        int row = 0;
        if(col<=0)
            col=0;
        else if(col>=quantity){
            col-=1;
        }
        float paddingX = 0;
        float paddingY = ball.y+Config.BALL_RADIUS+Config.ROW_HEGHT;
        float LineYUp=0;
//        System.out.println("checkkkkkkkkkkk: "+rowShift);
        if(rowShift)
            paddingX=Config.BALL_W/2;
        Array<BallGrid> NewrowBalls = new Array<>();
        for(int j=0;j<quantity;j++){
            if(col==j){
                ballGrid = new BallGrid(type,0,j,grGrid,this,paddingX,quantity,paddingY,rowShift);
                NewrowBalls.add(ballGrid);
                LineYUp=ballGrid.y+Config.ROW_HEGHT;

            }else {
                NewrowBalls.add(null);
            }
        }
        for (int i=0;i<arrGridBall.size;i++){
            for (int j=0;j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)!=null)
                    arrGridBall.get(i).get(j).row+=1;
//                System.out.println("log: "+i+": "+arrGridBall.get(i).get(j));
            }
        }
        arrGridBall.insert(0,NewrowBalls);
        BallAround(arrGridBall,ballGrid,ballGrid.row,ballGrid.col);
        detroyBall(ballGrid);
        updateLineGrid(LineYUp);
//        System.out.println("size max: "+arrGridBall.size);
    }
    public void addBallLeftRight(int row,int col,int type,BallGrid ball, boolean rowShift){
        arrBallSame.clear();
        if(col<=0)
            col=0;
        else if(col>=quantity){
            col-=1;
        }
        float paddingX = 0;
        float paddingY = ball.y+Config.BALL_RADIUS+Config.ROW_HEGHT;
        if(rowShift)
            paddingX=Config.BALL_W/2;
        BallGrid ballGrid = new BallGrid(type,row,col,grGrid,this,paddingX,quantity,paddingY,rowShift);
        arrGridBall.get(row).set(col,ballGrid);
        BallAround(arrGridBall,ballGrid,ballGrid.row,ballGrid.col);
        detroyBall(ballGrid);


    }
    public void addBallHorizontal(int row,int col,int type,BallGrid ball, boolean rowShift){
        arrBallSame.clear();
        if(col<=0)
            col=0;
        else if(col>=quantity){
            col-=1;
        }
        float paddingX = 0;
        float paddingY = ball.y+Config.BALL_RADIUS;
        if(rowShift)
            paddingX=Config.BALL_W/2;
        BallGrid ballGrid = new BallGrid(type,row,col,grGrid,this,paddingX,quantity,paddingY,rowShift);
        arrGridBall.get(row).set(col,ballGrid);
        BallAround(arrGridBall,ballGrid,ballGrid.row,ballGrid.col);
        detroyBall(ballGrid);


    }
    private void detroyBall(BallGrid ballGrid){
        exploxe=0;
        if(arrBallSame.size>2){
            for (BallGrid p :arrBallSame){
//                System.out.println("Ball: [row: "+p.row+"  col: "+p.col);
                Tweens.setTimeout(grGrid,Config.dradestroyball*arrBallSame.indexOf(p,true),()->{
                    exploxe++;
                    arrGridBall.get(p.row).set(p.col,null);
                    p.destroy();
                });
            }
            grGrid.addAction(GSimpleAction.simpleAction((d,a)->{
                if(exploxe==arrBallSame.size){
                    findFloatingClusters();
                    setArrDropBall2();
//            Culusters(arrDropBall);
//            Culusters(arrDropBall2);
//            effectBallDrop(arrDropBall);
                    effectBallDrop(arrDropBall2);
//                    System.out.println("size ball drop: "+arrDropBall.size);
//                    System.out.println("size ball drop2: "+arrDropBall2.size);
//            for (BallGrid p : arrDropBall)
//                System.out.println("ballDrop: "+p.row+"    "+p.col);
                    return true;
                }
                return false;
            }));


        }
        SetarrGridBall();
    }
    public void findClusters(Array<Array<BallGrid>> input, BallGrid ball,int row, int col){
        if (row < 0 || row >= input.size || col < 0 || col >= input.get(row).size)
            return;
        if(input==null)
            return;
        if (input.get(row).get(col)==null)
            return;
        for (BallGrid p: arrDropBall){
            if(p.row==row && p.col==col)
                return;
        }
        //if(ball.compare(input.get(row).get(col))){
            arrDropBall.add(input.get(row).get(col));
            int even=0,odd=0;
            if(arrGridBall.get(row).get(col).rowShift){
                odd=1;
            }else {
                even=1;
            }
            findClusters(input,ball,row-1,col-even);
            findClusters(input,ball,row-1,col+odd);
            findClusters(input,ball,row,col-1);
            findClusters(input,ball,row,col+1);
            findClusters(input,ball,row+1,col-even);
            findClusters(input,ball,row+1,col+odd);

       // }
    }
    private void findFloatingClusters(){
        arrDropBall.clear();
        for (int i=arrGridBall.size-1;i>=0;i--){
            for (int j=0;j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)!=null){
//                    System.out.println("here");
                    findClusters(arrGridBall,arrGridBall.get(i).get(j),arrGridBall.get(i).get(j).row,arrGridBall.get(i).get(j).col);
                    return;
                }
            }
        }
    }
    private void setArrDropBall2(){
        arrDropBall2.clear();
        for (int i=0;i<arrGridBall.size;i++){
            for (int j=0;j<arrGridBall.get(i).size;j++){
                int count=0;
                if(arrGridBall.get(i).get(j)!=null){
                    for (BallGrid p: arrDropBall){
                        if(arrGridBall.get(i).get(j).row==p.row && arrGridBall.get(i).get(j).col==p.col)
                            count++;
                    }
                    if(count==0){
                        arrDropBall2.add(arrGridBall.get(i).get(j));
                    }
                }

            }
        }
    }
    private void SetarrGridBall(){
        for(int i=0;i<arrGridBall.size;i++){
            int count=0;
            for (int j=0;j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)==null){
                    count++;
                }
            }
//            System.out.println("check here!!!!!!!!!: "+count);
//            System.out.println("check here!!!!!!!!!2: "+arrGridBall.get(i).size);
            if(count==Config.quantityBall){
                for (int i1=0;i1<arrGridBall.size;i1++){
                    for (int j1=0;j1<arrGridBall.get(i).size;j1++){
                        if(arrGridBall.get(i1).get(j1)!=null){
                            if( arrGridBall.get(i1).get(j1).row>0)
                                arrGridBall.get(i1).get(j1).row-=1;
                        }
//                System.out.println("log: "+i+": "+arrGridBall.get(i).get(j));
                    }
                }
                arrGridBall.removeIndex(i);
                updateLineGrid(-Config.ROW_HEGHT);
            }
        }
    }
    private void effectBallDrop(Array<BallGrid> arr){
        for (BallGrid p : arr)
        {
            arrGridBall.get(p.row).set(p.col,null);
            p.moveBall(Config.Speed_Drop);
        }
    }



}
