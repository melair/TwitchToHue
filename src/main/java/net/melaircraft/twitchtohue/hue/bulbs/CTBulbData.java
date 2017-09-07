package net.melaircraft.twitchtohue.hue.bulbs;

import org.json.JSONObject;

public class CTBulbData extends BulbData {
    final int ct;

    public CTBulbData(boolean state, double brightness, int ct) {
        super(state, brightness);
        this.ct = ct;
    }

    public CTBulbData(JSONObject jsonObject) {
        super(jsonObject);
        this.ct = jsonObject.getInt("ct");
    }

    @Override
    public JSONObject getJson() {
        JSONObject jsonObject = super.getJson();
        jsonObject.put("ct", ct);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "CTBulbData{" +
                "ct=" + ct +
                ", state=" + state +
                ", brightness=" + brightness +
                '}';
    }
}
