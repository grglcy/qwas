package ooo.george.qwas.keyboard.layout.json;

import java.util.ArrayList;

import ooo.george.qwas.Assets;
import ooo.george.qwas.Log;
import ooo.george.qwas.keyboard.key.HashKey;
import ooo.george.qwas.keyboard.key.Key;

public class layout {
    private String name;
    private ArrayList<combination> combinations;

    public String getName() {
        return name;
    }

    public boolean valid() {
        if (combinations == null || combinations.size() < 1) {
            return false;
        }

        int charCount = combinations.get(0).lines.get(0).size();

        int size = combinations.get(0).lineCount();
        for(combination m: combinations) {
            if (!m.valid()) {
                return false;
            }
            if (m.lineCount() != size) {
                Log.warning("Size mismatch in combination: " + name + ", line: " + m.combination);
                return false;
            }
        }
        return true;
    }

    public String[] getCombinations() {
        String[] combinationNames = new String[combinations.size()];

        for(int iii = 0; iii < combinationNames.length; iii++) {
            combinationNames[iii] = combinations.get(iii).combination;
        }

        return combinationNames;
    }

    public int lineCount() {
        return combinations.get(0).lineCount();
    }

    public int maxCharacters(){
        int size = 0;
        for(combination c: combinations)
        {
            if (c.maxLineLength() > size)
                size = c.maxLineLength();
        }
        return combinations.get(0).maxLineLength();
    }

    public Key getKey(int x, int y) {
        char defaultKey = '\0';
        for(combination c: combinations) {
            if (c.combination.equals("default"))
                defaultKey = c.getChar(x, y);
        }
        Key newKey = new HashKey(defaultKey);

        for(combination c: combinations) {
            if (Assets.combinations.containsKey(c.combination) && !c.combination.equals("default"))
                newKey.addKey(Assets.combinations.get((c.combination)), c.getChar(x, y));
        }

        if (newKey.contains('\0'))
            return null;
        else
            return newKey;
    }
}
