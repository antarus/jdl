package tech.jhipster.lite.jdl.domain.config;

import tech.jhipster.lite.error.domain.Assert;

import java.util.ArrayList;
import java.util.Collection;

public class ConfigApp {
    private ConfigBaseName configBaseName;
    private ConfigBasePackage configBasePackage;
    private ConfigBuildTool configBuildTool;

    private Collection<ConfigNotManaged> notManaged;

    public ConfigBaseName getConfigBaseName() {
        return configBaseName;
    }

    public ConfigBasePackage getConfigBasePackage() {
        return configBasePackage;
    }

    public ConfigBuildTool getConfigBuildTool() {
        return configBuildTool;
    }

    public Collection<ConfigNotManaged> getNotManaged() {
        return notManaged;
    }

    @Override
    public String toString() {
        return "ConfigApp{" +
                "configBaseName=" + configBaseName +
                ", configBasePackage=" + configBasePackage +
                ", configBuildTool=" + configBuildTool +
                ", notManaged=" + notManaged +
                '}';
    }

    public static final class ConfigAppBuilder {
        private ConfigBaseName configBaseName;
        private ConfigBasePackage configBasePackage;
        private ConfigBuildTool configBuildTool;
        private Collection<ConfigNotManaged> notManaged = new ArrayList<>();

        private ConfigAppBuilder() {
        }

        public static ConfigAppBuilder aConfigApp() {
            return new ConfigAppBuilder();
        }

        public ConfigAppBuilder configBaseName(ConfigBaseName configBaseName) {
            this.configBaseName = configBaseName;
            return this;
        }

        public ConfigAppBuilder configBasePackage(ConfigBasePackage configBasePackage) {
            this.configBasePackage = configBasePackage;
            return this;
        }

        public ConfigAppBuilder configBuildTool(ConfigBuildTool configBuildTool) {
            this.configBuildTool = configBuildTool;
            return this;
        }

        public ConfigAppBuilder addNotManaged(ConfigNotManaged notManaged) {
            Assert.notNull("notManaged", notManaged);
            this.notManaged.add(notManaged);
            return this;
        }

        public ConfigApp build() {
            ConfigApp configApp = new ConfigApp();
            configApp.configBuildTool = this.configBuildTool;
            configApp.configBasePackage = this.configBasePackage;
            configApp.notManaged = this.notManaged;
            configApp.configBaseName = this.configBaseName;
            return configApp;
        }
    }
}
