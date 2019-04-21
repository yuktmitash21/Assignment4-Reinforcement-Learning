package assignment4;

import assignment4.util.AnalysisAggregator;
import assignment4.util.AnalysisRunner;
import assignment4.util.BasicRewardFunction;
import assignment4.util.BasicTerminalFunction;
import assignment4.util.MapPrinter;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

public class EasyGridWorldLauncher {
	//These are some boolean variables that affect what will actually get executed
	private static boolean visualizeInitialGridWorld = true; //Loads a GUI with the agent, walls, and goal
	
	//runValueIteration, runPolicyIteration, and runQLearning indicate which algorithms will run in the experiment
	private static boolean runValueIteration = false;
	private static boolean runPolicyIteration = false;
	private static boolean runQLearning = true;
	
	//showValueIterationPolicyMap, showPolicyIterationPolicyMap, and showQLearningPolicyMap will open a GUI
	//you can use to visualize the policy maps. Consider only having one variable set to true at a time
	//since the pop-up window does not indicate what algorithm was used to generate the map.
	private static boolean showValueIterationPolicyMap = false;
	private static boolean showPolicyIterationPolicyMap = false;
	private static boolean showQLearningPolicyMap = true;
	
	private static Integer MAX_ITERATIONS = 3000;
	private static Integer NUM_INTERVALS = 3000;

	private static double livingRewatd = -0.1;

	protected static int[][] userMap = new int[][] { 
			{ 0, 0, 0, 0, 0},
			{ 0, 1, 1, 1, 0},
			{ 0, 1, 0, 1, 0},
			{ 0, 1, 1, 1, 0},
			{ 0, 0, 0, 0, 0}, };
	
//	private static Integer mapLen = map.length-1;

	public static void main(String[] args) {
		// convert to BURLAP indexing
		int[][] map = MapPrinter.mapToMatrix(userMap);
		int maxX = map.length-1;
		int maxY = map[0].length-1;
		// 

		BasicGridWorld gen = new BasicGridWorld(map,maxX,maxY); //0 index map is 11X11
		Domain domain = gen.generateDomain();

		State initialState = BasicGridWorld.getExampleState(domain);
		//System.out.println("inital state: " + initialState.getCompleteStateDescription());


		RewardFunction rf = new RewardFunction() {
			@Override
			public double reward(State state, GroundedAction groundedAction, State state1) {
				ObjectInstance agent = state1.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
				int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
				int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);
				if (ax == maxX && ay == maxX) {
					return 5;
				}

				if (ax == maxX && ay == 0) {
					return -5;
				}
				return livingRewatd;
			}
		};

		TerminalFunction tf = new TerminalFunction() {
			@Override
			public boolean isTerminal(State state) {
				ObjectInstance agent = state.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
				int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
				int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

				if (ax == maxX && ay == maxX) {
					return true;
				}

				if (ax == maxX && ay == 0) {
					return true;
				}
				return false;




			}
		};

		SimulatedEnvironment env = new SimulatedEnvironment(domain, rf, tf,
				initialState);
		//Print the map that is being analyzed
		System.out.println("/////Easy Grid World Analysis/////\n");
		MapPrinter.printMap(MapPrinter.matrixToMap(map));
		
		if (visualizeInitialGridWorld) {
			visualizeInitialGridWorld(domain, gen, env);
		}
		
		AnalysisRunner runner = new AnalysisRunner(MAX_ITERATIONS,NUM_INTERVALS);
		if(runValueIteration){
			runner.runValueIteration(gen,domain,initialState, rf, tf, showValueIterationPolicyMap);
		}
		if(runPolicyIteration){
			runner.runPolicyIteration(gen,domain,initialState, rf, tf, showPolicyIterationPolicyMap);
		}
		if(runQLearning){
			runner.runQLearning(gen,domain,initialState, rf, tf, env, showQLearningPolicyMap);
		}
		AnalysisAggregator.printAggregateAnalysis();
	}



	private static void visualizeInitialGridWorld(Domain domain,
			BasicGridWorld gen, SimulatedEnvironment env) {
		Visualizer v = gen.getVisualizer();
		VisualExplorer exp = new VisualExplorer(domain, env, v);

		exp.addKeyAction("w", BasicGridWorld.ACTIONNORTH);
		exp.addKeyAction("s", BasicGridWorld.ACTIONSOUTH);
		exp.addKeyAction("d", BasicGridWorld.ACTIONEAST);
		exp.addKeyAction("a", BasicGridWorld.ACTIONWEST);

		exp.setTitle("Easy Grid World");
		exp.initGUI();

	}
	

}
