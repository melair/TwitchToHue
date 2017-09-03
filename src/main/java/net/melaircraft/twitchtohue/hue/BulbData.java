package net.melaircraft.twitchtohue.hue;

public class BulbData {
    private final boolean state;
    private final double brightness;
    private final double saturation;
    private final int hue;

    public BulbData(boolean state, double brightness, double saturation, int hue) {
        this.state = state;
        this.brightness = brightness;
        this.saturation = saturation;
        this.hue = hue;
    }

    boolean isState() {
        return state;
    }

    double getBrightness() {
        return brightness;
    }

    double getSaturation() {
        return saturation;
    }

    int getHue() {
        return hue;
    }

    @Override
    public String toString() {
        return "BulbData{" +
                "state=" + state +
                ", brightness=" + brightness +
                ", saturation=" + saturation +
                ", hue=" + hue +
                '}';
    }
}
