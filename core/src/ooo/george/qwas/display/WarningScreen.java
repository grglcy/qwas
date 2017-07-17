package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Factory;

public class WarningScreen implements Screen {
    private final Viewport m_viewport;
    private final Stage m_stage;

    public WarningScreen() {
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


        TextButton.TextButtonStyle logoStyle = new TextButton.TextButtonStyle(Assets.trans_bg, Assets.trans_bg, Assets.trans_bg, Assets.oswald_regular_128);
        logoStyle.fontColor = Color.BLACK;
        Label.LabelStyle warningStyle = new Label.LabelStyle(Assets.oswald_regular_48, Color.BLACK);
        final String warningMessage = String.join(System.getProperty("line.separator")
                ,"Warning:"
                ,"The use of certain keyboard layouts may cause muscle strain if care is not taken."
                ,"Please move your hand to keys that are not reached comfortably, rather than stretching unnecessarily."
        );

        Label warningText = new Label(warningMessage, warningStyle);

        screen.add(warningText).expand();
        screen.row();
        screen.add(Factory.createButton("Okay", Assets.img_green, Assets.img_blue, Assets.setScreenMainMenu)).bottom();
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
