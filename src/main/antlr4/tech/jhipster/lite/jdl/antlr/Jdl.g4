grammar Jdl;

file_
   : (config | application |  entity | relationship | paginate | service | enumType | comment )+ EOF
   ;

application
   : 'application' applicationbody
   ;

config
   : 'config'  configbody
   ;

annotation
    : '@' label
    | '@' label LPAREN IDENTIFIER RPAREN
    | '@service' LPAREN IDENTIFIER RPAREN
    | '@paginate' LPAREN IDENTIFIER RPAREN
    ;

entities
    : 'entities' STAR
    | 'entities' label (COMMA label)*
    ;

entity
    : beforeEntity IDENTIFIER entityBody
    | beforeEntity IDENTIFIER entityTableName entityBody
    ;

entityTableName
    : LPAREN IDENTIFIER RPAREN
    ;

beforeEntity
    : 'entity'
    | (annotation)* 'entity'
    | comment (annotation)* 'entity'
    | (annotation)* comment 'entity'
    ;

relationship
    : 'relationship' RELATION_SHIP_TYPE relationShipBody
    ;

paginate
    : 'paginate' label (COMMA label)* 'with' paginatewith
    ;

enumType
    : 'enum' IDENTIFIER enumBody
    ;

baseName
   : 'baseName' label
   | 'baseName' label COMMA
   ;

applicationType
    : 'applicationType' applicationTypeValues
    | 'applicationType' applicationTypeValues COMMA
    ;

applicationTypeValues
    : 'microservice'
    | 'gateway'
    | 'monolith'
    ;

packageName
    : 'packageName' label
    | 'packageName' label COMMA
    ;

serviceDiscoveryType
    : 'serviceDiscoveryType' BOOL
    | 'serviceDiscoveryType' BOOL COMMA
    ;

cacheProvider
    : 'cacheProvider' BOOL
    | 'cacheProvider' BOOL COMMA
    ;

buildTool
    : 'buildTool' BUILDTOOL_TYPE
    | 'buildTool' BUILDTOOL_TYPE COMMA
    ;

authenticationType
        : 'authenticationType' AUTHENTICATION_TYPE
        | 'authenticationType' AUTHENTICATION_TYPE COMMA
        ;

clientFramework
    : 'clientFramework' CLIENT_TYPE
    |'clientFramework' CLIENT_TYPE COMMA
    ;

searchEngine
    : 'searchEngine' SEARCH_ENGINE
    | 'searchEngine' SEARCH_ENGINE COMMA
    ;

prodDatabaseType
    : 'prodDatabaseType' DATABASE_TYPE
    | 'prodDatabaseType' DATABASE_TYPE COMMA
    ;

useSass
    : 'useSass' BOOL
    | 'useSass' BOOL COMMA
    ;

testFrameworks
    : 'testFrameworks' testFrameworksBody
    | 'testFrameworks' testFrameworksBody COMMA
    ;

languages
    : 'languages' languagesBody
    | 'languages' languagesBody COMMA
    ;

reactive
    : 'reactive' BOOL
    | 'reactive' BOOL COMMA
    ;

//block
//   : blocktype label* blockbody
//   ;

serverPort
    : 'serverPort' NATURAL_NUMBER
    | 'serverPort' NATURAL_NUMBER COMMA
    ;

nativeLanguage
    : 'nativeLanguage'  label (COMMA label)*
    | 'nativeLanguage'  label (COMMA label)* COMMA
    ;

skipUserManagement
    : 'skipUserManagement' BOOL
    | 'skipUserManagement' BOOL COMMA
    ;

enableTranslation
    : 'enableTranslation' BOOL
    | 'enableTranslation' BOOL COMMA
    ;

//blocktype
//   : IDENTIFIER
//   ;

enumBody
    : LCURL enumLabel (COMMA enumLabel)*  RCURL
    | LCURL (enumLabel COMMA?)* RCURL
    | LCURL enumLabel*  RCURL
    ;

entityBody
    : LCURL (entityField)*  RCURL
    ;

relationShipBody
    : LCURL (relation)*  RCURL
    ;

relation
    : relationFrom TO relationTo COMMA?
//    | relationFrom TO comment? IDENTIFIER COMMA?
//    | relationFrom TO comment? IDENTIFIER LCURL identifierProperty REQUIRED? RCURL COMMA?
    ;

relationFrom
    : relationDescribe
    ;

relationTo
    : relationDescribe
    ;

