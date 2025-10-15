package com.imobly.imobly.configuration.upload

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UploadFIleConfig {
    fun credentials(): AWSCredentials? {
        return BasicAWSCredentials(System.getenv("ACCESS_KEY"), System.getenv("SECRECT_KEY"))
    }

    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials()))
            .withRegion(Regions.US_EAST_1)
            .build()
    }
}