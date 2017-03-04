JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-classpath /usr/share/java/cup.jar:.
CUP=$(JAVA) $(CLASSPATH) java_cup.Main <
#CUP=cup

all: Main.class

Main.class: absyn/*.java parser.java sym.java Lexer.java Main.java

%.class: %.java
	$(JAVAC) $(CLASSPATH)  $^

Lexer.java: tiny.flex
	$(JFLEX) tiny.flex

parser.java: tiny.cup
	$(CUP) tiny_no_code.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~
