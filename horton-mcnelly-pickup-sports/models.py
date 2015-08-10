'''
Created on Aug 8, 2015

@author: Charlie
'''
from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext import ndb

class Profile(EndpointsModel):
    _message_fields_schema = ("entityKey", "first_name", "last_name", "email", "phone_number", "friends")
    first_name=ndb.StringProperty()
    last_name=ndb.StringProperty()
    email=ndb.StringProperty()
    phone_number=ndb.StringProperty()
    friends=ndb.KeyProperty(kind="Profile", repeated=True)
    
class Sport(EndpointsModel):
    _message_fields_schema = ("entityKey", "name", "description", "date", "location", "availability")
    name=ndb.StringProperty()
    description=ndb.StringProperty()
    date=ndb.DateTimeProperty(auto_now=True)
    location=ndb.StringProperty()
    availability=ndb.StringProperty()