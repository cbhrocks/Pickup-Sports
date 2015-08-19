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
 * on 2015-08-19 at 06:35:14 UTC 
 * Modify at your own risk.
 */

package com.appspot.horton_mcnelly_pickup_sports.pickupsports.model;

/**
 * Model definition for Profile.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the pickupsports. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Profile extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String entityKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> events;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("first_name")
  private java.lang.String firstName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> friends;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("last_name")
  private java.lang.String lastName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("phone_number")
  private java.lang.String phoneNumber;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEntityKey() {
    return entityKey;
  }

  /**
   * @param entityKey entityKey or {@code null} for none
   */
  public Profile setEntityKey(java.lang.String entityKey) {
    this.entityKey = entityKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getEvents() {
    return events;
  }

  /**
   * @param events events or {@code null} for none
   */
  public Profile setEvents(java.util.List<java.lang.String> events) {
    this.events = events;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName firstName or {@code null} for none
   */
  public Profile setFirstName(java.lang.String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getFriends() {
    return friends;
  }

  /**
   * @param friends friends or {@code null} for none
   */
  public Profile setFriends(java.util.List<java.lang.String> friends) {
    this.friends = friends;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLastName() {
    return lastName;
  }

  /**
   * @param lastName lastName or {@code null} for none
   */
  public Profile setLastName(java.lang.String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber phoneNumber or {@code null} for none
   */
  public Profile setPhoneNumber(java.lang.String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public Profile set(String fieldName, Object value) {
    return (Profile) super.set(fieldName, value);
  }

  @Override
  public Profile clone() {
    return (Profile) super.clone();
  }

}
