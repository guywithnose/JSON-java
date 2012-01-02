test: compile
	java -cp lib/emma.jar emmarun -sp src -Dreport.columns=name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore tests.TestXML

compile: classes/tests/TestXML.class
	
classes/tests/TestXML.class: src/tests/TestXML.java
	javac -d classes -cp src:classes:lib/junit-4.10.jar src/tests/TestXML.java
