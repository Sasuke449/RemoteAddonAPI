package me.abhiram.example;

import io.github.abhiram555.RemoteAddonContainer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ExamplePlugin extends JavaPlugin {
    private RemoteAddonContainer<ExampleAddon> addonContainer;


    @Override
    public void onEnable(){
        this.addonContainer = new RemoteAddonContainer<>(this);

        addonContainer.getAddonSettings().setMainFest("abhi.yml"); // Supports only yaml

        try {
            addonContainer.loadAddon(new File("./addon/test.jar"));
        }catch (Exception exception){
            exception.printStackTrace();
        }

        for(ExampleAddon addon : addonContainer.getAddonClasses()){
            addon.onEnable();
        }
    }
}
