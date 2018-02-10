package cn.way.material_progressbar;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  : A {@code Drawable} that has a background.
 * </pre>
 */

public interface ShowBackgroundDrawable {
    /**
     * Get whether this drawable is showing a background. The default is {@code true}.
     *
     * @return Whether this drawable is showing a background.
     */
    boolean getShowBackground();

    /**
     * Set whether this drawable should show a background. The default is {@code true}.
     *
     * @param show Whether background should be shown.
     */
    void setShowBackground(boolean show);
}
