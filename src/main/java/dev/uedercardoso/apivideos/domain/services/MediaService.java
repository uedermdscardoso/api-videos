package dev.uedercardoso.apivideos.domain.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import dev.uedercardoso.apivideos.domain.exceptions.FileIsNotVideoException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaIsEmptyException;
import dev.uedercardoso.apivideos.domain.exceptions.MediaNotFoundException;
import dev.uedercardoso.apivideos.domain.exceptions.UploadFailedException;
import dev.uedercardoso.apivideos.domain.model.Media;
import dev.uedercardoso.apivideos.domain.repository.MediaRepository;

@Service
public class MediaService {

	@Autowired
	private MediaRepository midiaRepository;

	@Value("${aws.bucketName}")
	private String bucketName; //application.properties
	
	@Value("${aws.domain}")
	private String domainAws;
	
	@Value("${aws.accessKey}")
	private String accessKey; 
	
	@Value("${aws.secretKey}")
	private String secretKey;
	
	
	public List<Media> getMedias(Integer all){
		List<Media> media = new LinkedList<Media>();
		if(all == 1)
			media = midiaRepository.findAll();
		else 
			media = midiaRepository.findByDeleted(false);
		
		if(media.isEmpty() || media.size() == 0)
			throw new MediaIsEmptyException("Media is empty");
		
		return media;
	}
	
	public Media getMedia(Integer id){
		if(!midiaRepository.existsById(id) || midiaRepository.existsByIdAndDeleted(id, true))
			throw new MediaNotFoundException ("Media "+id+" not found");
			
		return midiaRepository.findById(id).get();
		
	}
	
	public void createMedia(String name, Integer duration, MultipartFile video) throws IOException, AmazonServiceException, SdkClientException, Exception {
		
		if(!isVideoFile(video)) 
			throw new FileIsNotVideoException("File is not video");
		
		String url = this.uploadMedia(video);
		
		if(!url.isEmpty()) {
			Media media = new Media(name, url, duration);
			midiaRepository.save(media);
		} else {
			throw new UploadFailedException("Upload failed");
		}
		
	}
	
	public void updateMedia(Media media) {
		if(media.getId() == null || !midiaRepository.existsById(media.getId()) || midiaRepository.existsByIdAndDeleted(media.getId(), true) )
			throw new MediaNotFoundException("Media not found");
		
		midiaRepository.save(media);
	}
	
	public void removeMedia(Integer id) {
		if(!midiaRepository.existsById(id) || midiaRepository.existsByIdAndDeleted(id, true))
			throw new MediaNotFoundException("Media not found");
		
		Media media = midiaRepository.findById(id).get();
		media.setDeleted(true);
		
		midiaRepository.save(media);
	}
	
	private String uploadMedia(MultipartFile video) throws IOException, AmazonServiceException, SdkClientException, Exception {
		
        try {
        	Regions clientRegion = Regions.SA_EAST_1;
    		
        	BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();
            
            InputStream conteudoArquivo = video.getInputStream();
            
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(video.getContentType());
			metadata.setContentLength(video.getSize());
			
			String fileName = UUID.randomUUID()+"-media"; //video.getName();
			PutObjectRequest request = new PutObjectRequest(bucketName, fileName, conteudoArquivo, metadata);
            
			s3.putObject(request);
    		
			String url = "https://"+bucketName+"."+domainAws+"/"+fileName;
						
			return url;
			
        } catch(AmazonServiceException e) {
            throw new AmazonServiceException(e.getMessage());
        } catch(SdkClientException e) {
            throw new SdkClientException(e.getMessage());
        } catch(Exception e) {
            throw new Exception(e.getMessage());	
        }
    
	}
	
	private boolean isVideoFile(MultipartFile video) {
	    String mimeType = video.getContentType();
	    return mimeType != null && mimeType.startsWith("video");
	}
	
	
}
