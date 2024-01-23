package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Spacecraft;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.Asteroid;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.utils.Settings;

public class GameScreen implements Screen {

    // Per controlar el gameover
    Boolean gameOver = false;

    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;
    // Representació de figures geomètriques
    private ShapeRenderer shapeRenderer;
    // Per obtenir el batch de l'stage
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;
    // Font
    public static BitmapFont font;
    private GlyphLayout textLayout;

    public GameScreen() {

// Iniciem la música
        AssetManager.music.play();

// Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

// Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
// Posant el paràmetre a true configurem la càmera perquè
// faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

// Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

// Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        batch = stage.getBatch();

// Creem la nau i la resta d'objectes
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();

// Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
// Donem nom a l'Actor
        spacecraft.setName("spacecraft");

// Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "GameOver");
    }

    private void drawElements(){

        /* 1 */
        /*
// Pintem el fons de negre per evitar el "flickering"
        Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);*/

        /* 2 */
// Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
// Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        /* 3 */
// Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));
// Pintem la nau
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        /* 4 */
// Recollim tots els Asteroid
        ArrayList<Asteroid> asteroids = scrollHandler.getAsteroids();
        Asteroid asteroid;

        for (int i = 0; i < asteroids.size(); i++) {

            asteroid = asteroids.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1,0,0,1);
                    break;
                case 1:
                    shapeRenderer.setColor(0,0,1,1);
                    break;
                case 2:
                    shapeRenderer.setColor(1,1,0,1);
                    break;
                default:
                    shapeRenderer.setColor(1,1,1,1);
                    break;
            }
            shapeRenderer.circle(asteroid.getX() + asteroid.getWidth()/2, asteroid.getY() + asteroid.getWidth()/2, asteroid.getWidth()/2);
        }
        /* 5 */
        shapeRenderer.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Dibuixem i actualitzem tots els actors de l'stage
        stage.draw();
        stage.act(delta);

        if (!gameOver) {
            if (scrollHandler.collides(spacecraft)) {
                // Si hi ha hagut col·lisió: Reproduïm l'explosió
                AssetManager.explosionSound.play();
                stage.getRoot().findActor("spacecraft").remove();
                gameOver = true;
            }
        } else {
            batch.begin();
            // Si hi ha hagut col·lisió: reproduïm l'explosió
            batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 32, spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);

            //Mensaje Texto
            AssetManager.font.draw(batch, textLayout, Settings.GAME_WIDTH/2 - textLayout.width/2, Settings.GAME_HEIGHT/2 - textLayout.height/2);

            batch.end();

            explosionTime += delta;
        }


        //drawElements();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Stage getStage() {
        return stage;
    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }
}
