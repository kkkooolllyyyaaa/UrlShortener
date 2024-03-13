package com.tsypk.urlshortener;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "Request object for POST method")
public class UrlLongRequest {

    @Schema(required = true, description = "Url to convert to short")
    private String longUrl;

    @Schema(description = "Expiration datetime of url")
    private Date expiresDate;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }
}
