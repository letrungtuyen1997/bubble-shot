package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.ss.GMain;
import com.ss.core.util.GStage;
import com.ss.gameLogic.objects.LoadLv;

public class Config {
    public static float ScreenW = GStage.getWorldWidth();
    public static float ScreenH = GStage.getWorldHeight();
    public static boolean checkConnet = true;
    public static boolean checkWheel = false;
    public static float paddingY=90;
    public static float BALL_W=80;
    public static float BALL_H=80;
    public static float BALL_RADIUS = BALL_W/2;
    public static float BALL_DIAMETER = BALL_RADIUS*2;
    public static float BALL_SPEED=70;
    public static float Dr_Ball_Cre=0.03f;
    public static float ROW_HEGHT = (float)(BALL_DIAMETER * Math.sin(Math.PI/3));
    public static int quantityBall =9;
    public static float GridMove=1f;
    public static float SpeedX=0;
    public static float SpeedY=0;
    public static float bounce =-100;
    public static float Speed_Drop=15;
    public static float draBounce=0.2f;
    public static float SpeedGrid=5;
    public static float vibrate=10;
    public static float duravibrate=0.2f;
    public static long ScBall=100;
    public static long ScBallDrop=200;
    public static long PercentScCB=50;
    public static long Score=0;
    public static long ScoreWow=5000;
    public static long ScoreCrazy=10000;
    public static long ScoreFabulous=20000;
    public static int combo=0;
    public static int rowDrop=0;
    public static int Wow=8;
    public static int Crazy=12;
    public static int Fabulous=16;
    public static int FireBall=6;
    public static int ChangeColor=7;
    public static int Bomb=9;
    public static int timefireBall=5;
    public static int typeChangeColor=0;
    public static int timeSkillColor=5;
    public static int pecentSkillColor=3;
    public static int timeSkillClock=5;
    public static int pecentSkillClock=5;
    public static int timeSkillBomb=10;
    public static int pecentSkillBomb=10;
    public static int BonusTime=5;
    public static float ballApear = -ROW_HEGHT*5;
    public static int ballType=1;
    public static long monney = GMain.prefs.getLong("Monney",2000);
    public static boolean checkSkillTime = false;
    public static boolean checkSkillColor = false;
    public static boolean checkSkillBomb = false;
    public static int     indexMusic = 0;
    public static String OTHER_GAME_STRING=GMain.platform.GetConfigStringValue("crosspanel", Gdx.files.internal("data/other_games.json").readString());


    //////////////////// Achivement /////////////////////////////

    public static int BubbleMonment = GMain.prefs.getInteger("bubbleMM",0);
    public static long highScMomnet = GMain.prefs.getLong("HighSc",0);
    public static int comboMoment = GMain.prefs.getInteger("comboMM");
    public static int levelMoment = GMain.prefs.getInteger("level",0);
    public static int coinMonment = GMain.prefs.getInteger("coinsMM",0);
    public static int wowMoment = GMain.prefs.getInteger("wowMM",0);
    public static int crazyMoment = GMain.prefs.getInteger("crazyMM",0);
    public static int fanbulousMoment = GMain.prefs.getInteger("fanblMM",0);

