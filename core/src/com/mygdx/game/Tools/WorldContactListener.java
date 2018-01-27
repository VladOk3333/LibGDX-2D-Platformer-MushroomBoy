/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Влад
 */
public class WorldContactListener implements ContactListener{
private boolean isGround;
    @Override
    public void beginContact(Contact contact) {
       /*Fixture fixA = contact.getFixtureA();
       Fixture fixB = contact.getFixtureB();
       if(fixA.getUserData()!=null && fixA.getUserData() == "foot" ){
           isGround = true;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "foot" ){
           isGround = true;
       }
        if(fixA.getUserData()!=null && fixA.getUserData() == "rside" ){
           isGround = true;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "rside" ){
           isGround = true;
       }
        if(fixA.getUserData()!=null && fixA.getUserData() == "lside" ){
           isGround = true;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "lside" ){
           isGround = true;
       }*/
        isGround = true;
              
    }

    @Override
    public void endContact(Contact contact) {
       /* Fixture fixA = contact.getFixtureA();
       Fixture fixB = contact.getFixtureB();
       if(fixA.getUserData()!=null && fixA.getUserData() == "foot" ){
           isGround = false;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "foot" ){
           isGround = false;
       }
        if(fixA.getUserData()!=null && fixA.getUserData() == "rside" ){
           isGround = false;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "rside" ){
           isGround = false;
       }
        if(fixA.getUserData()!=null && fixA.getUserData() == "lside" ){
           isGround = false;
       }
       if(fixB.getUserData()!=null && fixB.getUserData() == "lside" ){
           isGround = false;
       }*/
         isGround = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
       
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
    
    public boolean getStatus(){
        return isGround;
    }
    
}
