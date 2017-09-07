package net.melaircraft.twitchtohue.hue.bulbs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class XYBulbData extends BulbData {
    final double x;
    final double y;

    public XYBulbData(boolean state, double brightness, double x, double y) {
        super(state, brightness);
        this.x = x;
        this.y = y;
    }

    public XYBulbData(JSONObject jsonObject) {
        super(jsonObject);
        JSONArray array = jsonObject.getJSONArray("xy");
        x = array.getDouble(0);
        y = array.getDouble(1);
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = super.getJson();
        jsonObject.put("xy", new JSONArray(Arrays.asList(x, y)));
        return jsonObject;
    }

    @Override
    public String toString() {
        return "XYBulbData{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                ", brightness=" + brightness +
                '}';
    }
}
