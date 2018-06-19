# GridU learning path: QA engineer to Mobile QA engineer
The repository contains Certification task project source code and app under test.

Test Framework is based on:
- Appium
- Java
- TestNG
- Allure

## Project overview
To provide desired capabilities use `${baseDir}/test/resources/appium.properties`
this file will be read only if  `ClassPathPropertiesFileCapabilitiesProvider` is chosen as
`CapabilitiesProvider` implementation.

There are two implementation available at the moment
1. `ClassPathPropertiesFileCapabilitiesProvider `- reads `appium.properties`
2. `AWSCapabilitiesProvider` - returns empty `Capabilities` as required by Amazon

You can control which implementation to use by setting a `system property`.
For `maven-surefire-plugin` it looks like

    <systemPropertyVariables>
        <capabilities.provider>configuration</capabilities.provider>
        ...
    </systemPropertyVariable>
 By default (if no `capabilities.provider` property was set) `AWSCapabilitiesProvider` will be used.

 Default `TestNG` listener is configured in `pom.xml` see `maven-surefire-plugin` section.

    <property>
        <name>listener</name>
            <value>org.noname.core.allure.TakingScreenshotOnTestFailureListener</value>
    </property>

The listener for `allure` only it depends on its feature called `@Attachment`
see [allure project docs](http://allure.qatools.ru/)

## Chosen application is Shopping list-1.6.apk
### Test Cases
#### TC1
 >- create a new empty shopping list
 >- check that MainScreen has new list

#### TC2
>- create new product in shopping list
>- check that AddScreen has new product

#### TC3
>- change default currency
>- check that new currency is applied

#### TC4
>- change name of shopping list
>- check that name has changed on MainScreen

#### TC5
>- swap categories on CategoriesScreen
>- check that categories are swapped

#### TC6
>- create new category
>- check that new category has been created

#### TC7
>- create new product on MyListScreen
>- check that new product has been created

#### TC8
>- delete shopping list from MainScreen
>- shopping list has been deleted

#### TC9
>- add all products to shopping list from MyList
>- all products have been added

#### TC10
>- create own currency
>- new currency has been created and applied to products on MainScreen

## How to Run
>### requirements
>- allure (for local run)
>- android sdk (for local run)
>- android emulator/or android device (for local run)
>- appium (for local run)
>- maven
>- project on your file system (root of the project is `${baseDir}`)

### Local Execution
>### steps
>1. run appium server
>2. run android emulator, or connect a device
>3. download "Shopping list-1.6.apk"
>4. go to project  `${baseDir}/test/resources/appium.properties`
>5. change `app` property to full system path of  Shopping list-1.6.apk"
>6. if needed change `deviceName` property (to check connected run `adb devices` in terminal)
>7. execute `mvn clean test` from `${baseDir}`
>8. execute `mvn allure:serve` inorder to see allure report

### Execution on AWS
1. go to `${baseDir}` and run `mvn clean package –DskipTests=true` in shell
Follow the instruction provided by Amazon
