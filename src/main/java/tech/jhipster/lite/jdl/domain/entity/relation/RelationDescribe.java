package tech.jhipster.lite.jdl.domain.entity.relation;

import com.google.common.base.CaseFormat;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.Assert;

public record RelationDescribe(String source, String injectedField, String displayField, boolean isInjectedFieldRequired, String comment) {

    public static RelationDescribeBuilder relationDescribeBuilder() {
        return new RelationDescribeBuilder();
    }

    public RelationDescribe(String source, String injectedField, String displayField, boolean isInjectedFieldRequired, String comment) {
        Assert.field("source", source).notBlank();
        Assert.field("injectedFieldIn", injectedField).notBlank();


        this.source = WordUtils.upperFirst(source);
        if (injectedField == null) {
            injectedField =  WordUtils.lowerFirst(source);
        }
        this.injectedField = WordUtils.lowerFirst(injectedField);
        this.isInjectedFieldRequired = isInjectedFieldRequired;
        if (displayField == null) {
            displayField = "id";
        }
        this.displayField = WordUtils.lowerFirst(displayField);
        this.comment = comment;
    }

    public static final class RelationDescribeBuilder {
        private String source;
        private String injectedField;

        private String displayField;
        private boolean isInjectedFieldRequired = false;
        private String comment;

        private RelationDescribeBuilder() {
        }

        public String getInjectedField() {
            return injectedField;
        }

        public String getSource() {
            return source;
        }

        public RelationDescribeBuilder source(String source) {
            Assert.field("source", source).notBlank();
            this.source = source;
            return this;
        }

        public RelationDescribeBuilder injectedField(String injectedField) {
            Assert.field("injectedField", injectedField).notBlank();
            this.injectedField = WordUtils.lowerFirst(injectedField);
            return this;
        }
        public RelationDescribeBuilder displayField(String displayField) {
            Assert.field("displayField", displayField).notBlank();
            this.displayField = displayField;
            return this;
        }

        public RelationDescribeBuilder isInjectedFieldRequired(boolean isInjectedFieldRequired) {
            this.isInjectedFieldRequired = isInjectedFieldRequired;
            return this;
        }

        public RelationDescribeBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public RelationDescribe build() {

            return new RelationDescribe(source, injectedField, displayField, isInjectedFieldRequired, comment);
        }
    }
}
