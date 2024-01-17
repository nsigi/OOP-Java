package gui;

import java.io.FileInputStream;
import java.io.IOException;

public class FunctionClassLoader extends ClassLoader {
    private byte[] loadClassData(String filename) throws IOException {
        FileInputStream in = new FileInputStream(filename);
        byte[] fileContent = new byte[in.available()];
        in.read(fileContent);
        in.close();
        return fileContent;
    }

    public Class loadClassFromFile(String filename) throws IOException {
        byte[] b = loadClassData(filename);
        return defineClass(null, b, 0, b.length);
    }
}
