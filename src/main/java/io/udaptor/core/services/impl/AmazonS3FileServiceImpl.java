package io.udaptor.core.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import io.udaptor.core.services.FileService;
import io.udaptor.core.util.Assert;
import io.udaptor.core.util.Util;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AmazonS3FileServiceImpl implements FileService {

    private final AmazonS3 s3;
    private final String bucketName;
    private final String outputFolder;
    private final String s3BaseUrl;

    public AmazonS3FileServiceImpl(
            final AmazonS3 s3,
            @Value("${s3_bucket}") final String bucketName,
            @Value("${output_folder}")final String outputFolder,
            @Value("${s3_bucket_url}") final String s3BaseUrl
    )
    {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.outputFolder = outputFolder;
        this.s3BaseUrl = s3BaseUrl;
    }

    @Override
    public String upload(final MultipartFile file) throws IOException {
        Assert.notNull(file, "file cannot be null");
        final String key = outputFolder + "/" + file.getName() + "_" + RandomStringUtils.randomAlphabetic(20);
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getResource().contentLength());
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        s3.putObject(bucketName, key, file.getInputStream(), objectMetadata);
        return s3BaseUrl + "/" + key;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public String upload(final File file) throws IOException {
        Assert.notNull(file, "file cannot be null");
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        final String fileExt = Util.getFileExtension(file);
        final String key = buildS3Key(file, fileExt);
        objectMetadata.setContentLength(file.length());
        objectMetadata.setContentType(Util.getFileExtension(file));
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        s3.putObject(bucketName, key, new FileInputStream(file.getPath()), objectMetadata);
        return s3BaseUrl + "/" + key;
    }

    @Override
    public File download(final String url) throws URISyntaxException, IOException {
        final URI s3Uri = new URI(url);
        final AmazonS3URI s3URI = new AmazonS3URI(s3Uri);
        final S3Object s3Object = s3.getObject(s3URI.getBucket(), s3URI.getKey());
        InputStream is = s3Object.getObjectContent();
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        File target = new File( "src/main/resources/" + s3URI.getKey() );
        if(!target.exists()) {
            target.getParentFile().mkdirs();
            target.createNewFile();
        }

        Files.write(target.toPath(), IOUtils.toByteArray(is));
        return target;
    }

    private String buildS3Key(final File file, final String fileExt) {
        return outputFolder + "/" + file.getName() + "_" + RandomStringUtils.randomAlphabetic(20) + "." + fileExt ;
    }


    @PostConstruct
    void init() throws IOException, URISyntaxException {
//        System.out.println("Testinf file upload");
//        String file = upload(new File("/Users/ankushsharma/Downloads/VBP_fina.pptx"));
//        System.out.println(file);

        File file = download("https://udaptor.s3-eu-west-1.amazonaws.com/output/VBP_fina.pptx_TxRiFaBoTgmSmJfgdyDv.pptx");

    }
}
