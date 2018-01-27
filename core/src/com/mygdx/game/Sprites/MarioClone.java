/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.platformer;
import com.mygdx.game.screens.PlayScreen;

/**
 *
 * @author Влад
 */
public class MarioClone extends Sprite{
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public boolean status;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsDead;
    
    
    
    
    
    public MarioClone(PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        world = screen.getWorld();
       
        status = true;
        this.world = world;
        
        currentState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // Анимация бега
        for(int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 11, 16, 16));
            
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();
        //Анимация прыжка
         for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 11, 16, 16));
            
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();
        
        
        
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0, 11, 16, 16);
        setBounds(0, 0, 16 / platformer.PPM, 16 / platformer.PPM);
        setRegion(marioStand);
    }
    
    
    
    
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 );
        setRegion(getFrame(dt));
         System.out.println("X= ");
        System.out.println(b2body.getPosition().x);
        System.out.println("Y= ");
        System.out.println(b2body.getPosition().y);
    }
    
    
    public TextureRegion getFrame(float dt){
        currentState = getState();
        
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default: 
                region = marioStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    
    
    public boolean isDead(){
        return marioIsDead;
    }
    
    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(16 / platformer.PPM ,50 / platformer.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / platformer.PPM);
        //fdef.friction = 3f;
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
        //Создание фикстуры слушателя для определения состояния
       /* EdgeShape foot = new EdgeShape();
        
        foot.set(new Vector2(-1 / platformer.PPM, -7 / platformer.PPM), new Vector2(1 / platformer.PPM, -7 / platformer.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("foot");
        
        EdgeShape lside = new EdgeShape();
        //lside.set(-6, -6, , stateTimer);
        fdef.shape = lside;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("lside");
        
        EdgeShape rside = new EdgeShape();
        rside.setVertex0(new Vector2(6 / platformer.PPM, -5 / platformer.PPM));
        fdef.shape = rside;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("rside");*/
    }
    
    public boolean isGround(){
        if((int)b2body.getLinearVelocity().y == 0){
            status = true;  
        }
        else
        {
            status = false;
        }
        return status;
    }
    
}
