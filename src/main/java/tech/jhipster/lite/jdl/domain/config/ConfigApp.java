package tech.jhipster.lite.jdl.domain.config;

public class ConfigApp {
    private ConfigBaseName configBaseName;
    private ConfigBasePackage configBasePackage;
    private ConfigBuildTool configBuildTool;

    @Override
    public String toString() {
        return "ConfigApp{" +
                "configBaseName=" + configBaseName +
                ", configBasePackage=" + configBasePackage +
                ", configBuildTool=" + configBuildTool +
                '}';
    }

    public static final class ConfigAppBuilder {
        private ConfigBaseName configBaseName;
        private ConfigBasePackage configBasePackage;
        private ConfigBuildTool configBuildTool;

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

        public ConfigApp build() {
            ConfigApp configApp = new ConfigApp();
            configApp.configBuildTool = this.configBuildTool;
            configApp.configBasePackage = this.configBasePackage;
            configApp.configBaseName = this.configBaseName;
            return configApp;
        }
    }
}
