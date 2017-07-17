package ooo.george.qwas.metrics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import ooo.george.qwas.Log;
import ooo.george.qwas.exercise.Exercise;
import ooo.george.qwas.keyboard.layout.Layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Table implements Iterable<Record>, Json.Serializable {
    private List<Record> m_records;
    private FileHandle m_location;

    public Table() {
        m_records = new ArrayList<>();
    }

    public Table(FileHandle p_location) {
        m_records = new ArrayList<>();
        m_location = p_location;

        if (m_location.exists()) {
            Json jsonObject = new Json();
            JsonValue jsonValue = new JsonReader().parse(m_location.readString());
            try {
                this.read(jsonObject, jsonValue);
            } catch (Exception e ) {
                // Todo: restore from backup if exists
                Log.error(String.format("Read failed: %s", m_location.name()));
            }
        }
    }

    public void addRecord(Layout p_keyboardLayout, Exercise p_exercise) {
        Record newRecord = new Record(p_keyboardLayout,
                p_exercise.charactersPerSecond(p_exercise.getFinishedTime()),
                p_exercise.getName(), p_exercise.correctlyTypedPercent());
        if (m_records.add(newRecord)) {
            saveToFile();
        } else {
            Log.warning(String.format("Record add failed. %s", newRecord.getName() == null ? "[NO NAME]" : newRecord.getName()));
        }
    }

    private String getJson() {
        Json json = new Json();
        return json.prettyPrint(this);
    }

    public void read(Json p_json, JsonValue p_jsonMap){
        m_records = new ArrayList<>();
        for (JsonValue f: p_jsonMap.child()) {
            Record c = new Record();
            c.read(p_json, f);
            m_records.add(c);
        }
    }

    public void write(Json p_json) {
        p_json.writeValue("records", m_records);
    }

    private void saveToFile() {
        m_location.writeString(this.getJson(), false);
    }

    @Override
    public Iterator<Record> iterator() {
        return m_records.iterator();
    }

    public void dispose() {
        saveToFile();
    }
}
