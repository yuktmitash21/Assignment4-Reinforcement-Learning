package assignment4.util;

import burlap.oomdp.core.values.DoubleArrayValue;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.primitives.UnsignedInts.max;

public final class AnalysisAggregator {
	private static List<Integer> numIterations = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishValueIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishPolicyIteration = new ArrayList<Integer>();
	private static List<Integer> stepsToFinishQLearning = new ArrayList<Integer>();
	
	private static List<Integer> millisecondsToFinishValueIteration = new ArrayList<Integer>();
	private static List<Integer> millisecondsToFinishPolicyIteration = new ArrayList<Integer>();
	private static List<Integer> millisecondsToFinishQLearning = new ArrayList<Integer>();

	private static List<Double> rewardsForValueIteration = new ArrayList<Double>();
	private static List<Double> rewardsForPolicyIteration = new ArrayList<Double>();
	private static List<Double> rewardsForQLearning = new ArrayList<Double>();

	public static void addNumberOfIterations(Integer numIterations1){
		numIterations.add(numIterations1);
	}
	public static void addStepsToFinishValueIteration(Integer stepsToFinishValueIteration1){
		stepsToFinishValueIteration.add(stepsToFinishValueIteration1);
	}
	public static void addStepsToFinishPolicyIteration(Integer stepsToFinishPolicyIteration1){
		stepsToFinishPolicyIteration.add(stepsToFinishPolicyIteration1);
	}
	public static void addStepsToFinishQLearning(Integer stepsToFinishQLearning1){
		stepsToFinishQLearning.add(stepsToFinishQLearning1);
	}

	public static void printValueIterationResults(){
		File file = new File("./valueIteration.csv");
		printList(file, rewardsForValueIteration, millisecondsToFinishValueIteration, stepsToFinishValueIteration);
	}
	public static void printPolicyIterationResults(){
		File file = new File("./policyIteration.csv");
		printList(file, rewardsForPolicyIteration, millisecondsToFinishPolicyIteration, stepsToFinishPolicyIteration);
	}
	public static void printQLearningResults(){
		File file = new File("./QLearning.csv");
		printList(file, rewardsForQLearning, millisecondsToFinishQLearning, stepsToFinishQLearning);
	}
	

	public static void addMillisecondsToFinishValueIteration(Integer millisecondsToFinishValueIteration1){
		millisecondsToFinishValueIteration.add(millisecondsToFinishValueIteration1);
	}
	public static void addMillisecondsToFinishPolicyIteration(Integer millisecondsToFinishPolicyIteration1){
		millisecondsToFinishPolicyIteration.add(millisecondsToFinishPolicyIteration1);
	}
	public static void addMillisecondsToFinishQLearning(Integer millisecondsToFinishQLearning1){
		millisecondsToFinishQLearning.add(millisecondsToFinishQLearning1);
	}
	public static void addValueIterationReward(double reward) {
		rewardsForValueIteration.add(reward);
	}
	public static void addPolicyIterationReward(double reward) {
		rewardsForPolicyIteration.add(reward);
	}
	public static void addQLearningReward(double reward) {
		rewardsForQLearning.add(reward);
	}


	private static void printList(File file,
								  List<Double> rewards, List<Integer> time,
								  List<Integer> steps){
		FileWriter outputfile;
		CSVWriter writer = null;
		try {
			outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);


			String[] header = {"Iterations", "Rewards", "Time", "Steps"};
			writer.writeNext(header);
			int[] sizes = {rewards.size(), time.size(), steps.size()};
			int size = max(sizes);
			System.out.println("size" + size);
			for (int i = 0; i < size; i++) {
				String myReward = "";
				String myTime = "";
				String mySteps = "";

				if (i < rewards.size()) {
					myReward = String.valueOf(rewards.get(i));
				}
				if (i < time.size()) {
					myTime = String.valueOf(time.get(i));
				}
				if (i < steps.size()) {
					mySteps = String.valueOf(steps.get(i));
				}
				String iterations = (i + 1) + "";
				String[] header1 = {iterations, myReward, myTime, mySteps};
				writer.writeNext(header1);
				System.out.println(iterations + " " + myReward + " " + myTime + " " + mySteps);
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printAggregateAnalysis(){
		System.out.println("//Aggregate Analysis//\n");
		printValueIterationResults();
		printPolicyIterationResults();
		printQLearningResults();

	}
}
