package ooo.george.qwas;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ooo.george.qwas.display.ExerciseListUI;
import ooo.george.qwas.display.MainMenuScreen;
import ooo.george.qwas.display.MetricSummary;
import ooo.george.qwas.exercise.Exercise;
import ooo.george.qwas.exercise.ExerciseList;
import ooo.george.qwas.keyboard.Combination;
import ooo.george.qwas.keyboard.CombinationOR;
import ooo.george.qwas.keyboard.layout.LayoutList;
import ooo.george.qwas.metrics.Table;

import java.util.HashMap;

public class Assets {
    public static Game game;

    public static Exercise currentExercise;

    public static Table metricsStorage;

    public static HashMap<String, Combination> combinations;

    public static TextureRegionDrawable img_bg;
    public static NinePatchDrawable trans_bg;

    public static TextureRegionDrawable img_red;
    public static TextureRegionDrawable img_green;
    public static TextureRegionDrawable img_blue;
    public static TextureRegionDrawable img_charbg;
    public static TextureRegionDrawable img_keybg;
    public static TextureRegionDrawable img_keybg_pressed;
    public static TextureRegionDrawable img_selectbg;
    public static TextureRegionDrawable img_checkbox_ticked;
    public static TextureRegionDrawable img_checkbox_unticked;

    public static BitmapFont oswald_regular_32;
    public static BitmapFont oswald_regular_48;
    public static BitmapFont oswald_regular_64;
    public static BitmapFont oswald_regular_128;
    public static BitmapFont oswald_regular_256;

    public static Skin blackpx;
    public static TextureRegionDrawable img_blackpx;

    public static LayoutList layouts;

    public static KeyboardInputListener keyboard;

    public static ExerciseList exercises;

    public static ScrollPane.ScrollPaneStyle defaultScrollPaneStyle;

    public static ChangeListener setScreenMainMenu;
    public static ChangeListener setScreenExerciseListUI;
    public static ChangeListener setScreenMetricSummary;

    public static boolean initialised = false;

    public static void initialise(Game p_game) {
        if (initialised) {
            return;
        }

        game = p_game;

        metricsStorage = new Table(Gdx.files.local("metrics/db.json"));

        // Freetype not compatible with WebGL, so bitmap fonts are used instead
        if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            oswald_regular_32 = new BitmapFont(Gdx.files.internal("fonts/oswald/bm/oswald-regular-32.fnt"));
            oswald_regular_48 = new BitmapFont(Gdx.files.internal("fonts/oswald/bm/oswald-regular-48.fnt"));
            oswald_regular_64 = new BitmapFont(Gdx.files.internal("fonts/oswald/bm/oswald-regular-64.fnt"));
            oswald_regular_128 = new BitmapFont(Gdx.files.internal("fonts/oswald/bm/oswald-regular-128.fnt"));
            oswald_regular_256 = new BitmapFont(Gdx.files.internal("fonts/oswald/bm/oswald-regular-256.fnt"));
        } else {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/oswald/Oswald-Regular.ttf"));
            //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OxygenMono-Regular.otf"));
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameters.genMipMaps = true;
            fontParameters.kerning = true;
            fontParameters.minFilter = Texture.TextureFilter.Linear;
            fontParameters.magFilter = Texture.TextureFilter.Linear;
            fontParameters.size = 32;
            oswald_regular_32 = generator.generateFont(fontParameters);
            fontParameters.size = 48;
            oswald_regular_48 = generator.generateFont(fontParameters);
            fontParameters.size = 64;
            oswald_regular_64 = generator.generateFont(fontParameters);
            fontParameters.size = 128;
            oswald_regular_128 = generator.generateFont(fontParameters);
            fontParameters.size = 256;
            oswald_regular_256 = generator.generateFont(fontParameters);
            oswald_regular_32.setUseIntegerPositions(false);
            oswald_regular_48.setUseIntegerPositions(false);
            oswald_regular_64.setUseIntegerPositions(false);
            generator.dispose();
        }

        combinations = new HashMap<>();
        Combination shift = new CombinationOR(Input.Keys.SHIFT_LEFT, "shift", 2);
        shift.add(new Combination(Input.Keys.SHIFT_RIGHT, 0));
        Combination shift_space = new Combination(Input.Keys.SPACE, "shift_space", 1);
        shift_space.add(shift);
        combinations.put("shift_space", shift_space);
        combinations.put("shift", shift);
        combinations.put("space", new Combination(Input.Keys.SPACE, "space", 3));

        img_bg = new TextureRegionDrawable((new TextureRegion(new Texture("bg.png"))));
        trans_bg = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal("transbg.png")), 4, 4, 4, 4));

        img_red = new TextureRegionDrawable(new TextureRegion(new Texture("red.bmp")));
        img_green = new TextureRegionDrawable(new TextureRegion(new Texture("green.bmp")));
        img_blue = new TextureRegionDrawable(new TextureRegion(new Texture("blue.bmp")));
        img_charbg = new TextureRegionDrawable(new TextureRegion(new Texture("charbg.png")));
        img_keybg = new TextureRegionDrawable(new TextureRegion(new Texture("keybg.png")));
        img_keybg_pressed = new TextureRegionDrawable(new TextureRegion(new Texture("keybg_pressed.png")));
        img_selectbg = new TextureRegionDrawable(new TextureRegion(new Texture("selectbg.png")));
        img_checkbox_ticked = new TextureRegionDrawable(new TextureRegion(new Texture("checkbox_ticked.png")));
        img_checkbox_unticked = new TextureRegionDrawable(new TextureRegion(new Texture("checkbox_unticked.png")));

        blackpx = new Skin();
        blackpx.add("bg", new NinePatch(new Texture(Gdx.files.internal("pixels/black.png"))));
        img_blackpx = new TextureRegionDrawable(new TextureRegion(new Texture("pixels/black.png")));

        layouts = new LayoutList(Gdx.files.local("layouts/"));

        keyboard = new KeyboardInputListener(layouts);

        exercises = new ExerciseList(Gdx.files.internal("exercises/"));

        defaultScrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        defaultScrollPaneStyle.vScrollKnob = Assets.trans_bg;

        setScreenMainMenu = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.game.setScreen(new MainMenuScreen());
            }
        };

        setScreenExerciseListUI = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.game.setScreen(new ExerciseListUI(exercises));
            }
        };

        setScreenMetricSummary = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.game.setScreen(new MetricSummary());
            }
        };

        initialised = true;
    }

    public static void dispose() {
        metricsStorage.dispose();
    }
}
