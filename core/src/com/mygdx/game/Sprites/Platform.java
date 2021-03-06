/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.PlayScreen;

/**
 *
 * @author Влад
 */
public abstract class Platform extends Sprite{
    public enum velocityState {VERTICAL, HORIZONTAL, DIAGONAL};
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    
    public Vector2 velocity;
    
    
    public Platform(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        definePlatform(x ,y);
        setVelocity();
    }
    
    protected abstract void definePlatform(float x, float y);
    public void reverseVelocity(boolean x, boolean y){
       if(x)
           velocity.x = -velocity.x;
       if(y)
           velocity.y = -velocity.y;
    }    
    
    
    protected abstract void setVelocity();
    
}
