# Remote Addon API
A api to create your own addon loading system for spigot plugins

# Feature's
- Uses own class loader to load addons

# Example 
You can see all the example's in src/main/java/me/abhiram/example


# Addon manifest format
```yaml
main: path.to.mainclass
name: addonname
version: "my verson 1.0"
author: authorname
```

# Maven Repo
```xml
    <repositories>
        <repository>
            <id>JitPack</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.abhiram555</groupId>
            <artifactId>RemoteAddonAPI</artifactId>
            <version>dev-19ca9e9f9f-1</version>
        </dependency>
    </dependencies>
```	