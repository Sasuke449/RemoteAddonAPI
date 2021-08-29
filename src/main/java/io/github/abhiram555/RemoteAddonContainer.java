package io.github.abhiram555;

import io.github.abhiram555.exception.InvalidAddonException;
import io.github.abhiram555.loader.AddonClassLoader;
import io.github.abhiram555.model.Addon;
import io.github.abhiram555.model.AddonInfo;
import io.github.abhiram555.model.AddonSettings;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class RemoteAddonContainer <T>{
    private final ArrayList<T> addonmainclasses = new ArrayList<>();
    private final ArrayList<AddonClassLoader> classLoaders = new ArrayList<>();
    private final AddonSettings addonSettings = new AddonSettings();
    private final Plugin plugin;

    public RemoteAddonContainer(Plugin plugin){
        this.plugin = plugin;
    }

    public void loadAddon(File file) throws Exception {
        if(!file.getName().endsWith(".jar")){
            throw new InvalidAddonException(file.getName() + " is not a jar file!");
        }

        AddonInfo info = this.getAddonInfo(file);

        AddonClassLoader<T> addonClassLoader = new AddonClassLoader(getClass().getClassLoader(),file,info,this);
        this.classLoaders.add(addonClassLoader);
        T mainclazz = (T) addonClassLoader.getMainClass();
        Addon addon = (Addon) mainclazz;
        addon.setAddonInfo(info);
        addonmainclasses.add(mainclazz);

    }

    public AddonSettings getAddonSettings(){
        return this.addonSettings;
    }


    public Class<?> getClassByName(String name, boolean resolve){
        for(AddonClassLoader classLoader : this.classLoaders){
            try{
                return classLoader.loadClass0(name,resolve,true);
            }catch (ClassNotFoundException cnfe){
                // INGORE
            }
        }

        return null;
    }

    private AddonInfo getAddonInfo(File jar) throws Exception {
        JarFile jarFile = new JarFile(jar);
        JarEntry mainfile = jarFile.getJarEntry(addonSettings.getMainFest());

        if(mainfile == null){
            throw new InvalidAddonException("manifest not found in " + jar.getName());
        }

        InputStream stream = jarFile.getInputStream(mainfile);
        return new AddonInfo(stream);
    }

    public Plugin getPlugin(){
        return this.plugin;
    }

    public ArrayList<T> getAddonClasses(){
        return this.addonmainclasses;
    }
}
