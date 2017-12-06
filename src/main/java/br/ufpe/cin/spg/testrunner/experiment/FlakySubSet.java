package br.ufpe.cin.spg.testrunner.experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.ufpe.cin.spg.testrunner.App;
import br.ufpe.cin.spg.testrunner.TestListener;

public class FlakySubSet {

	public static void main(String[] args) throws ClassNotFoundException {
		int N = Integer.parseInt(args[0]);
		String modeParam = args[1];
		String[] testClasses = Arrays.copyOfRange(args, 2, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = App.configureMode(modeParam);

		Result[] results = new Result[N];
		for (int i = 0; i < N; i++) {
			Result result = runner.run(cp, App.findClasses(testClasses));
			results[i] = result;
		}

		System.out.println("--- EXPERIMENT SUMMARY ---");
		for (Result r : results) {
			App.displayReport(r);
		}
		Map<String, Integer> freq = new HashMap<>();
		for (Result r : results) {
			for (Failure f : r.getFailures()) {
				Integer value = freq.get(f.getTestHeader());
				freq.put(f.getTestHeader(), (value == null) ? 1 : value + 1);
			}
		}
		System.out.println(String.format("# of runs: %d", N));
		for (Map.Entry<String, Integer> entry : freq.entrySet()) {
			System.out.println(String.format("%s: %d", entry.getKey(), entry.getValue()));
		}
	}
}
