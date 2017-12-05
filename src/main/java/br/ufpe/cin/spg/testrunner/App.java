package br.ufpe.cin.spg.testrunner;

import java.util.Arrays;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class App {

	public static void main(String[] args) throws ClassNotFoundException {
		String modeParam = args[0];
		String[] classNames = Arrays.copyOfRange(args, 1, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = configureMode(modeParam);

		Result result = runner.run(cp, findClasses(classNames));
		displayReport(result);
	}

	public static Computer configureMode(String modeParam) {
		boolean isParallelClasses = false;
		boolean isParallelMethods = false;

		switch (modeParam) {
		case "L0":
			isParallelClasses = isParallelMethods = false;
			break;
		case "L1":
			isParallelClasses = false;
			isParallelMethods = true;
			break;
		case "L2":
			isParallelClasses = true;
			isParallelMethods = false;
			break;
		case "L3":
			isParallelClasses = true;
			isParallelMethods = true;
			break;
		}

		return new ParallelComputer(isParallelClasses, isParallelMethods);
	}

	public static Class<?>[] findClasses(String[] classNames) throws ClassNotFoundException {
		Class<?>[] testClasses = new Class<?>[classNames.length];
		for (int i = 0; i < testClasses.length; i++) {
			testClasses[i] = Class.forName(classNames[i]);
		}
		return testClasses;
	}

	public static void displayReport(Result r) {
		String resultCounters = String.format("Total: %d Failures: %d Skip: %d", r.getRunCount(), r.getFailureCount(),
				r.getIgnoreCount());
		String resultElapsedTime = String.format("Elapsed time: %.2f s", r.getRunTime() / 1_000.00);

		StringBuilder sb = new StringBuilder();
		for (Failure f : r.getFailures()) {
			sb.append(f.getTestHeader()).append("\n");
		}
		System.out.println(resultCounters);
		System.out.println(resultElapsedTime);
		System.out.println(sb.toString());
	}
}
