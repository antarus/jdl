package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;

public class ConfigVisitor {

    public static class ConfigVisitorJdl extends JdlBaseVisitor<ConfigApp> {
        @Override
        public ConfigApp visitConfig(JdlParser.ConfigContext ctx) {
            ConfigApp.ConfigAppBuilder configBuilder = ConfigApp.ConfigAppBuilder.aConfigApp();

            if (ctx.configbody().baseName().size() == 0) {
                throw new RuntimeException(" baseName is mandatory");
            } else if (ctx.configbody().baseName().size() > 1) {
                throw new RuntimeException(" duplicate baseName ");
            }
            if (ctx.configbody().packageName().size() == 0) {
                throw new RuntimeException(" packageName is mandatory");
            } else if (ctx.configbody().packageName().size() > 1) {
                throw new RuntimeException(" duplicate packageName ");
            }
            if (ctx.configbody().buildTool().size() == 0) {
                throw new RuntimeException(" buildTool is mandatory");
            } else if (ctx.configbody().buildTool().size() > 1) {
                throw new RuntimeException(" duplicate buildTool ");
            }
          return   configBuilder.configBaseName(new BaseNameVisitor().visitBaseName(ctx.configbody().baseName().get(0)))
                    .configBasePackage(new PackageNameVisitor().visitPackageName(ctx.configbody().packageName().get(0)))
                    .configBuildTool(new BuildToolVisitor().visitBuildTool(ctx.configbody().buildTool().get(0))).build();

        }
    }

    private static class BaseNameVisitor extends JdlBaseVisitor<ConfigBaseName> {
        @Override
        public ConfigBaseName visitBaseName(JdlParser.BaseNameContext ctx) {
            return new ConfigBaseName(ctx.label().getText());
        }
    }

    private static class PackageNameVisitor extends JdlBaseVisitor<ConfigBasePackage> {
        @Override
        public ConfigBasePackage visitPackageName(JdlParser.PackageNameContext ctx) {
            return new ConfigBasePackage(ctx.label().getText());
        }
    }


    private static class BuildToolVisitor extends JdlBaseVisitor<ConfigBuildTool> {
        @Override
        public ConfigBuildTool visitBuildTool(JdlParser.BuildToolContext ctx) {
            return new ConfigBuildTool(ctx.getText());
        }
    }
}