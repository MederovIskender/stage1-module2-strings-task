package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        MethodSignature methodSignature = new MethodSignature();

        // Split the signatureString into parts
        String[] parts = signatureString.split("\\s|\\(|\\)|,");

        // Set access modifier if present
        if (isAccessModifier(parts[0])) {
            methodSignature.setAccessModifier(parts[0]);
            parts = shiftArray(parts, 1);
        }

        // Set return type
        methodSignature.setReturnType(parts[0]);
        parts = shiftArray(parts, 1);

        // Set method name
        methodSignature.setMethodName(parts[0]);
        parts = shiftArray(parts, 1);

        // Parse arguments
        List<MethodSignature.Argument> arguments = parseArguments(parts);
        methodSignature.setArguments(arguments);

        return methodSignature;
    }

    private boolean isAccessModifier(String s) {
        return s.equals("public") || s.equals("protected") || s.equals("private");
    }

    private String[] shiftArray(String[] arr, int count) {
        String[] result = new String[arr.length - count];
        System.arraycopy(arr, count, result, 0, result.length);
        return result;
    }

    private List<MethodSignature.Argument> parseArguments(String[] parts) {
        List<MethodSignature.Argument> arguments = new ArrayList<>();
        for (int i = 0; i < parts.length-1; i += 2) {
            String argType = parts[i];
            String argName = parts[i + 1];
            arguments.add(new MethodSignature.Argument(argType, argName));
        }
        return arguments;
    }

}
