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
public class PlatformUpDown extends Platform{

    private float stateTime;
    private float spawnX;
    private float spawnY;
    private float currentX;
    private float currentY;
    
    public PlatformUpDown(PlayScreen screen, float x, float y){
        super(screen, x, y);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / platformer.PPM, 16 / platformer.PPM);
        b2body.setGravityScale(0);
        spawnY = b2body.getPosition().y;
        spawnX = b2body.getPosition().x;
    }
    
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
       
        b2body.setLinearVelocity(velocity);
       /* if((int)b2body.getLinearVelocity().x == 0)
            reverseVelocity(true, false);*/
        currentY = b2body.getPosition().y;
        currentX = b2body.getPosition().x;
        if(abs(currentY - spawnY) > 2.8)
        {
            reverseVelocity(false, true);
            spawnY = currentY;
        }
      /*  if(currentY != spawnY){
            setPosition(b2body.getPosition().x, spawnY);
            b2body.getPosition();
        }*/
       
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
        velocity = new Vector2(0, 0.5f);
    }
    
}
