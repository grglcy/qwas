package ooo.george.qwas.keyboard.layout;

import com.badlogic.gdx.files.FileHandle;
import ooo.george.qwas.file.File;

import java.util.ArrayList;
import java.util.List;

public class LayoutList {
    private final List<Layout> m_layouts;

    private int Count() {
        return m_layouts.size();
    }

    private Layout getLayout(int p_index) {
        return m_layouts.get(p_index);
    }

    public LayoutList(FileHandle p_path) {
        m_layouts = new ArrayList<>();

        File files = new File(p_path);

        List<File> fileList = files.getFileList();

        for (File f: fileList) {
            m_layouts.add(new JSONLayout(f.getFileContents(), f.getName()));
        }
    }

    public Layout get(String p_name) {
        for (Layout l:m_layouts) {
            if (l.getName().equals(p_name))
                return l;
        }
        return null;
    }

    public String[] getNames(boolean p_skip){
        if (p_skip) {
            String[] names = new String[Count() - 1];
            int actual = 0;
            for (int iii = 0; iii < Count(); iii++) {
                if (!getLayout(iii).getName().equals("qwerty_uk_mirror")) {
                    names[actual] = getLayout(iii).getName();
                    actual++;
                }
            }
            return names;
        } else {
            String[] names = new String[Count()];
            for (int iii = 0; iii < Count(); iii++) {
                names[iii] = getLayout(iii).getName();
            }
            return names;
        }
    }
}