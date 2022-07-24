package com.jdl;

import com.jdl.antlr.JdlParser;
import org.antlr.v4.runtime.tree.ParseTree;
import com.jdl.antlr.JdlBaseListener;

public class ListenerJdl extends JdlBaseListener {

    @Override
    public void exitBaseName(JdlParser.BaseNameContext ctx) {
//        System.out.println("BaseName : " + ctx.getText());
        System.out.println("BaseName : " + ctx.label().getText());
        super.exitBaseName(ctx);

    }

    @Override
    public void exitApplicationType(JdlParser.ApplicationTypeContext ctx) {
        System.out.println("applicationType : " + ctx.getChild(1));
        super.exitApplicationType(ctx);
    }

    @Override
    public void exitPackageName(JdlParser.PackageNameContext ctx) {
        System.out.println("PackageName : " + ctx.label().getText());
        super.exitPackageName(ctx);
    }

    @Override
    public void exitServiceDiscoveryType(JdlParser.ServiceDiscoveryTypeContext ctx) {

        System.out.println("ServiceDiscoveryType : " + ctx.getChild(1));
        super.exitServiceDiscoveryType(ctx);
    }

    @Override
    public void exitCacheProvider(JdlParser.CacheProviderContext ctx) {

        System.out.println("cacheProvider : " + ctx.getChild(1));
        super.exitCacheProvider(ctx);
    }

    @Override
    public void exitBuildTool(JdlParser.BuildToolContext ctx) {
        System.out.println("buildTool : " + ctx.getChild(1));

        super.exitBuildTool(ctx);
    }

    @Override
    public void exitUseSass(JdlParser.UseSassContext ctx) {
        System.out.println("useSass : " + ctx.getChild(1));
        super.exitUseSass(ctx);
    }

    @Override
    public void exitTestFrameworks(JdlParser.TestFrameworksContext ctx) {
        ParseTree lst = ctx.getChild(1);

        for (int i = 1; i < lst.getChildCount()-1; i++) {
            if (!",".equals(lst.getChild(i).getText())){
            System.out.println("testFramework : " + lst.getChild(i).getText());}
        }

        super.exitTestFrameworks(ctx);
    }

    @Override
    public void exitEnumType(JdlParser.EnumTypeContext ctx) {
        System.out.println("enumType Name: " + ctx.getChild(1));
        System.out.println("enumType content: " + ctx.getChild(2).getText());
        super.exitEnumType(ctx);
    }

    @Override
    public void exitSkipUserManagement(JdlParser.SkipUserManagementContext ctx) {
        System.out.println("SkipUserManagement : " + ctx.getChild(1));
        super.exitSkipUserManagement(ctx);
    }

    @Override
    public void exitRelationship(JdlParser.RelationshipContext ctx) {
        System.out.println("RelationShip : " + ctx.getText());
        super.exitRelationship(ctx);
    }

    @Override
    public void exitService(JdlParser.ServiceContext ctx) {
        System.out.println("Service : " + ctx.getText());
        super.exitService(ctx);
    }

    @Override
    public void exitPaginate(JdlParser.PaginateContext ctx) {
        System.out.println("Paginate : " + ctx.getText());
        super.exitPaginate(ctx);
    }

    @Override
    public void exitEntity(JdlParser.EntityContext ctx) {
        System.out.println("entity : " + ctx.getText());
        super.exitEntity(ctx);
    }

    @Override
    public void exitServerPort(JdlParser.ServerPortContext ctx) {
        System.out.println("ServerPort : " + ctx.getChild(1));
        super.exitServerPort(ctx);
    }

    @Override
    public void exitEntities(JdlParser.EntitiesContext ctx) {
        for (int i =1; i < ctx.getChildCount(); i++) {
            if (!",".equals(ctx.getChild(i).getText())){
                System.out.println("entities : " + ctx.getChild(i).getText());}
        }

        super.exitEntities(ctx);
    }

    @Override
    public void exitReactive(JdlParser.ReactiveContext ctx) {

        System.out.println("reactive : " + ctx.getChild(1));
        super.exitReactive(ctx);
    }

    @Override
    public void exitClientFramework(JdlParser.ClientFrameworkContext ctx) {
        System.out.println("clientFramework : " + ctx.getChild(1));

        super.exitClientFramework(ctx);
    }

    @Override
    public void exitProdDatabaseType(JdlParser.ProdDatabaseTypeContext ctx) {
        System.out.println("ProdDatabaseType : " + ctx.getChild(1));
        super.exitProdDatabaseType(ctx);
    }

    @Override
    public void exitAuthenticationType(JdlParser.AuthenticationTypeContext ctx) {
        System.out.println("AuthenticationType : " + ctx.getChild(1));
        super.exitAuthenticationType(ctx);
    }
}
