package model.utility.tester;

import org.objectweb.asm.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by meranote on 4/6/2016 AD.
 */
public class Tester {

    public static Method getMethod(final StackTraceElement stackTraceElement) throws Exception {
        final String stackTraceClassName = stackTraceElement.getClassName();
        final String stackTraceMethodName = stackTraceElement.getMethodName();
        final int stackTraceLineNumber = stackTraceElement.getLineNumber();
        Class<?> stackTraceClass = Class.forName(stackTraceClassName);

        // I am only using AtomicReference as a container to dump a String into, feel free to ignore it for now
        final AtomicReference<String> methodDescriptorReference = new AtomicReference<String>();

        String classFileResourceName = "/" + stackTraceClassName.replaceAll("\\.", "/") + ".class";
        InputStream classFileStream = stackTraceClass.getResourceAsStream(classFileResourceName);

        if (classFileStream == null) {
            throw new RuntimeException("Could not acquire the class file containing for the calling class");
        }

        try {
            ClassReader classReader = new ClassReader(classFileStream);
            classReader.accept(new ClassVisitor(327680) {
                @Override
                public MethodVisitor visitMethod(int access, final String name, final String desc, String signature, String[] exceptions) {
                    if (!name.equals(stackTraceMethodName)) {
                        return null;
                    }

                    return new MethodVisitor(327680) {
                        @Override
                        public void visitLineNumber(int line, Label start) {
                            if (line == stackTraceLineNumber) {
                                methodDescriptorReference.set(desc);
                            }
                        }
                    };
                }
            },0);
        } finally {
            classFileStream.close();
        }

        String methodDescriptor = methodDescriptorReference.get();

        if (methodDescriptor == null) {
            throw new RuntimeException("Could not find line " + stackTraceLineNumber);
        }

        for (Method method : stackTraceClass.getMethods()) {
            if (stackTraceMethodName.equals(method.getName()) && methodDescriptor.equals(Type.getMethodDescriptor(method))) {
                return method;
            }
        }

        throw new RuntimeException("Could not find the calling method");
    }

    public static boolean isCalledByMain() {
        for(StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            try {
                String methodname = (Tester.getMethod(elem)).getName();
                if(methodname.equals("main")) {
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        if(isCalledByMain()) {
            System.out.println("testing");
        }
    }

}
