package ooo.george.qwas.display;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import ooo.george.qwas.Assets;

class WordDisplay {
    private final HorizontalGroup m_typingArea;
    private final Array<TextButton> m_buttons;

    public WordDisplay() {
        m_typingArea = new HorizontalGroup();
        m_buttons = new Array<>();

        for (int iii = 0; iii < 6; iii++) {
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(Assets.img_charbg, Assets.img_charbg, null, Assets.oswald_regular_48);
            m_buttons.add(new TextButton("?", style));
        }

        for (int iii = 0; iii < m_buttons.size; iii++) {
            m_typingArea.addActor(m_buttons.get(iii));
        }
    }

    public HorizontalGroup getContainer() {
        return m_typingArea;
    }

    public void render(String p_textToDisplay) {
        for (int iii = 0; iii < m_buttons.size; iii++) {
            if (iii < p_textToDisplay.length()) {
                m_buttons.get(iii).setText(Character.toString(p_textToDisplay.charAt(iii)));
            } else {
                m_buttons.get(iii).setText("");
            }
        }
    }
}
