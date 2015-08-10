'''
Created on Aug 9, 2015

@author: Charlie
'''
import endpoints
import protorpc

from models import Profile
import models
import main

@endpoints.api(name="pickupsports", version="v1", description="Pickup Sports API")
class PickupSportsApi(protorpc.remote.Service):
    """ API for the CRUD methods """ 
    # Methods will be either methods (returns a single object) or query methods (return a collection)
    
    @Profile.method(name="profile.insert", path="pickupsports/profile/insert", http_method="POST")
    def profile_insert(self, request):
        """ insert or update profile """
        if request.from_datastore:
            my_profile = request
        else:
            my_profile = Profile(parent = main.PARENT_PROFILE_KEY, first_name=request.first_name, last_name=request.last_name,
                               email=request.email, phone_number=request.phone_number, friends=request.friends)
        my_profile.put()
        return my_profile
    
    @Profile.method(name="profile.add_friend", path="pickupsports/profile/add_friend", http_method="POST")
    def profile_add_friend(self, request):
        """ add friend to profile """
        if not request.from_datastore:
            raise endpoints.NotFoundException("profile cannot be found")
        my_profile = request
        my_profile.friends = request.friends
        my_profile.put()
        return my_profile
    
    @Profile.query_method(query_fields = ("limit", "order", "pageToken"),name="profile.list", path="pickupsports/profile/list", http_method="GET")
    def profile_list(self, query):
        """ get all the profiles """
        #we could add filters
        return query
    
    @Profile.method(request_fields=("entityKey",), name="profile.delete", path="pickupsports/profile/delete/{entityKey}", http_method="DELETE")
    def profile_delete(self, request):
        """ Delete a profile if its there """
        if not request.from_datastore:
            raise endpoints.NotFoundException("profile to be deleted not found")
         
        request.key.delete()
        return Profile(quote="deleted")
    
app = endpoints.api_server([PickupSportsApi], restricted = False)    