package com.mygdx.game.screens;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.badlogic.gdx.physics.box2d.Shape.Type.Polygon;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.platformer;
import static com.badlogic.gdx.utils.JsonValue.ValueType.object;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.MarioClone;
import com.mygdx.game.Sprites.PlatformCircular;
import com.mygdx.game.Sprites.PlatformDifficult;
import com.mygdx.game.Sprites.PlatformLeftRight;
import com.mygdx.game.Sprites.PlatformUpDown;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

/**
 *
 * @author Влад
 */
public class PlayScreen implements Screen{
    //Переменные Tiled map 
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private platformer game;
    private OrthographicCamera gamecam;
    private FitViewport gamePort;
    private Hud hud;
    
    //Переменные Box2D
    private World world;
    private Box2DDebugRenderer b2dr;
    
    private MarioClone player;
    private PlatformLeftRight platform;
    private PlatformUpDown platformUpDown;
    private PlatformCircular platformCircular;
    private PlatformDifficult platformDifficult;
    private TextureAtlas atlas;
    
    private WorldContactListener listener;
    
    private Music music;
    private Sound jumpSound;
    private Sound dieSound;
    private Sound winSound;
 
    
    private int lastTime;
   
    

    
    
    
   //platfor
    /**
     *
     * @param game
     */
    public PlayScreen(platformer game){
          atlas = new TextureAtlas("Mario_and_Enemies.pack");
          this.game = game; 
          //Создаем камеру следующую за игроком
          gamecam = new OrthographicCamera();
          gamecam.setToOrtho(false, 5 , 5);
          //
          gamePort = new FitViewport(platformer.V_WIDTH / platformer.PPM, platformer.V_HEIGHT / platformer.PPM, gamecam);
          
          //Создаем интерфейс игры с отображением информации
          hud = new Hud(game.batch);
          
          //Загружаем нашу карту и устанавливаем отрисовку карты
          mapLoader = new TmxMapLoader();
          map = mapLoader.load("lvlmap.tmx");
          renderer = new OrthogonalTiledMapRenderer(map, 1 / platformer.PPM);
          renderer.setMap(map);
          
          renderer.setView(gamecam);
          
          //Устанавливаем нашу камеру на старт
         // gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
          
          //Создаем мир в Box2D
          //Стандарт гравитации -400
          world = new World(new Vector2(0, -400 / platformer.PPM), true);
          b2dr = new Box2DDebugRenderer();
          platform = new PlatformLeftRight(this, 739, 55);
          platformUpDown = new PlatformUpDown(this, 23f * platformer.PPM, 0.8f * platformer.PPM);
          platformCircular = new PlatformCircular(this, 2f * platformer.PPM, 2f * platformer.PPM);
          platformDifficult = new PlatformDifficult(this, 2f * platformer.PPM, 1f * platformer.PPM);
          player = new MarioClone(this);
          listener = new WorldContactListener();
         // world.setContactListener(listener);
          music = platformer.manager.get("Hiked Up Tube Socks.mp3", Music.class);
          music.setLooping(true);
          music.setVolume(0.2f);
          music.play();
          
          new B2WorldCreator(this);
          
          
    }

    @Override
    public void render(float delta) {
        update(delta);
        //Очищаем экран игры черным цветом
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Отрендерить нашу карту
        renderer.render();
        
        //Отрендерить дебагер линий Box2D
        b2dr.render(world, gamecam.combined);
        
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
       // platform.draw(game.batch);
        game.batch.end();
        
        //Установка камеры интерфейса
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if(gameOver()){
            music.stop();
            game.setScreen(new GameOverScreen(game));
            dieSound = platformer.manager.get("mariodie.wav", Sound.class);
            dieSound.play(0.7f);
            dispose();
        }
        if(gameWin()){
            lastTime = 600 - hud.getWorldTime();
            music.stop();
            game.setScreen(new WinScreen(game, lastTime));
            winSound = platformer.manager.get("win.wav", Sound.class);
            winSound.play(0.7f);
            dispose();
        }
        
    }
    
    public void update(float dt){
        handleInput(dt);
        
        world.step(1/60f, 6, 2);
        player.update(dt);
        platform.update(dt);
        platformUpDown.update(dt);
       // platformCircular.update(dt);
        platformDifficult.update(dt);
        hud.update(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }
    
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
       
    }

    
    public boolean gameOver(){
        if(player.b2body.getPosition().y < 0)
            return true;
        return false;
    }
    
    public boolean gameWin(){
        if(player.b2body.getPosition().x >= 94.2 && player.b2body.getPosition().x <= 94.9 && player.b2body.getPosition().y >= 1.4 && player.b2body.getPosition().y <= 1.725)
            return true;
        return false;
    }
    
    
    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
    
   @Override
    public void show() {
        
    }
    
    public TiledMap getMap(){
        return map;
    }
    
    public World getWorld(){
        return world;
    }
    
    
    
    
    public TextureAtlas getAtlas(){
        return atlas;
    }
    public void handleInput(float dt){
        //Старая версия
       /*if(Gdx.input.justTouched()){
            gamecam.position.x += 100 * dt;
        }*/
        
        //Новая версия
        //Стандарт прыжка 4f
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
         //  if(listener.getStatus()==true){
            if(player.isGround() == true){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            jumpSound = platformer.manager.get("jump.mp3", Sound.class);
            jumpSound.play(0.2f);
            
           }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.15f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.15f, 0), player.b2body.getWorldCenter(), true);
    
    }
}
 