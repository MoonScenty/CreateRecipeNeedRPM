package me.moonscenty.createrecipeneedrpm.foundation.utility;

import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import net.createmod.catnip.lang.LangBuilder;
import net.createmod.catnip.lang.LangNumberFormat;

public final class CreateRecipeNeedRPMLang {

    private CreateRecipeNeedRPMLang() {
    }

    public static LangBuilder builder() {
        return new LangBuilder(CreateRecipeNeedRPM.MOD_ID);
    }

    public static LangBuilder translate(String key, Object... args) {
        return builder().translate(key, args);
    }

    public static LangBuilder number(double value) {
        return builder().text(LangNumberFormat.format(value));
    }
}