package me.oganesson.ceu_tweaker.client;

import me.oganesson.ceu_tweaker.client.texture.CTTextures;
import me.oganesson.ceu_tweaker.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber({Side.CLIENT})
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
    }

    public void preLoad() {
        super.preLoad();
        CTTextures.init();
    }
}