package ooo.george.qwas.keyboard.layout;

import com.badlogic.gdx.math.Vector2;
import ooo.george.qwas.keyboard.Combination;
import ooo.george.qwas.keyboard.KeyPress;

import java.util.Hashtable;

public interface Layout {

        char getKey(int x, int y, Hashtable<Integer, KeyPress> p_keys);

        Vector2 getPosition(char p_key);

        int Rows();

        int RowLength(int p_row);

        String getName();

        boolean addCombination(Combination p_combination);
}
