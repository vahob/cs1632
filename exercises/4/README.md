# Exercise 4 Performance Testing Exercise
Fall Semester 2020 - Exercise 4

* DUE: Oct 21, 2020 09:00 AM (Mon/Wed class)
* DUE: Oct 22, 2020 09:00 AM (Tue/Thu class)

Let's start by downloading the VisualVM Java profiler from:
https://visualvm.github.io/

Please click on the "Download Standalone" link at the top of the project page.
Keep the download running as you read the below instructions and install it
when it is ready.

## Description

For this exercise, you will test and improve the performance of a monkey
simulation software.  When you are asked to improve the performance of any
code, how would you go about it?  Maybe you will start eyeballing the code to
guess where the program is wasting a lot of its time and try to refactor the
code that way.  But what if your code base is millions of lines long?  Talk
about finding a needle in a haystack.  We will learn to use a technique called
profiling that takes all the guesswork out of the picture.

Profiling is a form of dynamic program analysis where data is collected
during runtime of a program, usually for the purposes of performance
optimization.  The data is typically collected through some form of
instrumentation on the program code, where extra instructions are inserted
specifically for the purposes of monitoring the program while it runs and
collecting data.  For Java, this instrumentation happens at the bytecode
level.  For example, if the profiler wanted to measure how long a method
takes to execute, it may do instrumentation similar to the following:

```
void foo() {
  ___instrumentMethodBegin("foo");
  // body of foo()
  ___instrumentMethodEnd("foo");
}

void ___instrumentMethodBegin(String method) {
  beginTime = ___getTime();
}

void ___instrumentMethodEnd(String method) {
  endTime = ___getTime();
  duration = endTime - beginTime;
  ___addToMethodRunningTime(method, duration);
}
```

But enough of profiling theory, how do I actually use it?  Performance
debugging through profiling is an iterative process.  On each iteration, you
will do the following:

1. Profile the program.  Sort all methods in descending order of CPU utilization and
   search for refactoring opportunities starting from the top.
1. Refactor selected method to be more performant (being careful not to change functionality using pinning tests).
1. Profile again to determine whether you made enough improvement, otherwise go back to 1.

In this way, on each iteration, you will be able to focus on the method that
has the most potential for improvement.  It is important to profile at the
beginning of each iteration to get the most up-to-date profile since the last modification.

The code is available under the src/ directory.

## MonkeySim Requirements

This code runs MonkeySim, which simulates a group of monkeys throwing a banana back around until it gets to the first monkey.

* The game shall accepts one argument, which states which monkey has the banana initially.

* The monkey who has the banana shall throw it to another monkey during each round.

