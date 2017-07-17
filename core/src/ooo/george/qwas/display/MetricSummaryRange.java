package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Factory;
import ooo.george.qwas.metrics.Record;

import java.time.Instant;
import java.util.ArrayList;

class MetricSummaryRange implements Screen {
    private final Viewport m_viewport;

    private final Stage m_stage;

    private final Label.LabelStyle m_defaultLabelStyle;

    public MetricSummaryRange(String p_labelText, Instant p_start, Instant p_end) {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        m_defaultLabelStyle = new Label.LabelStyle(Assets.oswald_regular_48, Color.BLACK);

        ArrayList<Record> records = new ArrayList<>();

        for(Record r: Assets.metricsStorage) {
            if (r.getInstant().isAfter(p_start) && r.getInstant().isBefore(p_end)) {
                records.add(r);
            }
        }

        m_stage = new Stage(m_viewport);
        Table m_screen = new Table();
        m_screen.setFillParent(true);

        m_screen.add(Factory.generateTitle(p_labelText, Assets.setScreenMetricSummary, null)).expandX().fill();
        m_screen.row();

        if (records.isEmpty()) {
            m_screen.add(new Label("Insufficient data available.", new Label.LabelStyle(Assets.oswald_regular_128, Color.BLACK))).align(Align.center).expand();
        } else {
            ScrollPane recordListPane = new ScrollPane(generateRecordList(records).align(Align.topLeft), Assets.defaultScrollPaneStyle);

            m_screen.add(new Label("Averages:", new Label.LabelStyle(Assets.oswald_regular_64, Color.BLACK))).align(Align.left);
            m_screen.row();
            m_screen.add(getAverage(records)).align(Align.left);
            m_screen.row();
            m_screen.add(new Label("Records:", new Label.LabelStyle(Assets.oswald_regular_64, Color.BLACK))).align(Align.left);
            m_screen.row();
            m_screen.add(recordListPane).fill().expand();
        }

        m_stage.addActor(m_screen);
    }

    private Table generateRecordList(ArrayList<Record> p_records) {
        Table recordList = new Table();
        recordList.add(new Label("Exercise", m_defaultLabelStyle)).padRight(50).align(Align.left);
        recordList.add(new Label("CPS", m_defaultLabelStyle)).padRight(50).align(Align.left);
        recordList.add(new Label("Accuracy", m_defaultLabelStyle)).align(Align.left);
        recordList.row();

        for(Record r: p_records) {
            getRecord(recordList, r);
            recordList.row();
        }

        return recordList;
    }

    private void getRecord(Table p_recordList, Record p_record) {
        p_recordList.add(new Label(p_record.getShortenedName(8), m_defaultLabelStyle)).align(Align.left);
        p_recordList.add(new Label(String.format("%.2f", p_record.getCharactersPerSecond()), m_defaultLabelStyle)).align(Align.left);
        p_recordList.add(new Label(String.format("%.2f%%", p_record.getAccuracy()), m_defaultLabelStyle)).align(Align.left);
    }

    private HorizontalGroup getAverage(ArrayList<Record> p_records) {
        HorizontalGroup average = new HorizontalGroup();

        float cpm = 0;
        float accuracy = 0;
        int count = 0;

        for(Record r: p_records) {
                cpm += r.getCharactersPerSecond();
                accuracy += r.getAccuracy();
                count++;
        }

        cpm /= count;
        accuracy /= count;

        average.addActor(new Label("Characters typed per second:", m_defaultLabelStyle));
        average.space(50);
        average.addActor(new Label(String.format("%.2f", cpm), m_defaultLabelStyle));
        average.space(50);
        average.addActor(new Label("Accuracy:", m_defaultLabelStyle));
        average.space(50);
        average.addActor(new Label(String.format("%.2f%%", accuracy), m_defaultLabelStyle));

        return average;
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
