package ooo.george.qwas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;


public class Factory {
    public static TextButton createButton(String p_txt, TextureRegionDrawable p_up, TextureRegionDrawable p_down, ChangeListener p_listener) {
        if (p_listener == null) {
            p_listener = new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            };
            Log.debug("Inactive button created.");
        }

        TextButton buttonToAdd;
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(p_up, p_down, null, Assets.oswald_regular_32);
        style.fontColor = Color.BLACK;

        buttonToAdd = new TextButton(p_txt, style);
        buttonToAdd.addListener(p_listener);

        return buttonToAdd;
    }

    public static Table generateTitle(String p_titleText, ChangeListener p_screen, String p_backText) {
        if (p_backText == null) {
            p_backText = "Back";
        }
        Table title = new Table();
        TextButton backButton = Factory.createButton(p_backText, Assets.img_blue, Assets.img_red, p_screen);
        title.add(backButton).align(Align.left).left().top().pad(25);
        title.add(new Label(p_titleText, new Label.LabelStyle(Assets.oswald_regular_128, Color.BLACK))).expandX().padRight(backButton.getWidth());

        return title;
    }
}
