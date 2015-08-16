'''
Created on Aug 8, 2015

@author: Charlie
'''
from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext import ndb

class Profile(EndpointsModel):
    _message_fields_schema = ("entityKey", "first_name", "last_name", "phone_number", "friends", "events")
    first_name=ndb.StringProperty()
    last_name=ndb.StringProperty()
    phone_number=ndb.StringProperty()
    friends=ndb.KeyProperty(kind="Profile", repeated=True)
    events=ndb.KeyProperty(kind="Sport", repeated=True)
    
class Sport(EndpointsModel):
    _message_fields_schema = ("entityKey", "name", "description", "date", "location", "availability", "latLon")
    name=ndb.StringProperty()
    description=ndb.StringProperty()
    date=ndb.StringProperty()
    location=ndb.StringProperty()
    availability=ndb.StringProperty()
    latLon=ndb.StringProperty()
