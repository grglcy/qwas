package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Factory;

import java.time.Duration;
import java.time.Instant;

public class MetricSummary implements Screen {
    private final Viewport m_viewport;
    private final Instant m_now;
    private final Stage m_stage;

    public MetricSummary() {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        m_stage = new Stage(m_viewport);
        m_now = Instant.now();

        Label.LabelStyle defaultLabel = new Label.LabelStyle(Assets.oswald_regular_32, Color.BLACK);

        Table screen = new Table();

        screen.setFillParent(true);
        m_stage.addActor(screen);

        screen.add(Factory.generateTitle("Progress", Assets.setScreenMainMenu, null)).expandX().fill();
        screen.row();
        screen.add(createButtons()).expand().fill();
    }

    private Table createButtons() {
        Table buttons = new Table();

        buttons.add(createRangeButton("All time", Instant.MIN, Instant.MAX)).pad(10);
        buttons.row();
        buttons.add(createRangeButton("This month", m_now.minus(Duration.ofDays(30)), m_now)).pad(10);
        buttons.row();
        buttons.add(createRangeButton("Last month", m_now.minus(Duration.ofDays(60)), m_now.minus(Duration.ofDays(30)))).pad(10);
        buttons.row();
        buttons.add(createRangeButton("2 months ago", m_now.minus(Duration.ofDays(90)), m_now.minus(Duration.ofDays(60)))).pad(10);

        return buttons;
    }

    private TextButton createRangeButton(final String p_text, final Instant p_start, final Instant p_end) {
        return Factory.createButton(p_text, Assets.img_green, Assets.img_blue, new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.game.setScreen(new MetricSummaryRange(p_text, p_start, p_end));
            }
        });
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
