
package com.bahrunnur.monocle.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.bahrunnur.monocle.models.PostList;
import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

@Rest(rootUrl = "http://monocle.io", converters = { GsonHttpMessageConverter.class })
public interface RestClient {

    @Get("/")
    public abstract void main();
    
    @Get("/v1/posts/popular")
    PostList getPopularPost();
    
    @Get("/v1/posts/newest")
    PostList getNewestPost();

}
