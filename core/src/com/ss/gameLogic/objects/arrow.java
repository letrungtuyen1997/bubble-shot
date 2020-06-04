package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;

public class arrow {
    private boolean checkRec=false;
    private Array<Image> dotleft= new Array<>();
    private Array<Image> dotright= new Array<>();
    private Array<Image> dotcenter= new Array<>();
    arrow(float ball_x, float ball_y, float mouse_x, float mouse_y,Group group,int id){

        Double deg = Math.atan2(ball_y-mouse_y,mouse_x-ball_x);
        for (int i=1;i<3;i++){
            String type="light"+id;
            if(i==2)
                type="LightDot"+id;
            Image dot = GUI.createImage(TextureAtlasC.Boardgame,type);
            float x0=0,y0=0;
            x0 = mouse_x - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/4)*i)*(float) Math.cos(deg);
            y0 = mouse_y - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/4)*i)*(float) Math.sin(deg);
            dot.setPosition(x0,y0,Align.center);
            group.addActor(dot);
            dotright.add(dot);
        }

        for (int i=1;i<3;i++){
            String type="light"+id;
            if(i==2)
                type="LightDot"+id;
            Image dot = GUI.createImage(TextureAtlasC.Boardgame,type);
            float x0=0,y0=0;
            x0 = mouse_x - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/4)*i)*(float) Math.cos(deg);
            y0 = mouse_y - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/4)*i)*(float) Math.sin(deg);
            dot.setPosition(x0,y0,Align.center);
            group.addActor(dot);
            dotleft.add(dot);
        }


        for (int i=1;i<9;i++){
            String type="light"+id;
            int dec=7;
            if(i==8){
                type = "LightDot"+id;
                dec=7;
            }

            Image dot = GUI.createImage(TextureAtlasC.Boardgame,type);
            float x=0,y=0;
            x = ball_x + ((Distance(ball_x,ball_y,mouse_x,mouse_y)/dec)*i)*(float) Math.cos(deg);
            y= ball_y - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/dec)*i)*(float) Math.sin(deg);
            dot.setPosition(x-dot.getWidth()/2,y-dot.getHeight()/2);
            group.addActor(dot);
            dotcenter.add(dot);

        }
        setVisible(dotleft,false);
        setVisible(dotright,false);

    }
    public void SetArrow(float ball_x, float ball_y, float mouse_x, float mouse_y){
        boolean check=false;
        Double deg = Math.atan2(ball_y-mouse_y,mouse_x-ball_x);
        if(mouse_x>= GStage.getWorldWidth()-50){
            check=true;
            setVisible(dotright,true);
            setVisible(dotleft,false);
            for (int i=0; i<dotright.size;i++){
                float x0=0,y0=0;
                x0 = mouse_x - ((Distance(ball_x,ball_y,mouse_x- Config.BALL_RADIUS,mouse_y- Config.BALL_RADIUS)/4)*(i+1))*(float) Math.cos(deg);
                y0 = mouse_y - ((Distance(ball_x,ball_y,mouse_x- Config.BALL_RADIUS,mouse_y- Config.BALL_RADIUS)/4)*(i+1))*(float) Math.sin(deg);
                dotright.get(i).setPosition(x0,y0,Align.center);

            }
        }else if(mouse_x<=50){
            setVisible(dotright,false);
            setVisible(dotleft,true);
            check=true;
            for (int i=0; i<dotleft.size;i++){
                float x0=0,y0=0;
                x0 = mouse_x - ((Distance(ball_x,ball_y,mouse_x- Config.BALL_RADIUS,mouse_y- Config.BALL_RADIUS)/4)*(i+1))*(float) Math.cos(deg);
                y0 = mouse_y - ((Distance(ball_x,ball_y,mouse_x- Config.BALL_RADIUS,mouse_y- Config.BALL_RADIUS)/4)*(i+1))*(float) Math.sin(deg);
                dotleft.get(i).setPosition(x0,y0,Align.center);

            }


        }
        if(check==false){
            setVisible(dotright,false);
            setVisible(dotleft,false);
        }
        for(int i=0;i<dotcenter.size;i++){
            int dec=8;
            if(i==7&&check==true){

                dotcenter.get(i).setVisible(false);
                dec=7;
            }else {
                dotcenter.get(i).setVisible(true);
            }

            float x=0,y=0;
            x = ball_x + ((Distance(ball_x,ball_y,mouse_x,mouse_y)/dec)*i)*(float) Math.cos(deg);
            y= ball_y - ((Distance(ball_x,ball_y,mouse_x,mouse_y)/dec)*i)*(float) Math.sin(deg);
            dotcenter.get(i).setPosition(x,y,Align.center);
        }
    }
    void setVisible(Array<Image> arr,boolean set){
        for(Image img : arr)
            img.setVisible(set);
    }
    private float Distance(float x1,float y1, float x2, float y2){
        float doi = y1-y2;
        float ke  = Math.abs(x1-x2);
        return (float) Math.sqrt((ke*ke)+(doi*doi));

    }



}
