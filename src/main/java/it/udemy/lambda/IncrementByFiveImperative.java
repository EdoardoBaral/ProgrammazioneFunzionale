package it.udemy.lambda;

public class IncrementByFiveImperative implements IncrementByFiveInterface
{
	@Override
	public int incrementByFive(int num)
	{
		return num + 5;
	}
}
