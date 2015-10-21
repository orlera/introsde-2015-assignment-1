### Introduction to Service Design and Engineering Course - ASSIGNMENT 1

######SECTION ONE: code structure
The code is organized in three packages, following the schema used during the lab lessons:

1. the `model` package, containing the model objects, namely HealthProfile and Person java classes;
2. the `dao` (Data Access Object) package, containing the Storage class, used as a data storage when marshalling/unmarshalling; 
3. the `default` package, containing a class for every "main" action to be performed: Jsonizer, Mymarshaller, Myunmarshaller, XpathGetter.

######SECTION TWO: code insight
Launching the classes in the default package with the appropriate arguments, the requested tasks will be performed.
XpathGetter retrieves the data from the `people.xml` file, associated to `people.xsd`, while Mymarshaller and Myunmarshaller marshal/unmarshal the People (and therefore HealthProfile) class to/from the `marshalperple.xml` file. The Jsonizer print its output to `people.json`.

######SECTION THREE: run the code
To run the code and have the requested tasks performed, simply run `ant execute.evaluation` script in the project home directory.
It is also possible to run the six tasks singularly, by executing `ant execute.X`, where X is an integer between 1 and 6 corrisponding to the task list in the website's assignment page.