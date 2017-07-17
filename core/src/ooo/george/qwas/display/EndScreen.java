package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Factory;
import ooo.george.qwas.exercise.Exercise;

class EndScreen implements Screen {
    private final Viewport m_viewport;
    private final Label.LabelStyle m_labelSkin;
    private final Stage m_stage;
    private final TextButton m_menuButton;

    public EndScreen(Exercise p_exercise) {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        m_labelSkin = new Label.LabelStyle(Assets.oswald_regular_64, Color.BLACK);
        m_stage = new Stage(m_viewport);
        m_menuButton = Factory.createButton("Back", Assets.img_blue, Assets.img_red, Assets.setScreenMainMenu);
        constructScreen(p_exercise);
    }

    private void constructScreen(Exercise p_exercise) {
        Table screen = new Table();
        screen.setFillParent(true);
        Table attributes = new Table();

        attributes.add(new Label("CPM:", m_labelSkin)).left();
        attributes.add(new Label(String.format("%.2f", p_exercise.charactersPerSecond()), m_labelSkin)).expandX();
        attributes.row();
        attributes.add(new Label("Accuracy:", m_labelSkin)).left().padRight(25);
        attributes.add(new Label(String.format("%.2f%%", p_exercise.correctlyTypedPercent()), m_labelSkin)).expandX();

        TextButton.TextButtonStyle logoStyle = new TextButton.TextButtonStyle(Assets.trans_bg, Assets.trans_bg, Assets.trans_bg, Assets.oswald_regular_128);
        logoStyle.fontColor = Color.BLACK;

        screen.add(new TextButton(String.format("Exercise: \"%s\" complete!", p_exercise.getName()), logoStyle)).align(Align.center).pad(25);
        screen.row();
        screen.add(attributes).fillY();
        screen.row();
        screen.add(m_menuButton);

        m_stage.addActor(screen);
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
