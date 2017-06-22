package com.jara.retrofit_rxjava_okhttp_demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by jara on 2017-6-21.
 */

public class SerializableUtils {

    public static <T extends Serializable> Object readObject(File file) {
        ObjectInputStream ois = null;
        T t = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            t = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T extends Serializable> Object writeObject(T t, String filename) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(t);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (oos != null) try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getSerializableFile(String rootPath, String fileName) throws IOException {
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File serializable = new File(file, fileName);
        if (!serializable.exists()) {
            serializable.createNewFile();
        }
        return serializable;
    }

}
