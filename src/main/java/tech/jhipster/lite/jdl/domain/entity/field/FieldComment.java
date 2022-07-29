package tech.jhipster.lite.jdl.domain.entity.field;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.common.domain.EntityUtils;
import tech.jhipster.lite.error.domain.Assert;

public record FieldComment(String comment) {

    public FieldComment(String comment) {
        this.comment = cleanComment(comment);
    }

    private String cleanComment(String comment) {
        Assert.field("comment", comment).notBlank();
        return EntityUtils.cleanComment(comment);
    }
    public String get() {
        return comment();
    }
}
