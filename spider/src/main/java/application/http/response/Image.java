package application.http.response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Image {


    private String type;

    private byte[] bytes;

    private long size;

    private BufferedImage bufferedImage;


    /**
     * @param type
     * @param size
     * @param bytes
     */
    public Image(String type, long size, byte[] bytes) {
        this.type = type;
        this.size = size;
        this.bytes = bytes;
    }


    /**
     * @return
     */
    public String type() {
        return type;
    }

    /**
     * @return
     */
    public byte[] bytes() {
        return bytes;
    }

    public long size() {
        return this.size;
    }


    /**
     * @return
     * @throws IOException
     */
    public BufferedImage bufferedImage() throws IOException {
        if (this.bufferedImage == null) {
            this.bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
        }
        return this.bufferedImage;
    }


    /**
     * output stream and format by type
     *
     * @param out
     * @throws IOException
     */
    public void write(OutputStream out) throws IOException {
        ImageIO.write(bufferedImage(), type(), out);
    }
}
