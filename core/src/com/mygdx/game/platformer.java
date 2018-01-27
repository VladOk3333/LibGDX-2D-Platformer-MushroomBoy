package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import static com.badlogic.gdx.utils.JsonValue.ValueType.object;
import com.mygdx.game.screens.PlayScreen;

public class platformer extends Game {
        //Размеры экрана в пикселях
        public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 210;
        public static final float PPM = 50;

        public SpriteBatch batch;
        
        public static AssetManager manager;
        
	
	@Override
	public void create () { 
            batch = new SpriteBatch();
            manager = new AssetManager();
            manager.load("Hiked Up Tube Socks.mp3", Music.class);
            manager.load("jump.mp3", Sound.class);
            manager.load("mariodie.wav", Sound.class);
            manager.load("win.wav", Sound.class);
            manager.finishLoading();
            
            setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
            super.render();
	}
	
	@Override
	public void dispose () {
                super.dispose();
                manager.dispose();
		batch.dispose();
	}

   
}
