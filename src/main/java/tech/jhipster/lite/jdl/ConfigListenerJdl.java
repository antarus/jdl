package tech.jhipster.lite.jdl;

import tech.jhipster.lite.jdl.antlr.JdlBaseListener;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;

public class ConfigListenerJdl extends JdlBaseListener {

    private ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.ConfigAppBuilder.aConfigApp();

    private ConfigApp configApp;

    public ConfigApp getConfigApp() {
        return configApp;
    }

    @Override
    public void exitConfig(JdlParser.ConfigContext ctx) {
        System.out.println("config: " + ctx.getText());
        configApp = configBuilder.build();
        super.exitConfig(ctx);
    }

    @Override
    public void exitBaseName(JdlParser.BaseNameContext ctx) {
        ConfigBaseName confBaseName= new ConfigBaseName(ctx.label().getText()) ;
        configBuilder.configBaseName(confBaseName);
        super.exitBaseName(ctx);

    }

    @Override
    public void exitPackageName(JdlParser.PackageNameContext ctx) {
        ConfigBasePackage confBasePackage= new ConfigBasePackage(ctx.label().getText()) ;
        configBuilder.configBasePackage(confBasePackage);
        super.exitPackageName(ctx);
    }


    @Override
    public void exitBuildTool(JdlParser.BuildToolContext ctx) {
        ConfigBuildTool configBuildTool= new ConfigBuildTool(ctx.getText()) ;
        configBuilder.configBuildTool(configBuildTool);

        super.exitBuildTool(ctx);
    }
}
