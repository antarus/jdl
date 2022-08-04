package tech.jhipster.lite.jdl.domain.config;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class ConfigApp {

    public static ConfigAppBuilder configBuilder() {
        return new ConfigAppBuilder();
    }

    private JHipsterProjectFolder projectFolder;
    private ConfigFluentMethod fluentMethod;

    private ConfigBaseName configBaseName;
    private ConfigBasePackage configBasePackage;
    private ConfigEntityPath configEntityPath;
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

    public ConfigEntityPath getConfigEntityPath() {
        return configEntityPath;
    }

    public ConfigFluentMethod getFluentMethod() {
        return fluentMethod;
    }

    public JHipsterProjectFolder getProjectFolder() {
        return projectFolder;
    }

    @Override
    public String toString() {
        return "ConfigApp{" +
                "projectFolder=" + projectFolder +
                ", fluentMethod=" + fluentMethod +
                ", configBaseName=" + configBaseName +
                ", configBasePackage=" + configBasePackage +
                ", configEntityPath=" + configEntityPath +
                ", configBuildTool=" + configBuildTool +
                ", notManaged=" + notManaged +
                '}';
    }

    public static final class ConfigAppBuilder {
        private ConfigFluentMethod fluentMethod;
        private ConfigBaseName configBaseName;
        private ConfigBasePackage configBasePackage;
        private ConfigEntityPath configEntityPath;
        private ConfigBuildTool configBuildTool;
        private final Collection<ConfigNotManaged> notManaged = new ArrayList<>();

        private JHipsterProjectFolder projectFolder;
        private ConfigAppBuilder() {
        }

        public ConfigAppBuilder projectFolder(JHipsterProjectFolder projectFolder) {
            this.projectFolder = projectFolder;
            return this;
        }
        public ConfigAppBuilder fluentMethod(ConfigFluentMethod fluentMethod) {
            this.fluentMethod = fluentMethod;
            return this;
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
            if (this.fluentMethod == null) {
                this.fluentMethod = new ConfigFluentMethod(true);
            }
            if (this.configBasePackage == null) {
                this.configBasePackage = new ConfigBasePackage("");
            }
            if (this.configEntityPath == null) {
                this.configEntityPath = new ConfigEntityPath("");
            }
            if (this.projectFolder == null){
                this.projectFolder = new JHipsterProjectFolder("/tmp/" + UUID.randomUUID().toString());
            }
            configApp.projectFolder=this.projectFolder;
            configApp.fluentMethod=this.fluentMethod;
            configApp.configBuildTool = this.configBuildTool;
            configApp.configBasePackage = this.configBasePackage;
            configApp.configEntityPath = this.configEntityPath;
            configApp.notManaged = this.notManaged;
            configApp.configBaseName = this.configBaseName;
            return configApp;
        }
    }
}
