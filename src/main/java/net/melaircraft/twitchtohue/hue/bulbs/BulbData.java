package net.melaircraft.twitchtohue.hue.bulbs;

import org.json.JSONObject;

public abstract class BulbData {
    final boolean state;
    final double brightness;

    public BulbData(boolean state, double brightness) {
        this.state = state;
        this.brightness = brightness;
    }

    public BulbData(JSONObject jsonObject) {
        this.state = jsonObject.getBoolean("on");
        this.brightness = jsonObject.getInt("bri") / 256.0;
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("on", state);
        jsonObject.put("bri", (int) Math.floor(brightness * 255));
        return jsonObject;
    }

    static public BulbData getBulb(JSONObject jsonObject) {
        switch (jsonObject.getString("colormode")) {
            case "hs":
                return new HSBulbData(jsonObject);
            case "xy":
                return new XYBulbData(jsonObject);
            case "ct":
                return new CTBulbData(jsonObject);
        }

        return null;
    }
}
