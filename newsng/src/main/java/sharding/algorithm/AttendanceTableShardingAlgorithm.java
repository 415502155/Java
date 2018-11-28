package sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public final class AttendanceTableShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

	@Override
	public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
			if (each.endsWith(shardingValue.getValue() % 4 + "")) {
				return each;
			}
		}
		throw new UnsupportedOperationException();
	}

}
