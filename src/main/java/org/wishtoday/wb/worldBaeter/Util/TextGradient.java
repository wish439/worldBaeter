package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import java.util.HashMap;
import java.util.Map;

public class TextGradient {
    private static final Map<String, Component> GRADIENT_CACHE = new HashMap<>();

    public static Component createGradient(String text, TextColor... colors) {
        String cacheKey = buildCacheKey(text, colors);
        if (GRADIENT_CACHE.containsKey(cacheKey)) {
            return GRADIENT_CACHE.get(cacheKey);
        }

        TextComponent.Builder builder = Component.text();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / Math.max(1, length - 1);
            TextColor color = interpolateColors(colors, ratio);
            builder.append(Component.text(text.charAt(i)).color(color));
        }

        Component result = builder.build();
        GRADIENT_CACHE.put(cacheKey, result);
        return result;
    }

    private static TextColor interpolateColors(TextColor[] colors, double ratio) {
        if (colors.length == 1) return colors[0];

        double segment = 1.0 / (colors.length - 1);
        int index = (int) Math.min(colors.length - 2, Math.floor(ratio / segment));
        double localRatio = (ratio - index * segment) / segment;

        return interpolate(
                colors[index],
                colors[index + 1],
                localRatio
        );
    }

    private static TextColor interpolate(TextColor start, TextColor end, double ratio) {
        int r = interpolateChannel((start.value() >> 16) & 0xFF, (end.value() >> 16) & 0xFF, ratio);
        int g = interpolateChannel((start.value() >> 8) & 0xFF, (end.value() >> 8) & 0xFF, ratio);
        int b = interpolateChannel(start.value() & 0xFF, end.value() & 0xFF, ratio);

        return TextColor.color(r, g, b);
    }

    private static int interpolateChannel(int start, int end, double ratio) {
        return (int) (start + ratio * (end - start));
    }

    private static String buildCacheKey(String text, TextColor[] colors) {
        StringBuilder key = new StringBuilder(text);
        for (TextColor color : colors) {
            key.append("-").append(color.value());
        }
        return key.toString();
    }
}