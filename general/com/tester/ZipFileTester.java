package com.tester;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipFileTester {

    public static void main(String[] args) throws Exception {
        method1();
        method2();
        method3();
        method4();
    }
    
    private static void method1() {
        try (ZipFile zipFile = new ZipFile("/Users/tvoniadka/tmp/JPFormForCarPdf.zip")) {
            Collections.list(zipFile.entries()).stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void method2() {
        try (ZipInputStream zis = new ZipInputStream(new InflaterInputStream(new FileInputStream("/Users/tvoniadka/tmp/JPFormForCarPdf_fixed.zip")))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void method3() {
        try (ZipFile zipFile = new ZipFile("/Users/tvoniadka/tmp/JPFormForCarPdf.zip",
                Charset.forName("Shift_JIS"))) {
            Collections.list(zipFile.entries()).stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void method4() {
        String path1 = "%E2%94%98I%E2%95%93%D1%9E%D0%B4%E2%95%9A%E2%95%90%D0%BC%D0%B4%E2%95%95.png";
        String path2 = "┘I╓ўд╚═мд╕.png";
        String path3 = URLDecoder.decode(path1, StandardCharsets.UTF_8);
        System.out.println(path1);
        System.out.println(path2);
        try (JarFile zipFile = new JarFile("/Users/tvoniadka/tmp/JPFormForCarPdf_fixed.zip")) {
            Collections.list(zipFile.entries()).stream().forEach(System.out::println);
            JarEntry jarEntry = zipFile.getJarEntry(path1);
            System.out.println(jarEntry);
            jarEntry = zipFile.getJarEntry(path3);
            System.out.println(jarEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
