SPtester:
	javac -d bin -cp lib/gui.jar -sourcepath src src/tests/ShortestPathTester.java
	
RobotsPompiers:
	javac -d bin -cp lib/gui.jar -sourcepath src src/simulation/RobotsPompiers.java
		
execSPtester:
	java -cp bin:lib/gui.jar ShortestPathTester examples/carteSujet.map

execRobotsPompiers:
	java -cp bin:lib/gui.jar simulation.RobotsPompiers examples/carteSujet.map