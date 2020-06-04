package com.ss.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.commons.TextureAtlasC;
import com.ss.core.util.GAssetsManager;

public class effectWin extends Actor{

    FileHandle Destroy = Gdx.files.internal("effects/destroy");
    FileHandle FireBall = Gdx.files.internal("effects/fireBall");
    FileHandle Bomb = Gdx.files.internal("effects/bomb");
    FileHandle star = Gdx.files.internal("effects/star");
    public ParticleEffect effect;
    public ParticleEffectPool effectPool;
    public ParticleEffectPool.PooledEffect pooledEffect;
    private Actor parent = this.parent;
    private Group group;
    private Array<Sprite> arrSprite= new Array<>();
    public boolean isAlive = false;

    public effectWin(int id, float f, float f2, Group group) {
        this.group = group;
        this.effect = new ParticleEffect();
        this.effectPool = new ParticleEffectPool(effect,0,100);
        this.pooledEffect = effectPool.obtain();
        setX(f);
        setY(f2);
            if(id==1){

                this.effect.load(Destroy, TextureAtlasC.effectAtlas);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
                arrSprite.addAll(this.effect.getEmitters().get(0).getSprites());
//                System.out.println("check2: "+this.effect.getEmitters().get(0).getSprites().size);
//                System.out.println("check3: "+arrSprite.size);


//                this.effect.getEmitters().get(0).getSprites().swap(0,(id2-1));
                this.effect.scaleEffect(1.5f);

            }else if(id==2){

                this.effect.load(FireBall, TextureAtlasC.effectAtlas);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
//                this.effect.getEmitters().get(0).getSprites().swap(0,(id2-1));
                this.effect.scaleEffect(1f);

            }else if(id==3){

                this.effect.load(Bomb, TextureAtlasC.effectAtlas);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
//                this.effect.getEmitters().get(0).getSprites().swap(0,(id2-1));
                this.effect.scaleEffect(1f);

            }else if(id==4){

                this.effect.load(star, TextureAtlasC.effectAtlas);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
//                this.effect.getEmitters().get(0).getSprites().swap(0,(id2-1));
                this.effect.scaleEffect(1.2f);

            }



        this.effect.setPosition(f, f2);


    }
    public void resetSprites(){
       // for (int i = 0; i < this.effect.getEmitters().size; i++) {
            this.effect.getEmitters().get(0).getSprites().clear();
            this.effect.getEmitters().get(0).getSprites().addAll(arrSprite);

       // }
    }
    public void changeSprites(int id){
        resetSprites();
//        this.effect.getEmitters().get(0).getSprites().swap(0,(id-1));
//        System.out.println("check: "+this.effect.getEmitters().get(0).getSprites().size);
        if(this.effect.getEmitters().get(0).getSprites()!=null)
            this.effect.getEmitters().get(0).getSprites().swap(0,(id-1));

    }
    public void changeEffect(int id){

    }
    public void SetPosition(float x,float y){
        this.effect.setPosition(x,y);
    }

    @Override
    public boolean remove() {
        if(pooledEffect!=null)
            pooledEffect.free();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.effect.setPosition(getX(), getY());
        this.effect.update(delta);
        if(this.effect.isComplete()){
            remove();
            isAlive=false;
        }
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!this.effect.isComplete()) {
            this.effect.draw(batch);
            return;
        }
        this.effect.dispose();
    }


    public void setScale(float ratio){
        this.effect.scaleEffect(ratio);
    }

    public void setScale(float ratioX, float ratioY){
        this.effect.scaleEffect(ratioX, ratioY);
    }

    public void start() {
        isAlive =true;
        this.group.addActor(this);
        this.effect.start();
    }
}
