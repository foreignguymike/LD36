package com.distraction.ld36;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class Content {

    private static Map<String, TextureAtlas> atlasMap;
    private static Map<String, Sound> soundMap;
    private static Map<String, BitmapFont> fontMap;

    static {
        atlasMap = new HashMap<String, TextureAtlas>();
        soundMap = new HashMap<String, Sound>();
        fontMap = new HashMap<String, BitmapFont>();

        putAtlas("main", new TextureAtlas(Gdx.files.internal("pack.pack")));

        putSound("miss", Gdx.audio.newSound(Gdx.files.internal("miss.wav")));
        putSound("plugin", Gdx.audio.newSound(Gdx.files.internal("plugin.wav")));
        putSound("point", Gdx.audio.newSound(Gdx.files.internal("point.wav")));
        putSound("ring", Gdx.audio.newSound(Gdx.files.internal("ring.wav")));
        putSound("start", Gdx.audio.newSound(Gdx.files.internal("start.wav")));

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("coders_crux.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 16;
        params.minFilter = Texture.TextureFilter.Nearest;
        params.magFilter = Texture.TextureFilter.Nearest;
        params.characters = "0123456789()->MANUALSTARTRUSHBULLETFINISH!CALLERSLEFTCONNECTEDFinalScore: ";
        putFont("mainFont", gen.generateFont(params));
        params.size = 32;
        putFont("bigFont", gen.generateFont(params));
        params.size = 128;
        params.borderColor = Color.BLACK;
        params.color = Color.WHITE;
        putFont("scrollingFont", gen.generateFont(params));
        gen.dispose();
    }

    public static void putAtlas(String key, TextureAtlas atlas) {
        atlasMap.put(key, atlas);
    }

    public static TextureAtlas getAtlas(String key) {
        return atlasMap.get(key);
    }

    public static void putSound(String key, Sound sound) {
        soundMap.put(key, sound);
    }

    public static Sound getSound(String key) {
        return soundMap.get(key);
    }

    public static void putFont(String key, BitmapFont font) {
        fontMap.put(key, font);
    }

    public static BitmapFont getFont(String key) {
        return fontMap.get(key);
    }

}
