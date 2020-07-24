package com.ss.effects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
  public static int MAX_COMMON = 38;
  public static Music bgSound = null;
  public static Music bgSound2 = null;
  public static Music bgSound3 = null;
  public static Sound[] commons = null;
  public static boolean music = false;
  public static boolean mute = false;

  public static int shot = 0;
  public static int colision = 1;
  public static int corect= 2;
  public static int snap = 3;
  public static int lose = 4;
  public static int vutmat = 5;
  public static int drop1 = 6;
  public static int drop2 = 7;
  public static int wow = 8;
  public static int crazy = 9;
  public static int fabulous= 10;
  public static int fireball = 11;
  public static int fireFly = 12;
  public static int go = 13;
  public static int ready = 14;
  public static int timeDown = 15;
  public static int ScUp = 16;
  public static int ExpUp = 17;
  public static int MonneyUp = 18;
  public static int Bomb = 19;
  public static int ChangeColor = 20;
  public static int addTime = 21;
  public static int levelup = 22;
  public static int gameover = 23;
  public static int mission = 24;
  public static int panel_out = 25;
  public static int Win = 26;
  private static Sound[] explode;


  public static void initSound() {
    explode = new Sound[14];
    for (int i = 0; i < 10; i++)
      explode[i] = GAssetsManager.getSound("e" + (i + 1) + ".mp3");
    commons = new Sound[MAX_COMMON];
    commons[shot] = GAssetsManager.getSound("shot.mp3");
    commons[colision] = GAssetsManager.getSound("colisionRec.mp3");
    commons[corect] = GAssetsManager.getSound("corect.mp3");
    commons[snap] = GAssetsManager.getSound("snap.mp3");
    commons[lose] = GAssetsManager.getSound("lose.mp3");
    commons[vutmat] = GAssetsManager.getSound("vutmat.mp3");
    commons[drop1] = GAssetsManager.getSound("ballDrop1.mp3");
    commons[drop2] = GAssetsManager.getSound("ballDrop2.mp3");
    commons[wow] = GAssetsManager.getSound("wow.mp3");
    commons[crazy] = GAssetsManager.getSound("crazy.mp3");
    commons[fabulous] = GAssetsManager.getSound("fabulous.mp3");
    commons[fireball] = GAssetsManager.getSound("fireball.mp3");
    commons[fireFly] = GAssetsManager.getSound("fireFly.mp3");
    commons[go] = GAssetsManager.getSound("go.mp3");
    commons[ready] = GAssetsManager.getSound("ready.mp3");
    commons[timeDown] = GAssetsManager.getSound("timeDown.mp3");
    commons[ScUp] = GAssetsManager.getSound("ScUp.mp3");
    commons[ExpUp] = GAssetsManager.getSound("ExpUp.mp3");
    commons[MonneyUp] = GAssetsManager.getSound("Coin.mp3");
    commons[Bomb] = GAssetsManager.getSound("Bomb.mp3");
    commons[ChangeColor] = GAssetsManager.getSound("ChangeColor.mp3");
    commons[addTime] = GAssetsManager.getSound("UpTime.mp3");
    commons[levelup] = GAssetsManager.getSound("levelup.mp3");
    commons[gameover] = GAssetsManager.getSound("GameOver.mp3");
    commons[mission] = GAssetsManager.getSound("unlockMission.mp3");
    commons[panel_out] = GAssetsManager.getSound("panel_Out.mp3");
    commons[Win] = GAssetsManager.getSound("Win.mp3");
////        commons[coins] = GAssetsManager.getSound("Coin.mp3");
////        commons[coins].setVolume(2,5);
    bgSound = GAssetsManager.getMusic("bg.mp3");
    bgSound2 = GAssetsManager.getMusic("bg2.mp3");

  }

  public static long Play(int i) {
    long id = -1;
    if (!mute) {
      id = commons[i].play();
      commons[0].setVolume(id,2f);
    }
    return id;
  }
  public static void explode(int level) {
    if(!mute)
      explode[(level > 13) ? 13 : level].play();
  }

  public static void Playmusic(int mode) {
    music = false;
    switch (mode) {
      case 1: {
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(1f);
        break;
      }
      case 2: {
        bgSound2.play();
        bgSound2.setLooping(true);
        bgSound2.setVolume(1f);
        break;
      }
      case 3: {
        bgSound3.play();
        bgSound3.setLooping(true);
        bgSound3.setVolume(0.2f);
        break;
      }
      default:{
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(0.2f);
        break;
      }
    }
  }

  public static void Stopmusic(int mode) {
    music = true;
    switch (mode){
      case 1: {
        bgSound.pause();
        break;
      }
      case 2: {
        bgSound2.stop();
        break;
      }
      case 3: {
        bgSound3.pause();
        break;
      }
      default:{
        bgSound.stop();
        break;
      }
    }
  }
}
