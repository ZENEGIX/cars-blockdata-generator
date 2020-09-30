package ru.zenegix.generator;

import javax.crypto.Cipher;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class BlockDataGeneratorMain {

    public static void main(String[] args) throws Exception {
        File workDirectory = new File(".");
        File inputDirectory = new File(workDirectory, "input");
        inputDirectory.mkdirs();
        String publicKey = Files.readString(getFile(inputDirectory, "publicKey.txt").toPath());
        String pin = Files.readString(getFile(inputDirectory, "pin.txt").toPath());
        String cardNumber = Files.readString(getFile(inputDirectory, "cardNumber.txt").toPath());

        File output = getFile(workDirectory, "output.txt");
        Files.writeString(output.toPath(), process(publicKey, pin, cardNumber));
    }

    private static String process(String key, String pin, String cardNumber) {
        try {
            if (cardNumber.length() != 16) {
                return "cardNumber должен быть 16-значным числом!";
            }

            cardNumber = cardNumber.substring(4, cardNumber.length() - 4);
            byte[] decodedKey = Base64.getDecoder().decode(key);

            KeyFactory kFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec spec =  new X509EncodedKeySpec(decodedKey);
            PublicKey publicKey = kFactory.generatePublic(spec);
            byte[] bytes = encrypt(publicKey, (cardNumber + "," + pin).getBytes());

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));

            return writer.getBuffer().toString();
        }
    }

    private static File getFile(File directory, String fileName) throws Exception {
        File file = new File(directory, fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    private static byte[] encrypt(Key publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

}
