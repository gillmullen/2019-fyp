# Blog: Hopeful - A First Programming Language

**Gillian Mullen**

## 05/02/19

Delay in beginning project as the ethics of surveying DCU students and staff. The solution was fill in an Ethics form, and to completely anonymise the data and collect no personal data from the participants.<br/>
Surveys have been drafted and proofread. Ethics form, Plain Language Statement, and Informed Consent Form has been completed and submitted.<br/>
Next step is to distribute surveys to the different demographics: beginning programmers, Engineering students, advanced programmers, and School of Computing staff. Once the surveys have been collected, they must be analysed.<br/>
Before the next meeting, I will complete the analysis. In the mean time, I will practice a run-through of the project with a very simple language, as to ensure that I completely understand what needs to be done.

## 12/02/19

Surveys have been collected, and the analysis on them completed.<br/>
Finer details of the language need to be decided. For example, will parameters be passed by value or reference, and will I include both arrays and lists in my language?<br/>
The next step is to write up a complete analysis of the data, and create a language definition. In order to help create a language definition, I will sketch out what my language should look like. Then, I will break my language down into sub languages. I am doing this as to not overwhelm myself with too much complex work at once.

## 20/02/19

Due to a brief illness, the analysis was not finished. The language definition is complete, with the exception of arrays. I have decided to only include arrays in my language, as I believe including both arrays and lists could be confusing to the student. I have also decided to use pass-by-value, as I believe it will be easier to understand for the students. I successfully broke my language down into a series of sub-languages which I will be working on for the next few weeks.<br/>
My goal for the next week is to complete the first three sub-languages and complete my analysis.<br/>
In terms of testing, I will write test for each sub-language. However, each language will also use the tests of the previous languages. For example, the second sub-language will use the second set of tests as well as the first.

## 01/03/19

This week, I finished the write up of my analysis. I have mostly completed the first three sub-languages. The only things to be finished are the generating of executable LLVM, and some semantic checks in the third sub-language.<br/>
My goal for next week is to finish the semantic checks, LLVM, and creating a testing structure. I am also aiming to finish the fourth and fifth sub-languages.

## 11/03/19

This week, I finished the syntax and semantic of the fourth and fifth sub-languages. I am leaving the IR generation until I have finished reinstalling LLVM, as the first time installing was unsuccessful. Currently, I am slightly behind schedule.<br/>
My aim for the next week is to finish reinstalling LLVM, to finish sub-languages 4 and 5, do syntax and semantics for the sixth, and catch up on testing.

## 16/03/19

Multiple attempts of reinstalling LLVM has been unsuccessful, owing mainly to my use of Windows which LLVM does not work well with.  In order to overcome this, my plan is to create a Linux Virtual Machine and install LLVM on there, as the installation process seems simpler for Linux.

## 19/03/19

Installing LLVM on a Linux VM was successful. After this, IR up to the fifth sub-language was completed, as well as syntax and semantics. Schedule-wise, the project is right on track.<br/>
For the next week, my goal is finish the sixth sub-language, and do the syntax and semantics for the seventh. As well as this, I will catch up on testing. I will also be cleaning up and commenting on parts of the code. My goal for the rest of the month is to completely finish the language. Then, at the start of April, I will write user manuals and conduct user testing.

## 29/03/19

Progress was minimum for this sprint due to an illness. So, I am trying to finish the IR for the sixth sub-language, which is the most difficult of them all due to the fact I am implementing arrays. I am still on schedule, with 2 weeks of development left and 1 left for evaluation this semester. My goal for next week is to finish both the sixth and seventh sub-languages.

## 05/04/19

Due to difficulty implementing arrays, the sixth and seventh sub-languages were swapped. This means that the sixth will implement functions and the seventh arrays. The sixth is nearly finished, IR needs finishing. Plans for next week is to finish both languages.

## 12/04/19

Progress was slower than expected. The sixth IR is nearl finished which leaves the seventh.<br/>
My plans are to only evaluate up to the sixth sub-language, due to time constraints. Next week, my plan is to create all of the documents, and virtual machine, neccessary to carry out the evaluation and then carry out the evaluation.

## 20/04/19

There were delays this week, due to illness and the evaluation version of Hopeful and the enviroment not cooperating. Because of this, evaluations is being pushed until after the bank holiday.<br/>
In the meantime, I will continue with getting the evaluation ready and then continue with the final IR.

## 26/04/19

There was a lackluster turnout at the evaluations. This is because the email due for the Computing facility was not sent out in time due to my evaluation needing to be checked in accordance to data privacy regulations, once more. So, as of now, I have only a small response from Engineering students from my email, and Computing students from word of mouth. I will attempt to write up a report based on the feedback I already have, which was of good quality.<br/>
Due to arrays proving more difficult than anticipated, I will move onto fixing bugs that came up during evaluation. I will also try to incorporate some suggestions I received during the evaluation.

## 16/05/19

The evaluation report has been finished. Bugs have been fixed and some suggestions have been added. These include print function calls and letting the user assign a value to a variable on its declaration line.<br/>
I have decided to remove arrays and floats from the final version due to time constraints. I believe that without these features, the language still accomplishes its task of being a beginners' language, although it would mean using the language for less time when using it.<br/>
Leaving these features out, leaving me more time to perfect my documentation and testing which will be my focus until the deadline.
