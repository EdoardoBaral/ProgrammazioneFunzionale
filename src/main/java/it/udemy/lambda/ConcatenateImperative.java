package it.udemy.lambda;

public class ConcatenateImperative implements ConcatenateInterface
{
	@Override
	public String concatStrings(String a, String b)
	{
		return a.concat(b);
	}
}
