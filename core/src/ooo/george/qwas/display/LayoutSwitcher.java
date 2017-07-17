package ooo.george.qwas.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ooo.george.qwas.Assets;

class LayoutSwitcher {
    private final HorizontalGroup m_container;
    private SelectBox<String> m_sourceLayoutSelect;
    private SelectBox<String> m_targetLayoutSelect;
    private boolean m_emulateLayout;
    private String m_source;
    private String m_target;

    public LayoutSwitcher(HorizontalGroup p_container) {
        m_container = p_container;

        m_emulateLayout = true;

        Assets.keyboard.setEmulateKeyboard(true);

        construct();
    }

    private void construct() {
        m_container.clear();

        final CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle(Assets.img_checkbox_unticked, Assets.img_checkbox_ticked, Assets.oswald_regular_32, Color.WHITE);
        CheckBox m_checkBox = new CheckBox(null, checkBoxStyle);
        m_checkBox.setChecked(m_emulateLayout);
        m_checkBox.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                m_emulateLayout = !m_emulateLayout;
                Assets.keyboard.setEmulateKeyboard(m_emulateLayout);
                construct();
            }
        });

        setDialogue();

        m_container.addActor(new Label("Emulate layout:", new Label.LabelStyle(Assets.oswald_regular_32, Color.BLACK)));
        m_container.space(20);
        m_container.addActor(m_checkBox);
    }

    private void setDialogue() {
        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle(Assets.img_selectbg, Assets.img_selectbg, Assets.img_selectbg, Assets.img_selectbg, Assets.img_selectbg);
        List.ListStyle listStyle = new List.ListStyle(Assets.oswald_regular_32, Color.WHITE, Color.WHITE, Assets.img_red);
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle(Assets.oswald_regular_32, Color.WHITE, Assets.img_selectbg, scrollStyle, listStyle);

        m_container.addActor(new Label("I am using the:", new Label.LabelStyle(Assets.oswald_regular_32, Color.BLACK)));
        m_sourceLayoutSelect = new SelectBox<>(style);
        m_sourceLayoutSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                m_source = m_sourceLayoutSelect.getSelected();
                Assets.keyboard.setSource(m_source);
                if (!m_emulateLayout) {
                    Assets.keyboard.setTarget(m_source);
                }
            }
        });
        if (m_source != null) {
            m_sourceLayoutSelect.setSelected(m_source);
        }
        m_sourceLayoutSelect.setItems(Assets.layouts.getNames(true));

        m_container.addActor(m_sourceLayoutSelect);

        if (m_emulateLayout) {
            m_container.addActor(new Label("layout and want to emulate the: ", new Label.LabelStyle(Assets.oswald_regular_32, Color.BLACK)));

            m_targetLayoutSelect = new SelectBox<>(style);
            m_targetLayoutSelect.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    m_target = m_targetLayoutSelect.getSelected();
                    Assets.keyboard.setTarget(m_target);
                }
            });
            if (m_target != null) {
                m_targetLayoutSelect.setSelected(m_target);
            }
            m_targetLayoutSelect.setItems(Assets.layouts.getNames(false));

            m_container.addActor(m_targetLayoutSelect);
        }

        m_container.addActor(new Label("layout.", new Label.LabelStyle(Assets.oswald_regular_32, Color.BLACK)));
    }
}
