package com.epam.mjc;

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
        if (signatureString == null || signatureString.isEmpty()) {
            throw new IllegalArgumentException("Signature string must not be null or empty.");
        }

        String[] parts = signatureString.split("\\(");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid method signature format: " + signatureString);
        }

        String methodInfo = parts[0].trim();
        String argumentsPart = parts[1].trim().replace(")", ""); // Remove the closing parenthesis

        String[] methodParts = methodInfo.split("\\s+", 2); // Split into at most 2 parts
        MethodSignature methodSignature = new MethodSignature(methodParts[methodParts.length - 1]); // Last part is always the method name

        if (methodParts.length >= 2) {
            methodSignature.setReturnType(methodParts[0]); // Swap the order of assignment
            methodSignature.setAccessModifier(methodParts[1]); // Swap the order of assignment
        } else {
            methodSignature.setReturnType(methodParts[0]);
        }

        String[] argumentStrings = argumentsPart.split(",");
        for (String argumentString : argumentStrings) {
            argumentString = argumentString.trim();
            String[] argumentParts = argumentString.split("\\s+");
            if (argumentParts.length == 2) {
                methodSignature.getArguments().add(new MethodSignature.Argument(argumentParts[0], argumentParts[1]));
            } else {
                throw new IllegalArgumentException("Invalid argument format: " + argumentString);
            }
        }

        return methodSignature;
    }
}
