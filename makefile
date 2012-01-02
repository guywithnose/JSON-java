test: compile
	java -cp lib/emma.jar emmarun -sp src -Dreport.columns=name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore org.json.tests.TestXML

compile: classes/org/json/tests/TestXML.class
	
classes/org/json/tests/TestXML.class: src/org/json/tests/TestXML.java
	javac -d classes -cp src:classes:lib/junit-4.10.jar src/org/json/tests/TestXML.java

clean:
	rm -rf classes/*
