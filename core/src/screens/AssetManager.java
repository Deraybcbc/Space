package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cat.xtec.ioc.utils.Settings;

public class AssetManager {

    // Sprite Sheet
    public static Texture sheet;

    // Nau i fons
    public static TextureRegion spacecraft, spacecraftDown, spacecraftUp, background;

    // Asteroide
    public static TextureRegion[] asteroid;
    public static Animation asteroidAnim;
    // Sons
    public static Sound explosionSound;
    public static Music music;
    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Font
    public static BitmapFont font;
    public static void load() {
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Sprites de la nau
        spacecraft = new TextureRegion(sheet, 0, 0, 36, 15);
        spacecraft.flip(false, true);

        spacecraftUp = new TextureRegion(sheet, 36, 0, 36, 15);
        spacecraftUp.flip(false, true);

        spacecraftDown = new TextureRegion(sheet, 72, 0, 36, 15);
        spacecraftDown.flip(false, true);

        // Carreguem els 16 estats de l'asteroide
        asteroid = new TextureRegion[16];
        for (int i = 0; i < asteroid.length; i++) {

            asteroid[i] = new TextureRegion(sheet, i * 34, 15, 34, 34);
            asteroid[i].flip(false, true);

        }

        // Creem l'animació de l'asteroide i fem que s'executi contínuament en sentit antihorari
        asteroidAnim = new Animation(0.05f, asteroid);
        asteroidAnim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);

        // Creem els 16 estats de l'explosió
        explosion = new TextureRegion[16];

// Carreguem els 16 estats de l'explosió
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                explosion[index++] = new TextureRegion(sheet, j * 64, i * 64 + 49, 64, 64);
                explosion[index-1].flip(false, true);
            }
        }

        // Fons de pantalla
        background = new TextureRegion(sheet, 0, 177, 480, 135);
        background.flip(false, true);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));

        //ANIMACION DE LA EXPLOSION DE LA NAVE
        explosionAnim = new Animation(0.04f, explosion);

        /******************************* Sounds *************************************/
// Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

// Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);


/******************************* Text *************************************/
// Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);

    }

    public static void dispose() {
// Descartem els recursos
        sheet.dispose();
        explosionSound.dispose();
        music.dispose();    }

}
