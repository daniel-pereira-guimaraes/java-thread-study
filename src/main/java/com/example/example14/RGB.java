package com.example.example14;

public class RGB {
    private final int value;

    public RGB(int red, int green, int blue) {
        this.value = Math.max(0, Math.min(255, red)) << 16
                | Math.max(0, Math.min(255, green)) << 8
                | Math.max(0, Math.min(255, blue));
    }

    public RGB(int value) {
        this.value = value;
    }

    public int red() {
        return (value & 0x00FF0000) >> 16;
    }

    public int green() {
        return (value & 0x0000FF00) >> 8;
    }

    public int blue() {
        return value & 0x000000FF;
    }

    public int value() {
        return value;
    }

}
