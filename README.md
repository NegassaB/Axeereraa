# Testing in Java
* Read all the slides that came with the instruction videos.
## Why automate the test?
* to remove the fear of change
* to have an early, reliable feedback on what's going right/wrong in the design, what functionality works, what's just been broken 
* to have repeatablilty of tests
* to continously deliver the products
* to find problems, bugs & errors before they or the software becomes to big to fix
* to correctly identify & handle corner cases
* to confirm that it meets the stated functional requirements
* to acheive stake holder goals
## The testing hierarchy
### Types of tests
1. Unit tests
  * tests a single unit of functionality
  * unit is a class with a single behavior running through it, method or sub-module
  * fast, brittle
  * no non-trivial dependencies (like database, internet connection etc)
  * *__COVERS A SINGLE METHOD, A SINGLE BEHAVIOR IN A CLASS__*
2. Aggregate tests
  * test a single component to check if it does it's job or not
  * slower, less brittle
  * can use non-trivial dependencies
  * *__COVERS A SINGLE COMPONENT__*
3. System tests (end to end test)
  * check that the application meets the requirements
  * slowest, less brittle
  * requires the ability to run the whole application
  * *__COVERS BROAD FUNCTIONALITY__*
## How to go about testing the code
* we use junit
* the problem domain a cafe class that holds a stock of coffee & milk, with enum types of coffee, that brews coffee, can be requested different types of coffee & a coffee class that represents a single brewed cup of coffee.
## The first test
> can be found in Intellij: project name Cafe
## The common structures of tests
* there are 3 things that you need to get right whenever you're writing a test and there are 3 things each test needs to have inorder to be a decent test.
  * **the given clause**: establishes the pre-condition for the test.
  * **the when clause**: answers the behaviour of what is it we're testing. the action itself. don't make it test too much behaviour, only test one behaviour.
  * **the then clause**: It's the post-condition of the test its full of assertions. 
## Exceptions, Failures and Errors
* two things can go wrong in any code:
  * the state of the outside world is wrong in some sense and you've gotta cope with it
  * or the test itself goes wrong
* sometimes the correct result is an Exception, for e.g in a user input field the user doesn't input anything and on the validating method might throw an exception
* ```@Test(expected = Exception.class)``` is how to test for exceptions
* there are two benefits in test for exeptions:
  1. you can test the edge cases
  2. you're providing a powerful, executable documentation for what happens when an error occurs
* **failures** are the result of some kind of assertion about the result failing. It means the code under test is broken.
* **error** happens when the test throws an exception that's unexpected. It might mean the test is broken, the application code is broken etc.
## Writing Good Tests
### Why should you care?
* tests are a trade-off
  * with the benefits of testing, comes costs that will be associated.
  1. maintainance: make sure your tests don't take as much maintainace efforts as the code their testing.
  2. readablilty: make sure you, your collegues, or some random developer can read the test a year or more later.
  3. coupling: do not couple the test to the implementation and leads to worst maintainance.
* **Tests are code, treat them as such.**
### Good Practices
1. use the regular software engineering best practices.
2. there are 4 things to follow:
  * well named tests
  * test behaviour not implementation
  * Don't Repeat Yourself (DRY)
  * diagnostics
#### naming 
* provides:
  * executable documentation we read the test as a form of documentation on the implementation code.
  * maintainace to easily find what test does what.
  * readability we want to tie the name to the body of the code.
* Do not:
  * name tests as test1() etc
  * give names of tests that don't describe the property that is under test
  * give names that are generalized & don't describe what the test does
  * give names that are exact similar to the method under test
  * combine the above to restock1() etc
* Do:
  * name tests so as to immediatley see both what the action and the implication of what's going
