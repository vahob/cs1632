# CS 1632 - Software Quality Assurance
Fall Semester 2020 - Supplementary Exercise 1

* DUE: Sep 28, 2020 09:00 AM (Mon/Wed class)
* DUE: Sep 29, 2020 09:00 AM (Tue/Thu class)

## Description

In this exercise, we will test the Rent-A-Cat rental system software once more,
but this time with BDD.  Please review the "Lecture 10 Supplement: BDD" slides
if you haven't already.

We will use the Gherkin language to specify behaviors for Rent-A-Cat and use
the Cucumber framework to test those behaviors.

# Prerequisites

I recommend that you do this on the Eclipse IDE since it is the easiest.

Please install the Cucumber plug-in for Eclipse following these steps (recommended):
1. Click on Help > Eclipse Marketplace.
2. Type "cucumber" in the Find search box and press Enter.
3. Install the "Cucumber Eclipse Plugin" that pops up in the search box.
4. You will be asked to restart Eclipse to complete installation.  Please do so.

Otherwise, you should be able to use the command line Maven tool to install the
Cucumber plug-in.  But for that, you will need to install Maven first using the
following link (not recommended): https://maven.apache.org/download.cgi

## Running Cucumber Tests

To use the Eclipse Cucumber plug-in, first open the Eclipse project I have made for you:
1. Click on File > Open Projects from File System.
2. Click on Directory and browse to the folder with CS1632_Fall2020/exercises/Supplement1.
3. The "Supplement1" folder imported as "Eclipse project" checkbox should be auto-selected.
4. Click on the Finish button.

Then, run the Maven test goal:
1. Right click on the project root "RentACat-Cucumber".
2. Click on "Run as" in the context menu.
3. Select "Maven test".
4. Then the test results should be visible in the bottom Console window.

If you don't want to keep doing the above when running Cucumber tests, you can create a Run Configuration.
1. Click on Run > Run Configurations.
2. Select "Maven Build" in the list of run types at the left.
3. Click on the "New launch configuration" icon at the far left of the toolbar.
4. Edit the "Name:" box to something like "RentACat Cucumber Test"
5. Edit the "Base directory" box by clickig on the Workspace button and selecting the RentACat-Cucumber project.
6. Edit the "Goals:" box by inputing "test"
7. Click on the Apply button.

Now if you click on the little arrow beside the Run button on the toolbar that
looks like a play icon, you should see your newly made run configuration.

If you are not using Eclipse and you wish to run the tests from the command line, use the command line Maven tool:
1. cd into the CS1632_Fal2020/exercises/Supplement1 directory.
2. Invoke 'mvn test':
    ```
    > mvn test
    ```

## Expected Outcome

Initially when you run the Cucumber tests, all your tests will fail, partly
because RentACatImpl is incomplete and partly because your Cucumber testing
code is incomplete.  You will get a long list of failures followed by this summary text:

```
...
Tests run: 14, Failures: 9, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.659 s
[INFO] Finished at: 2020-09-20T22:08:14-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.12.4:test (default-test) on project RentACat-Cucumber: There are test failures.
...
```

The above tells you that out of 14 tests, 9 tests failed, and there was an
error in one test.  An error happens when a test is incomplete or is otherwise
malformed.  In order to get the details about the failures and errors, you can
read the messages that precede the summary output, or you can also read the
Cucumber report which is much nicer.  Somewhere above that summary is going to
be a link to a report in red font that looks like this:

```
????????????????????????????????????????????????????????????????????????????
? View your Cucumber Report at:                                            ?
? https://reports.cucumber.io/reports/0334222f-8b03-4075-80ba-a63d2c887c90 ?
?                                                                          ?
? This report will self-destruct in 24h unless it is claimed or deleted.   ?
????????????????????????????????????????????????????????????????????????????
```

Copy and paste that link on a web browser and you should see a report that
looks like the following:

<img alt="Cucumber Report" src=img/cucumber_report.png width=700>

The green check marks indicate steps that have passed.  The red cross marks
indicate steps that have failed.  The blue square marks indicate steps that
were skipped because a previous step failed.  The yellow question marks
indicate steps that were erroneous (e.g. matching Cucumber step does not exist
for that step).  For the failed steps, a Java stack trace is attached so that
you can track down the failure.

## What To Do

You will modify three files: **RentACatImpl.java**, **StepDefinitions.java**,
and **rent_a_cat_return_cats.feature**.  The RentACatImpl class is the
(incomplete) implementation of the Rent-A-Cat system.  The StepDefinitions
class is the (incomplete) implementation of Cucumber steps corresponding to the
Gherkin steps.  The rent_a_cat_return_cats.feature file is a description of the
"return cat" feature in the Rent-A-Cat system written in the Gherkin language.
All the places to modify have been marked by // TODO comments.

