package me.oganesson.ceu_tweaker.client.texture;

import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

public class CTTextures {

    public static SimpleOverlayRenderer MANA_HATCH;

    public static void init() {
        MANA_HATCH = new SimpleOverlayRenderer("multipart/mana_hatch");
    }

}
