package ooo.george.qwas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ooo.george.qwas.display.*;

public class Application extends Game {
    private SpriteBatch batch;

	@Override
	public void create () {
        Assets.initialise(this);

	    this.setScreen(new WarningScreen());

        batch = new SpriteBatch();
    }

    public void resize(int width, int height) {
	    super.resize(width, height);
    }
	
	@Override
	public void dispose() {
	    Assets.dispose();
		batch.dispose();
	}
}
