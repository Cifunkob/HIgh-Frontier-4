package hr.tvz.pejkunovic.highfrontier.util;

import hr.tvz.pejkunovic.highfrontier.exception.DocumentationGenerationException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DocumentationUtil {

    private DocumentationUtil() {}

    public static final void generateDocumentation() {
        String htmlPageStart = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Page Title</title>
        </head>
        <body>""";

        String htmlPageEnd = """
        </body>
        </html>""";

        StringBuilder htmlBodyBuilder = new StringBuilder();
        String classPath = "./target/classes/";

        try (Stream<Path> pathStream = Files.walk(Path.of(classPath))) {
            List<Path> tempClassNamesList = pathStream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".class"))
                    .filter(p -> !p.toString().endsWith("module-info.class"))
                    .toList();

            for (Path p : tempClassNamesList) {
                String fullClassName = Path.of(classPath).relativize(p).toString()
                        .replace(File.separatorChar, '.')
                        .replaceAll("\\.class$", "");

                Class<?> reflectionClass = loadClassSafely(fullClassName);
                if (reflectionClass == null) {
                    continue;
                }

                appendConstructors(reflectionClass, htmlBodyBuilder);
                appendMethods(reflectionClass, htmlBodyBuilder);
                appendFields(reflectionClass, htmlBodyBuilder);
            }

            String htmlFileContent = htmlPageStart + htmlBodyBuilder + htmlPageEnd;
            Files.writeString(Path.of("doc/documentation.html"), htmlFileContent);

        } catch (IOException e) {
            throw new DocumentationGenerationException("The documentation wasn't generated!", e);
        }
    }

    private static Class<?> loadClassSafely(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException | NoClassDefFoundError _) {
            return null;
        }
    }



    private static void appendConstructors(Class<?> clazz, StringBuilder htmlBodyBuilder) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            htmlBodyBuilder.append("<h3>")
                    .append(Modifier.toString(constructor.getModifiers()))
                    .append(" ")
                    .append(constructor.getName())
                    .append("(");
            appendParameters(constructor.getParameters(), htmlBodyBuilder);
            htmlBodyBuilder.append(")</h3>");
        }
    }

    private static void appendMethods(Class<?> clazz, StringBuilder htmlBodyBuilder) {
        for (Method method : clazz.getDeclaredMethods()) {
            htmlBodyBuilder.append("<h3>")
                    .append(Modifier.toString(method.getModifiers()))
                    .append(" ")
                    .append(method.getReturnType().getSimpleName())
                    .append(" ")
                    .append(method.getName())
                    .append("(");
            appendParameters(method.getParameters(), htmlBodyBuilder);
            htmlBodyBuilder.append(")</h3>");
        }
    }

    private static void appendFields(Class<?> clazz, StringBuilder htmlBodyBuilder) {
        for (Field field : clazz.getDeclaredFields()) {
            htmlBodyBuilder.append("<h3>")
                    .append(Modifier.toString(field.getModifiers()))
                    .append(" ")
                    .append(field.getType().getSimpleName())
                    .append(" ")
                    .append(field.getName())
                    .append("</h3>");
        }
    }

    private static void appendParameters(Parameter[] parameters, StringBuilder htmlBodyBuilder) {
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            htmlBodyBuilder.append(parameter.getType().getSimpleName())
                    .append(" ")
                    .append(parameter.getName());
            if (i < parameters.length - 1) {
                htmlBodyBuilder.append(", ");
            }
        }
    }


}
