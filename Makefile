all:
	@javac Ant.java AntColonyApplication.java AntSystem.java Graph.java

clean:
	@rm -f *.class

run:
	@java AntColonyApplication

