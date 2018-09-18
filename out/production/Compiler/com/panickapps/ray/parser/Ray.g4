grammar Ray;

program:  tierDefinition | programPart+ ;

programPart: (statement ) #MainStatement
            | functionDefinition #ProgramPartFunctionDefinition
            ;

statement: println ';'
        | print ';'
        | varDeclaration ';'
        | varInitialization ';'
        | assignment ';'
        | branch
        ;

branch: 'if' '(' condition=expression ')' onTrue=block 'else' onFalse=block
      ;

block: '{' statement* '}' ;

expression:
            '(' expression ')' #BracketedExpression
          | left=expression '/' right=expression #Division
          | left=expression '%' right=expression #Modulo
          | left=expression '*' right=expression #Multiplication
          | left=expression '-' right=expression #Minus
          | left=expression '+' right=expression #Plus
          | left=expression operator=('<' | '<=' | '>' | '>=') right=expression #RelationalOperator
          | left=expression '&&' right=expression #LogicalAnd
          | left=expression '||' right=expression #LogicalOr
          | number=INTEGER #Number
          | txt=STRING #String
          | varName=IDENTIFIER #Variable
          | functionCall #funcCallExpression
          ;

varDeclaration: 'int' varName=IDENTIFIER;

varInitialization: 'int' varName=IDENTIFIER '=' expr=expression;

assignment: varName=IDENTIFIER '=' expr=expression;

println: 'println(' argument=expression ')' ;

print: 'print(' argument=expression ')' ;

functionDefinition: 'int' funcName=IDENTIFIER '(' params=parameterDeclaration ')' '{' statements=statementList 'return' returnValue=expression ';' '}';

parameterDeclaration: declarations+=varDeclaration (',' declarations+=varDeclaration)*
                    |
                    ;

statementList: statement*;

functionCall: funcName=IDENTIFIER '(' arguments=expressionList  ')';

expressionList: expressions+=expression (',' expressions+=expression)*
              |
              ;

tierDefinition: '#TIER ' tierID=('0' | '1' | '2') ';';

IDENTIFIER: [a-zA-Z][a-zA-Z0-9]*;
INTEGER: DIGIT+;
WHITESPACE: [ \t\n\r]+ -> skip;
STRING: '"' .*? '"' ;
fragment DIGIT: [0-9];
