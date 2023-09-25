package me.oganesson.ceu_tweaker;

import me.oganesson.ceu_tweaker.client.texture.CTTextures;
import me.oganesson.ceu_tweaker.common.CommonProxy;
import me.oganesson.ceu_tweaker.common.metatileentities.CTMetaTileEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = "ceu_tweaker",
        name = "CEuTweaker",
        version = "2.1.4",
        dependencies = "required:forge@[14.23.5.2847,);required-after:codechickenlib@[3.2.3,);required-after:gregtech@[2.7.2-beta,);after:crafttweaker"
)
public class CEuTweaker {

    public static final String MODID = "ceu_tweaker";

    @SidedProxy(
            clientSide = "me.oganesson.ceu_tweaker.client.ClientProxy",
            serverSide = "me.oganesson.ceu_tweaker.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        CTMetaTileEntities.init();
        proxy.preLoad();
    }

}
