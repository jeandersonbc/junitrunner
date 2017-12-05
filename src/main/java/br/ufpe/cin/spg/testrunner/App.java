package br.ufpe.cin.spg.testrunner;

import org.junit.experimental.ParallelComputer;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class App {

	public static void main(String[] args) throws ClassNotFoundException {
		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());

		boolean isParallelClasses = true;
		boolean isParallelMethods = true;

		Computer cp = new ParallelComputer(isParallelClasses, isParallelMethods);
		Result result = runner.run(cp, findClasses(args));

		if (!result.getFailures().isEmpty()) {
			System.out.println("Failed Tests:");
			for (Failure fail : result.getFailures()) {
				System.out.println(fail.getTestHeader());
			}
		}
	}

	private static Class<?>[] findClasses(String[] args) throws ClassNotFoundException {
		Class<?>[] testClasses = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			testClasses[i] = Class.forName(args[i]);
		}
		return testClasses;
	}
}
