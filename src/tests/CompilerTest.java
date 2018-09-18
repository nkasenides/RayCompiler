package tests;

import com.panickapps.ray.exceptions.FunctionAlreadyDefinedException;
import com.panickapps.ray.exceptions.UndeclaredVariableException;
import com.panickapps.ray.compiler.Main;
import com.panickapps.ray.exceptions.UndefinedFunctionException;
import com.panickapps.ray.exceptions.VariableAlreadyDefinedException;
import jasmin.ClassFile;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class CompilerTest {

    private Path tempDir;
    public static final String TEMP_DIRECTORY_NAME = "interpreterTest";

    @Test(dataProvider = "provideCodeExpectedText")
    public void codeTest(String description, String code, String expectedText) throws Exception {

        //Execution
        String actualOutput = compileAndRun(code);

        //Evaluation
        Assert.assertEquals(actualOutput, expectedText);

    }//end codeTest()

    /*
        tests for attempts to access undeclared variables.
     */
    @Test(expectedExceptions = UndeclaredVariableException.class, expectedExceptionsMessageRegExp = "1:8 Undeclared variable \"x\".")
    public void compilingCodeThrowsUndeclaredVariableException_IfVariableIsUndefined() throws Exception {
        //Execution
        compileAndRun("println(x);");

        //Evaluation performed by expected exception:


    }//end compilingCodeThrowsUndeclaredVariableException_IfVariableIsUndefined()

    /*
        tests for attempts to access undefined/uninitialized but declared variables.
     */
    @Test(expectedExceptions = UndeclaredVariableException.class, expectedExceptionsMessageRegExp = "1:8 Undeclared variable \"x\".")
    public void compilingCodeThrowsUndeclaredVariableException_IfReadingUndefinedVariable() throws Exception {
        //Execution
        compileAndRun("println(x);");

        //Evaluation performed by expected exception:


    }//end compilingCodeThrowsUndeclaredVariableException_IfReadingUndefinedVariable()

    /*
    tests for attempts to access undefined/uninitialized but declared variables.
 */
    @Test(expectedExceptions = UndeclaredVariableException.class, expectedExceptionsMessageRegExp = "1:0 Undeclared variable \"x\".")
    public void compilingCodeThrowsUndeclaredVariableException_IfWritingUndefinedVariable() throws Exception {
        //Execution
        compileAndRun("x = 5;");

        //Evaluation performed by expected exception:


    }//end compilingCodeThrowsUndeclaredVariableException_IfWritingUndefinedVariable()

    @Test(expectedExceptions = VariableAlreadyDefinedException.class, expectedExceptionsMessageRegExp = "2:4 Variable already defined: \"x\".")
    public void compilingCodeThrowsVariableAlreadyDefinedException_whenDefiningAlreadyDefinedVariable() throws Exception {
        //Execution
        compileAndRun("int x;"+ System.lineSeparator() +
                "int x;");

        //Evaluation performed by expected exception:

    }//end compilingCodeThrowsVariableAlreadyDefinedException_whenDefiningAlreadyDefinedVariable()

    @Test(expectedExceptions = UndefinedFunctionException.class, expectedExceptionsMessageRegExp = "1:8 Call to undefined function: \"someUndefinedFunction\".")
    public void compilingCodeThrowsUndefinedFunctionException_whenCallingUndefinedFunction() throws Exception {
        //Execution
        compileAndRun("println(someUndefinedFunction());");

        //Evaluation performed by expected exception:

    }//end compilingCodeThrowsUndefinedFunctionException_whenCallingUndefinedFunction()

    @Test(expectedExceptions = FunctionAlreadyDefinedException.class, expectedExceptionsMessageRegExp = "2:4 function already defined: \"x\".")
    public void compilingCodeThrowsFunctionAlreadyDefinedException_whenDefiningFunctionTwice() throws Exception {
        //Execution
        compileAndRun("int x() { return 42; }\nint x() { return 42; }");

        //Evaluation performed by expected exception:

    }//end compilingCodeThrowsFunctionAlreadyDefinedException_whenDefiningFunctionTwice()

    /*
        tests mathematical expressions.
     */
    @DataProvider
    public Object[][] provideCodeExpectedText() throws IOException {
        return new Object[][] {
                //{ "Addition", "1+2", "3" + System.lineSeparator()},
                //{ "Chained Plus", "1+2+42", "45" + System.lineSeparator()},

                //{"Program definition", "#PROGRAM Nicos", ""},
                {"Tier definition", "#TIER 1; println(3);", "3" + System.lineSeparator() },

                { "Var Init", "int x = 3; println(x);", "3" + System.lineSeparator()},
                { "Var Init", "int x = 3; int y = 2; println(x + y);", "5" + System.lineSeparator()},
                { "Multiple statements", "println(1); println(2);", "1" + System.lineSeparator() + "2" + System.lineSeparator()},
                { "Subtraction", "println(3-2);", "1" + System.lineSeparator()},
                { "Multiplication", "println(2*3);", "6" + System.lineSeparator()},
                { "Integer Division", "println(6/2);", "3" + System.lineSeparator()},
                { "Integer Division with Truncation", "println(7/2);", "3" + System.lineSeparator()},
                { "Mult/Div Precedence", "println(8/2*4);", "16" + System.lineSeparator()},
                { "Add/Mult Precedence", "println(2+3*3);", "11" + System.lineSeparator()},
                { "Mult/Sub Precedence", "println(9-2*3);", "3" + System.lineSeparator()},
                { "Add/Sub Precedence", "println(8-2+5);", "11" + System.lineSeparator()},
                { "Bracketed Expressions", "int x; x = 2 * (3 + 2); println(x);", "10" + System.lineSeparator() },
                { "expr", "int x; x = 1+2+3+4; println(x);", "10" + System.lineSeparator() },
                { "expr", "int x; x = 1+2+(3*4); println(x);", "15" + System.lineSeparator() },
                { "expr", "int x; x = (1+2)*(3+4); println(x);", "21" + System.lineSeparator() },
                { "expr", "int x; x = (1+2) * (3+4) * (3-2); println(x);", "21" + System.lineSeparator() },
                { "expr", "int x; x = (30+10+(5+5)); println(x);", "50" + System.lineSeparator() },
                { "expr", "int x; x = (50 + 50) / (10 * (4 + 1)); println(x);", "2" + System.lineSeparator() },
                { "expr", "int x; x = (50+50) / (4*25) + (100-30-(40+30)); println(x);", "1" + System.lineSeparator() },
                { "expr", "int x; x = (10 + 20) / (15 + 15) + (10 - 3 - (4 + 3)); println(x);", "1" + System.lineSeparator() },
                { "expr", "int x; x = 3%2; println(x);", "1" + System.lineSeparator() },
                { "expr", "int x; x = 46%30; println(x);", "16" + System.lineSeparator() },
                { "negatives", "int x; x = 50 + (-50); println(x);", "0" + System.lineSeparator() },

                { "Variable declaration and assignment", "int foo; foo = 42; println(foo);", "42" + System.lineSeparator()},
                { "Parameter passing", "int foo; foo = 42; println(foo+2);", "44" + System.lineSeparator()},
                { "Multiple variables", "int a; int b; a = 2; b = 5; println(a+b); ", "7" + System.lineSeparator()},

                example("function/function", "4" + System.lineSeparator()),
                example("function/simple_function", "4" + System.lineSeparator()) ,
                example("function/scopes", "4" + System.lineSeparator() + "42" + System.lineSeparator()),
                example("function/parameters", "13" + System.lineSeparator()),
                example("function/overloading", "0" + System.lineSeparator() + "42" + System.lineSeparator()),

                example("branch/if_int_false", "42" + System.lineSeparator()),
                example("branch/if_int_true", "81" + System.lineSeparator()),

                { "lower than true", "println(1 < 2);", "1" + System.lineSeparator() },
                { "lower than false", "println(2 < 2);", "0" + System.lineSeparator() },
                { "lower than or equal true", "println(2 <= 2);", "1" + System.lineSeparator() },
                { "lower than or equal false", "println(3 <= 2);", "0" + System.lineSeparator() },
                { "greater than true", "println(3 > 2);", "1" + System.lineSeparator() },
                { "greater than false", "println(2 > 2);", "0" + System.lineSeparator() },
                { "greater than or equal true", "println(2 >= 2);", "1" + System.lineSeparator() },
                { "greater than or equal false", "println(1 >= 2);", "0" + System.lineSeparator() },

                { "and comparison - true", "println(1 && 1);", "1" + System.lineSeparator() },
                { "and comparison - left false", "println(0 && 1);", "0" + System.lineSeparator() },
                { "and comparison - right false", "println(1 && 0);", "0" + System.lineSeparator() },
                example("operators/and-skip-right", "1" + System.lineSeparator() + "0" + System.lineSeparator() + "0" + System.lineSeparator()),

                { "or comparison - false", "println(0 || 0);", "0" + System.lineSeparator() },
                { "or comparison - left true", "println(1 || 0);", "1" + System.lineSeparator() },
                { "or comparison - right true", "println(0 || 1);", "1" + System.lineSeparator() },
                example("operators/or-skip-right", "1" + System.lineSeparator() + "1" + System.lineSeparator()),

                {"print", "print(42);", "42"},
                {"print string literal", "print(\"Hello World\");", "Hello World"},

        };
    }//end provideCodeExpectedText()

    private static String[] example(String name, String expectedResult) throws IOException {
        try (InputStream in = CompilerTest.class.getResourceAsStream("/examples/" + name + ".txt")) {
            if (in == null) {
                throw new IllegalArgumentException("No such example \"" + name + "\"");
            }
            String code = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
            return new String[] { name, code, expectedResult };
        }
    }

    private String compileAndRun(String code) throws Exception {
        code = Main.compile(new ANTLRInputStream(code));
        System.out.println("\n\nBYTECODE\n\n" + code + "\n\n");
        ClassFile classFile = new ClassFile();
        classFile.readJasmin(new StringReader(code), "", false);
        Path outputPath = tempDir.resolve(classFile.getClassName() + ".class");
        try(OutputStream out = Files.newOutputStream(outputPath)) { classFile.write(out); }
        return runJavaClass(tempDir, classFile.getClassName());
    }//end compileAndRun()

    private String runJavaClass(Path dir, String className) throws IOException {
        Process process = Runtime.getRuntime().exec(new String[] {"java", "-cp", dir.toString(), className});
        try (InputStream in = process.getInputStream()) {
            return new Scanner(in).useDelimiter("\\A").next();
        }//end try
    }//end runJavaClass()

    @BeforeMethod
    public void createTempDir() throws IOException {
        tempDir = Files.createTempDirectory(TEMP_DIRECTORY_NAME);
    }//end createTempDir()

    @AfterMethod
    public void deleteTempDir() {
        deleteRecursive(tempDir.toFile());
    }//end deleteTempDir()

    private void deleteRecursive(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursive(child);
            }
        }//end if
        if (!file.delete()) {
            throw new Error("Could not delete file <" + file + ">");
        }
    }//end deleteRecursive()

}//end class CompilerTest