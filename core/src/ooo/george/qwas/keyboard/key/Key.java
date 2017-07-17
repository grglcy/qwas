package ooo.george.qwas.keyboard.key;

import ooo.george.qwas.keyboard.Combination;

public interface Key {

    char getKey();

    char getKey(Combination p_modifier);

    boolean contains(char p_value);

    void addKey(Combination p_key, char p_value);
}
