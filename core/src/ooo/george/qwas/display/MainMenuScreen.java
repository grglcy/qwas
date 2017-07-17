package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.*;

public class MainMenuScreen implements Screen {
    private final Viewport m_viewport;
    private final Stage m_stage;

    public MainMenuScreen() {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        m_stage = new Stage(m_viewport);

        constructScreen();
    }

    private void constructScreen() {
        m_stage.clear();
        Table screen = new Table();
        screen.setFillParent(true);
        m_stage.addActor(screen);

        VerticalGroup mainButtons = new VerticalGroup();
        HorizontalGroup layoutSelect = new HorizontalGroup();
        layoutSelect.bottom();

        TextButtonStyle logoStyle = new TextButtonStyle(Assets.trans_bg, Assets.trans_bg, Assets.trans_bg, Assets.oswald_regular_128);
        logoStyle.fontColor = Color.BLACK;

        m_stage.addActor(screen);

        mainButtons.addActor(Factory.createButton("Exercise", Assets.img_green, Assets.img_blue, Assets.setScreenExerciseListUI));
        mainButtons.space(Constants.SPACE_BETWEEN_BUTTONS);
        mainButtons.addActor(Factory.createButton("Progress", Assets.img_green, Assets.img_blue, Assets.setScreenMetricSummary));

        LayoutSwitcher m_switcher = new LayoutSwitcher(layoutSelect);

        screen.add(new TextButton("QWAS", logoStyle).padLeft(25).padRight(25).padTop(10).padBottom(10)).align(Align.center).pad(25);
        screen.row();
        screen.add(mainButtons).expandY();
        screen.row();
        screen.add(layoutSelect).expandX().right();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(m_stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(33/255.0f, 99/255.0f, 255/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act();
        m_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        m_viewport.update(width, height, true);
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
        m_stage.dispose();
    }
}
