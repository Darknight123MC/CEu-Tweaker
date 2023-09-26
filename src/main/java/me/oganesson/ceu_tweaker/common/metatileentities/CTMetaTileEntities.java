package me.oganesson.ceu_tweaker.common.metatileentities;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import me.oganesson.ceu_tweaker.common.metatileentities.part.MetaTileEntityManaImportHatch;
import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntities;

public class CTMetaTileEntities {

    public static final MetaTileEntityManaImportHatch[] IMPORT_MANA_HATCH = new MetaTileEntityManaImportHatch[16];

    public CTMetaTileEntities() {}

    public static void init() {
        registerMetaTileEntities(IMPORT_MANA_HATCH, 23456, "mana_import_hatch", (tier, voltageNamex) -> {
            MetaTileEntityManaImportHatch var10000;
            ResourceLocation var10002 = GTUtility.gregtechId(String.format("%s.%s", "mana.import", voltageNamex));

            var10000 = new MetaTileEntityManaImportHatch(var10002, tier);
            return var10000;
        });
    }

}
