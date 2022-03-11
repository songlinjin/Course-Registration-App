# My Personal Project: Course Registration Application

## Introduction: An application designed for students to manage course registration

This course registration application is designed for students to *add* and *drop* the courses they would like to 
register in the course worklist, and *register* the courses from the course worklist they create. Students are able to 
*add* the current worklist they work on to terms list. Additionally, the total registered credits will be *calculated*, 
and the associated remaining credits need to be completed in order to graduate are recorded. Students 
are presented with the views during they use different function of this application as the following: 

- their registered courses
- the total credits they have registered
- the remaining credits they need to complete to graduate
- the number of terms(worklists) they added
- all of courses in course list

The application is designed for **students** and students will use it to manage their course registration and keep 
track of courses that they prefer to register, as well as keeping track of the accumulated credits. I am **interested**
in this project since course registration is highly related to school life and I would like to design an application
that is easy and convenient for students to use. Besides general course registration, this project is of my interest
since I hope to provide students a global and holistic view when they are managing their course registration, for
example, providing them with the total credits remaining to graduate. This will help them keep the whole degree process
in mind and eventually helping them select courses.

## User Stories
The following are user stories for this course registration application:
- As a user, I want to be able to add a course to my course worklist
- As a user, I want to be able to register a course from my course worklist
- As a user, I want to be able to add the current workList I work on to list of terms and view the degree progress
- As a user, I want to be able to view record of total registered courses and credits of the term that I am working on
- As a user, I want to be able to drop a registered course

**user stories in phase 2 are added below**
- As a user, I want to be able to save my workList
- As a user, I want to be able to reload and resume my course workList
- As a user, I want to be able to print my course workList

## Citation

Cited source: the sample provided on EDX course page regarding project phase 2 with the following GitHub link:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

List Demo provided by EDX Project Phase3 Instruction page
 https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
 
 http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
 as a reference and learn how to add sound to button; the author is not found in the link

## Phase 4: Task 2
The option I choose:

- Test and design a class in your model package that is robust.  You must have at least one method that throws a 
checked exception.  You must have one test for the case where the exception is expected and another where 
the exception is not expected.

**The classes and methods in that play a role in this task:**

Class: CourseWorkList

Method: register

## Phase 4: Task 3
The refactoring to improve this project:

Given the UML graph, there are multiple ways from CourseRegistrationApp to CourseWorkList. So it is better if 
we remove the association between CourseRegistrationApp and CourseWorkList and use the path from CourseRegistrationApp
to Terms and then Terms have path to the CourseWorkList. In addition, CourseWorkList have two distinct lists of
courses, so it is better to extract one of them and their aoocciated methods to another new class, in this way
the project design will be more clear.


