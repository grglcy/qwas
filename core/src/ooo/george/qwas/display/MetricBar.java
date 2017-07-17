package ooo.george.qwas.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ooo.george.qwas.Assets;
import ooo.george.qwas.exercise.Exercise;

class MetricBar {
    private final Table m_metricsDisplay;
    private final Label m_metricCpm;
    private final Label m_metricCorrectlyTyped;
    private final Label m_exerciseName;
    private final Label m_progress;

    public MetricBar() {
        m_metricsDisplay = new Table();

        Label.LabelStyle buttonStyle = new Label.LabelStyle(Assets.oswald_regular_32, Color.WHITE);
        m_metricsDisplay.setBackground(Assets.img_blackpx);

        m_metricCpm = new Label("", buttonStyle);
        m_metricCorrectlyTyped = new Label("", buttonStyle);
        m_progress = new Label("", buttonStyle);
        m_exerciseName = new Label("", buttonStyle);
        //updateMetricDisplay(null);

        m_metricsDisplay.add(m_metricCpm).prefWidth(Integer.MAX_VALUE);
        m_metricsDisplay.add(m_metricCorrectlyTyped).prefWidth(Integer.MAX_VALUE);
        m_metricsDisplay.add(m_exerciseName).prefWidth(Integer.MAX_VALUE);
        m_metricsDisplay.add(m_progress).prefWidth(Integer.MAX_VALUE);
        m_metricsDisplay.pad(-1);
    }

    public void updateMetricDisplay(Exercise p_exercise) {
        if (p_exercise == null) {
            m_metricCpm.setText("NO EXERCISE!");
            m_metricCorrectlyTyped.setText("");
            m_exerciseName.setText("");
            m_progress.setText("");
        } else {
            m_metricCpm.setText(String.format("CPS: %.1f", p_exercise.charactersPerSecond(System.currentTimeMillis())));
            m_metricCorrectlyTyped.setText(String.format("Correctly typed: %.1f%%", p_exercise.correctlyTypedPercent()));
            m_exerciseName.setText(String.format("Exercise: %s", p_exercise.getName()));
            m_progress.setText(String.format("Progress: %.2f%%", p_exercise.percentComplete()));
        }
    }

    public Table getTable() {
        return m_metricsDisplay;
    }
}
