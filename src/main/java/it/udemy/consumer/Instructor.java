package it.udemy.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Instructor
{
	private String name;
	private int yearsOfExperience;
	private String title;
	private String gender;
	private boolean onlineCourses;
	private List<String> courses;
}
