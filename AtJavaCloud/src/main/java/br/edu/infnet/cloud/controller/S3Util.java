package br.edu.infnet.cloud.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3Util {
	
    private static final String NOMEBUCKET = "firstinfnetbucket";
    
	private static final String key = "AKIA2BH2JQ26MQHBIHNJ";

	private static final String secret = "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";
	
    public static void excluirObjeto(String arquivoNome) {
        
    	System.out.println(arquivoNome);
        
    	try {
            AmazonS3 s3 = configurar();
            s3.deleteObject(NOMEBUCKET, "imagens/" + arquivoNome);
        } 
    	
    	catch (Exception ex) {
            
    		System.out.println(ex.getMessage());;
        }
    }

    public static void downloadObjeto(String fotoUrl) {
        
    	System.out.println(fotoUrl);
        AmazonS3 s3 = configurar();
        S3Object s3Object = s3.getObject(NOMEBUCKET, "imagens/"+fotoUrl);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        
        System.out.println(s3Object.getBucketName());
        
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("C:\\Users\\bruno\\Downloads\\"+fotoUrl+".png"));
        } 
        
        catch (IOException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    public static void uploadObjeto(File file,String arquivoNome) {
        AmazonS3 s3 = configurar();

        try {
            s3.putObject(NOMEBUCKET,"imagens/"+ arquivoNome,file);
            
            System.out.println("Upload realizado com sucesso.");
        }
        
        catch (AmazonS3Exception ex) {
            
        	System.out.println(ex.getMessage());
        }
    }

    public static AmazonS3 configurar() {
        AWSCredentials credentials = new BasicAWSCredentials(key,secret);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        
        return s3;
    }
}