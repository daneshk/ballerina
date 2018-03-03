package org.ballerinalang.grpc.utils;

import com.google.protobuf.DescriptorProtos;
import org.ballerinalang.grpc.exception.BalGenToolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * Util function used when generating bal file from .proto definition.
 */
public class BalFileGenerationUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BalFileGenerationUtils.class);
    
    /**
     * Create meta folder for storing meta files which is used in intermediate processing.
     *
     * @param folderPath folder path which is needed to be created.
     */
    public static void createMetaFolder(String folderPath) {
        boolean isFileCreated = new File(folderPath).getParentFile().mkdirs();
        if (!isFileCreated) {
            LOG.debug("Meta folder did not create successfully '" + folderPath + "'");
        }
        byte dataBytes[] = new byte[0];
        try {
            Path file = Paths.get(folderPath);
            Files.write(file, dataBytes);
        } catch (IOException e) {
            throw new BalGenToolException("Error creating .desc meta files.", e);
        }
    }
    
    /**
     * Execute command and generate file descriptor.
     *
     * @param command protoc executor command.
     * @throws UnsupportedEncodingException
     */
    public static void generateDescriptor(String command) throws UnsupportedEncodingException {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase(Locale.ENGLISH).startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", "dir");
        } else {
            builder.command("sh", "-c", command);
        }
        builder.directory(new File(System.getProperty("user.home")));
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new BalGenToolException("Error in executing protoc command '" + command + "'.", e);
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new BalGenToolException("Process not successfully completed. Process is interrupted while" +
                    " running the protoC executor.", e);
        }
    }
    
    /**
     * Build descriptor path using dependent proto path.
     *
     * @param protoPath dependent protoPath
     * @return descriptor path of proto
     */
    public static String getDescriptorPath(String protoPath) {
        return BalGenerationConstants.META_DEPENDENCY_LOCATION + protoPath
                .substring(protoPath.lastIndexOf(BalGenerationConstants.FILE_SEPARATOR), protoPath.length())
                .replace(".proto", BalGenerationConstants.EMPTY_STRING) + ".desc";
    }
    
    /**
     * Generate proto file and convert it to byte array.
     *
     * @param exePath        protoc executor path
     * @param protoPath      .proto file path
     * @param descriptorPath file descriptor path.
     * @return byte array of generated proto file.
     */
    public static byte[] getProtoByteArray(String exePath, String protoPath, String descriptorPath) {
        String command = new ProtocCommandBuilder(exePath, protoPath, protoPath.substring(0,
                protoPath.lastIndexOf(BalGenerationConstants.FILE_SEPARATOR)),
                descriptorPath).build();
        try {
            generateDescriptor(command);
        } catch (UnsupportedEncodingException e) {
            throw new BalGenToolException("Error generating descriptor for command '" + command + "'", e);
        }
        File initialFile = new File(descriptorPath);
        try (InputStream targetStream = new FileInputStream(initialFile)) {
            DescriptorProtos.FileDescriptorSet set = DescriptorProtos.FileDescriptorSet.parseFrom(targetStream);
            if (set.getFileList().size() > 0) {
                return set.getFile(0).toByteArray();
            }
        } catch (IOException e) {
            throw new BalGenToolException("Error reading generated descriptor file '" + descriptorPath + "'.", e);
        }
        return new byte[0];
    }
    
    /**
     * Used to clear the temporary files.
     *
     * @param file file to be deleted
     */
    public static void delete(File file) {
        if ((file != null) && file.exists() && file.isDirectory()) {
            String files[] = file.list();
            if (files != null) {
                if (files.length != 0) {
                    for (String temp : files) {
                        File fileDelete = new File(file, temp);
                        if (fileDelete.isDirectory()) {
                            delete(fileDelete);
                        }
                        if (fileDelete.delete()) {
                            LOG.debug("Successfully deleted file " + file.toString());
                        }
                    }
                }
            }
            if (file.delete()) {
                LOG.debug("Successfully deleted file " + file.toString());
            }
            if ((file.getParentFile() != null) && (file.getParentFile().delete())) {
                LOG.debug("Successfully deleted parent file " + file.toString());
            }
        } else if (file != null) {
            if (file.delete()) {
                LOG.debug("Successfully deleted parent file " + file.toString());
            }
        }
    }
    
    /**
     * Sae generated intermediate files.
     *
     * @param url  file URL
     * @param file destination file location
     */
    public static void saveFile(URL url, String file) {
        try (InputStream in = url.openStream(); FileOutputStream fos = new FileOutputStream(new File(file))) {
            int length;
            byte[] buffer = new byte[1024]; // buffer for portion of data from
            while ((length = in.read(buffer)) > -1) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            in.close();
        } catch (IOException e) {
            throw new BalGenToolException("Error saving file '" + file + "'.", e);
        }
        
    }
    
    /**
     * Grant permission to the protoc executor file.
     *
     * @param file protoc executor file.
     */
    public static void grantPermission(File file) {
        boolean isExecutable = file.setExecutable(true);
        boolean isReadable = file.setReadable(true);
        boolean isWritable = file.setWritable(true);
        if (isExecutable && isReadable && isWritable) {
            LOG.debug("Successfully grated permission for protoc exe file");
        }
    }
}
