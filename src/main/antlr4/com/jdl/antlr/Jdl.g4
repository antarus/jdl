grammar Jdl;

file_
   : (config | application | enumType | entity | relationship | paginate | service)+ EOF
   ;

application
   : 'application' applicationbody
   ;

config
   : 'config'  configbody
   ;

entities
    : 'entities' STAR
    | 'entities' label (COMMA label)*
    ;

entity
    : 'entity' IDENTIFIER entityBody
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

service
    : 'service' label 'with' servicewith
    ;

enumType
    : 'enum' IDENTIFIER enumBody
    ;

baseName
   : 'baseName' label
   ;

applicationType
    : 'applicationType' applicationTypeValues
    ;
applicationTypeValues
    : 'microservice'
    | 'gateway'
    ;

packageName
    : 'packageName' label
    ;

serviceDiscoveryType
    : 'serviceDiscoveryType' BOOL
    ;

cacheProvider
    : 'cacheProvider' BOOL
    ;
buildTool
    : 'buildTool' BUILDTOOL_TYPE
    ;
authenticationType
        : 'authenticationType' AUTHENTICATION_TYPE
        ;
clientFramework
    : 'clientFramework' CLIENT_TYPE
    ;

prodDatabaseType
    : 'prodDatabaseType' DATABASE_TYPE
    | 'prodDatabaseType' DATABASE_TYPE COMMA
    ;

useSass
    : 'useSass' BOOL
    ;

testFrameworks
    : 'testFrameworks' testFrameworksBody
    ;

reactive
    : 'reactive' BOOL
    ;

block
   : blocktype label* blockbody
   ;

serverPort
    : 'serverPort' NATURAL_NUMBER
    ;

skipUserManagement
    : 'skipUserManagement' BOOL
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
    : IDENTIFIER FIELD_TYPE
    | IDENTIFIER FIELD_TYPE (minmax)*
    | IDENTIFIER FIELD_TYPE (validation)*
    | IDENTIFIER FIELD_TYPE (validation)* (minmax)*
    | IDENTIFIER IDENTIFIER (validation)*
    ;

testFrameworksBody
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
   | useSass
   | testFrameworks
   | reactive
   | serverPort
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

expression
   : expression operator_ expression
   | LPAREN expression RPAREN
   | expression '?' expression ':' expression
   ;

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
//    | IDENTIFIER
    ;
APPLICATION_TYPE
    : 'microservice'
    | 'gateway'
    ;

AUTHENTICATION_TYPE
    : 'jwt'
    | 'oauth'
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
//IDENTIFIER: [a-zA-Z]([a-zA-Z0-9_])* ;

COMMENT
  : ('#' | '//') ~ [\r\n]* -> channel(HIDDEN)
  ;

BLOCKCOMMENT
  : '/*' .*? '*/' -> channel(HIDDEN)
  ;

WS
   : [ \r\n\t]+ -> skip
   ;

NEWLINE :   '\r'? '\n' ;

