# Calendar Service
- *Marked fields are required, others are optional.
- The database is designed in a minimalistic way, only the required fields for the scope of assignment are added and rest ignored. (Like description or location of the events etc.)
- All timestamp fields are exchanged in ISO format.

## Endpoints

### Fetch Events: `GET /user/{User Id}/events`
Returns all the events for each user based on from and to filters

### Create Events: `POST /events`
Create events and the enpoint accepts an array of events so that multiple events can be created using the single API call.

**Request Body**
```
{
    *"events": [ Event ]
}
```

### Create User: `POST /users`
Create user

**Request Body**
```
{
    *"user": {
        *"email": String
        *"name": String
    }
}
```


<!-- ### Busy Slot: `POST /user/slot`
Marks the time between from and to as busy for user

**Request Body**
```
{
    *"user_id": User Id,
    *"from": Timestamp,
    *"to": Timestamp
}
``` -->

### Fetch all conflicting events: `GET /user/{User Id}/conflicts`
Returns all conflicting events for a user

**Response Body**
```
{
    "events": [
        {
            "event": Event Id,
            "conflicting_with": Event Ids
        }
    ]
}
```

### Get Available Slots: `GET /user/available_slots`
Returns upto 5 nearest slot with minimum of `minutes` duration

**Request Body**
```
{
    *"user_ids": [ User Id ],
    *"minutes": Int
}
```

**Response Body**
```
[
        {
            "from": Timestamp,
            "to" Timestamp
        }
]
```


# Databse Schema
## User
- User Id: UUID (PK)
- Email Id: String (Unique)
- Name: String

## Event
- Event Id: UUID (PK)
- Title: String
- Start Time: Timestamp (Non Null)
- End Time: Timestamp (Non Null)

## Attendee
- Event Id: UUID (FK Event.Event Id, Non Null)
- User Id: UUID (FK User.User Id, Non Null)
- Status: Enum (Tentative, Accepted, Rejected, NoReply) (Non Null)
- PK: Event Id, User Id