/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.platformer;
import com.mygdx.game.screens.PlayScreen;
import static java.lang.Math.abs;

/**
 *
 * @author Влад
 */
public class PlatformDifficult extends Platform{

     private float stateTime;
    private float spawnX;
    private float spawnY;
    private float currentX;
    private float currentY;
    private velocityState currentState;
    private float lastX;
    private float lastY;
    
    public PlatformDifficult(PlayScreen screen, float x, float y){
        super(screen, x, y);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / platformer.PPM, 16 / platformer.PPM);
        spawnY = b2body.getPosition().y;
        spawnX = b2body.getPosition().x;
        currentState = velocityState.HORIZONTAL;
        
    }
    
    public void update(float dt){
       // currentState = getState();
        stateTime = dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
       b2body.setLinearVelocity(velocity);
       if(abs(b2body.getPosition().x - spawnX) > 1.8){
                velocity.set(0, 0.5f);
                if(b2body.getPosition().y - spawnY > 1.8){
                     velocity.set(0.5f, 0);
                     if(b2body.getPosition().x - spawnX > 3.6 && b2body.getPosition().y - spawnY < 3.6){
                         velocity.set(0, 0.5f);
                         
                     }
                     else{
                         
                             velocity.set(-0.5f, -0.5f);}
                     
                }
                
       }
       
        
        
        
       /* 
        
        switch(currentState){
            case HORIZONTAL:
                if(abs(b2body.getPosition().x - spawnX) > 1.8){
                velocity.set(0, 0.5f);
                b2body.setLinearVelocity(velocity);
                }
                break;
            case VERTICAL:
                if(abs(b2body.getPosition().y - spawnY) > 1.8){
                velocity.set(0.5f, 0);
                b2body.setLinearVelocity(velocity);
                }
                 if(abs(b2body.getPosition().x - spawnX) > 3.6){
                      velocity.set(-0.5f, 0);
                      b2body.setLinearVelocity(velocity);
                 }
                break;
            case DIAGONAL:
                if(abs(b2body.getPosition().x - spawnX) != 0){
                    velocity.set(0, 0);
                    b2body.setLinearVelocity(velocity);
                }
                break;
            default:
                break;
        }
        
        */
        
        
        /*
        
        if((int)b2body.getLinearVelocity().x == 0)
            reverseVelocity(true, false);
        currentY = b2body.getPosition().y;
        currentX = b2body.getPosition().x;
        lastX = spawnX;
        lastY = spawnY;
        */
 
        /*
        if(stateTime > 0.05f){
            velocity.set(0, 0.5f);
            
        }
        if(stateTime > 0.09f){
            velocity.set(0.5f, 0);
        }*/
        /*
        if(abs(currentX - lastX) > 1.8)
        {
            lastY = currentY;
            lastX = currentX;
            velocity.set(0, 0.5f);
            stateTime = dt;
        }
        
        if(abs(b2body.getPosition().y - spawnY) > 1.8){
            
            lastY = currentY;
            lastX = currentX;
            velocity.set(0.5f, 0);
        
        }*/
    
 
        }
    
     public velocityState getState(){
        if(b2body.getLinearVelocity().x > 0)
            return velocityState.HORIZONTAL;
        else if(b2body.getLinearVelocity().y > 0)
            return velocityState.VERTICAL;
        else
            return velocityState.DIAGONAL;
    }
   
    
    
    @Override
    protected void definePlatform(float x, float y) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / platformer.PPM , y / platformer.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);
       
        FixtureDef fdef = new FixtureDef();
        fdef.friction = 3f;
        PolygonShape shape = new PolygonShape();
        Rectangle rect = new Rectangle();
        rect.height = 6 / platformer.PPM;
        rect.width = 15 / platformer.PPM;
        shape.setAsBox((rect.getWidth()), (rect.getHeight() / 2));
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    @Override
    protected void setVelocity() {
        velocity = new Vector2(0.5f, 0);
    }
    
}
