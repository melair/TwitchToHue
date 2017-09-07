package net.melaircraft.twitchtohue.hue.bulbs;

import org.json.JSONObject;

public class HSBulbData extends BulbData {
    final int hue;
    final double saturation;

    public HSBulbData(boolean state, double brightness, int hue, double saturation) {
        super(state, brightness);
        this.hue = hue;
        this.saturation = saturation;
    }

    public HSBulbData(JSONObject jsonObject) {
        super(jsonObject);
        this.hue = jsonObject.getInt("hue");
        this.saturation = jsonObject.getInt("sat") / 255.0;
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = super.getJson();
        jsonObject.put("hue", hue);
        jsonObject.put("sat", (int) Math.floor(saturation * 255));
        return jsonObject;
    }

    @Override
    public String toString() {
        return "HSBulbData{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", state=" + state +
                ", brightness=" + brightness +
                '}';
    }
}
