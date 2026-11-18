package com.kavkovskyi.helloapp;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Ten sam blok co w MainActivity (odsunięcie treści od pasków systemowych),
 * ale z zachowaniem paddingu z XML (np. 24dp), żeby nie powtarzać kodu w każdej Activity.
 */
final class EdgeToEdgeHelper {

    private EdgeToEdgeHelper() { }

    static void apply(View root) {
        final int left = root.getPaddingLeft();
        final int top = root.getPaddingTop();
        final int right = root.getPaddingRight();
        final int bottom = root.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(left + bars.left, top + bars.top, right + bars.right, bottom + bars.bottom);
            return insets;
        });
    }
}
