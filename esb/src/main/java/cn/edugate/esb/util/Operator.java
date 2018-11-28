package cn.edugate.esb.util;


import java.util.EnumSet;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using=OperatorJsonSerializer.class)
@JsonDeserialize(using=OperatorJsonDeserializer.class)
public enum Operator {
	eq("="),
	ne("!="),
	lt("<"),
	le("<="),
	gt(">"),
	ge(">="),
	in("in"),
	anywhere("anywhere"),
	start("start"),
	end("end");
	private final String value;
	
	public String getValue() {
		return value;
	}

	private Operator(String value)
	{
		this.value=value;
	}
	
	public static Operator getOperator(String value)
	{
		Operator operator=Operator.anywhere;
		EnumSet<Operator> operatorSet = EnumSet.allOf(Operator.class);
		for(Operator op:operatorSet)
		{
			if(op.getValue().equals(value))
			{
				operator=op;
				break;
			}
		}
		return operator;
	}
}
