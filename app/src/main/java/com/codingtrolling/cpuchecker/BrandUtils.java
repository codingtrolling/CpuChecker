package com.codingtrolling.cpuchecker;

import android.os.Build;
import android.widget.ImageView;

public class BrandUtils {
    public static void applyLogo(ImageView view) {
        String man = Build.MANUFACTURER.toLowerCase();
        String hw = Build.HARDWARE.toLowerCase();
        String board = Build.BOARD.toLowerCase();

        if (hw.contains("qcom") || hw.contains("snapdragon") || board.contains("qcom") || board.contains("msm")) {
            view.setImageResource(R.drawable.snapdragon);
        } else if (hw.contains("mt") || hw.contains("mediatek") || board.contains("mt")) {
            view.setImageResource(R.drawable.mediatek);
        } else if (hw.contains("exynos") || board.contains("exynos")) {
            view.setImageResource(R.drawable.exynos);
        } else if (man.contains("xiaomi") || man.contains("redmi") || man.contains("poco")) {
            view.setImageResource(R.drawable.logo_xiaomi);
        } else if (man.contains("samsung")) {
            view.setImageResource(R.drawable.logo_samsung);
        } else if (man.contains("google")) {
            view.setImageResource(R.drawable.logo_google);
        } else {
            view.setImageResource(R.drawable.logo_generic);
        }
    }
}
