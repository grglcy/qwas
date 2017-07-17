package ooo.george.qwas;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import ooo.george.qwas.display.MainMenuScreen;
import ooo.george.qwas.keyboard.KeyPress;
import ooo.george.qwas.keyboard.Keyboard;
import ooo.george.qwas.keyboard.layout.Layout;
import ooo.george.qwas.keyboard.layout.LayoutList;

import java.util.Hashtable;

import static com.badlogic.gdx.Gdx.graphics;

public class KeyboardInputListener implements InputProcessor {
    private final Keyboard m_keyboard;
    private boolean m_emulateKeyboard;

    public KeyboardInputListener(LayoutList p_layouts) {
        //a_mousePos = a_camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        m_keyboard = new Keyboard(p_layouts);
        m_emulateKeyboard = false;
    }

    public Layout getTarget() {
        return m_keyboard.getTarget();
    }

    public void setSource(String p_layout) {
        m_keyboard.setSource(p_layout);
    }

    public void setTarget(String p_layout) {
        m_keyboard.setTarget(p_layout);
    }

    public void setEmulateKeyboard(boolean p_input) {
        m_emulateKeyboard = p_input;
    }

    public Hashtable<Integer, KeyPress> getPressedKeys() {
        return m_keyboard.getPressedKeys();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Assets.game.setScreen(new MainMenuScreen());
            return true;
        } else if (keycode == Input.Keys.F11) {
            if (graphics.isFullscreen()) {
                graphics.setWindowedMode(1366, 768);
            } else {
                graphics.setFullscreenMode(graphics.getDisplayMode());
            }
        } else if (keycode == Input.Keys.F9) {
            Constants.DEBUG_MODE = !Constants.DEBUG_MODE;
        }
        m_keyboard.keyDown(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            if (!m_keyboard.getKeyTyped() && m_keyboard.getTime(Input.Keys.SPACE) < 0.5) {
                Assets.currentExercise.attempt(' ');
            }
        }

        m_keyboard.keyUp(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char p_character) {
        if (m_emulateKeyboard) {
            char m_lastPressMapped = m_keyboard.keyTyped(p_character);
            if (m_lastPressMapped == '\0') {
                m_lastPressMapped = p_character;
            }
            if ((p_character > 31 && p_character < 166) || p_character == 8 || p_character == '\t') {
                if(m_lastPressMapped != ' ') {
                    Assets.currentExercise.attempt(m_lastPressMapped);
                    return true;
                }
            }
        } else {
            if (p_character == '\0') {
                return false;
            }
            if ((p_character > 31 && p_character < 166) || p_character == 8 || p_character == '\t') {
                if(p_character != ' ') {
                    Assets.currentExercise.attempt(p_character);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void render() {
        m_keyboard.render();
    }
}
