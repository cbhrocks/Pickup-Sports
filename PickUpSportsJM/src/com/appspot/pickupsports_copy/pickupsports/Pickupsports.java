/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-08-16 at 05:21:55 UTC 
 * Modify at your own risk.
 */

package com.appspot.pickupsports_copy.pickupsports;

/**
 * Service definition for Pickupsports (v1).
 *
 * <p>
 * Pickup Sports API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link PickupsportsRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Pickupsports extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the pickupsports library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://pickupsports-copy.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "pickupsports/v1/pickupsports/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Pickupsports(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Pickupsports(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the Profile collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Pickupsports pickupsports = new Pickupsports(...);}
   *   {@code Pickupsports.Profile.List request = pickupsports.profile().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Profile profile() {
    return new Profile();
  }

  /**
   * The "profile" collection of methods.
   */
  public class Profile {

    /**
     * add friend to profile
     *
     * Create a request for the method "profile.add_friend".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link AddFriend#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Profile}
     * @return the request
     */
    public AddFriend addFriend(com.appspot.pickupsports_copy.pickupsports.model.Profile content) throws java.io.IOException {
      AddFriend result = new AddFriend(content);
      initialize(result);
      return result;
    }

    public class AddFriend extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.Profile> {

      private static final String REST_PATH = "profile/add_friend";

      /**
       * add friend to profile
       *
       * Create a request for the method "profile.add_friend".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link AddFriend#execute()} method to invoke the remote
       * operation. <p> {@link
       * AddFriend#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Profile}
       * @since 1.13
       */
      protected AddFriend(com.appspot.pickupsports_copy.pickupsports.model.Profile content) {
        super(Pickupsports.this, "POST", REST_PATH, content, com.appspot.pickupsports_copy.pickupsports.model.Profile.class);
      }

      @Override
      public AddFriend setAlt(java.lang.String alt) {
        return (AddFriend) super.setAlt(alt);
      }

      @Override
      public AddFriend setFields(java.lang.String fields) {
        return (AddFriend) super.setFields(fields);
      }

      @Override
      public AddFriend setKey(java.lang.String key) {
        return (AddFriend) super.setKey(key);
      }

      @Override
      public AddFriend setOauthToken(java.lang.String oauthToken) {
        return (AddFriend) super.setOauthToken(oauthToken);
      }

      @Override
      public AddFriend setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (AddFriend) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public AddFriend setQuotaUser(java.lang.String quotaUser) {
        return (AddFriend) super.setQuotaUser(quotaUser);
      }

      @Override
      public AddFriend setUserIp(java.lang.String userIp) {
        return (AddFriend) super.setUserIp(userIp);
      }

      @Override
      public AddFriend set(String parameterName, Object value) {
        return (AddFriend) super.set(parameterName, value);
      }
    }
    /**
     * Delete a profile if its there
     *
     * Create a request for the method "profile.delete".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link Delete#execute()} method to invoke the remote operation.
     *
     * @param entityKey
     * @return the request
     */
    public Delete delete(java.lang.String entityKey) throws java.io.IOException {
      Delete result = new Delete(entityKey);
      initialize(result);
      return result;
    }

    public class Delete extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.Profile> {

      private static final String REST_PATH = "profile/delete/{entityKey}";

      /**
       * Delete a profile if its there
       *
       * Create a request for the method "profile.delete".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link Delete#execute()} method to invoke the remote operation.
       * <p> {@link
       * Delete#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param entityKey
       * @since 1.13
       */
      protected Delete(java.lang.String entityKey) {
        super(Pickupsports.this, "DELETE", REST_PATH, null, com.appspot.pickupsports_copy.pickupsports.model.Profile.class);
        this.entityKey = com.google.api.client.util.Preconditions.checkNotNull(entityKey, "Required parameter entityKey must be specified.");
      }

      @Override
      public Delete setAlt(java.lang.String alt) {
        return (Delete) super.setAlt(alt);
      }

      @Override
      public Delete setFields(java.lang.String fields) {
        return (Delete) super.setFields(fields);
      }

      @Override
      public Delete setKey(java.lang.String key) {
        return (Delete) super.setKey(key);
      }

      @Override
      public Delete setOauthToken(java.lang.String oauthToken) {
        return (Delete) super.setOauthToken(oauthToken);
      }

      @Override
      public Delete setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Delete) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Delete setQuotaUser(java.lang.String quotaUser) {
        return (Delete) super.setQuotaUser(quotaUser);
      }

      @Override
      public Delete setUserIp(java.lang.String userIp) {
        return (Delete) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String entityKey;

      /**

       */
      public java.lang.String getEntityKey() {
        return entityKey;
      }

      public Delete setEntityKey(java.lang.String entityKey) {
        this.entityKey = entityKey;
        return this;
      }

      @Override
      public Delete set(String parameterName, Object value) {
        return (Delete) super.set(parameterName, value);
      }
    }
    /**
     * insert or update profile
     *
     * Create a request for the method "profile.insert".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Profile}
     * @return the request
     */
    public Insert insert(com.appspot.pickupsports_copy.pickupsports.model.Profile content) throws java.io.IOException {
      Insert result = new Insert(content);
      initialize(result);
      return result;
    }

    public class Insert extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.Profile> {

      private static final String REST_PATH = "profile/insert";

      /**
       * insert or update profile
       *
       * Create a request for the method "profile.insert".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Profile}
       * @since 1.13
       */
      protected Insert(com.appspot.pickupsports_copy.pickupsports.model.Profile content) {
        super(Pickupsports.this, "POST", REST_PATH, content, com.appspot.pickupsports_copy.pickupsports.model.Profile.class);
      }

      @Override
      public Insert setAlt(java.lang.String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(java.lang.String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(java.lang.String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(java.lang.String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(java.lang.String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(java.lang.String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }
    /**
     * get all the profiles
     *
     * Create a request for the method "profile.list".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.ProfileCollection> {

      private static final String REST_PATH = "profile/list";

      /**
       * get all the profiles
       *
       * Create a request for the method "profile.list".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
       * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(Pickupsports.this, "GET", REST_PATH, null, com.appspot.pickupsports_copy.pickupsports.model.ProfileCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(java.lang.String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(java.lang.String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(java.lang.String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(java.lang.String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(java.lang.String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(java.lang.String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String pageToken;

      /**

       */
      public java.lang.String getPageToken() {
        return pageToken;
      }

      public List setPageToken(java.lang.String pageToken) {
        this.pageToken = pageToken;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.Long limit;

      /**

       */
      public java.lang.Long getLimit() {
        return limit;
      }

      public List setLimit(java.lang.Long limit) {
        this.limit = limit;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.String order;

      /**

       */
      public java.lang.String getOrder() {
        return order;
      }

      public List setOrder(java.lang.String order) {
        this.order = order;
        return this;
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * An accessor for creating requests from the Sport collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Pickupsports pickupsports = new Pickupsports(...);}
   *   {@code Pickupsports.Sport.List request = pickupsports.sport().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Sport sport() {
    return new Sport();
  }

  /**
   * The "sport" collection of methods.
   */
  public class Sport {

    /**
     * Create a request for the method "sport.delete".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link Delete#execute()} method to invoke the remote operation.
     *
     * @param entityKey
     * @return the request
     */
    public Delete delete(java.lang.String entityKey) throws java.io.IOException {
      Delete result = new Delete(entityKey);
      initialize(result);
      return result;
    }

    public class Delete extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.Sport> {

      private static final String REST_PATH = "sport/delete/{entityKey}";

      /**
       * Create a request for the method "sport.delete".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link Delete#execute()} method to invoke the remote operation.
       * <p> {@link
       * Delete#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param entityKey
       * @since 1.13
       */
      protected Delete(java.lang.String entityKey) {
        super(Pickupsports.this, "DELETE", REST_PATH, null, com.appspot.pickupsports_copy.pickupsports.model.Sport.class);
        this.entityKey = com.google.api.client.util.Preconditions.checkNotNull(entityKey, "Required parameter entityKey must be specified.");
      }

      @Override
      public Delete setAlt(java.lang.String alt) {
        return (Delete) super.setAlt(alt);
      }

      @Override
      public Delete setFields(java.lang.String fields) {
        return (Delete) super.setFields(fields);
      }

      @Override
      public Delete setKey(java.lang.String key) {
        return (Delete) super.setKey(key);
      }

      @Override
      public Delete setOauthToken(java.lang.String oauthToken) {
        return (Delete) super.setOauthToken(oauthToken);
      }

      @Override
      public Delete setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Delete) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Delete setQuotaUser(java.lang.String quotaUser) {
        return (Delete) super.setQuotaUser(quotaUser);
      }

      @Override
      public Delete setUserIp(java.lang.String userIp) {
        return (Delete) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String entityKey;

      /**

       */
      public java.lang.String getEntityKey() {
        return entityKey;
      }

      public Delete setEntityKey(java.lang.String entityKey) {
        this.entityKey = entityKey;
        return this;
      }

      @Override
      public Delete set(String parameterName, Object value) {
        return (Delete) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "sport.insert".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Sport}
     * @return the request
     */
    public Insert insert(com.appspot.pickupsports_copy.pickupsports.model.Sport content) throws java.io.IOException {
      Insert result = new Insert(content);
      initialize(result);
      return result;
    }

    public class Insert extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.Sport> {

      private static final String REST_PATH = "sport/insert";

      /**
       * Create a request for the method "sport.insert".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
       * <p> {@link
       * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.appspot.pickupsports_copy.pickupsports.model.Sport}
       * @since 1.13
       */
      protected Insert(com.appspot.pickupsports_copy.pickupsports.model.Sport content) {
        super(Pickupsports.this, "POST", REST_PATH, content, com.appspot.pickupsports_copy.pickupsports.model.Sport.class);
      }

      @Override
      public Insert setAlt(java.lang.String alt) {
        return (Insert) super.setAlt(alt);
      }

      @Override
      public Insert setFields(java.lang.String fields) {
        return (Insert) super.setFields(fields);
      }

      @Override
      public Insert setKey(java.lang.String key) {
        return (Insert) super.setKey(key);
      }

      @Override
      public Insert setOauthToken(java.lang.String oauthToken) {
        return (Insert) super.setOauthToken(oauthToken);
      }

      @Override
      public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Insert) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Insert setQuotaUser(java.lang.String quotaUser) {
        return (Insert) super.setQuotaUser(quotaUser);
      }

      @Override
      public Insert setUserIp(java.lang.String userIp) {
        return (Insert) super.setUserIp(userIp);
      }

      @Override
      public Insert set(String parameterName, Object value) {
        return (Insert) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "sport.list".
     *
     * This request holds the parameters needed by the pickupsports server.  After setting any optional
     * parameters, call the {@link List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends PickupsportsRequest<com.appspot.pickupsports_copy.pickupsports.model.SportCollection> {

      private static final String REST_PATH = "sport/list";

      /**
       * Create a request for the method "sport.list".
       *
       * This request holds the parameters needed by the the pickupsports server.  After setting any
       * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
       * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(Pickupsports.this, "GET", REST_PATH, null, com.appspot.pickupsports_copy.pickupsports.model.SportCollection.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(java.lang.String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(java.lang.String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(java.lang.String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(java.lang.String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(java.lang.String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(java.lang.String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String pageToken;

      /**

       */
      public java.lang.String getPageToken() {
        return pageToken;
      }

      public List setPageToken(java.lang.String pageToken) {
        this.pageToken = pageToken;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.Long limit;

      /**

       */
      public java.lang.Long getLimit() {
        return limit;
      }

      public List setLimit(java.lang.Long limit) {
        this.limit = limit;
        return this;
      }

      @com.google.api.client.util.Key
      private java.lang.String order;

      /**

       */
      public java.lang.String getOrder() {
        return order;
      }

      public List setOrder(java.lang.String order) {
        this.order = order;
        return this;
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link Pickupsports}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Pickupsports}. */
    @Override
    public Pickupsports build() {
      return new Pickupsports(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link PickupsportsRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPickupsportsRequestInitializer(
        PickupsportsRequestInitializer pickupsportsRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(pickupsportsRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
