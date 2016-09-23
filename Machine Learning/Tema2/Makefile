sourcefiles = \
Function.java \
FunctionImpl.java \
TestNeuralNet.java \
NeuralNet.java
 
classfiles  = $(sourcefiles:.java=.class)
 
all: $(classfiles)
%.class: %.java
	javac -d . -classpath . $<
clean:
	rm -f *.class
	rm -f *.png