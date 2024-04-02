# Test Automation Instructions

## Table of Contents

- [SoftwareComponents](#SoftwareComponents)
- [FrameworkStructure](#FrameworkStructure)
- [AutomationTestSetupRules](#AutomationTestSetupRules)
- [LocatorPriorities](#LocatorPriorities)
- [WrapperMethods](#WrapperMethods)
- [TestReport](#TestReport)
- [PullRequests](#PullRequests)
- [OutOfScope](#OutOfScope)
- [Contact](#Contact)

### SoftwareComponents

| Component                 | Version |    Usage    |    URL |	
|---------------------------|---------| ---	|	---	|	
| Maven                     | 3.9.6   |    Build Tool    |    https://maven.apache.org/install.html |
| Java                      | 17      |    Java JDK    |    https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html |
| Selenium                  | 4.13.0  |    Core Web Automation libray    |    It will auto download through build.gradle |
| testNg                    | 7.8.0   |    Core Libray to invoke Tests      |    It will auto download through build.gradle |
| Extent Reports/aventstack | 4.1.7   |    To Generate Reports    |    It will auto download through build.gradle |
| Eclipse/Intellij          | latest  |    IDE    |    https://www.jetbrains.com/idea/download/ |
| Appium                    | 8.3.0   |    Mobile Automation    |    http://appium.io/ |
| Android Studio            | 1.15.1  |    For Android SDK and To use emulator for Android Automation    |    https://developer.android.com/studio/install |
| GitBash                   | Latest  |    For Git Shell        |    https://git-scm.com/downloads |
| GitHub Desktop            | Latest  |    Git UI: For easy use of git navigations    |    https://desktop.github.com/ |

### FrameworkStructure

### AutomationTestSetupRules

1. Clone Project from https://github.com/TY-vinoth/TestYantra_Framework.git
2. Launch Intellij and Import project as Maven Project
5. Build the Project "Maven test"
6. If you are running for mobile, add .apk/.app/.ipa to src/test/resources/
7. Ensure that your project folder is not modified, it should be 'TestYantra'

Rules:

1. Always Execute Tests from Suite Level from the path src/test/resources/TestSuites
2. Make sure to provide below parameters in Suite file, refer web.xml

    <parameter name="browser" value=" "></parameter>
		<parameter name="platform" value=" "></parameter>
		<parameter name="execution_type" value=" "></parameter>
		<parameter name="url" value=" "></parameter>
3. One JSON file per page
4. One Page Class per JSON
5. Include all Page class objects in ObjectsFactory class.

### LocatorPriorities

|    Priority    | Web    |    Android    |    IOS    |	
|	---	|	---	| ---	|	---	|	
|    1    |    id    |    accessibilityid    |    accessibilityid    |
|    2    |    name    |    id    |    id    |
|    3    |    classname    |    uiautomator    |    uiautomator    |
|    4    |    linktext    |    name    |    name    |
|    5    |    css    |    xpath    |    xpath    |
|    6    |    xpath    |    datamatcher    |    datamatcher    |
|    7    |    partiallinktext    |   classname    |    classname    |
|    8    |    tagname    |    viewtag    |    viewtag    |
|    9    |		|    image    |    image    |
|    10    |		|    custom    |    custom    |
|    11    |		|    partiallinktext    |    partiallinktext    |
|    12    |		|    linktext    |    linktext    |
|    13    |		|    tagname    |    tagname    |
|    14    |		|    css    |    css    |

### WrapperMethods

|    Method Name    |    Return Value    |    TC Status    |    Logs In Report    |    Platforms    |    Usage    |
|	:---:	|	:---:	|	:---:	|	:---:	|	:---:	|	:---:	|
|    isPresent    |    TRUE - if the element is present<br><br>FALSE- if the element is NOT present    |    NOT FAIL    |    NO    |    Web,Mobile    |    Perform some action only if the element isPresent    |
|    isNotPresent    |    TRUE - if the element is present<br><br>FALSE- if the element is NOT present    |    FAIL - If element Present<br> PASS - if element isNotPresent    |    Yes    |    Web,Mobile    |    To Check Element presence and Log To Report    |
|    isNotDisplayed    |    TRUE - if the element is NOT Displayed<br><br>FALSE- if the element is Displayed    |    FAIL - If element is displayed<br> PASS - if element is NOT Displayed    |    Yes    |    Web,Mobile    |    To check invisibilty of Condition like, Element is present and also check if it is interactable or not    |
|    isEnabled    |    TRUE - if the element is Enabled<br><br>FALSE- if the element is NOT Enabled    |    FAIL - If element is NOT ENABLED<br> PASS - if element is ENABLED    |    Yes    |    Web,Mobile    |    To check if the element is enabled or not, also perform some actions only if element is enabled    |
|    isSelected    |    TRUE - if the element is Selected<br><br>FALSE- if the element is NOT Selected    |    FAIL - If element is NOT SELECTED<br> PASS - if element is SELECTED    |    Yes    |    Web    |    To check if the dropdown value is selected or not, also perform some actions only if the element selected    |

### UsageOfCodeGenerator

|    Parameter    | Usage    |    Additional Instructions    |		
|	---	|	---	| ---	|	
|    elementRepoPath    |    Location of the folder where the JSON files are available.    |    Provide Only folder path    |	
|    javaClassPath    |    Location of the folder where the generated pages has to be stored.    |    NA    |	
|    objFactoryPath    |    Location of the folder where the Object factory is generated.    |    NA    |	

### TestReport

1. Test Reports will be stored in locally src/test/resources/TestResults.
2. Test Report can be identified with your suitename along with Run date and time.

### PullRequests

After creating a pull request, codepipeline will check that TestYantra and the automationframework library
compile together.

- If a new version number of the automationlibrary is published, modify this within the buildspec.yml.
- If a change is required to be published in this library, first publish the change, then run git commit --amend, git
  push -f to retrigger the pipeline check (ensure git branch mode is 'simple' - git config --global push.default simple)
  to only push the current branch.
- The PR Build can be viewed here:

## OutOfScope

NA

## Contact

Incase of any issues, Please reach out at vinothkumar.e@testyantra.com

