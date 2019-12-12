package com.swivel.assignment.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organization {

    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("external_id")
    @Expose
    private String externalId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("domain_names")
    @Expose
    private List<String> domainNames = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("shared_tickets")
    @Expose
    private Boolean sharedTickets;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDomainNames() {
        return domainNames;
    }

    public void setDomainNames(List<String> domainNames) {
        this.domainNames = domainNames;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean getSharedTickets() {
        return sharedTickets;
    }

    public void setSharedTickets(Boolean sharedTickets) {
        this.sharedTickets = sharedTickets;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
