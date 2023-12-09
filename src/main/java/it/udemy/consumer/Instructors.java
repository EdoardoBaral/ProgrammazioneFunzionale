package it.udemy.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Instructors
{
	public static List<Instructor> getAll() {
		Instructor in1 = new Instructor("Mike", 10, "Software developer", "M", true, Arrays.asList("Java programming", "C++ programming", "Python programming"));
		Instructor in2 = new Instructor("Jenny", 6, "Software analyst", "F", false, Arrays.asList("Java programming"));
		Instructor in3 = new Instructor("John", 9, "Project manager", "M", false, Arrays.asList("Mathemetic analysis"));
		Instructor in4 = new Instructor("Gina", 20, "Teacher", "F", true, Arrays.asList("Data structures", "Algorithms"));
		
		return Arrays.asList(in1, in2, in3, in4);
	}
}
