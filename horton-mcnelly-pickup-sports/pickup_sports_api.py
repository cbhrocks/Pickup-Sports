'''
Created on Aug 9, 2015

@author: Charlie
'''
import endpoints
import protorpc

from models import Profile, Sport
from google.appengine.ext import ndb
import main

WEB_CLIENT_ID=""
ANDROID_CLIENT_ID=""
IOS_CLIENT_ID=""

@endpoints.api(name="pickupsports", version="v1", description="Pickup Sports API", audiences=[WEB_CLIENT_ID], 
               allowed_client_ids=[endpoints.API_EXPLORER_CLIENT_ID, WEB_CLIENT_ID, ANDROID_CLIENT_ID, IOS_CLIENT_ID])
class PickupSportsApi(protorpc.remote.Service):
    """ API for the CRUD methods """ 
    # Methods will be either methods (returns a single object) or query methods (return a collection)
    
    @Profile.method(name="profile.insert", path="pickupsports/profile/insert", http_method="POST")
    def profile_insert(self, request):
        """ insert or update profile """
        if request.from_datastore:
            my_profile_with_parent = request
        else:
            my_profile_with_parent = Profile(parent = main.get_parent_key(endpoints.get_current_user()), first_name=request.first_name, last_name=request.last_name,
                               phone_number=request.phone_number, friends=request.friends)
        my_profile_with_parent.put()
        return my_profile_with_parent
    
#     @Profile.method(user_required=True, name="profile.add_friend", path="pickupsports/profile/add_friend", http_method="POST")
#     def profile_add_friend(self, request):
#         """ add friend to profile """
#         user = endpoints.get_current_user()
#         
#         if not request.from_datastore:
#             raise endpoints.NotFoundException("profile cannot be found")
#         my_profile = request
#         my_profile.friends = request.friends
#         my_profile.put()
#         return my_profile
    
    @Profile.query_method(user_required = True, name="profile.get", path="pickupsports/profile/get", http_method="GET")
    def profile_get(self, query):
        """ get the current user's profile """
        user = endpoints.get_current_user()
        profile=Profile.query(ancestor=main.get_parent_key(user)).order(Profile.first_name)
        return profile
    
    @Profile.query_method(user_required=True, query_fields = ("limit", "order", "pageToken"),name="profile.list", path="pickupsports/profile/list", http_method="GET")
    def profile_list(self, query):
        """ get all the profiles """
        #we could add filters
        return query
    
    @Profile.query_method(user_required=True, query_fields=("limit", "pageToken"), name="profile.friends", path="pickupsports/profile/friends", http_method="GET")
    def profile_friend_list(self, query):
        """ get all the profiles in friends list """
        user = endpoints.get_current_user()
        profile=Profile.query(ancestor=main.get_parent_key(user))
        
        friends = ndb.get_multi(profile)
        return friends
    
    @Profile.method(user_required=True, request_fields=("entityKey",), name="profile.delete", path="pickupsports/profile/delete/{entityKey}", http_method="DELETE")
    def profile_delete(self, request):
        """ Delete a profile if its there """
        if not request.from_datastore:
            raise endpoints.NotFoundException("profile to be deleted not found")
         
        request.key.delete()
        return Profile(quote="deleted")
    

    @Sport.query_method(query_fields=("limit", "order", "pageToken"), name="sport.list", path="pickupsports/sport/list", http_method="GET")
    def sport_list(self, query):
        return query
    
    
    @Sport.method(name="sport.insert", path="pickupsports/sport/insert", http_method="POST")
    def sport_insert(self, request):
        if request.from_datastore:
            newSport = request
        else:
            newSport = Sport(parent=main.PARENT_PROFILE_KEY, name=request.name, description=request.description, availability=request.availability, location=request.location, latLon=request.latLon, date=request.date)
        newSport.put()
        return newSport
    
    
    @Sport.method(request_fields=("entityKey",), name="sport.delete", path="pickupsports/sport/delete/{entityKey}", http_method="DELETE")
    def sport_delete(self, request):
        if not request.from_datastore:
            raise endpoints.NotFoundException("sport not found")
        request.key.delete()
        
        return Sport(name="deleted")
    
    
app = endpoints.api_server([PickupSportsApi], restricted = False)    