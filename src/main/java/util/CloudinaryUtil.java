package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryUtil {

    private static final String CLOUDINARY_URL = "cloudinary://682296232774583:nV9TU2W7f0wcUlRxUtLFT9R9_8U@dkih1jlus";
    private static Cloudinary cloudinary;

    static {
        cloudinary = new Cloudinary(CLOUDINARY_URL);
    }

    /**
     * Uploads a file to Cloudinary and returns the secure URL.
     * @param file The file to upload.
     * @return The secure URL of the uploaded image.
     * @throws IOException If the upload fails.
     */
    public static String upload(File file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }
}
