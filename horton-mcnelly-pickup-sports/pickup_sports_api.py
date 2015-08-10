'''
Created on Aug 9, 2015

@author: Charlie
'''
import endpoints
import protorpc

from models import Profile
import models

@endpoints.api(name="profiles", version="v1", description="Pickup Sports API")
class ProfilesApi(protorpc.remote.Service):
    """ API for the CRUD methods """ 
    # Methods will be either methods (returns a single object) or query methods (return a collection)
    
    @Profile.method(name="profile.insert", path="profile/insert", http_method="POST")
    def profile_insert(self, request):
        """ insert or update profile """
        if request.from_datastore:
            my_profile = request
        else:
            my_profile = Profile(parent = models.PARENT_PROFILE_KEY, first_name=request.first_name, last_name=request.last_name,
                               email=request.email, phone_number=request.phone_number)
        my_profile.put()
        return my_profile
    
    @Profile.method(name="profile.add_friend", path="profile/add_friend", http_method="POST")
    def profile_add_friend(self, request):
        """ add friend to profile """
        if not request.from_datastore:
            raise endpoints.NotFoundException("profile cannot be found")
        my_profile = request
        my_profile.friends = request.friends
        my_profile.put()
        return my_profile
    
app = endpoints.api_server([ProfilesApi], restricted = False)
        