package ooo.george.qwas.file;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.List;

public class File{
    private String m_name;
    private String m_fileContents;
    private List<File> m_contents;

    public File(FileHandle p_path) {
        Construct(p_path);
    }

    private void Construct(FileHandle p_path) {
        m_contents = new ArrayList<>();

        if (p_path.isDirectory()) {
            for (int iii = 0; iii < p_path.list().length; iii++) {
                addFile(new File(p_path.list()[iii]));
            }
        } else {
            byte[] byteArray = p_path.readBytes();
            try {
                this.m_fileContents = new String(byteArray, "UTF-8");
            } catch (Exception e) {
                this.m_fileContents= "[Invalid file]";
            }
            this.m_name = p_path.nameWithoutExtension();
        }
    }

    private void addFile(File p_file) {
        m_contents.add(p_file);
    }

    public String getFileContents() {
        return m_fileContents;
    }

    private boolean isDirectory() {
        return m_fileContents == null && m_contents != null;
    }

    public String getName(){
        return m_name;
    }

    public List<File> getFileList() {
        List<File> returnList = new ArrayList<>();

        for (File f: m_contents) {
            if (f.isDirectory()) {
                returnList.addAll(f.getFileList());
            } else {
                returnList.add(f);
            }
        }

        return returnList;
    }
}
