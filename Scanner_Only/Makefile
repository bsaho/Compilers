JAVAC=javac
JFLEX=jflex
#JFLEX=/home/fsong/jflex/bin/jflex

all: Scanner.class

Scanner.class: Scanner.java Lexer.java Token.java

%.class: %.java
	$(JAVAC) $^

Lexer.java: tiny.flex
	$(JFLEX) tiny.flex

clean:
	rm -f Lexer.java *.class *~
