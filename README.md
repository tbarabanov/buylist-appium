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
 By default (if no `capabilities.provider` property was set) `ClassPathPropertiesFileCapabilitiesProvider` will be used.

 Default `TestNG` listener is configured in `org.noname.test.HappyPathTest.java` class through annotation `@Listeners({TakingScreenshotOnTestFailureListener.class})`, but another option is `pom.xml` see `maven-surefire-plugin` section.

    <property>
        <name>listener</name>
            <value>org.noname.core.allure.TakingScreenshotOnTestFailureListener</value>
    </property>

The listener is for `allure` ONLY it depends on its feature called `@Attachment`
see [allure project docs](http://allure.qatools.ru/)

## Chosen application is Shopping list-1.6.apk
### Test Cases
#### TC1
 1.  create a new empty shopping list
 2. check that `MainScreen` has a created list
#### TC2
1. create new product in shopping list
2. check that `AddScreen` has new product
#### TC3
1. change default currency
2. check that new currency is applied
#### TC4
1. change name of shopping list
2. check that name has changed on `MainScreen`
#### TC5
1.  swap categories on `CategoriesScreen`
2. check that categories are swapped
#### TC6
1. create new category
2. check that new category has been created
#### TC7
1. create new product on `MyListScreen`
2. check that new product has been created
#### TC8
1. delete shopping list from `MainScreen`
2. shopping list has been deleted
#### TC9
1. add all products to shopping list from `MyList`
2. all products have been added
#### TC10
1. create own currency
2. new currency has been created and applied to products on `MainScreen`

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
>3. go to project  `${baseDir}/test/resources/appium.properties`
>4. change `app` property to full system path of Shopping list-1.6.apk (`opt/BuyList.apk`)
>5. if needed change `deviceName` property (to check connected run `adb devices` in terminal)
>6. execute `mvn clean test` from `${baseDir}`
>7. execute `mvn allure:serve` to see allure report

### Execution on `SauceLabs`
1. set `testobject_api_key= `
2. follow the instruction provided by `SauceLabs`, to configure `Capabilities` change/set them in `${baseDir}/src/test/resources/appium.properties`
3. execute `mvn clean test` from `${baseDir}`
4. to generate local report execute `mvn allure:serve`
