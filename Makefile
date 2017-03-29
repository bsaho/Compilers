#Flex, Cup and Main are all part of the cminus package
JAVA=java
JAVAC=javac
JFLEX=jflex -d cminus
CLASSPATH=-classpath /usr/share/java/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main -package cminus <
#CUP=cup


all: cminus/Lexer.java cminus/parser.java
	$(JAVAC) $(CLASSPATH) absyn/*.java codeGen/*.java cminus/parser.java cminus/sym.java cminus/Lexer.java cminus/Main.java

#lexer file is created an output to cminus directory to be compiled
cminus/Lexer.java: cminus/cminus.flex
	$(JFLEX) cminus/cminus.flex

#parser and sym files are created by CUP and moved to cminus directory to be compiled
cminus/parser.java: cminus/cminus.cup
	$(CUP) cminus/cminus.cup; mv parser.java cminus/ ; mv sym.java cminus/

clean:
	rm -f cminus/parser.java cminus/Lexer.java cminus/sym.java *.class absyn/*.class codeGen/*.class cminus/*.class *~
