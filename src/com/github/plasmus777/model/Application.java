package com.github.plasmus777.model;

import com.github.plasmus777.model.user.Publisher;

public class Application {

    private long id;
    private String title;
    private String description;
    private float version;
    private boolean verified;
    private Publisher publisher;
    private Category[] categories;
    private String aboutUrl;
    private String developmentUrl;
    private String md5;
    private String sha256;

    public Application(String title, String description, float version, boolean verified,
                       Publisher publisher, Category[] categories, String aboutUrl,
                       String developmentUrl, String md5, String sha256){
        setTitle(title);
        setDescription(description);
        setVersion(version);
        setPublisher(publisher);
        setCategories(categories);
        setAboutUrl(aboutUrl);
        setDevelopmentUrl(developmentUrl);
        setMd5(md5);
        setSha256(sha256);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Publisher getPublisher(){
        return publisher;
    }

    public void setPublisher(Publisher publisher){
        this.publisher = publisher;
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getDevelopmentUrl() {
        return developmentUrl;
    }

    public void setDevelopmentUrl(String developmentUrl) {
        this.developmentUrl = developmentUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @Override
    public String toString() {
        return "Título: " + getTitle() + ", id = " + getId() +
                "\nDescrição: " + getDescription() +
                "\nVersão: " + getVersion() +
                "\n" + (isVerified() ? "Verificado" : "Não Verificado") +
                "\nEditor: " + publisher.getAgencyName() +
                "\nCategorias: " + categories.toString() +
                "\nSobre o projeto: " + getAboutUrl() +
                "\nDesenvolvimento: " + getDevelopmentUrl() +
                "\nMD5: " + getMd5() +
                "\nSHA-256: " + getSha256();
    }

    /*
    private long id;
    private String title;
    private String description;
    private float version;
    private boolean verified;
    private Publisher publisher;
    private Category[] categories;
    private String aboutUrl;
    private String developmentUrl;
    private String md5;
    private String sha256;
    */

    @Override
    public boolean equals(Object obj) {
        //Object is not an Application
        if(!(obj instanceof Application)) return false;

        //Verify class attributes
        if(this.getId() <= 0 || this.getTitle() == null || this.getDescription() == null || this.getVersion() <= 0 ||
                this.getPublisher() == null || this.getCategories() == null || this.getAboutUrl() == null ||
                this.getDevelopmentUrl() == null || this.getMd5() == null || this.getSha256() == null) return false;

        //Verify obj (Application) attributes
        Application application = (Application) obj;
        if(application.getId() <= 0 || application.getTitle() == null || application.getDescription() == null ||
                application.getVersion() <= 0 || application.getPublisher() == null || application.getCategories() == null ||
                application.getAboutUrl() == null || application.getDevelopmentUrl() == null || application.getMd5() == null ||
                application.getSha256() == null) return false;

        return (this.getId() == application.getId());
    }
}
