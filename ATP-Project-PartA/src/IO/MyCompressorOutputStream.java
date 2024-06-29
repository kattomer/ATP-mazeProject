import java.io.IOException;
import java.io.OutputStream;


public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(compress(b));
    }

    public byte[] compress(byte[] bytes) {
        ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

        byte currentByte = bytes[0];
        int count = 1;

        for (int i = 1; i < bytes.length; i++) {
            if (bytes[i] == currentByte && count < 255) {
                count++;
            } else {
                compressedStream.write(currentByte);
                compressedStream.write(count);
                currentByte = bytes[i];
                count = 1;
            }
        }

        // Write the remaining byte and count
        compressedStream.write(currentByte);
        compressedStream.write(count);

        return compressedStream.toByteArray();
    }