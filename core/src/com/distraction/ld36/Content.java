package com.distraction.ld36;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class Content {

    private static Map<String, Texture> textureMap;

    static {
        textureMap = new HashMap<String, Texture>();

        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 20, 20);
        Texture texture = new Texture(pixmap);
        putTexture("test", texture);
    }

    public static void putTexture(String key, Texture texture) {
        textureMap.put(key, texture);
    }

    public static Texture getTexture(String key) {
        return textureMap.get(key);
    }

}
