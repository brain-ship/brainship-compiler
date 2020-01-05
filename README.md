# BrainShip Compiler
## Cross-platform, Cross-language, unified build toolkit
### Travis CI Build Status       :       [![Build Status](https://travis-ci.org/brain-ship/brainship-compiler.svg?branch=master)](https://travis-ci.org/brain-ship/brainship-compiler) 
### Azure Pipelines Build Status : [![Build Status](https://dev.azure.com/projectbrainship/projectbrainship/_apis/build/status/brain-ship.brainship-compiler?branchName=master)](https://dev.azure.com/projectbrainship/projectbrainship/_build/latest?definitionId=1&branchName=master) 
### SemaphoreCI Build Pipleines  : ![Semaphore Build Pipeline](https://brain-ship.semaphoreci.com/badges/brainship-compiler.svg?style=shields)
### Codacy Code Quality Grade : [![Codacy Badge](https://api.codacy.com/project/badge/Grade/dd528923c5b94102acefd2815278e004)](https://www.codacy.com/manual/project.brainship/brainship-compiler?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=brain-ship/brainship-compiler&amp;utm_campaign=Badge_Grade)

## About BrainShip Compile System
Project BrainShip is a **unified** code building platform for **multiple languages and platforms**\
Currently BrainShip supports:
*  Java Projects
*  C++ Integration [ Feature Under Construction ]
*  Python Integration [ Feature Under Construction ]

and

*	Windows
*	Linux [ Feature Under Construction ]

## Compiling Project BrainShip
BrainShip uses Maven build system\
Use the following command to compile and run tests ...
```shell
mvn package
cd target\
java -jar shield-compiler-1.0-SNAPSHOT.jar
```
## Using BrainShip
Run the jar file using the command given above\
Type ```newp [project-name]``` to create a new project\
Type ```compile``` to compile the project\
Type ```help``` to show a synopsis of commands

## Contact
For any information please feel free to drop a mail to project.brainship@gmail.com