### Updating RentACatImpl.java

Let's first start by completing src/main/java/RentACatImpl.java.  You can just
copy the version that you completed for Exercise 2.  Just by doing that, many
tests will pass now.  Try running it after copying the file and you will get:

```
...
Tests run: 14, Failures: 5, Errors: 1, Skipped: 0
...
```

Now we only have 5 failures and 1 errors.  All the tests in Feature: Rent-A-Cat
listing (in the
src/test/resources/edu/pitt/cs/cs1632/rent_a_cat_list_cats.feature file) pass.
Most of the failures are from Feature: Rent-A-Cat renting.  

### Adding Steps in StepDefinitions.java for the "rent cats" Feature

Let's look at the
src/test/resources/edu/pitt/cs/cs1632/rent_a_cat_rent_cats.feature file to see
what the problem is.  Well, the Gherkin feature description makes logical
sense.  So it must be the Cucumber steps that implement the Gherkin steps that
must be the problem.  All the Cucumber steps are inside the
src/test/java/edu/pitt/cs/cs1632/StepDefinitions.java file.  In that file, you
can see all corresponding methods for each Gherkin step.  Some of the methods
have the // TODO: Implement comment and the default action is to fail().  The
failing methods are the ones used for the renting feature in Gherkin.  Replace
fail() with the proper implementation of each method.  Observe how other steps
were implemented to get hints.  After this, if you run Cucumber again, you will
get:

```
...
Tests run: 14, Failures: 0, Errors: 1, Skipped: 0
...
```

### Further Modifying StepDefinitions.java and User Story for the "rent cats" Feature

So where did this error come from?  If you scroll up in the Cucumber output a
little bit, you will see the following messages:


```
...
Attempt to return a cat that does not exist(Rent-A-Cat returning)  Time elapsed: 0.07 sec  <<< ERROR!
io.cucumber.junit.UndefinedStepException: The step "I return cat number 4" is undefined. You can implement it using the snippet(s) below:

@When("I return cat number {int}")
public void iReturnCatNumber(Integer int1) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


Some other steps were also undefined:

@Then("the return is unsuccessful")
public void theReturnIsUnsuccessful() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}
...
```

Cucumber is telling you that there is no corresponding Cucumber step for the
Gherkin step "I return cat number 4".  And then, it is kind enough to even give
a code snippet you can use to start implementing that step!  Of course, you
will have to replace "throw new io.cucumber.java.PendingException();" with code
to actually implement that step.  Cucumber also tells you some additional steps
that were undefined as a courtesy.

Once you implement those steps, you may suffer a NullPointerException due to
the "r" reference being null.  Now why would that happen all of a sudden?
Hint: compare rent_a_cat_return_cats.feature where the error happened to the
rent_a_cat_rent_cats.feature, focusing on the "Background:" section.  Once you
fix that, you should finally get the following:

```
...
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.983 sec
...
```

Congratulations!  If you have time, try to complete the other 4 scenarios in
rent_a_cat_return_cats.feature and see if you can have them pass too!

## Submission

Each pairwise group will submit the exercise *once* to GradeScope, by *one
member* of the group.  The submitting member will press the "View or edit
group" link at the top-right corner of the assignment page after submission to
add his/her partner.  That way, the feedback will be accessible to both of you.
I recommend that you divide the list of methods to implement / test into two
halves and working on one half each.

You will create a github repository just for Supplementary Exercise 1.  Add
your partner as a collaborator so both of you have access.  Make sure you keep
the repository *PRIVATE* so that nobody else can access your repository.  This
applies to all future submissions for this course.  Once you are done modifying
code, don't forget to commit and push your changes to the github repository.
When you are done, submit your github repository to GradeScope at the
"Supplementary Exercise 1 GitHub" link.  Once you submit, GradeScope will run
the autograder to grade you.  If you get deductions, fix your code based on the
feedback and resubmit.  Repeat until you don't get deductions.

IMPORTANT: Please keep the github private!

## GradeScope Feedback

The feedback you get from the GradeScope autograder is based on the Cucumber
summary output.  For example, if you get the following output:

```
...
Tests run: 14, Failures: 9, Errors: 1, Skipped: 0
...
```

There were 9 failures and 1 errors so the final score is: 14 - 9 - 1 = 4.

## Resources

* Gherkin Syntax Reference:  
https://cucumber.io/docs/gherkin/reference/

* Tutorial on how to write Cucumber steps:
https://cucumber.io/docs/cucumber/step-definitions/

* Cucumber API reference:
https://cucumber.io/docs/cucumber/api/

* Introduction to Behavior Driven Development (BDD):
https://cucumber.io/docs/bdd/
