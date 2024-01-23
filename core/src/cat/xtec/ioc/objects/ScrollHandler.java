package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.Spacecraft;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {
    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    ArrayList<Asteroid> asteroids;

    // Objecte random
    Random r;

    public ScrollHandler() {
        //Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

//Afegim els fons (actors) al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

// Comencem amb 3 asteroides
        numAsteroids = 3;

// Creem l'ArrayList
        asteroids = new ArrayList<Asteroid>();

// Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;

// Afegim el primer asteroide a l'array i al grup
        Asteroid asteroid = new Asteroid(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
        asteroids.add(asteroid);
        addActor(asteroid);

// Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
// Creem la mida aleatòria
            newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
// Afegim l'asteroide
            asteroid = new Asteroid(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
// Afegim l'asteroide a l'ArrayList
            asteroids.add(asteroid);
// Afegim l'asteroide al grup d'actors
            addActor(asteroid);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
// Si algun element es troba fora de la pantalla, fem un reset de l'element
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < asteroids.size(); i++) {

            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isLeftOfScreen()) {
                if (i == 0) {
                    asteroid.reset(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    asteroid.reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }
    }

    public boolean collides(Spacecraft nau) {

        // Comprovem les col·lisions entre cada asteroide i la nau
        for (Asteroid asteroid : asteroids) {
            if (asteroid.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }
}
