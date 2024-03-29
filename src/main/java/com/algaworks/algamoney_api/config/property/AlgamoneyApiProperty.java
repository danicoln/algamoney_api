package com.algaworks.algamoney_api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Aula: 7.2. Profiles do Spring
 * */
@Component
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private String originPermitida = "http://localhost:8000";

    private final Seguranca seguranca = new Seguranca();

    private final S3 s3 = new S3();

    private final Mail mail = new Mail();

    public String getOriginPermitida() {
        return originPermitida;
    }

    public void setOriginPermitida(String originPermitida) {
        this.originPermitida = originPermitida;
    }

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public S3 getS3() {
        return s3;
    }


    public static class S3 {

        private String accessKeyId;

        private String secretAccessKey;

        private String bucket = "aw-algamoney-arquivos";

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getSecretAccessKey() {
            return secretAccessKey;
        }

        public void setSecretAccessKey(String secretAccessKey) {
            this.secretAccessKey = secretAccessKey;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

    }

    /**
     * Agrupar por temas semelhantes
     * */
    public static class Seguranca {

        // atributo referente a aula 7.2 Profiles do Spring
        private boolean enableHttps;
        private List<String> redirectsPermitidos;
        private String authServerUrl;

        public boolean isEnableHttps(){
            return enableHttps;
        }

        public void setEnableHttps(boolean enableHttps) {
            this.enableHttps = enableHttps;
        }

        public List<String> getRedirectsPermitidos() {
            return redirectsPermitidos;
        }

        public void setRedirectsPermitidos(List<String> redirectsPermitidos) {
            this.redirectsPermitidos = redirectsPermitidos;
        }

        public String getAuthServerUrl() {
            return authServerUrl;
        }

        public void setAuthServerUrl(String authServerUrl) {
            this.authServerUrl = authServerUrl;
        }


    }

    public static class Mail {

        private String host;

        private Integer port;

        private String username;

        private String password;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}