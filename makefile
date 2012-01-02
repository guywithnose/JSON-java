test: compile
	java -cp lib/emma.jar emmarun -sp src -Dreport.html.out.file=coverage/ALL/index.html -Dreport.columns=name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore org.json.tests.TestSuite

testCDL: compile
	java -cp lib/emma.jar emmarun -ix org.json.CDL -sp src -Dreport.html.out.file=coverage/CDL/index.html -Dreport.columns=name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore org.json.tests.TestCDL

testXML: compile
	java -cp lib/emma.jar emmarun -ix org.json.XML -sp src -Dreport.html.out.file=coverage/XML/index.html -Dreport.columns=name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore org.json.tests.TestXML

compile: clean classes/org/json/tests/TestSuite.class
	
classes/org/json/tests/TestSuite.class: src/org/json/tests/TestSuite.java
	mkdir classes
	javac -d classes -cp src:classes:lib/junit-4.10.jar src/org/json/tests/TestSuite.java

clean: cleanCoverage
	rm -rf classes/*

cleanCoverage:
	rm -rf coverage/*
