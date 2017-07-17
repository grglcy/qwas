package ooo.george.qwas.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ooo.george.qwas.Application;

class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "QWAS - A Typing Tutor for Single Handed Typing";
        config.width = 1366;
        config.height = 768;
        config.fullscreen = false;
        System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
        new LwjglApplication(new Application(), config);
	}
}
