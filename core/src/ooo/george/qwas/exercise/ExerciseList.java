package ooo.george.qwas.exercise;

import com.badlogic.gdx.files.FileHandle;
import ooo.george.qwas.Log;

import java.util.ArrayList;
import java.util.List;

public class ExerciseList {
    private String m_name;
    private List<ExerciseList> m_directories;
    private List<Exercise> m_exercises;

    public ExerciseList(FileHandle p_path) {
        Construct(p_path);
    }

    private void Construct(FileHandle p_path) {
        m_directories = new ArrayList<>();
        m_exercises = new ArrayList<>();
        m_name = p_path.nameWithoutExtension();

        for(FileHandle f:p_path.list()) {
            if (f.isDirectory()) {
                m_directories.add(new ExerciseList(f));
            } else if (!addExercise(f)) {
                Log.warning(String.format("Exercise creation fail: %s", f.name()));
            }
        }
    }

    private boolean addExercise(FileHandle p_path) {
        byte[] byteArray = p_path.readBytes();
        String exerciseContents;
        try {
            exerciseContents = new String(byteArray, "UTF-8");
        } catch (Exception e) {
            return false;
        }
        m_exercises.add(new Exercise(p_path.nameWithoutExtension(), exerciseContents));
        return true;
    }

    public int dirCount() {
        return m_directories.size();
    }

    public int exerciseCount() {
        return m_exercises.size();
    }

    public String getName(){
        return m_name;
    }

    public Exercise getExercise(int p_index) {
        if (p_index < m_exercises.size()) {
            return m_exercises.get(p_index);
        } else {
            Log.warning("Exercise out of range");
            return null;
        }
    }

    public ExerciseList getDir(int p_index) {
        if (p_index < m_directories.size()) {
            return m_directories.get(p_index);
        } else {
            Log.warning("Directory out of range");
            return null;
        }
    }
}
