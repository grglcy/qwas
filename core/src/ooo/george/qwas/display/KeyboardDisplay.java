package ooo.george.qwas.display;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ooo.george.qwas.Assets;
import ooo.george.qwas.exercise.Exercise;
import ooo.george.qwas.keyboard.layout.Layout;

import java.util.ArrayList;
import java.util.List;

class KeyboardDisplay {
    private final Exercise m_exercise;
    private final List<List<TextButton>> m_keys;
    private final Table m_keyboardArea;
    private final TextButton.TextButtonStyle m_keyStyle;
    private final TextButton.TextButtonStyle m_keyStylePressed;
    private TextButton m_lastSetKey;
    private Layout m_layout;

    public KeyboardDisplay(Exercise p_exercise) {
        m_keys = new ArrayList<>();
        m_keyboardArea = new Table();
        m_keyStyle = new TextButton.TextButtonStyle(Assets.img_keybg, Assets.img_keybg, null, Assets.oswald_regular_32);
        m_keyStylePressed = new TextButton.TextButtonStyle(Assets.img_keybg_pressed, Assets.img_keybg_pressed, null, Assets.oswald_regular_32);
        m_exercise = p_exercise;
    }

    public Table getTable() {
        return m_keyboardArea;
    }

    public void constructLayout(Layout p_layout) {
        m_layout = p_layout;
        m_keyboardArea.reset();

        m_keys.clear();

        for (int iii = 0; iii < m_layout.Rows(); iii++) {
            m_keys.add(new ArrayList<>());
            for (int jjj = 0; jjj < m_layout.RowLength(iii); jjj++) {
                String key = Character.toString(m_layout.getKey(iii, jjj, Assets.keyboard.getPressedKeys()));
                m_keys.get(iii).add(new TextButton(key, m_keyStyle));
            }
        }

        for (int iii = 0; iii < m_layout.Rows(); iii++) {
            HorizontalGroup rowGroup = new HorizontalGroup();
            for (int jjj = 0; jjj < m_layout.RowLength(iii); jjj++) {
                rowGroup.addActor(m_keys.get(iii).get(jjj));
                rowGroup.pad(2);
                rowGroup.space(2);
            }
            switch (iii) {
                case 1:
                    m_keyboardArea.add(rowGroup).left().padLeft(48 + 12);
                    break;
                case 2:
                    m_keyboardArea.add(rowGroup).left().padLeft(48 + 24);
                    break;
                case 3:
                    m_keyboardArea.add(rowGroup).left().padLeft(48);
                    break;
                default:
                    m_keyboardArea.add(rowGroup).left();
                    break;
            }
            m_keyboardArea.row();
        }
    }

    public void render() {
        constructLayout(m_layout);
        char nextChar = m_exercise.getNextChar();
            if (m_lastSetKey != null)
                m_lastSetKey.setStyle(m_keyStyle);
            for (List<TextButton> column: m_keys) {
                for (TextButton key: column) {
                    if (key.getText().charAt(0) == nextChar) {
                        key.setStyle(m_keyStylePressed);
                        m_lastSetKey = key;
                    }
                }
            }
        }
}
