package me.oganesson.ceu_tweaker.common.metatileentities.part;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.base.Predicates;
import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.custom.FireboxActiveRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import me.oganesson.ceu_tweaker.api.capability.CTMultiblockAbility;
import me.oganesson.ceu_tweaker.api.capability.impl.ManaReceiverHandler;
import me.oganesson.ceu_tweaker.client.texture.CTTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.ManaNetworkEvent;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.item.ItemTwigWand;

import java.util.List;

public class MetaTileEntityManaImportHatch extends MetaTileEntityMultiblockPart implements IMultiblockAbilityPart<IManaReceiver>, ISparkAttachable {
    public static int max_mana = 1000000;
    protected int mana;

    public MetaTileEntityManaImportHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        max_mana = tier * 1000000;
    }

    @Override
    public void invalidate() {
        ManaNetworkEvent.removeCollector(getWorld().getTileEntity(getPos()));
        super.invalidate();
    }

    @Override
    public void onLoad() {
        ManaNetworkEvent.addCollector(getWorld().getTileEntity(getPos()));
        super.onLoad();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityManaImportHatch(this.metaTileEntityId, this.getTier());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()){
            CTTextures.MANA_HATCH.renderSided(getFrontFacing(), renderState, translation, pipeline);
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public MultiblockAbility<IManaReceiver> getAbility() {
        return CTMultiblockAbility.IMPORT_MANA;
    }

    @Override
    public void registerAbilities(List<IManaReceiver> list) {
        list.add(new ManaReceiverHandler(this));
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        if (data.hasKey("mana_storage")) {
            this.mana = data.getInteger("mana_storage");
        } else {

        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("mana_storage", mana);
        return data;
    }

    @Override
    public boolean isFull() {
        return mana >= max_mana;
    }

    @Override
    public void recieveMana(int mana) {
        int old = this.mana;
        this.mana = Math.max(0, Math.min(this.getCurrentMana() + mana, max_mana));
        if (old != this.mana) {
            this.getWorld().updateComparatorOutputLevel(this.getPos(), this.getWorld().getBlockState(this.getPos()).getBlock());
        }
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        int space = Math.max(0, max_mana - getCurrentMana());
        if (space > 0)
            return space;
        else
            return 0;
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List<Entity> sparks = getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos().up(), getPos().up().add(1, 1, 1)), Predicates.instanceOf(ISparkEntity.class));
        if (sparks.size() == 1) {
            Entity e = sparks.get(0);
            return (ISparkEntity) e;
        }
        return null;
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        if (playerIn.getHeldItemMainhand().getItem() instanceof ItemTwigWand) {
            if (getWorld().isRemote) {
                renderHUD(Minecraft.getMinecraft(), new ScaledResolution(Minecraft.getMinecraft()));
                return true;
            } else return false;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderHUD(Minecraft mc, ScaledResolution res) {
        HUDHandler.drawSimpleManaHUD(0xFFFFFF, mana, max_mana, I18n.format("ceutweaker.mte.mana_import.name"), res);

    }
}
