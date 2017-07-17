package ooo.george.qwas.keyboard.layout.json;

import ooo.george.qwas.Log;

import java.util.ArrayList;

class combination {
    String combination;
    ArrayList<ArrayList<String>> lines;

    public int lineCount() {
        return lines.size();
    }

    public boolean valid() {
        return !(combination == null || combination.equals("") || lines == null || lines.size() < 1);
    }

    public ArrayList getLine(int p_index) {
        if (p_index >= lines.size()) {
            return null;
        } else {
            return lines.get(p_index);
        }
    }

    public int maxLineLength() {
        int size = 0;
        for (ArrayList<String> line : lines) {
            if (line.size() > size) {
                size = line.size();
            }
        }
        return size;
    }

    public char getChar(int x, int y) {
        if (y >= lines.size() || x >= lines.get(y).size()) {
            Log.warning("Character index out of range");
            return '\0';
        } else {
            return lines.get(y).get(x).charAt(0);
        }
    }
}
