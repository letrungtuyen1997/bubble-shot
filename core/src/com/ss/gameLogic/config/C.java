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
        public static String  gameover= "";
        public static String  timeup= "";



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
        }
    }

    public static void init() {
        remote.initRemoteConfig();
        lang.initLocalize();
    }
}
