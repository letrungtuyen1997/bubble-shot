package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;
import com.ss.GMain;

import java.util.Locale;
import java.util.MissingResourceException;

public class C {

    public static class remote {
        public static int adsTime = 50;
        static void initRemoteConfig() {

        }
    }

    public static class lang {
        private static I18NBundle locale;
        public static String title = "";
        public static String adsTimeLbl = "";
        public static String idcontry = "";
        public static String  gameover= "";
        public static String  timeup= "";
        public static String status = "";
        public static String status1 = "";
        public static String status2 = "";
        public static String lbYsc = "";
        public static String lbHsc = "";
        public static String lbBonus = "";
        public static String lbReward = "";
        public static String lbBtnRed = "";
        public static String lbAchiment = "";
        public static String lbComplete = "";
        public static String scoreBonus= "";
        public static String boostSkill= "";




        static void initLocalize() {
            String deviceLang = GMain.platform.GetDefaultLanguage();
            System.out.println("language: "+deviceLang);
            FileHandle specFilehandle = Gdx.files.internal("i18n/lang_" + deviceLang);
            FileHandle baseFileHandle = Gdx.files.internal("i18n/lang");

            try {
                locale = I18NBundle.createBundle(specFilehandle, new Locale(""));
//                idcontry = locale.get("idcontry");
                System.out.println("tryyyyyyyyy");
            }
            catch (MissingResourceException e) {
                locale = I18NBundle.createBundle(baseFileHandle, new Locale(""));
//                idcontry = locale.get("icontry");
                System.out.println("cuuuuuuuu");
            }

            title = locale.get("title");
            adsTimeLbl = locale.format("adsTime", remote.adsTime);
            gameover = locale.get("gameover");
            timeup = locale.get("timeup");
            status = locale.get("status");
            status1 = locale.get("status1");
            status2 = locale.get("status2");
            lbYsc = locale.get("lbYsc");
            lbHsc = locale.get("lbHsc");
            lbBonus = locale.get("lbBonus");
            lbReward = locale.get("lbReward");
            lbBtnRed = locale.get("lbBtnRed");
            lbAchiment = locale.get("lbAchiment");
            lbComplete = locale.get("lbComplete");
            idcontry = locale.get("idcontry");
            scoreBonus = locale.get("scoreBonus");
            boostSkill = locale.get("boostSkill");
        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}
