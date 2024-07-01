package IO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;


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
    public void write(byte[] b) throws IOException{
        this.out.write(compress(b));
    }

    public byte[] compress(byte[] bytes){
        // Create a Deflater with the best compression level
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        // Use a ByteArrayOutputStream to hold the compressed data in memory
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater)) {
            // Write the input bytes to the DeflaterOutputStream
            deflaterOutputStream.write(bytes);
            deflaterOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new byte[0]; // In case of an error, return an empty byte array
        }
    }
}