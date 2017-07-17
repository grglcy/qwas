package ooo.george.qwas.metrics;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import ooo.george.qwas.Log;
import ooo.george.qwas.keyboard.layout.Layout;

import java.time.Instant;

public class Record implements Json.Serializable {
    private String m_keyboardLayout;
    private float m_cpm;
    private float m_accuracy;
    private String m_name;
    private Instant m_timeStamp;

    public Record(){}

    public Record(Layout p_keyboardLayout, float p_cpm, String p_exerciseName, float p_pcCorrectlyTyped) {
        m_keyboardLayout = p_keyboardLayout.getName();
        m_cpm = p_cpm;
        m_accuracy = p_pcCorrectlyTyped;
        m_name = p_exerciseName;
        m_timeStamp = Instant.now();
    }

    public String parse() {
        Json json = new Json();
        return json.prettyPrint(this);
    }

    public void write (Json json) {
        json.writeValue("keyboardLayout", m_keyboardLayout);
        json.writeValue("cpm", m_cpm);
        json.writeValue("pcCorrectlyTyped", m_accuracy);
        json.writeValue("exerciseName", m_name);
        json.writeValue("date", m_timeStamp.toString());
    }

    public void read (Json json, JsonValue jsonMap) {
        m_keyboardLayout = jsonMap.get("keyboardLayout").asString();
        m_cpm = jsonMap.get("cpm").asFloat();
        m_accuracy = jsonMap.get("pcCorrectlyTyped").asFloat();
        m_name = jsonMap.get("exerciseName").asString();
        try {
            m_timeStamp = Instant.parse(jsonMap.get("date").asString());
        } catch (Exception e) {
            Log.error(String.format("Exception [%s] encountered while creating record from Json", e.getLocalizedMessage()));
            m_timeStamp = Instant.now();
        }
    }

    public String getName() {
        return m_name;
    }

    public String getShortenedName(int p_length) {
        if (m_name.length() <= p_length) {
            return m_name;
        } else {
            return m_name.substring(0, p_length) + "...";
        }
    }

    public float getCharactersPerSecond() {
        return m_cpm;
    }

    public float getAccuracy() {
        return m_accuracy;
    }

    public String getTimeStamp() {
        return m_timeStamp.toString();
    }

    public Instant getInstant() {
        return m_timeStamp;
    }
}
