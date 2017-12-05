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
		String[] testClasses = Arrays.copyOfRange(args, 1, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = configureMode(modeParam);

		Result result = runner.run(cp, findClasses(testClasses));

		if (!result.getFailures().isEmpty()) {
			System.out.println("Failed Tests:");
			for (Failure fail : result.getFailures()) {
				System.out.println(fail.getTestHeader());
			}
		}
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

	public static Class<?>[] findClasses(String[] args) throws ClassNotFoundException {
		Class<?>[] testClasses = new Class<?>[args.length - 1];
		for (int i = 0; i < testClasses.length; i++) {
			testClasses[i] = Class.forName(args[i + 1]);
		}
		return testClasses;
	}
}
