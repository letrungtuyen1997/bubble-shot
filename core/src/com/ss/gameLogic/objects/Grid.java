package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.BitmapFontC;
import com.ss.commons.TextureAtlasC;
import com.ss.commons.Tweens;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.effects.SoundEffect;
import com.ss.effects.effectWin;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Config;
import com.ss.scenes.GameOverScene;
import com.ss.scenes.GameScene;

public class Grid {
    public float x,y;
    private final GShapeSprite LineGrid = new GShapeSprite();
    public Group grGrid = new Group();
    public Group grParticle = new Group();
//    private  int row=0;
    private int quantity = Config.quantityBall;
    public Array<Array<BallGrid>> arrGridBall = new Array<>();
    public Array<BallGrid> arrBallSame = new Array<>();
    public Array<BallGrid> arrBallColor = new Array<>();
    public Array<BallGrid> arrBallBomb = new Array<>();
    public Array<BallGrid> arrBallother = new Array<>();
    public Array<BallGrid> arrDropBall = new Array<>();
    public Array<BallGrid> arrDropBall2 = new Array<>();
    private boolean ismoveGrid=false;
    private int countRowTop=0;
    private int level=0;
    private int exploxe=0;
    private long ScoreBall=0;
    private long ScoreBallDrop=0;
    private Label lbDrop, lbCombo;
    private boolean checkLbDrop=false;
    private Group grCombo = new Group();
    private Group grWow = new Group();
    private Group grCrazy = new Group();
    private Group grFabulous = new Group();
    private header header;
    private board board;
    private Array<effectWin> arrEffect = new Array<>();
    private Array<effectWin> arrEffectFireBall = new Array<>();
    private Array<effectWin> arrEffectBomb = new Array<>();
    private int countClock = 0;
    private int conntBomb = 0;
    private GameScene gameScene;
    private boolean checkRepeat = false;

    Grid(header header, board board, GameScene gameScene){
        this.header = header;
        this.board = board;
        this.gameScene = gameScene;
        GStage.addToLayer(GLayer.ui,grGrid);
        GStage.addToLayer(GLayer.top,grCombo);
        GStage.addToLayer(GLayer.ui,grParticle);
        grParticle.addActor(grWow);
        grParticle.addActor(grCrazy);
        grParticle.addActor(grFabulous);
        for (int i=0;i<80;i++){
            effectWin ef = new effectWin(1,0,0,grParticle);
            arrEffect.add(ef);
            effectWin ef1 = new effectWin(2,0,0,grParticle);
            arrEffectFireBall.add(ef1);
            effectWin ef2 = new effectWin(3,0,0,grParticle);
            arrEffectBomb.add(ef2);
        }
        createLine();
        moveGrid();
        createExpecial("wow",grWow);
        createExpecial("crazy",grCrazy);
        createExpecial("fabulous",grFabulous);
//        createNewRowTest();
       // System.out.println("checklevel: "+Config.LoadLv(1).lv.length);
    }

