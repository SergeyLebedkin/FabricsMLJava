package com.ingrain.fabrics.types;

// ImageInfo
public class PixelLocationOnOverview {
    public float x = 0.0f;
    public float y = 0.0f;
    public boolean inBlackList = false;

    // PixelLocationOnOverview
    PixelLocationOnOverview(float x, float  y) {
        this.x = x;
        this.y = y;
        this.inBlackList = false;
    }
}
