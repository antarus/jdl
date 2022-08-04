package tech.jhipster.lite.jdl.infrastructure.secondary;

import tech.jhipster.lite.jdl.antlr.JdlBaseListener;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.*;

public class ConfigListenerJdl extends JdlBaseListener {

    private ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.configBuilder();

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

    @Override
    public void exitProdDatabaseType(JdlParser.ProdDatabaseTypeContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitProdDatabaseType(ctx);
    }

    @Override
    public void exitServiceDiscoveryType(JdlParser.ServiceDiscoveryTypeContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitServiceDiscoveryType(ctx);
    }

    @Override
    public void exitSearchEngine(JdlParser.SearchEngineContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitSearchEngine(ctx);
    }

    @Override
    public void exitServerPort(JdlParser.ServerPortContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitServerPort(ctx);
    }

    @Override
    public void exitCacheProvider(JdlParser.CacheProviderContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitCacheProvider(ctx);
    }

    @Override
    public void exitAuthenticationType(JdlParser.AuthenticationTypeContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitAuthenticationType(ctx);
    }

    @Override
    public void exitLanguages(JdlParser.LanguagesContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitLanguages(ctx);
    }

    @Override
    public void exitApplicationType(JdlParser.ApplicationTypeContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitApplicationType(ctx);
    }

    @Override
    public void exitClientFramework(JdlParser.ClientFrameworkContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitClientFramework(ctx);
    }

    @Override
    public void exitUseSass(JdlParser.UseSassContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitUseSass(ctx);
    }

    @Override
    public void exitNativeLanguage(JdlParser.NativeLanguageContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitNativeLanguage(ctx);
    }

    @Override
    public void exitSkipUserManagement(JdlParser.SkipUserManagementContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitSkipUserManagement(ctx);
    }

    @Override
    public void exitEnableTranslation(JdlParser.EnableTranslationContext ctx) {
        configBuilder.addNotManaged(new ConfigNotManaged(ctx.getChild(0).getText()));
        super.exitEnableTranslation(ctx);
    }
}
