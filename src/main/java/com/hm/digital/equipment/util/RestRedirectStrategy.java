package com.hm.digital.equipment.util;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * 功能简介：重定向策略,GET/POST/PUT/DELTE 都支持重定向
 * 功能详解：
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class RestRedirectStrategy extends DefaultRedirectStrategy {

  /**
   * Redirect methods
   */
  private static final String[] REDIRECT_METHODS = new String[]{
      HttpGet.METHOD_NAME,
      HttpPut.METHOD_NAME,
      HttpDelete.METHOD_NAME,
      HttpPost.METHOD_NAME
  };

  @Override
  protected boolean isRedirectable(final String method) {
    for (final String m : REDIRECT_METHODS) {
      if (m.equalsIgnoreCase(method)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public HttpUriRequest getRedirect(final HttpRequest request, final HttpResponse response, final HttpContext context)
      throws ProtocolException {
    final URI uri = getLocationURI(request, response, context);
    final String method = request.getRequestLine().getMethod();
    if (method.equalsIgnoreCase(HttpGet.METHOD_NAME)) {
      return new HttpGet(uri);
    } else if (method.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
      HttpPost post = new HttpPost(uri);
      if (request instanceof HttpEntityEnclosingRequest) {
        HttpEntity oldEntity = ((HttpEntityEnclosingRequest) request).getEntity();
        post.setEntity(oldEntity);
      }
      return post;
    } else if (method.equalsIgnoreCase(HttpPut.METHOD_NAME)) {
      HttpPut put = new HttpPut(uri);
      if (request instanceof HttpEntityEnclosingRequest) {
        HttpEntity oldEntity = ((HttpEntityEnclosingRequest) request).getEntity();
        put.setEntity(oldEntity);
      }
      return put;
    } else if (method.equalsIgnoreCase(HttpDelete.METHOD_NAME)) {
      return new HttpDelete(uri);
    } else {
      final int status = response.getStatusLine().getStatusCode();
      if (status == HttpStatus.SC_TEMPORARY_REDIRECT) {
        return RequestBuilder.copy(request).setUri(uri).build();
      } else {
        return new HttpGet(uri);
      }
    }
  }
}
