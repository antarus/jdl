grammar Jdl;

file_
   : (config | application | enumType | entity | relationship | paginate | service | comment )+ EOF
   ;

application
   : 'application' applicationbody
   ;

config
   : 'config'  configbody
   ;

annotation
    : '@' label
    | '@' label LPAREN label RPAREN
    | '@service' LPAREN label RPAREN
    | '@paginate' LPAREN label RPAREN
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
    : 'relationship' 'OneToMany' relationShipBody
    | 'relationship' 'ManyToOne' relationShipBody
    | 'relationship' 'ManyToMany' relationShipBody
    | 'relationship' 'OneToOne' relationShipBody
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

block
   : blocktype label* blockbody
   ;

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

blocktype
   : IDENTIFIER
   ;

enumBody
    : LCURL enumLabel (COMMA enumLabel)*  RCURL
    | LCURL enumLabel*  RCURL
    ;

entityBody
    : LCURL (field)*  RCURL
    ;

relationShipBody
    : LCURL (relation)*  RCURL
    ;

relation
    : IDENTIFIER TO IDENTIFIER
    | IDENTIFIER LCURL identifierProperty RCURL TO IDENTIFIER
    | IDENTIFIER LCURL identifierProperty RCURL TO IDENTIFIER LCURL identifierProperty RCURL
    | IDENTIFIER LCURL identifierProperty RCURL TO IDENTIFIER LCURL identifierProperty RCURL COMMA
    ;

identifierProperty
    : IDENTIFIER
    | IDENTIFIER LPAREN IDENTIFIER RPAREN
    ;

field
    : comment? IDENTIFIER FIELD_TYPE
    | comment? IDENTIFIER FIELD_TYPE (minmax)*
    | comment? IDENTIFIER FIELD_TYPE (validation)*
    | comment? IDENTIFIER FIELD_TYPE (validation)* (minmax)*
    | comment? IDENTIFIER IDENTIFIER (minmax)*
    | comment? IDENTIFIER IDENTIFIER (minmax)*
    | comment? IDENTIFIER IDENTIFIER (validation)*
    | comment? IDENTIFIER IDENTIFIER (validation)* (minmax)*
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

blockbody
   : LCURL (block)* RCURL
   ;

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
    : 'required'
    | 'unique'
    | validatorPattern
    ;

minValidator
    : 'minlength' LPAREN NATURAL_NUMBER RPAREN
    | 'minbytes' LPAREN NATURAL_NUMBER RPAREN
    | 'min' LPAREN NATURAL_NUMBER RPAREN
    ;
maxValidator
    : 'maxlength' LPAREN NATURAL_NUMBER RPAREN
    | 'maxbytes' LPAREN NATURAL_NUMBER RPAREN
    | 'max' LPAREN NATURAL_NUMBER RPAREN
    ;
minmax
    : minValidator
    | maxValidator
    ;

FIELD_TYPE
    : 'String'
    | 'Long'
    | 'Instant'
    | 'Integer'
    | 'BigDecimal'
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

STRING_LITERAL: 'a'..'z' | 'A'..'Z' | '0'..'9' | ':' | DOT | '&' | LPAREN | RPAREN | LSQUARE | RSQUARE| '$' | '@' | '/' | '\\' | ';' |  '^';


STRING
   : '"' ( '\\"' | ~["\r\n] )* '"'
   | '\'' ( '\\\'' | ~["\r\n] )* '\''
   ;

IDENTIFIER_UPPER
    : [A-Z]
    | [A-Z][A-Z_]*;

fragment IDENTIFIER_NODE: [a-zA-Z]([a-zA-Z0-9_])* ;
IDENTIFIER: IDENTIFIER_NODE ('.' IDENTIFIER_NODE)*;

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