* If a monkey is even-numbered (e.g., monkey #2, monkey #4, etc.), then the monkey with the banana shall throw the banana to the monkey equal to one-half of that initial monkey's number `(n / 2)`.  For example, monkey #4 shall throw the banana to monkey #2, and monkey #20 shall throw the banana to monkey #10.

* If a monkey is odd-numbered (and not monkey #1), the monkey with the banana shall throw it to the monkey equal to three times the number of that monkey plus one `(3n + 1)`.  For example, monkey #5 shall throw the banana to monkey #16 `((3 * 5) + 1)`.

* If monkey #1 catches the banana, the system shall display the number of rounds it took for Monkey #1 to catch the banana and then the program shall exit.

* At each round, the current status of who is doing the throwing and who is catching shall be displayed, along with the round number (which shall start at 1).  It shall use the following format: "Round 1: Threw banana from Monkey (#54 / ID 223546) to Monkey (#27 / ID 223519)", where #54 and #27 are the monkey numbers, and 223546 and 223519 are the monkey IDs.

* Each monkey shall have an ID; this ID shall remain constant.  For instance, Monkey #54 shall always have ID 223546, and Monkey #27 shall always have ID 223519.  Any changes to the code should not modify the ID value.

Sample runs are shown in the [sample_runs.txt](sample_runs.txt) file.  For
those of you who are interested, MonkeySim is a simulation of the Collatz
Conjecture (https://en.wikipedia.org/wiki/Collatz_conjecture).  The conjecture
is that no matter which monkey initially has the banana, Monkey #1 will
eventually catch the banana in a finite amount of time.  Nobody has been able
to find an initial monkey which behaves otherwise, but nobody has been able to
prove that such a monkey does not exist either (which is why it is called a
conjecture)!

## How to Run MonkeySim

1. For Windows do (for running MonkeySim with argument 27):
    ```
    run.bat 27
    ```
1. For Mac / Linux do (for running MonkeySim with argument 27):
    ```
    bash run.sh 27
    ```

Alternatively, I've created an Eclipse project for you so you can use Eclipse to import the existing project and run it from there.

## What to do

In order to determine the "hot spots" of the application, you will need to run
a profiler such as VisualVM (https://visualvm.github.io/).  Using
the profiler, determine a method you can modify to measurably increase the
speed of the application without modifying behavior.

Some tips for using VisualVM:

1. Your Java app will only show up in VisualVM _during_ execution.  When the
   MonkeySim application shows up on the left panel, you need to quickly double
click on the MonkeySim application and then click on the Profiler tab.  Then,
on the Profiler window that shows up on the main pane, quickly click on the CPU
button to start profiling CPU utilization.  Finally, click on the "Hot spots"
button to get a list of methods sorted by running times.  ![alt
text](VisualVM_profiling.png "Using VisualVM profiler") If you right click on
one of the methods in the "Hot spots" methods list, you'll get a context menu.
If you click on a the "Find in Forward Calls" item, you can see the call tree
that got you to that method.

1. If your app runs very quickly, you may not have time to perform the above
   actions before the app terminates!  In that case, you may want to insert a
sleep() at the beginning of the main() method, during which you can perform
these actions.  For example:

   ```
   try {
      Thread.sleep(10000);
   } catch (InterruptedException iex) {
   }
   ```

Now you are ready to modify that method.  Remember, the program should work
EXACTLY the same as before, except it should be faster and take up less CPU
time.  

Refactor *four* of the most time consuming methods in MonkeySim.  You should
not change the behavior of any of the methods; only refactor the implementation
so that they are more efficient.  Three of the methods will be very
straightforward because they contain obviously redundant computation.

One method (generateId) is less straightforward.  All the computation seems
necessary to generate the monkey IDs that are displayed in the output.  Naively
removing the ID generation will result in a different output.  Hint: Do we
really need to generate all those IDs for the output?

This is what I got after optimizing:
![alt text](profile.png "VisualVM snapshot after optimizations")

I gave argument 27 for the run.  Note that now the run takes approximately 3
seconds to run, which is a marked improvement over 37 minutes for the original
code!  Now the most time consuming method is generateId by a wide margin.  I could refactor generateId() further but I am satisfied at this point.
So this is when you pat yourself on the back and declare victory.

## Pinning Tests

All the while refactoring methods to improve performance, you need to make sure
that the functional behavior of the program does not change.  We learned that
writing pinning tests and running them after every code change is a way of
guaranteeing this.  Lucky for you, I have already wrote a set of pinning tests
for you in the file MonkeySimPinningTest.java using JUnit.  You can run them
with TestRunner.java using the following script:

1. For Windows do:
    ```
    runTest.bat
    ```
1. For Mac / Linux do:
    ```
    bash runTest.sh
    ```

The tests pass with the original MonkeySim (obviously because the pinning tests
are based on the existing behavior of MonkeySim).  Your job is to make sure
that they stay that way.

Now let's look at the @Before setUp() method in MonkeySimPinningTest.java
because there a few interesting things to note there:

1. Note how I redirected the output stream for testing purposes:

    ```
    // Back up the old output stream
    stdout = System.out;
    // Redirect the output stream
    System.setOut(new PrintStream(out));
    ```

    This was done to be able to test system output.  In the last test
testArgument5RunSimulation(), lines printed to the screen using
System.out.println can now be compared to a String.  I also made sure I
restored the original output stream in the @After teadDown() method:

    ```
    System.setOut(stdout);
    ```

    Please read textbook Chapter 14.6 Testing System Output, for further
explanation.

1. Note how I used Java Reflection to force reset Monkey.monkeyNum, which is a
   private static field, to 0:

    ```
    Field f = Monkey.class.getDeclaredField("monkeyNum");
    f.setAccessible(true);
    f.set(null, 0);
    ```

    Previously, we have called private methods, but this is the first time we
accessed a private field.  The field is first made accessible and then set to 0.
The first instance argument in f.set(null, 0) is null because this is a
static field and there is no instance.  Please see textbook Chapter 24
Using Reflection to Test Private Methods in Java, for related material.

    Legacy code is often not written with ease-of-testing in mind and the same
goes for this one.  Monkey.monkeyNum is the monkey number assigned to a newly
created monkey and is incremented by one each time so that each monkey in the
list will get a monotonically increasing number.  The developer did not think
to put in a method to be able to reset Monkey.monkeyNum to 0, because it's not
needed for the program itself as the monkey list is only created once.  But in
a test scenario, we need to constantly reset Monkey.monkeyNum to 0 as we
repeatedly recreate the list of monkeys in our setUp() method.  So we are
forced to use Java reflection to force reset that number.

1. Note how I used real objects instead of mocked objects, even for external
   classes when I initialized the test fixture.  You may ask: isn't this
against all we learned about unit testing?  Are we not de facto testing large
parts of the system beyond the unit by not mocking and stubbing?  Yes,
absolutely!  In fact, I made the conscious choice of doing systems testing
instead of unit testing for the pinning tests.  This is often done when you
receive legacy code without any unit testing infrastructure.  Rather than look
for "seams" in the code to construct unit tests and emulating behavior of
mocked objects, which is time consuming, end-to-end systems tests are slapped
on for the purposes of pinning down existing behavior, which is much easier.
Eventually, unit pinning tests are added into the mix, by finding seams or
potentially modifying the code to create seams using dependency injection and
other techniques.  But when code modification is performed, it is done under
the cover of systems pinning tests so any divergence in system behavior would
be detected.

    For Deliverable 4, I will ask *you* to write pinning tests yourself.  And
for these pinning tests, I'm going to ask you to write unit pinning tests.

## Submission

I expect each group member to try running VisualVM and experience profiling for
him/herself.  In the end, you will compare your code, learn from each other and
submit one version, as usual.

1. You will create a github repository just for exercise 4.  Make sure you keep
   the repository *PRIVATE* so that nobody else can access your repository.
Once you are done modifying code, don't forget to commit and push your changes
to the github repository.  When you are done, submit your github repository to
GradeScope at the "Exercise 4 GitHub" link.  Once you submit, GradeScope will
run the autograder to grade you and give feedback.  If you get deductions, fix
your code based on the feedback and resubmit.  Repeat until you don't get
deductions. Don't forget to add your partner to the submission.

1. Create a screenshot of the VisualVM Hotspots panel after running MonkeySim with argument 27 and name it hotspots.png. This should be after you have done all your optimizations and refactoring.  Example:

   ![alt text](hotspots.png "Hotspots panel after optimizations")

   Make sure the "Hot spots" window lists the methods sorted in descending order of running time (Self Time).

   After you have created the screenshot, save the picture to a PDF file and
submit to GradeScope at the "Exercise 4 Profile" link.  Make sure the picture
fits in one page for easy viewing and grading.

## GradeScope Feedback

It is encouraged that you submit to GradeScope early and often.  Please use the
feedback you get on each submission to improve your code!  All the tests have
been performed after having called the @Before setUp() method which sets up the
test fixture with Monkey #5 having the banana initially (just like when
argument 5 has been passed on the commandline).

The GradeScope autograder works in 2 phases:

1. MonkeySim method pinning tests

   These are the JUnit pinning tests in MonkeySimPinningTest applied to Monkey
sim.  They all pass with the original MonkeySim and they should stay that way.

1. MonkeySim method performance tests

   These are JUnit tests that I wrote to see if you made improvements on the
four most time consuming methods in MonkeySim.  I set a timeout of 10 ms for
each of them and if you don't complete the task within that amount of time, the
test fails.  I also test the entire program using runSimulation() after setting
up the monkey list to begin with monkey #5.  The simulation has a timeout of
300 ms.  You could potentially try to glean the time consuming methods from
looking at the methods that I test, but please don't do that.  See if you can
extract that information from the VisualVM tool.  The test output will not be
so revealing on your deliverable!

## Resources

* VisualVM Download:  
https://visualvm.github.io/download.html

* VisualVM Documentation:  
https://visualvm.github.io/documentation.html

Method profiling is not the only thing that VisualVM knows how to do.  It can
also profile overall CPU usage, heap memory usage, thread creation/termination,
class loading/unloading, Java just-in-time compiler activity, etc.  It can also
profile heap memory in a detailed way to show which types of objects are
filling the memory and where their allocation sites were.  And needless to say,
VisualVM is not the only profiling tool out there.

In the unlikely case you can't find what you are looking for in existing
profilers, you can even write your own profiler using the Java Virtual Machine
Tool Interface (JVMTI).  JVMTI is what was used to build VisualVM.

* Creating a Debugging and Profiling Agent with JVMTI  
https://www.oracle.com/technical-resources/articles/javase/jvmti.html

* JVMTI Reference  
https://docs.oracle.com/javase/8/docs/platform/jvmti/jvmti.html
