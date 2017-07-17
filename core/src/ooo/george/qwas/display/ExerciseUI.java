package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Constants;
import ooo.george.qwas.exercise.Exercise;
import ooo.george.qwas.keyboard.layout.Layout;

class ExerciseUI implements Screen {
    private final Viewport m_viewport;
    private final Stage m_stage;
    private final Exercise m_exercise;
    private final WordDisplay m_wordDisplay;
    private final KeyboardDisplay m_keyboardDisplay;
    private final MetricBar m_metricDisplay;

    public ExerciseUI(Exercise p_exercise, Layout p_layout) {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        m_stage = new Stage(m_viewport);
        m_exercise = p_exercise;
        m_wordDisplay = new WordDisplay();
        m_keyboardDisplay = new KeyboardDisplay(m_exercise);
        m_metricDisplay = new MetricBar();

        Table m_screen = new Table();
        m_screen.setFillParent(true);
        m_screen.setDebug(Constants.DEBUG_MODE);
        m_stage.addActor(m_screen);

        m_screen.add(m_metricDisplay.getTable()).expandX();
        m_screen.row();

        m_screen.add(m_wordDisplay.getContainer()).expand();

        m_screen.row();

        constructLayout(p_layout);
        m_screen.add(m_keyboardDisplay.getTable());

        m_stage.addActor(m_screen);
    }

    private void constructLayout(Layout p_layout) {
        m_keyboardDisplay.constructLayout(p_layout);
    }

    @Override
    public void show() {
        // Todo: need to exit somehow other than esc...
        Gdx.input.setInputProcessor(Assets.keyboard);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(33/255.0f, 99/255.0f, 255/255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stage.act();
        m_stage.draw();

        if (m_exercise.isComplete()) {
            Assets.metricsStorage.addRecord(Assets.keyboard.getTarget(), m_exercise);
            Assets.game.setScreen(new EndScreen(m_exercise));
        }

        Assets.keyboard.render();
        m_keyboardDisplay.render();
        m_wordDisplay.render(m_exercise.getRemainingCharacter());
        m_metricDisplay.updateMetricDisplay(m_exercise);
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