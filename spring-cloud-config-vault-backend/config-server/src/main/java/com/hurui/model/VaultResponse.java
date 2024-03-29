package com.hurui.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultResponse {

    @JsonAlias(value = {"request_id"})
    private String requestId;

    @JsonAlias(value = {"lease_id"})
    private String leaseId;

    private Boolean renewable;

    @JsonAlias(value = {"lease_duration"})
    private Long leaseDuration;

    private Data data;

    @JsonAlias(value = {"wrap_info"})
    private String wrapInfo;

    private String warnings;

    private String auth;

    public VaultResponse() {
    }

    public VaultResponse(String requestId, String leaseId, Boolean renewable, Long leaseDuration, Data data, String wrapInfo, String warnings, String auth) {
        this.requestId = requestId;
        this.leaseId = leaseId;
        this.renewable = renewable;
        this.leaseDuration = leaseDuration;
        this.data = data;
        this.wrapInfo = wrapInfo;
        this.warnings = warnings;
        this.auth = auth;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public Boolean getRenewable() {
        return renewable;
    }

    public void setRenewable(Boolean renewable) {
        this.renewable = renewable;
    }

    public Long getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(Long leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getWrapInfo() {
        return wrapInfo;
    }

    public void setWrapInfo(String wrapInfo) {
        this.wrapInfo = wrapInfo;
    }

    public String getWarnings() {
        return warnings;
    }

    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
