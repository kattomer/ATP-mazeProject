import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        this.in.read(decompress(b));    
    }

    @Override
    public byte[] decompress(byte[] compressedBytes) throws IOException {
        ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();

        for (int i = 0; i < compressedBytes.length; i += 2) {
            byte currentByte = compressedBytes[i];
            int count = compressedBytes[i + 1];

            for (int j = 0; j < count; j++) {
                decompressedStream.write(currentByte);
            }
        }

        return decompressedStream.toByteArray();
    }
}
