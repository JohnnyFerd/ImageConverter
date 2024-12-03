import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Converter {
    public static boolean[] convertImage(String imagePath) throws IOException {
        int width = 128;
        int height = 64;
        int threshold = 128;

        // Load the image
        BufferedImage original = ImageIO.read(new File(imagePath));

        // Check if the image dimensions match
        if (original.getWidth() != width || original.getHeight() != height) {
            throw new IllegalArgumentException("Image must be exactly 128x64 pixels.");
        }

        // Initialize the boolean array
        boolean[] array = new boolean[width * height];

        // Process each pixel
        for (int i = 0; i < width * height; i++) {
            int x = i % width;
            int y = i / width;

            int rgb = original.getRGB(x, y);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;
            int grayValue = (r + g + b) / 3;

            array[i] = grayValue <= threshold; // true = black, false = white
        }

        return array;
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("File Name: ");
        String imagePath = scanner.nextLine();
        System.out.println();

        boolean[] array = convertImage(imagePath);
        if (array[0]) {System.out.print("{0x3f, ");} else {System.out.print("{0x00, ");}

        System.out.print("{");
        for (int i = 0; i < 128 * 64; i++) {
            if (array[i]) {System.out.print("0x3f");} else {System.out.print("0x00");}

            if (i < 128 * 64 - 1) {System.out.print(", ");}
        }
        System.out.println("}");
    }
}