    public static LoadLv LoadLv(int id){
        LoadLv info ;
        Json js = new Json();
        switch(id){
            case 1:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 2:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,6,7]}");
                break;
            }
            case 3:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,5,6,7]}");
                break;
            }
            case 4:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,6,7]}");
                break;
            }
            case 5:{
                info = js.fromJson(LoadLv.class,"{lv:[2,6]}");
                break;
            }
            case 6:{
                info = js.fromJson(LoadLv.class,"{lv:[3,6]}");
                break;
            }
            case 7:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5,6]}");
                break;
            }
            case 8:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5,6,7]}");
                break;
            }
            case 9:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,5,6,7]}");
                break;
            }
            case 10:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,6,7]}");
                break;
            }
            case 11:{
                info = js.fromJson(LoadLv.class,"{lv:[2,6]}");
                break;
            }
            case 12:{
                info = js.fromJson(LoadLv.class,"{lv:[2,7]}");
                break;
            }
            case 13:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6]}");
                break;
            }
            case 14:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5,6]}");
                break;
            }
            case 15:{
                info = js.fromJson(LoadLv.class,"{lv:[2,6]}");
                break;
            }
            case 16:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,6,7]}");
                break;
            }
            case 17:{
                info = js.fromJson(LoadLv.class,"{lv:[2,6]}");
                break;
            }
            case 18:{
                info = js.fromJson(LoadLv.class,"{lv:[3,6]}");
                break;
            }
            case 19:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5,6]}");
                break;
            }
            case 20:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5,6,7]}");
                break;
            }
            case 21:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,5,6,7]}");
                break;
            }
            case 22:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,6,7]}");
                break;
            }
            case 23:{
                info = js.fromJson(LoadLv.class,"{lv:[2,6]}");
                break;
            }
            case 24:{
                info = js.fromJson(LoadLv.class,"{lv:[2,7]}");
                break;
            }
            case 25:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 26:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6]}");
                break;
            }
            case 27:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2]}");
                break;
            }
            case 28:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 29:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 30:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 31:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 32:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4]}");
                break;
            }
            case 33:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4]}");
                break;
            }
            case 34:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5]}");
                break;
            }
            case 35:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5]}");
                break;
            }
            case 36:{
                info = js.fromJson(LoadLv.class,"{lv:[3,4,5,6]}");
                break;
            }
            case 37:{
                info = js.fromJson(LoadLv.class,"{lv:[3,4,5,6]}");
                break;
            }
            case 38:{
                info = js.fromJson(LoadLv.class,"{lv:[4,5,6,7]}");
                break;
            }
            case 39:{
                info = js.fromJson(LoadLv.class,"{lv:[4,5,6,7]}");
                break;
            }
            case 40:{
                info = js.fromJson(LoadLv.class,"{lv:[5,6,7,8]}");
                break;
            }
            case 41:{
                info = js.fromJson(LoadLv.class,"{lv:[5,6,7,8]}");
                break;
            }
            case 42:{
                info = js.fromJson(LoadLv.class,"{lv:[6,7,8]}");
                break;
            }
            case 43:{
                info = js.fromJson(LoadLv.class,"{lv:[5,6,7,8]}");
                break;
            }
            case 44:{
                info = js.fromJson(LoadLv.class,"{lv:[5,6,7,8]}");
                break;
            }
            case 45:{
                info = js.fromJson(LoadLv.class,"{lv:[4,5,6,7]}");
                break;
            }
            case 46:{
                info = js.fromJson(LoadLv.class,"{lv:[4,5,6,7]}");
                break;
            }
            case 47:{
                info = js.fromJson(LoadLv.class,"{lv:[3,4,5,6]}");
                break;
            }
            case 48:{
                info = js.fromJson(LoadLv.class,"{lv:[3,4,5,6]}");
                break;
            }
            case 49:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5]}");
                break;
            }
            case 50:{
                info = js.fromJson(LoadLv.class,"{lv:[2,3,4,5]}");
                break;
            }
            case 51:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4]}");
                break;
            }
            case 52:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4]}");
                break;
            }
            case 53:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 54:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3]}");
                break;
            }
            case 55:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 56:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 57:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 58:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 59:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 60:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
            case 61:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,6]}");
                break;
            }
            case 62:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,6,7]}");
                break;
            }
            case 63:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,5,6,7]}");
                break;
            }
            case 64:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7,8]}");
                break;
            }
            case 65:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 66:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,6,7]}");
                break;
            }
            case 67:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,6,7]}");
                break;
            }
            case 68:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 69:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 70:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,5,6,7]}");
                break;
            }
            case 71:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,6,7]}");
                break;
            }
            case 72:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,5,6,7]}");
                break;
            }
            case 73:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 74:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            case 75:{
                info = js.fromJson(LoadLv.class,"{lv:[1,2,3,4,5,6,7]}");
                break;
            }
            default:{
                info = js.fromJson(LoadLv.class,"{lv:[0,1,2,3,4,5,6,7,8]}");
                break;
            }
        }
        return info;
    }
    public static void loadjson(){
        FileHandle js = Gdx.files.internal("data/ConfigData.json");
        String jsonStr = js.readString();
//        JsonReader json = new JsonReader();
//        JsonValue jv = json.parse(jsonStr);
        String jv2 =GMain.platform.GetConfigStringValue("config",jsonStr);
        System.out.println("log: "+jv2);
        JsonReader json = new JsonReader();
        JsonValue jv = null;
        try {
            jv = json.parse(jv2);
            System.out.println("log:"+jv.get("BALL_SPEED").asInt());
        }catch (Exception e){
            jv = json.parse(jsonStr);
        }
        BALL_SPEED          = jv.get("BALL_SPEED").asInt();
        Dr_Ball_Cre         = jv.get("Dr_Ball_Cre").asFloat();
        GridMove            = jv.get("GridMove").asFloat();
        bounce              = jv.get("bounce").asFloat();
        Speed_Drop          = jv.get("Speed_Drop").asFloat();
        draBounce           = jv.get("draBounce").asFloat();
        SpeedGrid           = jv.get("SpeedGrid").asFloat();
        vibrate             = jv.get("vibrate").asFloat();
        duravibrate         = jv.get("duravibrate").asFloat();
        ScBall              = jv.get("ScBall").asLong();
        ScBallDrop          = jv.get("ScBallDrop").asLong();
        PercentScCB         = jv.get("PercentScCB").asLong();
        ScoreWow            = jv.get("ScoreWow").asLong();
        ScoreCrazy          = jv.get("ScoreCrazy").asLong();
        ScoreFabulous       = jv.get("ScoreFabulous").asLong();
        Wow                 = jv.get("Wow").asInt();
        Crazy               = jv.get("Crazy").asInt();
        Fabulous            = jv.get("Fabulous").asInt();
        timeSkillColor      = jv.get("timeSkillColor").asInt();
        pecentSkillColor    = jv.get("pecentSkillColor").asInt();
        timeSkillClock      = jv.get("timeSkillClock").asInt();
        pecentSkillClock    = jv.get("pecentSkillClock").asInt();
        timeSkillBomb       = jv.get("timeSkillBomb").asInt();
        pecentSkillBomb     = jv.get("pecentSkillBomb").asInt();
        BonusTime           = jv.get("BonusTime").asInt();
        timefireBall        = jv.get("timefireBall").asInt();
    }

}

