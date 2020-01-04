package org.shield.compiler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PatternRecogTest
{
	
	
	private static final String[][] TestCases = {
		{"Sample" , "S**ple"},
		{"Sample" , "*ample"},
		{"Sample" , "***p**"},
		{"Sample" , "S*****"},
		{"Sample" , "S#le"},
		{"Sample" , "S#l*"},
		{"Sample" , "Sam#e"},
		{"Sample" , "*a#le"},
	};
	
	private static final boolean[] expected = {
		true,
		true,
		true,
		true,
		true,
		true,
		true,
		true,
	};
	
    @Test
    public void shouldAnswerWithTrue()
    {
		boolean flag  = true;
		for(int i = 0; i < TestCases.length; i++)
			flag = flag && (expected[i] && PatternRecog.check(TestCases[i][0], TestCases[i][1]));
        assertEquals( true , flag );
    }
}