relationDescribe
    : comment? IDENTIFIER
    | comment? IDENTIFIER LCURL identifierProperty REQUIRED? RCURL
    ;


identifierProperty
    : IDENTIFIER
    | IDENTIFIER LPAREN IDENTIFIER RPAREN
    ;

entityField
    : comment? IDENTIFIER FIELD_TYPE_NUMBER comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)* (minMaxNumberValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* (validation)* comment? COMMA?
//    : comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (validation)* (minMaxNumberValidator)*
//    | comment? IDENTIFIER FIELD_TYPE_NUMBER (minMaxNumberValidator)* (validation)*
    | comment? IDENTIFIER FIELD_TYPE_BLOB comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_BLOB (validation)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_BLOB (validation)* (minMaxByteValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_BLOB (minMaxByteValidator)* (validation)* comment? COMMA?

    | comment? IDENTIFIER FIELD_TYPE_STRING comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_STRING (validation | validatorPattern)* (minMaxStringValidator)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_STRING (minMaxStringValidator)* (validation | validatorPattern)* comment? COMMA?

    | comment? IDENTIFIER FIELD_TYPE_TIME comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_TIME (validation)* comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_OTHER comment? COMMA?
    | comment? IDENTIFIER FIELD_TYPE_OTHER (validation)* comment? COMMA?
    | comment? IDENTIFIER IDENTIFIER comment? COMMA?
    | comment? IDENTIFIER IDENTIFIER (validation)* comment? COMMA?
    ;

testFrameworksBody
    : LSQUARE  RSQUARE
    | LSQUARE label (COMMA label)* RSQUARE
    ;

languagesBody
    : LSQUARE  RSQUARE
    | LSQUARE label (COMMA label)* RSQUARE
    ;

enumLabel
    : IDENTIFIER_UPPER
    | IDENTIFIER_UPPER LPAREN STRING RPAREN
    ;

label
   : STRING
   | IDENTIFIER
   ;


applicationbody
  : LCURL (config | entities)* RCURL
  ;

configbody
   : LCURL (baseName
   | applicationType
   | packageName
   | serviceDiscoveryType
   | authenticationType
   | prodDatabaseType
   | cacheProvider
   | buildTool
   | clientFramework
   | languages
   | searchEngine
   | useSass
   | testFrameworks
   | reactive
   | serverPort
   | enableTranslation
   | nativeLanguage
   | skipUserManagement)* RCURL
   ;

//blockbody
//   : LCURL (block)* RCURL
//   ;

paginatewith
    : 'pagination'
    | 'infinite-scroll'
    ;

servicewith
    : 'serviceClass'
    ;

service
    : 'service' label 'with' servicewith
    ;

comment
    : BLOCKCOMMENT
    ;

//   : LCURL (argument | block)* RCURL

//identifier
//    : IdentifierChars;
//argument
//   : identifier '=' expression
//   ;
//
//identifier
//   : (('config' | 'entities' | 'var' | 'module') DOT)? identifierchain
//   ;
//
//identifierchain
//   : (IDENTIFIER | IN | VARIABLE | PROVIDER)  (DOT identifierchain)*
//   | STAR (DOT identifierchain)*
//   | inline_index (DOT identifierchain)*
//   ;
//
//inline_index
//   : NATURAL_NUMBER
//   ;

//expression
//   : expression operator_ expression
//   | LPAREN expression RPAREN
//   | expression '?' expression ':' expression
//   ;

string
   : STRING
   | MULTILINESTRING
   ;

fragment DIGIT
   : [0-9]
   ;

fragment RESTOFLINE
        :   ~('\r'|'\n')*;

validatorPattern
    : 'pattern' LPAREN STRING_LITERAL* RPAREN
    ;

validation
    : REQUIRED
    | 'unique'
    ;

minMaxNumberValidator
    : 'min' LPAREN NATURAL_NUMBER RPAREN
    | 'max' LPAREN NATURAL_NUMBER RPAREN
    ;

minMaxStringValidator
    : 'minlength' LPAREN NATURAL_NUMBER RPAREN
    | 'maxlength' LPAREN NATURAL_NUMBER RPAREN

    ;
minMaxByteValidator
    : 'minbytes' LPAREN NATURAL_NUMBER RPAREN
    | 'maxbytes' LPAREN NATURAL_NUMBER RPAREN
    ;


//
//FIELD_TYPE
//    : FIELD_TYPE_STRING
//    | FIELD_TYPE_NUMBER
//    | FIELD_TYPE_BLOB
//    | FIELD_TYPE_TIME
//    | FIELD_TYPE_OTHER
//    ;
REQUIRED
    : 'required'
    ;

FIELD_TYPE_OTHER
    : 'UUID'
    | 'Enum'
    ;

FIELD_TYPE_STRING
    : 'String'
    ;

FIELD_TYPE_TIME
    : 'Instant'
    | 'LocalDate'
    | 'ZonedDateTime'
    | 'Duration'
    | 'Period'
    ;

FIELD_TYPE_BLOB
    : 'Blob'
    | 'AnyBlob'
    | 'ImageBlob'
    | 'TextBlob'
    ;

FIELD_TYPE_NUMBER
    : 'Long'
    | 'Integer'
    | 'BigDecimal'
    | 'Float'
    | 'Double'
    ;

AUTHENTICATION_TYPE
    : 'jwt'
    | 'oauth2'
    ;

BUILDTOOL_TYPE
    : 'maven'
    | 'gradle'
    ;

DATABASE_TYPE
    : 'mariadb'
    | 'postgresql'
    | 'mysql'
    ;

SEARCH_ENGINE
    : 'elasticsearch'
    ;

CLIENT_TYPE
    : 'angular'
    | 'vuejs'
    | 'react'
    ;

VARIABLE
   : 'variable'
   ;

PROVIDER
   : 'provider'
   ;


RELATION_SHIP_TYPE
    : RELATION_SHIP_ONE_TO_MANY
    | RELATION_SHIP_MANY_TO_ONE
    | RELATION_SHIP_MANY_TO_MANY
    | RELATION_SHIP_ONE_TO_ONE
    ;

RELATION_SHIP_MANY_TO_MANY
    : 'ManyToMany'
    ;

RELATION_SHIP_ONE_TO_ONE
    : 'OneToOne'
    ;

RELATION_SHIP_ONE_TO_MANY
    : 'OneToMany'
    ;

RELATION_SHIP_MANY_TO_ONE
    : 'ManyToOne'
    ;


IN
   : 'in'
   ;

TO
   : 'to'
   ;

STAR
   : '*'
   ;

DOT
   : '.'
   ;

COMMA
    : ','
    ;

operator_
   : '/'
   | STAR
   | '%'
   | '+'
   | '-'
   | '>'
   | '>='
   | '<'
   | '<='
   | '=='
   | '!='
   | '&&'
   | '||'
   ;

LCURL
   : '{'
   ;

RCURL
   : '}'
   ;
LSQUARE
    : '['
    ;
RSQUARE
    : ']'
    ;
LPAREN
   : '('
   ;

RPAREN
   : ')'
   ;

EOF_
   : '<<EOF' .*? 'EOF'
   ;

NULL_
   : 'nul'
   ;

NATURAL_NUMBER
   : DIGIT+
   ;

number
   : NATURAL_NUMBER (DOT NATURAL_NUMBER)?
   ;

BOOL
   : 'true'
   | 'false'
   | 'yes'
   | 'no'
   ;

DESCRIPTION
   : '<<DESCRIPTION' .*? 'DESCRIPTION'
   ;

MULTILINESTRING
   : '<<-EOF' .*? 'EOF'
   ;


STRING
   : '"' ( '\\"' | ~["\r\n] )* '"'
   | '\'' ( '\\\'' | ~["\r\n] )* '\''
   ;

IDENTIFIER_UPPER
    : [A-Z]
    | [A-Z][A-Z_]*;

fragment IDENTIFIER_NODE: [a-zA-Z]([a-zA-Z0-9_])* ;
IDENTIFIER
    : IDENTIFIER_NODE ('.' IDENTIFIER_NODE)*
    | IDENTIFIER_UPPER;

//STRING_LITERALS
//    : STRING_LITERAL+
//    ;

STRING_LITERAL: 'a'..'z' | 'A'..'Z' | '0'..'9' | ':' | DOT | '&' | LPAREN | RPAREN | LSQUARE | RSQUARE| '$' | '@' | '/' | '\\' | ';' |  '^';



COMMENT
  : ('#' | '//') ~ [\r\n]* -> channel(HIDDEN)
  ;

BLOCKCOMMENT
  : '/*' .*? '*/'
  ;

WS
   : [ \r\n\t]+ -> skip
   ;

NEWLINE :   '\r'? '\n' ;

