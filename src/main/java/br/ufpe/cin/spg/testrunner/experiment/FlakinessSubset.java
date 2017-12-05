package br.ufpe.cin.spg.testrunner.experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.ufpe.cin.spg.testrunner.App;
import br.ufpe.cin.spg.testrunner.TestListener;

public class FlakinessSubset {

	public static void main(String[] args) throws ClassNotFoundException {
		String modeParam = args[0];
		String[] testClasses = Arrays.copyOfRange(args, 1, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = App.configureMode(modeParam);

		int N = 5;
		Result[] results = new Result[N];
		for (int i = 0; i < N; i++) {
			Result result = runner.run(cp, App.findClasses(testClasses));
			results[i] = result;
		}

		Map<String, Integer> freq = new HashMap<>();
		for (Result r : results) {
			for (Failure f : r.getFailures()) {
				String failedTest = f.getTestHeader();
				if (freq.containsKey(failedTest)) {
					freq.put(failedTest, freq.get(failedTest) + 1);
				} else {
					freq.put(failedTest, 1);
				}
			}
		}
		for (Entry<String, Integer> item : freq.entrySet()) {
			System.out.println(String.format("%s: %d", item.getKey(), item.getValue()));
		}
	}
}
