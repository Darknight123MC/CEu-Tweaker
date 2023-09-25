package me.oganesson.ceu_tweaker.api.capability.impl;

import me.oganesson.ceu_tweaker.common.metatileentities.part.MetaTileEntityManaImportHatch;
import vazkii.botania.api.mana.IManaReceiver;

public class ManaReceiverHandler implements IManaReceiver {
    private MetaTileEntityManaImportHatch importHatch;

    public ManaReceiverHandler(MetaTileEntityManaImportHatch importHatch) {
        this.importHatch = importHatch;
    }

    @Override
    public boolean isFull() {
        return importHatch.isFull();
    }

    @Override
    public void recieveMana(int i) {
        importHatch.recieveMana(i);
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return importHatch.canRecieveManaFromBursts();
    }

    @Override
    public int getCurrentMana() {
        return importHatch.getCurrentMana();
    }
}
