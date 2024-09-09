package com.github.plasmus777.model.application;

import com.github.plasmus777.model.user.Publisher;

import java.util.Arrays;

public class Application implements Comparable<Application>{

    private long id;
    private String title;
    private String description;
    private float version;
    private Publisher publisher;
    private Category[] categories;
    private String aboutUrl;
    private String developmentUrl;
    private String md5;
    private String sha256;

    public Application(String title, String description, float version,
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
        return publisher.isVerified();
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
                "\nCategorias: " + Arrays.toString(categories) +
                "\nSobre o projeto: " + getAboutUrl() +
                "\nDesenvolvimento: " + getDevelopmentUrl() +
                "\nMD5: " + getMd5() +
                "\nSHA-256: " + getSha256();
    }

    @Override
    public boolean equals(Object obj) {
        //Object is not an Application
        if(!(obj instanceof Application)) return false;

        //Verify class attributes
        if(this.getId() <= 0 || this.getTitle() == null || this.getDescription() == null || this.getPublisher() == null ||
                this.getCategories() == null || this.getAboutUrl() == null || this.getDevelopmentUrl() == null ||
                this.getMd5() == null || this.getSha256() == null)
            return false;

        //Verify obj (Application) attributes
        Application application = (Application) obj;
        if(application.getId() <= 0 || application.getTitle() == null || application.getDescription() == null ||
                application.getPublisher() == null || application.getCategories() == null || application.getAboutUrl() == null ||
                application.getDevelopmentUrl() == null || application.getMd5() == null || application.getSha256() == null)
            return false;

        //Ignore id and version, since the id may be different for creating the same application and the version can be modified as well
        return (this.getTitle().equals(application.getTitle()) && this.getDescription().equals(application.getDescription()) &&
                this.isVerified() == application.isVerified() && this.getPublisher().equals(application.getPublisher()) &&
                Arrays.equals(this.getCategories(), application.getCategories()) && this.getAboutUrl().equals(application.getAboutUrl()) &&
                this.getDevelopmentUrl().equals(application.getDevelopmentUrl()) && this.getMd5().equals(application.getMd5()) &&
                this.getSha256().equals(application.getSha256()));
    }

    @Override
    public int compareTo(Application application) {
        //Order by application title
        int compareTitle = this.getTitle().compareTo(application.getTitle());

        //Same title - cannot be compared
        if(compareTitle == 0){
            //Order by the publisher's agency names
            int comparePublisher = this.getPublisher().getAgencyName().compareTo(application.getPublisher().getAgencyName());

            //Same publisher - cannot be compared
            if(comparePublisher == 0){
                //Order by application version
                return Float.compare(this.getVersion(), application.getVersion());
            }

            return comparePublisher;
        }

        return compareTitle;
    }
}
