package tr4nt.autoplantcrops.config;

import io.wispforest.owo.config.annotation.Config;

@Config(name="atcconf", wrapperName="AutoCropsConf")
public class AutoCropsConfig {
    public boolean plantWhenWalkedOver = true;
    public boolean plantOneCrop = true;


}
