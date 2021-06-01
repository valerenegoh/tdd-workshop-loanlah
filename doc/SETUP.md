# Setup

## IntelliJ

### Import project
Run IntelliJ and `Open` project

![open](img/open.png)

Once it's done, make sure your project uses jvm version `11.x`

![project](img/project.png)

### Import gradle modules
Firstly open project structure (`⌘ ,` shortcut) and import `gradle` modules

![import1](img/import1.png)

select `build.gradle` file.

Make sure that *gradle wrapper* is selected and jvm version is `11.x`

![import1](img/import2.png)

Imported modules should be recognized by Intellij as modules

![gradle-modules](img/gradle-modules.png)

### Running the tests from IntelliJ
If you would like to run test by platform instead of gradle select `Build / Execution / Deployment` in preferences

![runner](img/runner.png)
