package ooo.george.qwas.keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import ooo.george.qwas.keyboard.layout.Layout;
import ooo.george.qwas.keyboard.layout.LayoutList;

import java.util.Hashtable;
import java.util.Set;

public class Keyboard {
    private final Hashtable<Integer, KeyPress> m_keys;
    private Layout m_source;
    private Layout m_target;
    private LayoutList m_layouts;
    private boolean m_keyTyped;

    private Keyboard() {
        m_keys = new Hashtable<>(256);
        m_keyTyped = false;
    }

    public Keyboard(LayoutList p_layouts) {
        this();
        m_layouts = p_layouts;
        this.m_source = p_layouts.get("qwerty_uk");
        this.m_target = p_layouts.get("dvorak_uk");

        m_keys.put(Input.Keys.SPACE, new KeyPress());
        m_keys.put(Input.Keys.SHIFT_LEFT, new KeyPress());
        m_keys.put(Input.Keys.SHIFT_RIGHT, new KeyPress());
        m_keys.get(Input.Keys.SPACE).reset();
        m_keys.get(Input.Keys.SHIFT_LEFT).reset();
        m_keys.get(Input.Keys.SHIFT_RIGHT).reset();
    }

    public void setSource(String p_name) {
        Layout newLayout = m_layouts.get(p_name);
        if (newLayout != null) {
            m_source = newLayout;
        }
    }

    public void setTarget(String p_name) {
        Layout newLayout = m_layouts.get(p_name);
        if (newLayout != null) {
            m_target = newLayout;
        }
    }

    public Hashtable<Integer, KeyPress> getPressedKeys() {
        return m_keys;
    }

    /**
     * Map character from source to target layout
     * @param p_value source key value
     * @return char Returns corresponding layout key value. Returns empty char \0 if not found or out of bounds
     */
    public char keyTyped(char p_value) {
        if (m_keys.get(Input.Keys.SPACE).isPressed() && p_value != ' ') {
            m_keyTyped = true;
        }
        Vector2 position = m_source.getPosition(p_value);
        if (position == null) {
            return '\0';
        }
        return m_target.getKey((int)position.x, (int)position.y, m_keys);
    }

    public void keyDown(int p_keycode) {
        if (!m_keys.containsKey(p_keycode)) {
            m_keys.put(p_keycode, new KeyPress());
        } else {
            m_keys.get(p_keycode).press();
        }
    }

    public void keyUp(int p_keycode) {
        if (m_keys.containsKey(p_keycode)) {
            m_keys.get(p_keycode).reset();
        }
    }

    public double getTime(int keyCode) {
        if (m_keys.containsKey(keyCode)) {
            return m_keys.get(keyCode).getTime();
        } else {
            return -1;
        }
    }

    public boolean getKeyTyped() {
        boolean returnValue = m_keyTyped;
        m_keyTyped = false;
        return returnValue;
    }

    private void incrementPresses() {
        Set<Integer> keys = m_keys.keySet();
        for(Integer k: keys) {
            if (m_keys.get(k).isPressed()) {
                m_keys.get(k).increment(Gdx.graphics.getDeltaTime());
            }
        }
    }

    public Layout getTarget() {
        return m_target;
    }

    public void render() {
        incrementPresses();
    }
}
