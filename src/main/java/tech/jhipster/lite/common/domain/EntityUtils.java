package tech.jhipster.lite.common.domain;

public class EntityUtils {
    public static String cleanComment(String comment) {
        return comment.replace("/*","").replace("*/","").trim();
    }
    public static String addQuote(String string) {
        return "\"" + string + "\"";
    }
}
