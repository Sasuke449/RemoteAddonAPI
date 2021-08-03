package io.github.abhiram555.loader;

import io.github.abhiram555.RemoteAddonContainer;
import io.github.abhiram555.exception.InvalidAddonException;
import io.github.abhiram555.model.Addon;
import io.github.abhiram555.model.AddonInfo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class AddonClassLoader<T> extends URLClassLoader {
    private final AddonInfo addonInfo;
    private RemoteAddonContainer<?> remoteAddonContainer;
    private T clazz;

    public AddonClassLoader(ClassLoader parant, File addon,AddonInfo addonInfo,RemoteAddonContainer<?> remoteAddonContainer) throws MalformedURLException {
        super(new URL[]{addon.toURI().toURL()},parant);
        this.addonInfo = addonInfo;
        this.remoteAddonContainer = remoteAddonContainer;

        try{
            Class<?> jarClass;
            try {
                jarClass = Class.forName(addonInfo.getClassPath(), true, this);
            } catch (ClassNotFoundException ex) {
                throw new InvalidAddonException("Cannot find main class `" + addonInfo.getClassPath() + "'");
            }

            Class<? extends T> pluginClass;
            try {
                pluginClass = (Class<? extends T>) jarClass.asSubclass(Addon.class);
            } catch (ClassCastException ex) {
                throw new InvalidAddonException("main class `" + addonInfo.getClassPath() + "' does not extend " + clazz.getClass().getName());
            }

            this.clazz = pluginClass.newInstance();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public URL getResource(String name) {
        return findResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        return findResources(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return loadClass0(name,resolve,true);
    }

    public Class<?> loadClass0(String name, boolean resolve, boolean checkGlobal) throws ClassNotFoundException {
        try{
            return super.loadClass(name,resolve);
        }catch (ClassNotFoundException classNotFoundException){
            // IGNORE
        }


        if(checkGlobal){
            Class<?> result = remoteAddonContainer.getClassByName(name,resolve);

            if(result != null){
                if(result.getClassLoader() instanceof AddonClassLoader){
                    AddonInfo addonInfo = ((AddonClassLoader) result.getClassLoader()).getAddonInfo();
                    remoteAddonContainer.getPlugin().getLogger().info("Loaded Class from addon " + addonInfo.getName() + " by " + addonInfo.getAuthor());
                }

                return result;
            }
        }

        throw new ClassNotFoundException(name);
    }

    public AddonInfo getAddonInfo(){
        return this.addonInfo;
    }

    @Override
    public void close() throws IOException {
        try {
            super.close();
        } catch (Exception exception){

        }
    }

    public T getMainClass(){
        return this.clazz;
    }
}
