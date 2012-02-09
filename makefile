test: clean compile
	java -cp lib/emma.jar emmarun -sp src -Dreport.html.out.file=coverage/ALL/index.html -Dreport.columns=class,block,name,method,line -r html -cp lib/junit-4.10.jar:classes org.junit.runner.JUnitCore org.json.tests.TestSuite

compile: classes/org/json/tests/TestSuite.class
	
classes/org/json/tests/TestSuite.class: classes src/org/json/tests/TestSuite.java
	javac -d classes -cp src:classes:lib/junit-4.10.jar src/org/json/tests/TestSuite.java

classes:
	mkdir classes

clean: cleanCoverage
	rm -rf classes/*

cleanCoverage:
	rm -rf coverage/*

makejar: clean compile
	cd bin; jar -cf ../json.jar org