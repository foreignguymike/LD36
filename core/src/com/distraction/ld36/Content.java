package com.distraction.ld36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class Content {

    private static Map<String, Texture> textureMap;
    private static Map<String, TextureAtlas> atlasMap;
    private static Map<String, BitmapFont> fontMap;

    static {
        textureMap = new HashMap<String, Texture>();
        atlasMap = new HashMap<String, TextureAtlas>();
        fontMap = new HashMap<String, BitmapFont>();

        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 20, 20);
        Texture texture = new Texture(pixmap);
        putTexture("test", texture);

        putAtlas("main", new TextureAtlas(Gdx.files.internal("pack.pack")));

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("coders_crux.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 16;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.characters = "0123456789";
        putFont("12", gen.generateFont(params));
        gen.dispose();
    }

    public static void putAtlas(String key, TextureAtlas atlas) {
        atlasMap.put(key, atlas);
    }

    public static TextureAtlas getAtlas(String key) {
        return atlasMap.get(key);
    }

    public static void putTexture(String key, Texture texture) {
        textureMap.put(key, texture);
    }

    public static Texture getTexture(String key) {
        return textureMap.get(key);
    }

    public static void putFont(String key, BitmapFont font) {
        fontMap.put(key, font);
    }

    public static BitmapFont getFont(String key) {
        return fontMap.get(key);
    }

}