* Naming rules:
1. Use domain terminology: name the test according to the properties that are being tested so as to tie it to the requirements. 
2. Natural lang: write english and don't include technical jargon.
3. Be descriptive: choose a name that can describe the system under tests and can be understood after a specific period of time.
#### Behavior not Implementation
* only think about properties that are public & not ones that are private.
* **__Do not trespass__**
* if you expose & test private state that's related to implementation you'll get brittle and hard to maintain tests. Why? b/c as the private state/field changes then the test fails b/c it's attached to the previous state/field of that private.
* what you want to do is test **__behaviour__**. make an assert about the result.
* do not create methods on the SUT just to test a field/s or method/s
* so call a public method or package scoped method on the class and only those things and make sure the results you get back are something that other classes can use and test that.
#### Don't Repeat Yourself (D.R.Y)
* duplication is never good in any SE endevour. why? b/c when you want to change one of those things you'll almost always need to change the duplicate.
* as much as possible remove any duplication and extract & make it common for all the test methods.
* and also remove magic numbers. You can't control them and they might be repeated. If you have magic numbers that continually appear in the application code just convert them to a constant that's way more stable and easily changable should the need arise. It makes sure that you're testing the logic rather than the definition of a magic number.
#### Diagnostics
* every test fails. If it doesn't fail, don't trust it. It's not testing much.
* to debug your test when it fails is to have diagnostics within the tests that tell you what has gone wrong.
* when comparing values always use AssertEquals rather than AssertTrue compounded with a ```==``` or an ```equals()``` method aka "exposing a value". Why? b/c if the test fails you'll see diagnostic information outputed that will help you debug it.
* to go a step further, add a message in the test that will be displayed when the test fails aka "exposing a reason".
## Before & After
* are annotations that help us reduce duplication on our test.
* ```@Before``` annotation for anything to be done **before each test methods run**
* ```@After``` annotation for anything to be done **after each test methods run**
* ```@BeforeClass``` annotation for anything to be done **before all tests in the class**
* ```@AfterClass``` annotation for anyting to be done **after all tests in the class**
## Hamcrest
* a compositional matcher library we use for writing better tests.
* it helps us avoid repeation and improve diagnostics
* __matcher:__ a blob of logic that we use in test assertions.
* by having a series of matchers in a library, we can just pull in this library & we don't need to write them everytime in our code.
* __compositional:__ means matchers can be combined multiple matchers.
* thus a compositional matcher is a matcher that will take another matcher as a parameter.
* it's really useful and flexible b/c it means you can build up your own language of tests in terms of hamcrest matchers.
## Why TDD?
* means using the tests to inform the implementation.
* it allows us to think about 3 things at the design stage so as to improve the software dev't process.
  1. Designing the code 
  2. To encourage testing
  3. YAGNI (You Ain't Gonna Need It)
1. **Designing the Code**
* sometimes we get designs that look really really good on paper but come out horrible on implementation.
* only force ppl to do actually design to actually to improve/alter their code when there's a failing test or something that needs to be fixed. This discourages over design that might happen.
* goal is to establish TDD as a methodolgy for encouraging well designed and tested code.
2. **To encourage testing**
* with TDD we write test before we write implementation.
* so when this b/mes a habit you're naturally encourgaed to write test before you crack the whip.
3. **YAGNI**
* > Always implement things when you actually need them, never when you foresee that you need them.
## What is TDD?
* think of it like a workflow.
1. start by writing a failing test. before implementation. Don't make assumptions about implementation, don't try to fake the test to fiddle the implementation.
2. write a simple implementation that passes the test.
3. **refactor** don't change the behaivour of the implementation & test code but rather tidy/clean it up and run the tests again.
## Triangulation
* a technique that lets us take specific examples and generalize whole algorithms from that.
* most real world projects won't have specific requirements that can easily be translated in to testable implementation.
* think in terms of examples. So generate examples from conversations from the business stake holders and take those examples and use those to drive an algorithm from those examples.
* take specific examples, expand on them to produce a few more examples and then try to figure out the **general case** from a those examples.
* basically write a few tests for very specific cases and try to triangulate an implementation that'll satisfy all the n cases. for example:
> for a WordWrap class, write a test for a single wrap from a specified wrap length, make it work, then a no wrap test b/c it's smaller than the wrap length, make it work, then a test with a double and/or triple wrap length make it work, and try to triangulate an implementation that will encompass them all.
* To sum up TDD in three words **_Red, Green, Refactor_**.
## Dependencies
* a r/ship b/n two components where the functionality of one depends on the other component. It makes a method call on the other component.
* the problem is if we're gonna write a test about one class, we want to make sure only this class fails (test isolation). We don't want any issue of the other class causing a failure to the tests of this class.
* as you know **coupling** is a bad thing in SE.
## Dependency Injection
* in the above scenario, the dependencies are *embedded* in the class, using DI approach we make the dependencies *injected* thus decoupling them.
* it means instead of each class containing it's depedencies within it, it's gonna have a **__constructor parameter and that constructor parameter is gonna take in the dependecy as an argument__**.
* it can also be done using a setter method, but constructors are the simplest and most common approach.
* thus enforcing separation of concern, meaning the business logic is now separated from the configration/framework code (e.g. database class, database calls, network connectivity etc). 
* This will lead to an effective separation that if you were to change the database, for example, from a csv file to a SQL database it will only entail changing the database class and the methods it has, instead of the whole application code.
> on the IntelliJIDEA project ```PlularSightIntroductionToTestingInJava``` you can find a very thorough example about dependecy injection. ```git checkout --detach [commitID]``` to load the previous commit of the branch and take a look at the difference. 
> what basically happened was all the necessary components that were required by the application to work were placed in their respective constructors so that they can be called when they are needed. Thus leading them to be coupled to an external entity.
> the changes done were, instead of calling the necessary component in the constructor we injected the class that will actually handle the functionality of that dependency, thus lowering "coupling".
## Test Double
* a technique that we can use to improve our isolation of code in tests.
* it's similar to a car crash dummy.
* has the same public API but internally it's much much simpler, it's just designed so that you can test.
* there are 3 types of Test doubles:
1. Fake
    * something that has a working implementation but much simpler that the actual implementation.
    * e.g. in memory database instead of a full fledged RDMS.
2. Stub
    * is an implememtation that has ready made answers for method calls and only what's required for a test. It's pre-hand, pre-programmed answers for tests.
    * it just returns simple dumb values w/c are only values needed for that test to pass.
    * e.g. for a size() method in a Collection class, a stub might always return 1
3. Mock
    * kinda similar to stub, they might return values which are pre-programmed.
    * but they also set out expections for the interaction with the mock object.
    * e.g. the size() method in the above example, there might different requirements on these invocation, maybe you need to notify the UI of a new value and you can use a mock to verify that the UI method is being called.
> look at the code on `PlularSightIntroductionTestingInJava` project.
* for stub and mock, other than hand writting the tests, we use a java framework by the name of Mockito that treats the term **mock** to mean a *mock* or a *stub* as well.
#### I have left the appendices b/c they don't seem relevant, should that change you have the videos.

## Going Outside-In
> project is [PlularSightIntroductionTestingInJava, module 6](~/IntelliJIDEAProjects/PlularSightIntroductionToTestingInJava/src/com/negassagisila/testing/module6)
* this section describes the combined usage of all the above in a practical manner, thus leading from abstract functional requirements to well defined, testable classes, methods and variables.
* so what we're basically doing is: building the previous project being led by TDD.
* we break down the single big outside task into multiple simple inside tasks. Then go outside in and start testing each of those individual components and make sure they work thus leading to a working application.
* evaluating going outside-in should produce the steps as follows:
  * we've mocked out depedencies
  * we've only determined the interaction with the dependecies (so we've not said how those dependencies work/operate just said what values do they return for our tests)
  * then we've run some code and verified that the results meet our expectations.