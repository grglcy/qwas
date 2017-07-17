package ooo.george.qwas.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Constants;
import ooo.george.qwas.Factory;
import ooo.george.qwas.exercise.Exercise;
import ooo.george.qwas.exercise.ExerciseList;

public class ExerciseListUI implements Screen {
    private final Viewport m_viewport;
    private final Stage m_stage;
    private final Table m_list;
    private final ExerciseList m_root;
    private ExerciseList m_back;

    public ExerciseListUI(ExerciseList p_exercises) {
        Camera m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_viewport = new FitViewport(1920, 1080, m_camera);
        m_camera.translate(m_camera.viewportWidth / 2, m_camera.viewportHeight / 2, 0);

        Table m_screen = new Table();
        m_stage = new Stage(m_viewport);
        m_list = new Table();
        m_root = p_exercises;

        ScrollPane m_scrollPane = new ScrollPane(m_list, Assets.defaultScrollPaneStyle);

        display(p_exercises);

        m_screen.setFillParent(true);

        m_screen.add(Factory.generateTitle("Choose an Exercise", Assets.setScreenMainMenu, "Main Menu")).expandX().fill();
        m_screen.row();
        m_screen.add(m_scrollPane).fill().expand();
        m_stage.addActor(m_screen);
    }

    private void display(final ExerciseList p_exerciseList) {
        m_list.reset();

        final ExerciseList previous = m_back;

        if (p_exerciseList != m_root) {
            ChangeListener goBack = backButton(previous);
            TextButton currentButton = Factory.createButton("Back", Assets.img_blue, Assets.img_red, goBack);
            m_list.add(currentButton).pad(Constants.SPACE_BETWEEN_BUTTONS);
            m_list.row();
        }
        m_back = p_exerciseList;

        for(int iii = 0; iii < p_exerciseList.dirCount(); iii++) {
            final ExerciseList currentDir = p_exerciseList.getDir(iii);
            ChangeListener changeExercise = backButton(currentDir);
            String dirName = p_exerciseList.getDir(iii).getName();

            TextButton currentButton = Factory.createButton(dirName, Assets.img_red, Assets.img_blue, changeExercise);
            m_list.add(currentButton).pad(Constants.SPACE_BETWEEN_BUTTONS);
            m_list.row();
        }
        for (int iii = 0; iii < p_exerciseList.exerciseCount(); iii++) {
            final Exercise currentExercise = p_exerciseList.getExercise(iii);
            ChangeListener changeExercise = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Assets.currentExercise = currentExercise;
                    currentExercise.start();
                    Assets.game.setScreen(new ExerciseUI(currentExercise, Assets.keyboard.getTarget()));
                }
            };
            String exerciseName = p_exerciseList.getExercise(iii).getShortenedName(10);

            TextButton currentButton = Factory.createButton(exerciseName, Assets.img_green, Assets.img_blue, changeExercise);
            m_list.add(currentButton).pad(Constants.SPACE_BETWEEN_BUTTONS);
            m_list.row();
        }
    }

    private ChangeListener backButton(ExerciseList p_target) {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                display(p_target);
            }
        };
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
