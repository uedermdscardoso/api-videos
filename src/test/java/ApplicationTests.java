

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.uedercardoso.apivideos.Application;
import dev.uedercardoso.apivideos.domain.model.Media;
import dev.uedercardoso.apivideos.domain.repository.MediaRepository;
import dev.uedercardoso.apivideos.web.controllers.MediaController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	private final String DEFAULT_URL = "http://localhost";

	@Autowired
	private MediaRepository mediaRepository;
	
	@LocalServerPort
    private int randomServerPort;

	private RestTemplate restTemplate;
	
	private File videoExample;
	
	@Before
    public void setUp() throws Exception {
		this.restTemplate = new RestTemplate();
	
		videoExample = new File(getClass().getResource("dev/uedercardoso/apivideos/city-and-car-3d.mp4").toURI());
		
	}
	
	@Test
    public void getAllMedia() throws Exception 
    {    
		URI uri = new URI(DEFAULT_URL + ":" + randomServerPort + "/medias/1");
        
		ResponseEntity<Media[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Media[].class);
        List<Media> media =  Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, media != null && media.size() > 0);        
    }
    
	@Test
    public void getExistMedia() throws Exception 
    {
    	this.restTemplate = new RestTemplate();
        URI uri = new URI(DEFAULT_URL + ":" + randomServerPort + "/medias/0");
        
        ResponseEntity<Media[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, Media[].class);
        List<Media> media =  Arrays.asList(responseEntity.getBody());
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(true, media != null && media.size() > 0);
        
    }
    
	@Test
    public void getUniqueMedia() throws Exception 
    {
    	Integer id = 11;
    	this.restTemplate = new RestTemplate();
    	
        URI uri = new URI(DEFAULT_URL + ":" + randomServerPort + "/medias/media/"+id);
        
        ResponseEntity<Media> response = restTemplate.exchange(uri, HttpMethod.GET, null, Media.class);
        Media media =  response.getBody();
        
        Assert.assertEquals(200, response.getStatusCodeValue());
        Assert.assertEquals(true, media != null);
    }
    
    @Test
    public void addMedia() throws Exception 
    {	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    	int number = (int)(Math.random() * (100 - 0));
    	int duration = (int)(Math.random() * (15 - 1));
    	
    	MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
    	map.add("name", "Media Test "+number);
    	map.add("duration", duration);
    	
    	String name = videoExample.getName();
    	String originalFileName = "city-and-car-3d.mp4";
    	
    	String mimeType = Files.probeContentType(Paths.get(getClass().getResource("dev/uedercardoso/apivideos/city-and-car-3d.mp4").toURI()));     
	
    	//Converter file to multipartfile
    	FileInputStream input = new FileInputStream(videoExample);
    	MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, mimeType, IOUtils.toByteArray(input));

    	MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("video")
                .filename(originalFileName)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        fileMap.add(HttpHeaders.CONTENT_TYPE, mimeType);
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(multipartFile.getBytes(), fileMap);
        
    	map.add("video", fileEntity);
    	
    	HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
    	URI uri = new URI(DEFAULT_URL + ":" + randomServerPort + "/medias");
    	ResponseEntity<Void> response = restTemplate.postForEntity( uri, request , Void.class );
    	
        Assert.assertEquals(201, response.getStatusCodeValue());
        
    }
    
    @Test
    public void updateMedia() throws Exception 
    {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	Media media = null;
    	Optional<Media> op = mediaRepository.findById(15);
    	if(op.isPresent()) {
    		media = op.get();
    		media.setName(media.getName()+" - Alterado");
    	}
    	
    	String url = DEFAULT_URL + ":" + randomServerPort + "/medias";
        Map<String, String> param = new HashMap<String, String>();
        if(media.getId() != null)
        	param.put("id", Long.toString(media.getId()));
        
        HttpEntity<Media> requestEntity = new HttpEntity<Media>(media, headers);
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class, param);

        Assert.assertEquals(200, response.getStatusCodeValue());
        
    }

    @Test
    public void removeMedia() throws Exception 
    {
    	this.restTemplate = new RestTemplate();
    	Integer id = 15;
    	URI uri = new URI(DEFAULT_URL + ":" + randomServerPort + "/medias/"+id);
        
        ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, null, Void.class);
        
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
    
}