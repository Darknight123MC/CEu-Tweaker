package me.oganesson.ceu_tweaker.common.metatileentities;

import me.oganesson.ceu_tweaker.common.metatileentities.part.MetaTileEntityManaImportHatch;

import java.util.ArrayList;

import static gregtech.api.util.GTUtility.gregtechId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

public class CTMetaTileEntities {

    public static final ArrayList<MetaTileEntityManaImportHatch> IMPORT_MANA_HATCH = new ArrayList<>();

    public CTMetaTileEntities() {}

    public static void init() {
        IMPORT_MANA_HATCH.forEach(var1 -> {
            int index = IMPORT_MANA_HATCH.indexOf(var1);
            var1 = registerMetaTileEntity(30000 + index + 1,
                    new MetaTileEntityManaImportHatch(gregtechId("mana_import.tier_" + index + 1), index));
        });
    }

}