    private void createLine(){

        LineGrid.createLine(0,Config.ballApear+Config.BALL_RADIUS, GStage.getWidth(),Config.ballApear+Config.BALL_RADIUS);
//        LineGrid.setY(-Config.ROW_HEGHT*5+Config.BALL_RADIUS);
        LineGrid.setColor(1,0,1,1f);
        grGrid.addActor(LineGrid);
    }
    private void moveGrid(){
        grGrid.addAction(
                GSimpleAction.simpleAction((d,a)->{
                    if(LineGrid.getY()<GStage.getWorldHeight()*0.6f-Config.ballApear){
                        Config.GridMove=Config.SpeedGrid;
                    }else
                        Config.GridMove=0.1f;
                    setPosBallGrid(Config.GridMove);
                    if(havetoCreateNewRow()){
                        createNewRow();
//                        System.out.println("create");

                    }
                    if(checkGameOver()){
                        this.header.runTime=true;
                        GameOver(C.lang.gameover);
                    }
                    if(this.header.time==0){
                        GameOver(C.lang.timeup);
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
        level++;
        countClock++;
        if(checkRepeat==false){
            if(level<10)
                Config.ballType=1;
            else if(level>=10&&level<30)
                Config.ballType=2;
            else if(level>=30&&level<60)
                Config.ballType=3;
            else if(level>=60&&level<75){
                Config.ballType=4;
            }else
                Config.ballType=5;
        }else {
            Config.ballType=5;

        }

        if(level>100){
            level=0;
            checkRepeat=true;
        }
        int[] arrlv = Config.LoadLv(level).lv;
       // System.out.println("check level!!!!!! "+arrlv.length);

        Array<BallGrid> NewrowBalls = new Array<>();
        for(int j=0;j<quantity;j++){
            int check=0;
            for (int i=0;i<arrlv.length;i++){
                if(j!=arrlv[i]){
                    check++;
                }
            }
            if(check!=arrlv.length){
//                System.out.println("ve: "+arrlv[i]);
                int type = (int)(Math.random()*Config.ballType)+1;
                boolean time=false;
                if(countClock==Config.timeSkillClock){
                    Config.timeSkillClock+=Config.pecentSkillClock;
                    countClock=0;
                    time=true;
                }
                BallGrid ballGrid = new BallGrid(type,row,j,grGrid,this,paddingX,quantity,Config.ballApear,rowShift,time,header);
                NewrowBalls.add(ballGrid);
            }else
                NewrowBalls.add(null);

        }
        arrGridBall.add(NewrowBalls);
//        setPosBallGrid(0.5f);
    }
    private boolean checkGameOver(){
        if(LineGrid.getY()>GStage.getWorldHeight()-Config.ROW_HEGHT-Config.ballApear)
            return true;
        return false;
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

        if(ball.compare(input.get(row).get(col))) {
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
                ballGrid = new BallGrid(type,0,j,grGrid,this,paddingX,quantity,paddingY,rowShift,false,header);
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
        BallGrid ballGrid = new BallGrid(type,row,col,grGrid,this,paddingX,quantity,paddingY,rowShift,false,header);
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
        BallGrid ballGrid = new BallGrid(type,row,col,grGrid,this,paddingX,quantity,paddingY,rowShift,false,header);
        arrGridBall.get(row).set(col,ballGrid);
        BallAround(arrGridBall,ballGrid,ballGrid.row,ballGrid.col);
        detroyBall(ballGrid);


    }
    public effectWin ef(){
        effectWin efs=null;
        for (effectWin e: arrEffect)
            if(e.isAlive==false)
                efs = e;
            return efs;
    }
    public effectWin efFireBall(){
        effectWin efs=null;
        for (effectWin e: arrEffectFireBall)
            if(e.isAlive==false)
                efs = e;
        return efs;
    }
    public effectWin efBomb(){
        effectWin efs=null;
        for (effectWin e: arrEffectBomb)
            if(e.isAlive==false)
                efs = e;
        return efs;
    }
    private void detroyBall(BallGrid ballGrid){
        exploxe=0;
//        if(this.board.checkFireBall==true){
//            Config.combo++;
//            Combo();
//        }
        if(arrBallSame.size>2){
            ShotBig(arrBallSame.size,ballGrid.gr.getX(),ballGrid.gr.getY());
            SoundEffect.Play(SoundEffect.corect);
            Config.combo++;
            int exploxeSound=Config.combo-1;
            if(Config.combo>=10){
                exploxeSound=9;
            }
            SoundEffect.explode(exploxeSound);
            SoundEffect.Play(SoundEffect.snap);

            CountScore(arrBallSame.size,ballGrid.gr.getX(),ballGrid.gr.getY());
            for (BallGrid p :arrBallSame){
                if(p.gr.getY()>0){
                    arrGridBall.get(p.row).set(p.col,null);
                    p.destroy(0);
                }
            }
                DropBall();

        }else {
            if(Config.combo>0)
                SoundEffect.Play(SoundEffect.lose);
            Config.combo=0;
            grCombo.addAction(Actions.sequence(
                    Actions.scaleTo(0,1,0.2f,Interpolation.swingIn),
                    GSimpleAction.simpleAction((d,a)->{
                        grCombo.clear();
                        grCombo.setScale(1);
                        return true;
                    })
            ));
            //grCombo.remove();
            arrBallother.clear();
          //  System.out.println("rung rung");
            try {
                findClusters2(arrGridBall,ballGrid,ballGrid.row,ballGrid.col);
                bounceGrid(ballGrid);
            }catch (Exception e){

            }

        }
        SetarrGridBall();
        this.board.setBallFire();

    }
    public void DropBall(){
        findFloatingClusters();
        setArrDropBall2();
        effectBallDrop(arrDropBall2);
        SetarrGridBall();
        Combo();

        System.out.println("ballDrop!!!!!: "+arrDropBall.size);
        System.out.println("ballDrop2!!!!!: "+arrDropBall2.size);
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

    }
    private void findFloatingClusters(){
        arrDropBall.clear();
        for (int i=arrGridBall.size-1;i>=0;i--){
            for (int j=0;j<arrGridBall.get(i).size;j++){
                if(arrGridBall.get(i).get(j)!=null){
                    System.out.println("here: "+arrGridBall.get(i).get(j).id);
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
//                    System.out.println("check count: "+count);
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
             //   System.out.println("aaaaaaaaaaaaaaaa");
                updateLineGrid(-Config.ROW_HEGHT);
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
    public void bounceGrid(BallGrid ball){

        float x=0,y=0;
        float x2=Config.vibrate,y2=Config.vibrate;
//        float y=5;
        ball.Bounce(x2,y2);
        for (BallGrid p : arrBallother ){
            if(p.row<ball.row)
                y=y2*-1;
            if(p.row==ball.row)
                y=0;
            if(p.row>ball.row)
                y=y2;
            if(p.col>ball.col)
                x=x2;
            if(p.col<ball.col)
                x=x2;
            if(p.col==ball.col)
                x=0;
            if(p.row<ball.row)
                y=y2*(-1);
            if(y>0)
                y2-=Config.vibrateDesc;
            if(x>0)
                x2-=Config.vibrateDesc;
            if(x<0)
                x2+=Config.vibrateDesc;
            x2=Math.abs(x2);
            y2=Math.abs(y2);
            float finalY = y;
            float finalX = x;
                p.Bounce(finalX, finalY);
        }
    }

    public void findClusters2(Array<Array<BallGrid>> input, BallGrid ball,int row, int col){
        if (row < 0 || row >= input.size || col < 0 || col >= input.get(row).size)
            return;
        if(input==null)
            return;
        if (input.get(row).get(col)==null)
            return;
        for (BallGrid p: arrBallother){
            if(p.row==row && p.col==col)
                return;
        }
        //if(ball.compare(input.get(row).get(col))){
        arrBallother.add(input.get(row).get(col));
        int even=0,odd=0;
        if(arrGridBall.get(row).get(col).rowShift){
            odd=1;
        }else {
            even=1;
        }
        findClusters2(input,ball,row,col+1);
        findClusters2(input,ball,row,col-1);
        findClusters2(input,ball,row+1,col-even);
        findClusters2(input,ball,row-1,col+odd);
        findClusters2(input,ball,row-1,col-even);
        findClusters2(input,ball,row+1,col+odd);

    }
    private void CountScore(int BallDead,float x, float y){
        Group gr = new Group();
        GStage.addToLayer(GLayer.top,gr);

        ScoreBall = Config.ScBall*BallDead;
        long Bonus=0;
        if(Config.combo>=5){
            Bonus=(long) ((float)(Config.combo)/Config.PercentScCB*ScoreBall);
        }
//        System.out.println("bonus: "+ScoreBall);
        ScoreBall+=Bonus;
        Label lb = new Label(""+ScoreBall,new Label.LabelStyle(BitmapFontC.FontScore,null));
        lb.setFontScale(1.5f);
        lb.setOrigin(Align.center);
        gr.addActor(lb);
        gr.setPosition(x,y);
//        System.out.println("bonus2: "+ScoreBall);
        Config.Score+=ScoreBall;

        this.header.updateSc(Config.Score);
        gr.addAction(Actions.sequence(
                Actions.moveBy(0,-5,0.8f),
                GSimpleAction.simpleAction((d,a)->{
                    gr.clear();
                    gr.remove();
                    return true;
                })
        ));
    }
    public void CountScoreDrop(float x, float y){
        if(checkLbDrop==false)
        {
            SoundEffect.Play(SoundEffect.drop1);
            checkLbDrop=true;
            Group gr = new Group();
            GStage.addToLayer(GLayer.top,gr);
            long Bonus=0;
            if(Config.combo>=5){
                float per=(float)Config.combo/Config.PercentScCB;
                Bonus= (long) (per*Config.ScBallDrop);
            }
            ScoreBallDrop += (Config.ScBallDrop+Bonus);
            lbDrop = new Label(""+ScoreBallDrop,new Label.LabelStyle(BitmapFontC.FontScore,null));
            lbDrop.setFontScale(2f);
            lbDrop.setOrigin(Align.center);
            lbDrop.setAlignment(Align.center);
            lbDrop.setPosition(0,0);
            gr.addActor(lbDrop);
            gr.setPosition(x,y);
            gr.addAction(Actions.sequence(
                    Actions.moveBy(0,-5,1f),
                    GSimpleAction.simpleAction((d,a)->{
                        checkLbDrop=false;
                        Config.Score+=ScoreBallDrop;
                        this.header.updateSc(Config.Score);
                        ScoreBallDrop=0;
                        gr.clear();
                        gr.remove();
                        return true;
                    })
            ));
        }else {
            SoundEffect.Play(SoundEffect.drop2);
            long Bonus=0;
            if(Config.combo>=5){
                float per=(float)Config.combo/Config.PercentScCB;
                Bonus= (long) (per*Config.ScBallDrop);

            }
            ScoreBallDrop += (Config.ScBallDrop+Bonus);
            lbDrop.setText(""+ScoreBallDrop);
        }

    }
    private void Combo(){
        if(Config.combo==1){
            Image frmCombo = GUI.createImage(TextureAtlasC.Boardgame,"frmCombo");
            frmCombo.setPosition(0,0);
            frmCombo.setOrigin(Align.center);
            grCombo.addActor(frmCombo);
            grCombo.setSize(frmCombo.getWidth(),frmCombo.getHeight());
            grCombo.setOrigin(Align.center);
            grCombo.setPosition(GStage.getWorldWidth()-frmCombo.getWidth()*0.7f,GStage.getWorldHeight()-frmCombo.getHeight()*0.7f,Align.center);
            ////// label combo ///////
            Label lb = new Label("combo",new Label.LabelStyle(BitmapFontC.FontGray,null));
            lb.setFontScale(0.6f);
            lb.setOrigin(Align.center);
            lb.setAlignment(Align.center);
            lb.setPosition(frmCombo.getX()+frmCombo.getWidth()/2,lb.getPrefHeight()/3,Align.center);
            grCombo.addActor(lb);

            frmCombo.setScale(0,1);

            frmCombo.addAction(Actions.sequence(
                    Actions.scaleTo(1,1,0.1f),
                    GSimpleAction.simpleAction((d,a)->{
                        lbCombo = new Label(""+Config.combo,new Label.LabelStyle(BitmapFontC.FontScore,null));
                        lbCombo.setAlignment(Align.center);
                        lbCombo.setOrigin(Align.center);
                        lbCombo.setPosition(frmCombo.getX()+frmCombo.getWidth()/2,frmCombo.getY()+frmCombo.getHeight()*0.38f,Align.center);
                        grCombo.addActor(lbCombo);
                        return true;
                    })
            ));
        }else {
            if(lbCombo!=null){
                lbCombo.setText(""+Config.combo);
                grCombo.addAction(Actions.sequence(
                        Actions.scaleBy(0.1f,0.1f,0.05f),
                        Actions.scaleBy(-0.1f,-0.1f,0.05f)
                ));
            }
        }


    }
    private void ShotBig(int quantity,float x, float y){
        if(quantity>=Config.Wow && quantity<Config.Crazy){
            SoundEffect.Play(SoundEffect.wow);
            bonusSc(x,y+100,Config.ScoreWow);
            AniFireBall(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,grWow);
        }
        if(quantity>=Config.Crazy && quantity<Config.Fabulous){
            SoundEffect.Play(SoundEffect.crazy);
            bonusSc(x,y+100,Config.ScoreCrazy);
            AniFireBall(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,grCrazy);
        }
        if(quantity>=Config.Fabulous){
            SoundEffect.Play(SoundEffect.fabulous);
            bonusSc(x,y+100,Config.ScoreFabulous);
            AniFireBall(GStage.getWorldWidth()/2,GStage.getWorldHeight()/2,grFabulous);
        }
    }
    private void createExpecial(String type,Group gr){
        Image img = GUI.createImage(TextureAtlasC.Boardgame,type);
        img.setRotation(15);
        img.setScale(3);
        img.setOrigin(Align.center);
        img.setPosition(0,0,Align.center);
        gr.addActor(img);
        gr.setVisible(false);
    }

    private void AniFireBall(float x,float y,Group gr){

        gr.setVisible(true);
        gr.setPosition(x,y-100,Align.center);
        gr.addAction(Actions.sequence(
                Actions.scaleTo(0.4f,0.4f,0.2f),
                Actions.moveBy(0,-200,1f,Interpolation.swingIn),
                GSimpleAction.simpleAction((d,a)->{
                    gr.setVisible(false);
                    return true;
                })
        ));
    }
    private void bonusSc(float x,float y,long ScBonus){
        Group gr = new Group();
        GStage.addToLayer(GLayer.top,gr);
        Label lb = new Label(""+ScBonus,new Label.LabelStyle(BitmapFontC.FontScore,null));
        lb.setFontScale(1f);
        lb.setOrigin(Align.center);
        lb.setAlignment(Align.center);
        lb.setPosition(0,0,Align.center);
        gr.addActor(lb);
        gr.setPosition(x,y);
        gr.addAction(Actions.sequence(
                Actions.scaleTo(2.5f,2.5f,0.7f),
                GSimpleAction.simpleAction((d,a)->{
                    checkLbDrop=false;
                    Config.Score+=ScBonus;
                    this.header.updateSc(Config.Score);
                    gr.clear();
                    gr.remove();
                    return true;
                })
        ));
    }
    private void findClusters3(Array<Array<BallGrid>> input, BallGrid ball,int row, int col){
        if (row < 0 || row >= input.size || col < 0 || col >= input.get(row).size)
            return;
        if(input==null)
            return;
        if (input.get(row).get(col)==null)
            return;
        for (BallGrid p: arrBallColor){
            if(p.row==row && p.col==col)
                return;
        }
        //if(ball.compare(input.get(row).get(col))){
        arrBallColor.add(input.get(row).get(col));
        int even=0,odd=0;
        if(arrGridBall.get(row).get(col).rowShift){
            odd=1;
        }else {
            even=1;
        }
        findClusters3(input,ball,row-1,col-even);
        findClusters3(input,ball,row-1,col+odd);
        findClusters3(input,ball,row,col+1);
        findClusters3(input,ball,row,col-1);
        findClusters3(input,ball,row+1,col+odd);
        findClusters3(input,ball,row+1,col-even);

    }
    private void findClusters4(Array<Array<BallGrid>> input, BallGrid ball,int row, int col){
        if (row < 0 || row >= input.size || col < 0 || col >= input.get(row).size)
            return;
        if(input==null)
            return;
        if (input.get(row).get(col)==null)
            return;
        for (BallGrid p: arrBallBomb){
            if(p.row==row && p.col==col)
                return;
        }
        //if(ball.compare(input.get(row).get(col))){
        arrBallBomb.add(input.get(row).get(col));
        int even=0,odd=0;
        if(arrGridBall.get(row).get(col).rowShift){
            odd=1;
        }else {
            even=1;
        }
        findClusters4(input,ball,row,col+1);
        findClusters4(input,ball,row-1,col-even);
        findClusters4(input,ball,row,col-1);
        findClusters4(input,ball,row+1,col+odd);
        findClusters4(input,ball,row+1,col-even);
        findClusters4(input,ball,row-1,col+odd);
    }
    public void ChangeColor(BallGrid ball){
        try {
            arrBallColor.clear();
            findClusters3(arrGridBall,ball,ball.row,ball.col);
            if(arrBallColor.size!=0){
                int connt=0;
                int type = (int)(Math.random()*5)+1;
                Config.typeChangeColor = type;
                for (BallGrid p: arrBallColor){
                    connt++;
                    if(connt<=25&&p.gr.getY()>0){
                        p.changeColor(type);
                    }
                }
            }
        }catch (Exception e){

        }

//        this.board.checkBallBusy=true;
    }
    public void SkillBomb(BallGrid ball){
//        SoundEffect.Play(SoundEffect.)
        arrBallBomb.clear();
        findClusters4(arrGridBall,ball,ball.row,ball.col);
        if(arrBallBomb.size!=0){
            ShotBig(arrBallBomb.size,ball.gr.getX(),ball.gr.getY());
            CountScore(arrBallSame.size,ball.gr.getX(),ball.gr.getY());
            for (BallGrid p :arrBallBomb){
                if(p.gr.getY()>0){
                    conntBomb++;
                    arrGridBall.get(p.row).set(p.col,null);
                    Tweens.setTimeout(grParticle,0.01f*arrBallBomb.indexOf(p,true),()->{
                        p.destroy(2);
                    });
                }
            }
            Tweens.setTimeout(grGrid,0.01f*(conntBomb),()->{
                DropBall();
            });

        }
    }
    private void GameOver(String type) {
        ismoveGrid = true;
        this.board.setTouch(Touchable.disabled);
        Group gr = new Group();
        GStage.addToLayer(GLayer.top, gr);
        Label lb = new Label(type, new Label.LabelStyle(BitmapFontC.FontRed, null));
        lb.setPosition(0, 0, Align.center);
        gr.addActor(lb);
        gr.setScale(1.5f);
        gr.setOrigin(Align.center);
        gr.setPosition(GStage.getWorldWidth() / 2, GStage.getWorldHeight() / 2);
        gr.addAction(Actions.sequence(
            Actions.moveBy(0, -GStage.getWorldHeight()*0.6f, 1.5f, Interpolation.swingIn),
            Actions.delay(1),
            GSimpleAction.simpleAction((d,a)->{
                this.gameScene.setScreen(new GameOverScene());
                gr.clear();;
                gr.remove();
                return true;
            })

        ));

    }


}
