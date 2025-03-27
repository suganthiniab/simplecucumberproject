package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src\test\resources\feature",
        glue = "steps" ,
        plugin ={
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml" ,
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        dryRun = true,
        monochrome = true
)

public class TestRunner {

}
