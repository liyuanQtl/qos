package demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Restart {

	private static final Logger logger			= LoggerFactory.getLogger(Restart.class);

	public ExecutionEnvironment setDelayRestart(ExecutionEnvironment env) {
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
		3, // number of restart attempts
		Time.of(10, TimeUnit.SECONDS) // delay
		));
		return env;
	} 
	
	public ExecutionEnvironment setRateRestart(ExecutionEnvironment env) {
		env.setRestartStrategy(RestartStrategies.failureRateRestart(
		3, // max failures per interval
		Time.of(1, TimeUnit.MINUTES), //time interval for measuring failure rate
		Time.of(10, TimeUnit.SECONDS) // delay
		));
		return env;
	}
	
	public StreamExecutionEnvironment setDelayRestart(StreamExecutionEnvironment env) {
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
		3, // number of restart attempts
		Time.of(10, TimeUnit.SECONDS) // delay
		));
		return env;
	} 
	
	public StreamExecutionEnvironment setRateRestart(StreamExecutionEnvironment env) {
		env.setRestartStrategy(RestartStrategies.failureRateRestart(
		3, // max failures per interval
		Time.of(1, TimeUnit.MINUTES), //time interval for measuring failure rate
		Time.of(10, TimeUnit.SECONDS) // delay
		));
		return env;
	}
}
