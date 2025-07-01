package org.wishtoday.wb.worldBaeter.Util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for generating coloured {@link Component}s with an optional gradient
 * <strong>and</strong> additional text decorations (bold, italic, underline, strikethrough).
 * <p>
 * The original public API remains intact so no existing code breaks.  You can
 * simply call the new overload when you need decorations.
 * <pre>
 * // 两端颜色渐变 + 加粗 + 下划线
 * Component title = TextGradient.createGradient(
 *         "World Baeter",
 *         new TextColor[]{ TextColor.color(0xFF5F6D), TextColor.color(0xFFC371) },
 *         TextDecoration.BOLD, TextDecoration.UNDERLINED);
 * </pre>
 */
public class TextGradient {

    /* ------------------------------------------------------------ */
    /* Internal cache for compiled gradients                         */
    /* ------------------------------------------------------------ */
    private static final Map<String, Component> GRADIENT_CACHE = new HashMap<>();

    /* ------------------------------------------------------------ */
    /* Public API                                                    */
    /* ------------------------------------------------------------ */

    /**
     * Original overload preserved for binary compatibility.
     */
    public static Component createGradient(String text, TextColor... colours) {
        return createGradient(text, colours, new TextDecoration[0]);
    }

    /**
     * Builds a gradient component from {@code text}, smoothly transitioning
     * through the supplied {@code colours}.  Any {@link TextDecoration}s passed
     * in (bold, italic, underline, strikethrough, etc.) are applied
     * <em>globally</em> after the gradient is built.
     *
     * @param text        Raw text to colour.
     * @param colours     At least one colour.  Two or more will produce a gradient.
     * @param decorations Optional text decorations.
     *
     * @return A cached, fully‑styled component.
     */
    public static Component createGradient(String text,
                                           TextColor[] colours,
                                           TextDecoration... decorations) {
        String cacheKey = buildCacheKey(text, colours, decorations);
        if (GRADIENT_CACHE.containsKey(cacheKey)) {
            return GRADIENT_CACHE.get(cacheKey);
        }

        // --- Build the gradient one glyph at a time ---------------------- //
        TextComponent.Builder builder = Component.text();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / Math.max(1, length - 1);
            TextColor colour = interpolateColours(colours, ratio);
            builder.append(Component.text(text.charAt(i)).color(colour));
        }

        Component result = builder.build();

        // --- Apply decorations globally ---------------------------------- //
        if (decorations != null) {
            for (TextDecoration deco : decorations) {
                result = result.decoration(deco, TextDecoration.State.TRUE);
            }
        }

        GRADIENT_CACHE.put(cacheKey, result);
        return result;
    }

    /* ------------------------------------------------------------ */
    /* Private helpers                                              */
    /* ------------------------------------------------------------ */

    private static String buildCacheKey(String text, TextColor[] colours, TextDecoration[] decos) {
        StringBuilder key = new StringBuilder(text);
        for (TextColor colour : colours) {
            key.append('-').append(colour.value());
        }
        if (decos != null) {
            for (TextDecoration deco : decos) {
                key.append('-').append(deco.name());
            }
        }
        return key.toString();
    }

    private static TextColor interpolateColours(TextColor[] colours, double ratio) {
        if (colours.length == 1) return colours[0];

        double segment = 1.0 / (colours.length - 1);
        int index = (int) Math.min(colours.length - 2, Math.floor(ratio / segment));
        double localRatio = (ratio - index * segment) / segment;

        return interpolate(
                colours[index],
                colours[index + 1],
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
}
